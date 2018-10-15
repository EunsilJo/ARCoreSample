package com.github.eunsiljo.arcoresample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_license.*

class LicenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        supportActionBar?.run {
            setTitle(R.string.license_title)
            setDisplayHomeAsUpEnabled(true)
        }

        tv_license.text =
            "•   ARCoreSample\n" +
            "https://github.com/EunsilJo/ARCoreSample\n\n" +
            "•   ARCore\n" +
            "https://developers.google.com/ar\n\n" +
            "•   Sceneform SDK\n" +
            "https://github.com/google-ar/sceneform-android-sdk\n\n" +
            "•   Duck\n" +
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf\n\n" +
            "•   Heart, Poly by Google, CC-BY\n" +
            "https://poly.google.com/view/8RA5hHU5gHK\n\n" +
            "•   Dhalsim, Anonymous, CC-BY\n" +
            "https://poly.google.com/view/4MMQYWR5Xi0"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
