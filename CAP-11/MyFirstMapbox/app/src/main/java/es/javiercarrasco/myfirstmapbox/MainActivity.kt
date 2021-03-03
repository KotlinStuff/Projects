package es.javiercarrasco.myfirstmapbox

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.android.gestures.MoveGestureDetector

import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.Marker
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import es.javiercarrasco.myfirstmapbox.databinding.ActivityMainBinding

@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivity : AppCompatActivity(), OnMapReadyCallback, PermissionsListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapboxMap: MapboxMap
    private var permissionsManager: PermissionsManager = PermissionsManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mapbox Access token
        Mapbox.getInstance(
            this,
            getString(R.string.mapbox_access_token)
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        /*binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
                // Mapa configurado y estilo cargado.
                // Ya se puede añadir información o hacer otros ajustes.

                val uiSettings = mapboxMap.uiSettings
                uiSettings.isLogoEnabled = false
                uiSettings.isAttributionEnabled = false

                // Únicamente muestra la brújula si se está mirando al norte.
                uiSettings.setCompassFadeFacingNorth(true)

                for (singleLayer in it.layers) {
                    Log.d("CAPAS", "Layer id = " + singleLayer.id)
                }

                val singleLayer = it.getLayer("water")
                singleLayer?.setProperties(
                    PropertyFactory.fillColor(Color.parseColor("#ee0000"))
                )
            }
        }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(
            this,
            "Se necesita el permiso de localización para el funcionamiento.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationComponent(mapboxMap.style!!)
        } else {
            Toast.makeText(
                this,
                "No está concedido el permiso!",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        // Se guarda el MapboxMap en la variable global
        // para poder utilizarlo más adelante.
        this.mapboxMap = mapboxMap

        mapboxMap.setStyle(Style.MAPBOX_STREETS) {
            // Mapa configurado y estilo cargado.
            // Ya se puede añadir información o hacer otros ajustes.

            // Se activa la localización.
            enableLocationComponent(it)
        }

        /**
         * Listener sobre el botón flotante que permite
         * eliminar todas las marcas del mapa.
         */
        binding.floatingActionButton.setOnClickListener {
            mapboxMap.clear()

            Toast.makeText(
                this,
                "Marcas eliminadas",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Se añade una marca tras una pulsación larga y elimina la última.
        mapboxMap.addOnMapLongClickListener {
            mapboxMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title(
                        String.format(
                            "Lat: %s \nLong: %s",
                            it.latitude, it.longitude
                        )
                    )
            )

            Toast.makeText(
                this,
                "Marca añadida!",
                Toast.LENGTH_LONG
            ).show()

            true
        }

        // Listener sobre cualquier punto del mapa.
        // Click & long click.
        mapboxMap.addOnMapClickListener {
            val coordenadas = mapboxMap.projection.toScreenLocation(it)

            Toast.makeText(
                this,
                String.format("Has pulsado aquí: %s", coordenadas),
                Toast.LENGTH_LONG
            ).show()
            true
        }

/*
        // Eventos de cámara.
        mapboxMap.addOnCameraMoveStartedListener(
            object : MapboxMap.OnCameraMoveStartedListener {
                // Array para identificar el tipo de movimiento.
                private val REASONS = arrayOf(
                    "REASON_API_GESTURE",
                    "REASON_DEVELOPER_ANIMATION",
                    "REASON_API_ANIMATION"
                )

                override fun onCameraMoveStarted(reason: Int) {
                    Toast.makeText(
                        applicationContext,
                        String.format("Movimiento de cámara número: %s", REASONS[reason - 1] ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        // Mientras la cámara se está moviendo.
        mapboxMap.addOnCameraMoveListener {
            Toast.makeText(
                applicationContext,
                "onCameraMove", Toast.LENGTH_SHORT
            ).show()
        }

        // Cuando se cancela el movimiento de la cámara, no lo causa el usuario.
        mapboxMap.addOnCameraMoveCancelListener {
            Toast.makeText(
                applicationContext,
                "onCameraMoveCanceled", Toast.LENGTH_SHORT
            ).show()
        }

        // Cuando la cámara ha terminado de moverse.
        mapboxMap.addOnCameraIdleListener {
            Toast.makeText(
                applicationContext,
                "onCameraIdle", Toast.LENGTH_SHORT
            ).show()
        }
*/

/*
        // Gestión de eventos de movimientos.
        mapboxMap.addOnMoveListener(object : MapboxMap.OnMoveListener {
            override fun onMoveBegin(detector: MoveGestureDetector) {
                Toast.makeText(
                    applicationContext,
                    "onMoveBegin", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onMove(detector: MoveGestureDetector) {
                Toast.makeText(
                    applicationContext,
                    "onMove", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onMoveEnd(detector: MoveGestureDetector) {
                Toast.makeText(
                    applicationContext,
                    "onMoveEnd", Toast.LENGTH_SHORT
                ).show()
            }
        })

        // Deslizamiento.
        mapboxMap.addOnFlingListener {
            Toast.makeText(
                applicationContext,
                "onFling", Toast.LENGTH_LONG
            ).show()
        }
*/

    }

    /**
     * Método para activar la localización del dispositivo,
     * también se comprueba el permiso necesario.
     */
    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(loadedMapStyle: Style) =
        // Se comprueba si el permiso está concedido.
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Se crea y personalizan las opciones de LocationComponent.
            val customLocationComponentOptions =
                LocationComponentOptions.builder(this)
                    .trackingGesturesManagement(true).build()

            // Se prepara la activación para mostrar la localización.
            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, loadedMapStyle)
                    .locationComponentOptions(customLocationComponentOptions)
                    .build()

            // Se establece la instancia de LocationComponent y aplican
            // los ajustes.
            mapboxMap.locationComponent.apply {
                // Activate the LocationComponent with options
                activateLocationComponent(locationComponentActivationOptions)

                // Se hace visible LocationComponent.
                isLocationComponentEnabled = true

                // Se establece el modo de cámara
                cameraMode = CameraMode.TRACKING

                // Se establece el modo de renderizado, o seguimiento.
                renderMode = RenderMode.COMPASS
            }

            val lat = mapboxMap.locationComponent.lastKnownLocation!!.latitude
            val long = mapboxMap.locationComponent.lastKnownLocation!!.longitude

            val position = CameraPosition.Builder()
                .target(LatLng(lat, long)) // Establece la nueva posición.
                .zoom(12.0) // Asigna el zoom. Entre 0 y 22, de lejos a cerca.
                .bearing(180.0) // Rotación de la cámara.
                .tilt(30.0) // Ajusta la inclinación de la cámara.
                .build() // Crea la nueva posición de la cámara.

            // Movimientos de cámara.
//            mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))

//            mapboxMap.easeCamera(
//                CameraUpdateFactory.newCameraPosition(position),
//                10000
//            )

            mapboxMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(position),
                7000
            )

        } else { // Permiso no concedido.
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)
        }

    override fun onStart() {
        super.onStart()
        binding.mapView?.onStart().let {
            Log.d("onStart", "Start Mapbox!")
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView?.onResume().let {
            Log.d("onResume", "Resume Mapbox!")
        }
    }

    override fun onPause() {
        super.onPause()
        binding.mapView?.onPause().let {
            Log.d("onPause", "Pause Mapbox!")
        }
    }

    override fun onStop() {
        super.onStop()
        binding.mapView?.onStop().let {
            Log.d("onStop", "Stop Mapbox!")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            binding.mapView.onSaveInstanceState(outState).let {
                Log.d("onSaveInstanceState", "SaveInstanceState Mapbox!")
            }
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory().let {
            Log.d("onLowMemory", "LowMemory Mapbox!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView?.onDestroy().let {
            Log.d("onDestroy", "Destroy Mapbox!")
        }
    }
}