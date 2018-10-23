package com.github.eunsiljo.arcoresample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.eunsiljo.arcoresample.renderables.AndroidWidgetsActivity
import com.github.eunsiljo.arcoresample.renderables.SimpleShapeActivity
import com.github.eunsiljo.arcoresample.renderables.SimpleShapeMaterialActivity
import com.github.eunsiljo.arcoresample.renderables.ThreeDAssetsActivity
import kotlinx.android.synthetic.main.activity_list.*

class CreateRenderablesActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.run {
            setTitle(R.string.create_renderables_title)
            setDisplayHomeAsUpEnabled(true)
        }

        with(list) {
            adapter = ArrayAdapter<CharSequence>(
                context,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                ListItem.values().map { it.title }
            )
            onItemClickListener = this@CreateRenderablesActivity
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
            ListItem.ANDROID_WIDGETS.ordinal -> {
                startActivity(Intent(this@CreateRenderablesActivity, AndroidWidgetsActivity::class.java))
            }
            ListItem.THREE_D_ASSETS.ordinal -> {
                startActivity(Intent(this@CreateRenderablesActivity, ThreeDAssetsActivity::class.java))
            }
            ListItem.SIMPLE_SHAPES.ordinal -> {
                startActivity(Intent(this@CreateRenderablesActivity, SimpleShapeActivity::class.java))
            }
            ListItem.SIMPLE_SHAPES_MATERIAL.ordinal -> {
                startActivity(Intent(this@CreateRenderablesActivity, SimpleShapeMaterialActivity::class.java))
            }
        }
    }

    enum class ListItem(val title: String) {
        ANDROID_WIDGETS("Android Widgets"),
        THREE_D_ASSETS("3D Assets"),
        SIMPLE_SHAPES("Simple Shapes"),
        SIMPLE_SHAPES_MATERIAL("Simple Shapes - Material")
    }
}
