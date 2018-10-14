package com.github.eunsiljo.arcoresample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment

class OverviewActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener {

    companion object {
        private const val ANDY_ASSET = "andy.sfb"
    }

    private val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }

    private var modelRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        ModelRenderable.builder()
            .setSource(this, Uri.parse(ANDY_ASSET))
            .build()
            .thenAccept { renderable -> modelRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load model renderable", Toast.LENGTH_LONG).show()
                null
            }

        arFragment.setOnTapArPlaneListener(this@OverviewActivity)
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
        }

        modelRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
    }
}
