package com.jasonmgru.smack

import android.databinding.ObservableArrayList
import android.databinding.ObservableList

class SimpleOnListChangedCallback(val onListChanged: () -> Unit)
    : ObservableList.OnListChangedCallback<ObservableArrayList<EventModel>>() {
    override fun onChanged(sender: ObservableArrayList<EventModel>?) {
        onListChanged()
    }

    override fun onItemRangeRemoved(sender: ObservableArrayList<EventModel>?, positionStart: Int, itemCount: Int) {
        onChanged(sender)
    }

    override fun onItemRangeMoved(sender: ObservableArrayList<EventModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
        onChanged(sender)
    }

    override fun onItemRangeInserted(sender: ObservableArrayList<EventModel>?, positionStart: Int, itemCount: Int) {
        onChanged(sender)
    }

    override fun onItemRangeChanged(sender: ObservableArrayList<EventModel>?, positionStart: Int, itemCount: Int) {
        onChanged(sender)
    }
}