package com.jasonmgru.smack

class MainViewModel {
    val repository = EventRepository()

    val data = repository.data
    val error = repository.error
}