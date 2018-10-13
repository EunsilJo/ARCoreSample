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
import java.util.concurrent.CompletableFuture

class OverviewActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener {

    companion object {
        private const val ANDY_ASSET = "andy.sfb"
    }

    private val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }
    private val andyState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder().setSource(this, Uri.parse(ANDY_ASSET)).build()
    }

    private var modelRenderable: ModelRenderable? = null

    private val modelNode
        get() = Node().apply { modelRenderable?.let { renderable = it } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        makeModelRenderable(andyState)
        arFragment.setOnTapArPlaneListener(this@OverviewActivity)
    }

    private fun makeModelRenderable(modelState: CompletableFuture<ModelRenderable>) {
        modelState
            .thenAccept { renderable -> modelRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load model renderable", Toast.LENGTH_LONG).show()
                null
            }
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
            addChild(modelNode)
        }
    }
}
