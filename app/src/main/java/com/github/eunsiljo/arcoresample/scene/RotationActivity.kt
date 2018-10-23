package com.github.eunsiljo.arcoresample.scene

import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import com.github.eunsiljo.arcoresample.ARActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable

class RotationActivity  : ARActivity() {

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
                localPosition = Vector3(-0.8f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 0f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(-0.6f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 45f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(-0.4f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 90f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(-0.2f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 135f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(0f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 180f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(0.2f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 225f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(0.4f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 270f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(0.6f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 315f)
            }

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(0.8f, 0f, 0f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 360f)
            }
        }
    }
}
