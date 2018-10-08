package com.github.eunsiljo.arcoresample

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.widget.Toast

fun Activity.checkIsSupportedDevice(): Boolean =
    when (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        true -> {
            Toast.makeText(this, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show()
            false
        }
        false -> {
            val openGlVersionString = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
            when (java.lang.Double.parseDouble(openGlVersionString) < SupportedDevice.MIN_OPENGL_VERSION) {
                true -> {
                    Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show()
                    false
                }
                false -> true
            }
        }
    }

object SupportedDevice {
    const val MIN_OPENGL_VERSION: Double = 3.0
}