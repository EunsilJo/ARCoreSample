package com.github.eunsiljo.arcoresample.renderables

import android.os.Bundle
import android.view.MotionEvent
import com.github.eunsiljo.arcoresample.ARActivity
import com.github.eunsiljo.arcoresample.R
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable

class AndroidWidgetsActivity : ARActivity() {

    private var textViewRenderable: ViewRenderable? = null
    private var imageViewRenderable: ViewRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewRenderable.builder()
            .setView(this, R.layout.view_ar_text)
            .build()
            .thenAccept { renderable -> textViewRenderable = renderable }
            .exceptionally {
                showToast("Unable to load view renderable")
                null
            }

        ViewRenderable.builder()
            .setView(this, R.layout.view_ar_image)
            .build()
            .thenAccept { renderable -> imageViewRenderable = renderable }
            .exceptionally {
                showToast("Unable to load view renderable")
                null
            }
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
