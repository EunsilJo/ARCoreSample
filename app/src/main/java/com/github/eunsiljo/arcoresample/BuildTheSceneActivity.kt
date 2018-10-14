package com.github.eunsiljo.arcoresample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.eunsiljo.arcoresample.scene.*
import kotlinx.android.synthetic.main.activity_list.*

class BuildTheSceneActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.run {
            setTitle(R.string.build_the_scene_title)
            setDisplayHomeAsUpEnabled(true)
        }

        with(list) {
            adapter = ArrayAdapter<CharSequence>(
                context,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                ListItem.values().map { it.title }
            )
            onItemClickListener = this@BuildTheSceneActivity
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            ListItem.LOCATION.ordinal -> {
                startActivity(Intent(this@BuildTheSceneActivity, LocationActivity::class.java))
            }
            ListItem.SCALE.ordinal -> {
                startActivity(Intent(this@BuildTheSceneActivity, ScaleActivity::class.java))
            }
            ListItem.ROTATION.ordinal -> {
                startActivity(Intent(this@BuildTheSceneActivity, RotationActivity::class.java))
            }
            ListItem.TRANSFORM.ordinal -> {
                startActivity(Intent(this@BuildTheSceneActivity, TransformActivity::class.java))
            }
            ListItem.ANIMATION.ordinal -> {
                startActivity(Intent(this@BuildTheSceneActivity, AnimationActivity::class.java))
            }
            ListItem.LIGHT_AND_SHADOW.ordinal -> {
                startActivity(Intent(this@BuildTheSceneActivity, LightAndShadowActivity::class.java))
            }
        }
    }

    enum class ListItem(val title: String) {
        LOCATION("Location"),
        SCALE("Scale"),
        ROTATION("Rotation"),
        TRANSFORM("Transform"),
        ANIMATION("Animation"),
        LIGHT_AND_SHADOW("Light & Shadow")
    }
}
