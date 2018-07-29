package com.jasonmgru.smack

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class EventModelMapAdapter(val eventList: ObservableArrayList<EventModel>, val googleMap: GoogleMap) {

    private var markers = ArrayList<Marker>()

    private val listener = SimpleOnListChangedCallback({
        markers.forEach { it.remove() }
        markers.clear()
        markers.addAll(eventList.map { createMarkerFromEvent(it) })
    })

    init {
        eventList.addOnListChangedCallback(listener)
    }

    private fun createMarkerFromEvent(eventModel: EventModel) : Marker {
        val snippet = eventModel.start + " - " + eventModel.end
        val position = LatLng(eventModel.latitude, eventModel.longitude)
        val marker = googleMap.addMarker(
                MarkerOptions()
                        .position(position)
                        .title(eventModel.title)
                        .snippet(snippet))
        marker.tag = MarkerTag(eventModel.id, MarkerStatus.ADDED)
        return marker
    }

    private enum class MarkerStatus { ADDED, REMOVED }
    private inner class MarkerTag(val id: String, var status: MarkerStatus)
}