package es.javiercarrasco.myfirstheremap

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapviewlite.*
import es.javiercarrasco.myfirstheremap.GestionPermisos.ResultListener
import es.javiercarrasco.myfirstheremap.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapViewLite: MapViewLite
    private lateinit var gestionPermisos: GestionPermisos

    private lateinit var locationManager: LocationManager
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private var mapMarker: MapMarker? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            p0.lastLocation
            if (mapMarker == null) {
                mapMarker = MapMarker(
                    GeoCoordinates(
                        p0.lastLocation.latitude,
                        p0.lastLocation.longitude
                    )
                )
            }

            onLocationChanged(p0.lastLocation)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se instancia MapViewLite desde el layout.
        mapViewLite = binding.mapView
        mapViewLite.onCreate(savedInstanceState)

        controlPermisos()
    }

    private fun controlPermisos() {
        gestionPermisos = GestionPermisos(this)
        gestionPermisos.solicitarPermisos(object : ResultListener {
            override fun permissionsGranted() {
                Log.i(TAG, "Permisos concedidos por el usuario.")

                localizacion()
                loadMapScene()
            }

            override fun permissionsDenied() {
                Log.e(TAG, "Permisos denegados por el usuario.")
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        gestionPermisos.onRequestPermissionsResult(requestCode, grantResults)
    }

    // Método encargado de cargar el estilo y la escena,
    // también controla los errores.
    @SuppressLint("MissingPermission")
    private fun loadMapScene() {
        mapViewLite.mapScene.loadScene(
            MapStyle.NORMAL_DAY, MapScene.LoadSceneCallback {
                if (it == null) {
                    mapViewLite.getCamera().setTarget(
                        GeoCoordinates(38.35782, -0.5425641)
                        //GeoCoordinates(lastLocation.latitude, lastLocation.longitude)
                    )
                    mapViewLite.getCamera().setZoomLevel(11.0)

                } else {
                    Log.d("ERROR", "onLoadScene failed: " + it.toString())
                }
            })
    }

    @SuppressLint("MissingPermission")
    private fun localizacion() {
        // Se comprueba si está activado el GPS o el proveedor de red.
        locationManager = getSystemService(Context.LOCATION_SERVICE)
                as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(
                applicationContext, "GPS no activado",
                Toast.LENGTH_SHORT
            ).show()
        } else {

            locationRequest = LocationRequest.create().apply {
                interval = 100
                fastestInterval = 500
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            // Se crea el objeto LocationSettingsRequest usando la respuesta
            // de localización.
            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(locationRequest)
            val locationSettingsRequest = builder.build()

            val settingsClient = LocationServices.getSettingsClient(this)
            settingsClient.checkLocationSettings(locationSettingsRequest)

            fusedLocationClient = LocationServices
                .getFusedLocationProviderClient(this)

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()!!
            )
        }
    }

    fun onLocationChanged(location: Location) {
        // Se actualiza la localización.
        if (mapMarker != null) {
            mapViewLite.mapScene.removeMapMarker(
                mapMarker!!
            )
        }

        lastLocation = location
        val geoCoordenadas =
            GeoCoordinates(location.latitude, location.longitude)

        Log.i(TAG, "${geoCoordenadas.latitude}, ${geoCoordenadas.longitude}")

        // Se crea la marca de posicionamiento.
        mapMarker!!.coordinates = geoCoordenadas
        val mapImage = MapImageFactory.fromResource(
            this.getResources(),
            R.drawable.marcador_posicion
        )
        val mapMarkerImageStyle = MapMarkerImageStyle()

        mapMarkerImageStyle.scale = 0.15F // Tamaño del marcador.
        mapMarker!!.addImage(mapImage, mapMarkerImageStyle)
        mapViewLite.mapScene.addMapMarker(mapMarker!!)
    }

    override fun onResume() {
        super.onResume()
        mapViewLite.onResume()
        // Se reinicia la localizción.
        localizacion()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapViewLite.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapViewLite.onDestroy()
        // Se detiene la localización.
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        mapViewLite.onPause()
        // Se detiene la localización.
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}