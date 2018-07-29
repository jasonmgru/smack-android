package com.jasonmgru.smack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.widget.RadioGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

val EAST_BANK = CameraUpdateFactory.newLatLngZoom(LatLng(44.975297, -93.233003), 15.3f)
val WEST_BANK = CameraUpdateFactory.newLatLngZoom(LatLng(44.971260, -93.243142), 16f)
val ST_PAUL = CameraUpdateFactory.newLatLngZoom(LatLng(44.984923, -93.183673), 16f)
val MINNEAPOLIS = CameraUpdateFactory.newLatLngZoom(LatLng(44.974891, -93.236929), 14.3f)
val ALL = CameraUpdateFactory.newLatLngZoom(LatLng(44.976859, -93.215119), 13f)

class MainActivity : AppCompatActivity() {

    private lateinit var map: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var drawer: DrawerLayout
    private lateinit var homeButton: View
    private lateinit var mapConfiguration: RadioGroup

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: EventModelMapAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        viewModel = MainViewModel()
        mapFragment.getMapAsync { onMapReady(it) }

        drawer = findViewById(R.id.drawer)
        homeButton = findViewById(R.id.home_button)
        mapConfiguration = findViewById(R.id.radio)

        homeButton.setOnClickListener { onHomeButtonPressed() }
        mapConfiguration.setOnCheckedChangeListener { _, i -> onMapConfigurationChanged(i) }
    }

    private fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        adapter = EventModelMapAdapter(viewModel.data, map)
        map.setOnCameraMoveStartedListener { onCameraMoved(it) }
        mapConfiguration.check(R.id.east_bank_button)
    }

    private fun onHomeButtonPressed() {
        drawer.openDrawer(Gravity.START)
    }

    private fun onMapConfigurationChanged(position: Int) {
        when(position) {
            R.id.east_bank_button ->    map.moveCamera(EAST_BANK)
            R.id.west_bank_button ->    map.moveCamera(WEST_BANK)
            R.id.st_paul_button ->      map.moveCamera(ST_PAUL)
            R.id.minneapolis_button ->  map.moveCamera(MINNEAPOLIS)
            R.id.all_button ->          map.moveCamera(ALL)
            else ->                     map.moveCamera(EAST_BANK)
        }
        drawer.closeDrawer(Gravity.START)
    }

    private fun onCameraMoved(reason: Int) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE)
            mapConfiguration.clearCheck()
    }
}
