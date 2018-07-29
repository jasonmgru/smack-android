package com.jasonmgru.smack

data class EventModel (
        val id: String,
        val emoji: String,
        val title: String,
        val host: String,
        val description: String,
        val address: String,
        val latitude: Double,
        val longitude: Double,
        val dollars: Int,
        val cents: Int,
        val start: String,
        val end: String)