package com.github.eunsiljo.arcoresample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.eunsiljo.arcoresample.renderables.AndroidWidgetsActivity
import kotlinx.android.synthetic.main.activity_create_renderables.*

class CreateRenderablesActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_renderables)

        supportActionBar?.run {
            setTitle(R.string.create_renderables_title)
            setDisplayHomeAsUpEnabled(true)
        }

        with(lv_create_renderables) {
            adapter = ArrayAdapter<CharSequence>(
                context,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                ListItem.values().map { it.title }
            )
            onItemClickListener = this@CreateRenderablesActivity
        }
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            ListItem.ANDROID_WIDGETS.ordinal -> {
                startActivity(Intent(this@CreateRenderablesActivity, AndroidWidgetsActivity::class.java))
            }
            ListItem.THREE_D_ASSETS.ordinal -> {

            }
            ListItem.SIMPLE_SHAPES.ordinal -> {

            }
            ListItem.SIMPLE_SHAPES_MATERIAL.ordinal -> {

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
