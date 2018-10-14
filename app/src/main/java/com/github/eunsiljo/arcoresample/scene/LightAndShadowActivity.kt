package com.github.eunsiljo.arcoresample.scene

import com.google.ar.sceneform.rendering.Color
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import com.github.eunsiljo.arcoresample.ARActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.Light
import com.google.ar.sceneform.rendering.ModelRenderable

class LightAndShadowActivity : ARActivity() {

    companion object {
        private const val ANDY_ASSET = "andy.sfb"
    }

    private var andyRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ModelRenderable.builder()
            .setSource(this, Uri.parse(ANDY_ASSET))
            .build()
            .thenAccept { renderable -> andyRenderable = renderable }
            .exceptionally {
                showToast("Unable to load model renderable")
                null
            }
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
        }

        andyRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                light = Light.builder(Light.Type.DIRECTIONAL)
                    .setColor(Color(android.graphics.Color.RED))
                    .setShadowCastingEnabled(false)
                    .build()
            }
        }
    }
}