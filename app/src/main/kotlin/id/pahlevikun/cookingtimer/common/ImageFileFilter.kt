package id.pahlevikun.cookingtimer.common

import java.io.File
import java.io.FileFilter
import java.util.*

class ImageFileFilter : FileFilter {
    private val okFileExtensions = arrayOf(
        "jpg",
        "png",
        "gif",
        "jpeg"
    )

    override fun accept(file: File): Boolean {
        for (extension in okFileExtensions) {
            if (file.name.toLowerCase(Locale.getDefault()).endsWith(extension)) {
                return true
            }
        }
        return false
    }

}