package es.javiercarrasco.myfirstgooglemaps

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import es.javiercarrasco.myfirstgooglemaps.databinding.ActivityMapsBinding
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityMapsBinding

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private var lastMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Se habilitan los botones del zoom.
        mMap.uiSettings.isZoomControlsEnabled = true
        // Se habilita la brújula, solo aparecerá cuando giremos el mapa.
        mMap.uiSettings.isCompassEnabled = true

        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)

        // Click sobre el mapa.
        mMap.setOnMapClickListener {
            Log.d("onMapClickListener", it.toString())
            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }

        // LongClick sobre el mapa.
        mMap.setOnMapLongClickListener {
            Log.d("onMapLongClickListener", it.toString())
            placeMarkerOnMap(it)
            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }

        mMap.setOnMarkerClickListener(this)

        configMap()

        // Botón de estilos de mapa.
        binding.button.setOnClickListener {
            // Se selecciona el tipo de mapa.
            when (mMap.mapType) {
                GoogleMap.MAP_TYPE_NORMAL -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                    binding.button.text = "Híbrido"
                }
                GoogleMap.MAP_TYPE_HYBRID -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                    binding.button.text = "Satélite"
                }
                GoogleMap.MAP_TYPE_SATELLITE -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                    binding.button.text = "Terreno"
                }
                GoogleMap.MAP_TYPE_TERRAIN -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                    binding.button.text = "Normal"
                }
            }
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Log.d("onMyLocationButtonClick", "Click sobre el botón ubicación!!")
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Log.d("onMyLocationClick", "Ubicación pulsada!!")
    }

    /**
     * Se dispara cuando se hace click sobre una marca en el mapa,
     * se implementa al heredar de GoogleMap.OnMarkerClickListener.
     */
    override fun onMarkerClick(p0: Marker?): Boolean {
        Log.d("onMarkerClick", "Click sobre una marca")
        p0!!.remove()

        return false
    }

    /**
     * Se comprueba los permisos de ubicación y se recoloca el mapa
     * según la ubicación.
     */
    private fun configMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // Se añade la marca en la ubicación que nos encontramos.
        mMap.isMyLocationEnabled = true

        // Se establece el tipo de mapa.
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        binding.button.text = "Normal"

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Se va la última ubicación conocida, en algunos casos puede ser null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f)
                )
            }
        }
    }

    /**
     * Método para añadir la marca en la localización indicada.
     */
    private fun placeMarkerOnMap(location: LatLng) {
        // Se crea un objeto MarkerOptions para configurar la marca.
        val markerOptions = MarkerOptions().position(location)

        // Cambia la marca de color.
        markerOptions.icon(
            BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_VIOLET
            )
        )

        val titleStr = getAddress(location)
        Log.d("titleStr", titleStr)
        markerOptions.title(titleStr)

        // Se elimina el último marcador.
        if (lastMarker != null)
            lastMarker!!.remove()

        // Se añade la marca al mapa.
        lastMarker = mMap.addMarker(markerOptions)
    }

    /**
     * Este método permite obtener la dirección de una marca.
     */
    private fun getAddress(latLng: LatLng): String {
        // Se instancian las variables necesarias.
        val geocoder = Geocoder(this)
        // Se pueden obtener más de una dirección de un mismo punto.
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // Se obtiene la información del punto concreto.
            addresses = geocoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                1
            )

            // Se comprueba que la dirección no sea nula o vacía.
            if (null != addresses && addresses.isNotEmpty()) {
                // Nos quedamos con la primera posición.
                address = addresses[0]

                // Se comprueba que la dirección tenga o no más de una línea.
                if (address.maxAddressLineIndex > 0) {
                    for (i in 0 until address.maxAddressLineIndex) {
                        addressText += if (i == 0) address.getAddressLine(i)
                        else "\n" + address.getAddressLine(i)
                    }
                } else { // Acción más habitual.
                    addressText += address.thoroughfare + ", " +
                            address.subThoroughfare + "\n"
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }
}