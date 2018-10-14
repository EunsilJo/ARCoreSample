package com.github.eunsiljo.arcoresample.scene

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.github.eunsiljo.arcoresample.ARActivity
import com.github.eunsiljo.arcoresample.node.RotatingNode
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.QuaternionEvaluator
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable

class AnimationActivity : ARActivity() {

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
            RotatingNode(1000L).apply {
                setParent(anchorNode)
                renderable = it
            }
        }
    }
}