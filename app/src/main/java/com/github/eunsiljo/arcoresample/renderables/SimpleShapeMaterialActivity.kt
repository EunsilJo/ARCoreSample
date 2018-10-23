package com.github.eunsiljo.arcoresample.renderables

import com.google.ar.sceneform.rendering.Color
import android.os.Bundle
import android.view.MotionEvent
import com.github.eunsiljo.arcoresample.ARActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory

class SimpleShapeMaterialActivity : ARActivity() {

    private var sphereRenderable: ModelRenderable? = null
    private var metallicRenderable: ModelRenderable? = null
    private var roughnessRenderable: ModelRenderable? = null
    private var reflectanceRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
            .thenAccept { material ->
                sphereRenderable = ShapeFactory.makeSphere(
                    0.1f,
                    Vector3(-0.45f, 0f, 0f),
                    material
                )
            }
            .exceptionally {
                showToast("Unable to load material renderable")
                null
            }

        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
            .thenAccept { material ->
                metallicRenderable = ShapeFactory.makeSphere(
                    0.1f,
                    Vector3(-0.15f, 0f, 0f),
                    material.apply {
                        setFloat(MaterialFactory.MATERIAL_METALLIC, 1f)
                    }
                )
            }
            .exceptionally {
                showToast("Unable to load material renderable")
                null
            }

        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
            .thenAccept { material ->
                roughnessRenderable = ShapeFactory.makeSphere(
                    0.1f,
                    Vector3(0.15f, 0f, 0f),
                    material.apply {
                        setFloat(MaterialFactory.MATERIAL_ROUGHNESS, 1f)
                    }
                )
            }
            .exceptionally {
                showToast("Unable to load material renderable")
                null
            }

        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
            .thenAccept { material ->
                reflectanceRenderable = ShapeFactory.makeSphere(
                    0.1f,
                    Vector3(0.45f, 0f, 0f),
                    material.apply {
                        setFloat(MaterialFactory.MATERIAL_REFLECTANCE, 1f)
                    }
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
        metallicRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
        roughnessRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
        reflectanceRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
    }
}
