package es.javiercarrasco.myfirstmapbox

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
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
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import es.javiercarrasco.myfirstmapbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback, PermissionsListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myMapboxMap: MapboxMap
    private var permissionsManager: PermissionsManager = PermissionsManager(this)

    private lateinit var symbolManager: SymbolManager
    private var lastMarker: Symbol? = null
    //private var lastMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se instancia el Mapa, debe hacerse antes de inflar la vista,
        // en caso contrario, la aplicación falla.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        // Listener sobre el botón flotante que permite
        // eliminar todas las marcas del mapa.
        binding.floatingActionButton.setOnClickListener {
            symbolManager.deleteAll()

            Toast.makeText(
                this,
                "Marcas eliminadas",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        myMapboxMap = mapboxMap
        mapboxMap.setStyle(Style.MAPBOX_STREETS) {
            // Mapa configurado y estilo cargado.
            // Ya se puede añadir información o hacer otros ajustes.

            // Se activa la localización.
            enableLocationComponent(it)

            val uiSettings = mapboxMap.uiSettings
            uiSettings.isLogoEnabled = false
            uiSettings.isAttributionEnabled = false

            // Únicamente muestra la brújula si no se está mirando al norte.
            uiSettings.setCompassFadeFacingNorth(true)
            for (singleLayer in it.layers) {
                Log.d("CAPAS", "Layer id = " + singleLayer.id)
            }

            // Marcadores.
            mapboxMap.addMarker(
                MarkerOptions()
                    .position(LatLng(38.4041828, -0.529396))
                    .title("IES San Vicente")
            )

            /*
            // Se añade una marca tras una pulsación larga y elimina la última.
            mapboxMap.addOnMapLongClickListener {
//                if (lastMarker != null)
//                    lastMarker?.remove()

                lastMarker = mapboxMap.addMarker(
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
            */

            // Añadir marcador con SymbolManager.
            symbolManager = SymbolManager(binding.mapView, mapboxMap, it)

            // Se establecen a true para mostrar el marcador aunque
            // choque con otro elemento del mapa.
            symbolManager.iconAllowOverlap = true
            symbolManager.iconIgnorePlacement = true

            it.addImage(
                "myMarker",
                BitmapFactory.decodeResource(
                    resources, R.drawable.mapbox_marker_icon_default
                )
            )

            mapboxMap.addOnMapLongClickListener {
//                if (lastMarker != null)
//                    symbolManager.delete(lastMarker)

                lastMarker = symbolManager.create(
                    SymbolOptions()
                        .withLatLng(it)
                        .withIconImage("myMarker")
                )

                true
            }
        }
    }

    // Método para activar la localización del dispositivo,
    // también se comprueba el permiso necesario.
    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(loadedStyle: Style): Any =
        // Se comprueba si el permiso está concedido.
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Se crea y personalizan las opciones de LocationComponent.
            val customLocationComponentOptions =
                LocationComponentOptions.builder(this)
                    .trackingGesturesManagement(true).build()

            // Se prepara la activación para mostrar la localización.
            val locationComponentActivationOptions =
                LocationComponentActivationOptions.builder(this, loadedStyle)
                    .locationComponentOptions(customLocationComponentOptions).build()

            // Se establece la instancia de LocationComponent y aplican
            // los ajustes.
            myMapboxMap.locationComponent.apply {
                // Activate the LocationComponent with options
                activateLocationComponent(locationComponentActivationOptions)
                // Se hace visible LocationComponent.
                isLocationComponentEnabled = true
                // Se establece el modo de cámara
                cameraMode = CameraMode.TRACKING
                // Se establece el modo de renderizado, o seguimiento.
                renderMode = RenderMode.COMPASS
            }

            val lat = myMapboxMap.locationComponent.lastKnownLocation!!.latitude
            val long = myMapboxMap.locationComponent.lastKnownLocation!!.longitude

            val position = CameraPosition.Builder()
                .target(LatLng(lat, long)) // Establece la nueva posición.
                .zoom(12.0) // Asigna el zoom. Entre 0 y 22, de lejos a cerca.
                .bearing(180.0) // Rotación de la cámara.
                .tilt(30.0) // Ajusta la inclinación de la cámara.
                .build() // Crea la nueva posición de la cámara.

            // Movimientos de cámara.
            myMapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))

            myMapboxMap.easeCamera(
                CameraUpdateFactory.newCameraPosition(position),
                10000
            )

            myMapboxMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(position),
                7000
            )

            // Listener sobre cualquier punto del mapa.
            // Click & long click.
            myMapboxMap.addOnMapClickListener {
                val coordenadas = myMapboxMap.projection.toScreenLocation(it)

                Toast.makeText(
                    this,
                    String.format("Has pulsado aquí: %s", coordenadas),
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            /*
            // Eventos de cámara.
            myMapboxMap.addOnCameraMoveStartedListener(
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
                            String.format(
                                "Movimiento de cámara número: %s",
                                REASONS[reason - 1]
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            // Mientras la cámara se está moviendo.
            myMapboxMap.addOnCameraMoveListener {
                Toast.makeText(
                    applicationContext,
                    "onCameraMove", Toast.LENGTH_SHORT
                ).show()
            }

            // Cuando se cancela el movimiento de la cámara, no lo causa el usuario.
            myMapboxMap.addOnCameraMoveCancelListener {
                Toast.makeText(
                    applicationContext,
                    "onCameraMoveCanceled", Toast.LENGTH_SHORT
                ).show()
            }

            // Cuando la cámara ha terminado de moverse.
            myMapboxMap.addOnCameraIdleListener {
                Toast.makeText(
                    applicationContext,
                    "onCameraIdle", Toast.LENGTH_SHORT
                ).show()
            }
            // Fin eventos de cámara.
            */

            /*
            // Gestión de eventos de movimientos.
            myMapboxMap.addOnMoveListener(object : MapboxMap.OnMoveListener {
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
            // Fin gestión de eventos de movimientos.
             */

            /*
            // Deslizamiento.
            myMapboxMap.addOnFlingListener {
                Toast.makeText(
                    applicationContext,
                    "onFling", Toast.LENGTH_SHORT
                ).show()
            }
            */

        } else { // Permiso no concedido.
            permissionsManager.requestLocationPermissions(this)
        }

    override fun onExplanationNeeded(p0: MutableList<String>?) {
        Toast.makeText(
            applicationContext,
            "Se necesita el permiso de localización para el funcionamiento.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPermissionResult(p0: Boolean) {
        if (p0) {
            enableLocationComponent(myMapboxMap.style!!)
        } else {
            Toast.makeText(
                this,
                "No está concedido el permiso!",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart().apply {
            Log.d("onStart", "Start Mapbox!!")
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume().apply {
            Log.d("onResume", "Resume Mapbox!!")
        }
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause().apply {
            Log.d("onPause", "Pause Mapbox!!")
        }
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop().apply {
            Log.d("onStop", "Stop Mapbox!!")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState).apply {
            Log.d("onSaveInstanceState", "SaveInstanceState Mapbox!")
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory().apply {
            Log.d("onLowMemory", "LowMemory Mapbox!")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy().apply {
            Log.d("onDestroy", "Destroy Mapbox!")
        }
    }
}