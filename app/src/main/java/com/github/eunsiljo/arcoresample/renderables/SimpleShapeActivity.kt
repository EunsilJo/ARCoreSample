package com.github.eunsiljo.arcoresample.renderables

import com.google.ar.sceneform.rendering.Color
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.github.eunsiljo.arcoresample.ARActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory

class SimpleShapeActivity : ARActivity() {

    private var sphereRenderable: ModelRenderable? = null
    private var cubeRenderable: ModelRenderable? = null
    private var cylinderRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
            .thenAccept { material ->
                sphereRenderable = ShapeFactory.makeSphere(
                    0.1f,
                    Vector3(-0.3f, 0f, 0f),
                    material
                )
            }
            .exceptionally {
                showToast("Unable to load material renderable")
                null
            }

        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.BLUE))
            .thenAccept { material ->
                cubeRenderable = ShapeFactory.makeCube(
                    Vector3(0.2f, 0.2f, 0.2f),
                    Vector3(0f, 0f, 0f),
                    material
                )
            }
            .exceptionally {
                showToast("Unable to load material renderable")
                null
            }

        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.YELLOW))
            .thenAccept { material ->
                cylinderRenderable = ShapeFactory.makeCylinder(
                   0.1f,
                    0.2f,
                    Vector3(0.3f, 0f, 0f),
                    material
                )
            }
            .exceptionally {
                showToast("Unable to load material renderable")
                null
            }
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
        }

        sphereRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
        cubeRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
        cylinderRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
    }
}
