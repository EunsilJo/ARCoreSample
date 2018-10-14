package com.github.eunsiljo.arcoresample.renderables

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.github.eunsiljo.arcoresample.R
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment

class AndroidWidgetsActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener {

    private val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }

    private var textViewRenderable: ViewRenderable? = null
    private var imageViewRenderable: ViewRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        ViewRenderable.builder()
            .setView(this, R.layout.view_ar_text)
            .build()
            .thenAccept { renderable -> textViewRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load view renderable", Toast.LENGTH_LONG).show()
                null
            }

        ViewRenderable.builder()
            .setView(this, R.layout.view_ar_image)
            .build()
            .thenAccept { renderable -> imageViewRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load view renderable", Toast.LENGTH_LONG).show()
                null
            }

        arFragment.setOnTapArPlaneListener(this@AndroidWidgetsActivity)
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
        }

        textViewRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(0.2f, 0f, 0f)
            }
        }
        imageViewRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(-0.2f, 0.1f, 0f)
            }
        }
    }
}
