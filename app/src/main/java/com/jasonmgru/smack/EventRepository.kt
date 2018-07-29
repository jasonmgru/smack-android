package com.jasonmgru.smack

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import com.google.firebase.database.*

class EventRepository {

    val data = ObservableArrayList<EventModel>()
    val error = ObservableField<Throwable>()

    private val firebase = FirebaseDatabase.getInstance()
    private val events = firebase.getReference("events")

    private val listener = object : ValueEventListener {
        override fun onCancelled(databaseError: DatabaseError) {
            error.set(Throwable(
                    "MESSAGE: ${databaseError.message}\n" +
                    "DETAILS: ${databaseError.message}\n" +
                    "CODE: ${databaseError.code}"))
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val value = (dataSnapshot.value as? List<HashMap<String, *>>)?.map {
                createEventModelFromHashMap(it)
            }
            if (value != null) {
                data.clear()
                data.addAll(value)
            } else {
                error.set(Throwable("INTERNAL ERROR: data gotten from Firebase was null!"))
            }
        }
    }

    init {
        events.addValueEventListener(listener)
    }

    private fun createEventModelFromHashMap(map: HashMap<String, *>) : EventModel {
        return EventModel(
                if (map["id"] != null)          map["id"]           as String else "",
                if (map["emoji"] != null)       map["emoji"]        as String else "",
                if (map["title"] != null)       map["title"]        as String else "",
                if (map["host"] != null)        map["host"]         as String else "",
                if (map["description"] != null) map["description"]  as String else "",
                if (map["address"] != null)     map["address"]      as String else "",
                if (map["latitude"] != null)    map["latitude"].toString().toDouble() else 0.0,
                if (map["longitude"] != null)   map["longitude"].toString().toDouble() else 0.0,
                if (map["dollars"] != null)     map["dollars"].toString().toInt()   else 0,
                if (map["cents"] != null)       map["cents"].toString().toInt()   else 0,
                if (map["start"] != null)       map["start"]        as String else "",
                if (map["end"] != null)         map["end"]          as String else "")
    }
}
