package com.nmwilkinson.dragndrop

import android.app.Activity
import android.content.ClipData
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.DragEvent
import android.view.DragEvent.ACTION_DRAG_STARTED
import android.view.DragEvent.ACTION_DROP
import android.view.View
import timber.log.Timber

class DragDropView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    lateinit var listener: Listener

    override fun onDragEvent(event: DragEvent?): Boolean {
        val action = event?.action

        Timber.d("onDrag action ${event?.action}")

        when (action) {
            ACTION_DRAG_STARTED -> {
                Timber.d("- description: ${event.clipDescription}")
            }
            ACTION_DROP -> {
                val result = (0 until event.clipData.itemCount).mapNotNull { event.clipData.getItemAt(it).uri }
                    .fold(mutableListOf<DropDropItems>()) { list, uri ->
                        list.add(DropDropItems(context.contentResolver.getType(uri) ?: "None", uri.toString()))
                        list
                    }

                listener.onFilesDropped(result)
            }
        }

        return action != null
    }

    interface Listener {
        fun onFilesDropped(result: List<DropDropItems>)
    }
}

data class DropDropItems(val mimeType: String, val uri: String)
