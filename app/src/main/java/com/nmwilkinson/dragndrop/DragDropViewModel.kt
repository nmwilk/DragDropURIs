package com.nmwilkinson.dragndrop

import androidx.lifecycle.ViewModel

class DragDropViewModel : ViewModel() {
    private val mutableData = mutableListOf<DropDropItems>()
    val allData: List<DropDropItems> = mutableData

    fun addResults(result: List<DropDropItems>) {
        mutableData.addAll(result)
    }
}
