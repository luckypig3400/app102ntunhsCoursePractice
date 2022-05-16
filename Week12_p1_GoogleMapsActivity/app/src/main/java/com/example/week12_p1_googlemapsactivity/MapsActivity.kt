package com.example.week12_p1_googlemapsactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.week12_p1_googlemapsactivity.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
        mMap = googleMap //將googleMap傳給外部變數mMap，讓fun外可以使用地圖

        initMapToNTUNHS()
    }

    fun initMapToNTUNHS() {
        // Add a marker in NTUNHS and move the camera
        val ntunhs = LatLng(25.11787771539104, 121.52147304364689)
        // Taiwan: 23.9037° N, 121.0794° E
        mMap.addMarker(MarkerOptions().position(ntunhs).title("國北護校本部"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ntunhs))
    }

    /*
    Google Map 顯示位置範例程式碼
    範例1:
        mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(25.118, 121.521)))
    範例2:
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(25.118, 121.521)) // Sets the LatLng
            .zoom(14f) // Sets the zoom
            .bearing(0f) // Sets the orientation of the camera to east
            .tilt(0f) // Sets the tilt of the camera to 30 degrees
            .build() // Creates a CameraPosition from the builder
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    // https://developers.google.com/maps/documentation/android-sdk/intro
     */
}