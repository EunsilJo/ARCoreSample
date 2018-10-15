package com.github.eunsiljo.arcoresample

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    companion object {
        private const val MIN_BUILD_VERSION_CODE: Int = Build.VERSION_CODES.N
        private const val MIN_OPENGL_VERSION: Double = 3.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (!checkIsSupportedDevice()) {
            finish()
        }

        with(list) {
            adapter = ArrayAdapter<CharSequence>(
                context,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                ListItem.values().map { it.title }
            )
            onItemClickListener = this@MainActivity
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_license -> {
                startActivity(Intent(this@MainActivity, LicenseActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            ListItem.OVERVIEW.ordinal -> {
                startActivity(Intent(this@MainActivity, OverviewActivity::class.java))
            }
            ListItem.CREATE_RENDERABLES.ordinal -> {
                startActivity(Intent(this@MainActivity, CreateRenderablesActivity::class.java))
            }
            ListItem.BUILD_THE_SCENE.ordinal -> {
                startActivity(Intent(this@MainActivity, BuildTheSceneActivity::class.java))
            }
            ListItem.LETS_PLAY_YOGA.ordinal -> {
                startActivity(Intent(this@MainActivity, LetsPlayYogaActivity::class.java))
            }
        }
    }

    private fun checkIsSupportedDevice(): Boolean =
        when (Build.VERSION.SDK_INT < MIN_BUILD_VERSION_CODE) {
            true -> {
                Toast.makeText(this, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show()
                false
            }
            false -> {
                val openGlVersionString = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                    .deviceConfigurationInfo
                    .glEsVersion
                when (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
                    true -> {
                        Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show()
                        false
                    }
                    false -> true
                }
            }
        }

    enum class ListItem(val title: String) {
        OVERVIEW("1. Overview"),
        CREATE_RENDERABLES("2. Create Renderables"),
        BUILD_THE_SCENE("3. Build the Scene"),
        LETS_PLAY_YOGA("4. Let's play Yoga")
    }
}
