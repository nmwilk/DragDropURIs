package com.nmwilkinson.dragndrop

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import timber.log.Timber

fun Uri.getOriginalFilename(contentResolver: ContentResolver, defaultFileName: String): String? {
    var fileName: String? = defaultFileName

    val uriString = this.toString()

    try {
        when {
            isContent() -> {
                contentResolver.query(this, null, null, null, null, null)?.use {
                    if (it.moveToFirst()) {
                        val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        fileName = it.getString(index)
                    }
                }
            }
            isFile() -> {
                fileName = uriString.substring(uriString.lastIndexOf('/') + 1)
            }
        }
    } catch (se: SecurityException) {
        Timber.e(se, "Getting original file name")
    } catch (iae: IllegalArgumentException) {
        Timber.e(iae, "Getting original file name")
    } catch (t: UnsupportedOperationException) {
        Timber.e(t, "Getting original file name")
    }

    return fileName
}

fun Uri.isContent() = toString().startsWith("content://")
fun Uri.isFile() = toString().startsWith("file:///")