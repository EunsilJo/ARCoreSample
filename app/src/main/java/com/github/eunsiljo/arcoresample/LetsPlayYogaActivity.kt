package com.github.eunsiljo.arcoresample

import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import com.github.eunsiljo.arcoresample.node.RotatingNode
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import java.util.concurrent.CompletableFuture

class LetsPlayYogaActivity : ARActivity() {

    companion object {
        private const val ANDY_ASSET = "andy.sfb"
        private const val HEART_ASSET = "heart.sfb"
        private const val DHALSIM_ASSET = "dhalsim.sfb"
        private const val DUCK_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf"
    }

    private val textViewState: CompletableFuture<ViewRenderable> by lazy {
        ViewRenderable.builder().setView(this, R.layout.view_ar_text).build()
    }
    private val imageViewState: CompletableFuture<ViewRenderable> by lazy {
        ViewRenderable.builder().setView(this, R.layout.view_ar_image).build()
    }
    private val materialState: CompletableFuture<Material> by lazy {
        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.RED))
    }
    private val andyState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder().setSource(this, Uri.parse(ANDY_ASSET)).build()
    }
    private val heartState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder().setSource(this, Uri.parse(HEART_ASSET)).build()
    }
    private val dhalsimState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder().setSource(this, Uri.parse(DHALSIM_ASSET)).build()
    }
    private val duckState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder()
            .setSource(this, RenderableSource.builder().setSource(
                this,
                Uri.parse(DUCK_ASSET),
                RenderableSource.SourceType.GLTF2)
                .setScale(0.2f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build()
            )
            .setRegistryId(DUCK_ASSET)
            .build()
    }

    private var textViewRenderable: ViewRenderable? = null
    private var imageViewRenderable: ViewRenderable? = null
    private var andyRenderable: ModelRenderable? = null
    private var heartRenderable: ModelRenderable? = null
    private var dhalsimRenderable: ModelRenderable? = null
    private var duckRenderable: ModelRenderable? = null
    private var sphereRenderable: ModelRenderable? = null
    private var cubeRenderable: ModelRenderable? = null
    private var cylinderRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CompletableFuture.allOf(
            textViewState,
            imageViewState,
            materialState,
            andyState,
            heartState,
            dhalsimState,
            duckState
        ).handle { _, throwable ->
            when (throwable == null) {
                true -> {
                    textViewRenderable = textViewState.get()
                    imageViewRenderable = imageViewState.get()
                    andyRenderable = andyState.get()
                    heartRenderable = heartState.get()
                    dhalsimRenderable = dhalsimState.get()
                    duckRenderable = duckState.get()
                    sphereRenderable =
                        ShapeFactory.makeSphere(
                            0.1f,
                            Vector3(0f, 0f, 0f),
                            materialState.get().makeCopy().apply {
                                setFloat3(
                                    MaterialFactory.MATERIAL_COLOR,
                                    Color(android.graphics.Color.BLUE)
                                )
                                setFloat(MaterialFactory.MATERIAL_METALLIC, 1f)
                            }
                        )
                    cubeRenderable =
                        ShapeFactory.makeCube(
                            Vector3(0.2f, 0.2f, 0.2f),
                            Vector3(0f, 0f, 0f),
                            materialState.get().makeCopy().apply {
                                setFloat3(
                                    MaterialFactory.MATERIAL_COLOR,
                                    Color(android.graphics.Color.CYAN)
                                )
                                setFloat(MaterialFactory.MATERIAL_ROUGHNESS, 1f)
                            }
                        )
                    cylinderRenderable =
                        ShapeFactory.makeCylinder(
                            0.1f,
                            0.2f,
                            Vector3(0f, 0f, 0f),
                            materialState.get().makeCopy().apply {
                                setFloat3(
                                    MaterialFactory.MATERIAL_COLOR,
                                    Color(android.graphics.Color.RED)
                                )
                                setFloat(MaterialFactory.MATERIAL_REFLECTANCE, 1f)
                            }
                        )
                }
                false -> {
                    showToast("Unable to load renderables")
                }
            }
        }
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
        }

        dhalsimRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                localScale = Vector3(0.5f, 0.5f, 0.5f)
                localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), -90f)
            }
        }

        val forwardRotateNode = RotatingNode(10000L).apply {
            setParent(anchorNode)
        }
        heartRenderable?.let {
            RotatingNode(4000L).apply {
                setParent(forwardRotateNode)
                renderable = it
                localPosition = Vector3(0.6f, 0.6f, 0.6f)
                localScale = Vector3(0.2f, 0.2f, 0.2f)
            }
        }
        imageViewRenderable?.let {
            RotatingNode(2000L).apply {
                setParent(forwardRotateNode)
                renderable = it
                localPosition = Vector3(0.3f, 0.3f, 0.3f)
                light = Light.builder(Light.Type.DIRECTIONAL)
                    .setShadowCastingEnabled(false)
                    .build()
            }
        }
        textViewRenderable?.let {
            RotatingNode(4000L).apply {
                setParent(forwardRotateNode)
                renderable = it
                localPosition = Vector3(-0.3f, -0.3f, -0.3f)
                light = Light.builder(Light.Type.DIRECTIONAL)
                    .setShadowCastingEnabled(false)
                    .build()
            }
        }
        duckRenderable?.let {
            RotatingNode(4000L).apply {
                setParent(forwardRotateNode)
                renderable = it
                localPosition = Vector3(-0.6f, 0.4f, -0.6f)
            }
        }

        val backwardRotateNode = RotatingNode(5000L, true).apply {
            setParent(anchorNode)
        }
        cubeRenderable?.let {
            Node().apply {
                setParent(backwardRotateNode)
                renderable = it
                localPosition = Vector3(-0.45f, 0.45f, 0.45f)
                localRotation = Quaternion.axisAngle(Vector3(1f, 1f, 0f), 45f)
            }
        }
        andyRenderable?.let {
            RotatingNode(1000L, true).apply {
                setParent(backwardRotateNode)
                renderable = it
                localPosition = Vector3(-0.3f, 0.15f, 0.15f)
            }
        }
        sphereRenderable?.let {
            RotatingNode(2000L, true).apply {
                setParent(backwardRotateNode)
                renderable = it
                localPosition = Vector3(0.15f, -0.15f, -0.15f)
            }
        }
        cylinderRenderable?.let {
            Node().apply {
                setParent(backwardRotateNode)
                renderable = it
                localPosition = Vector3(0.45f, -0.45f, -0.45f)
                localRotation = Quaternion.axisAngle(Vector3(1f, 1f, 0f), -45f)
            }
        }
    }
}
