package com.github.eunsiljo.arcoresample

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import java.util.concurrent.CompletableFuture

class MainActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener {

    companion object {
        private const val ANDY_ASSET = "andy.sfb"
        private const val DUCK_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf"
    }

    private val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }

    private val textViewState: CompletableFuture<ViewRenderable> by lazy {
        ViewRenderable.builder().setView(this, R.layout.view_ar_text).build()
    }
    private val imageViewState: CompletableFuture<ViewRenderable> by lazy {
        ViewRenderable.builder().setView(this, R.layout.view_ar_image).build()
    }
    private val andyState: CompletableFuture<ModelRenderable> by lazy {
        ModelRenderable.builder().setSource(this, Uri.parse(ANDY_ASSET)).build()
    }
    private val materialState: CompletableFuture<Material> by lazy {
        MaterialFactory.makeOpaqueWithColor(this, Color(android.graphics.Color.MAGENTA))
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

    private var viewRenderable: ViewRenderable? = null
    private var modelRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDevice()) {
            finish()
        }
        setContentView(R.layout.activity_main)

        setModelRenderable(duckState)
        arFragment.setOnTapArPlaneListener(this)
    }
    
    private fun setViewRenderable(viewState: CompletableFuture<ViewRenderable>) {
        viewState
            .thenAccept { renderable -> viewRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load view renderable", Toast.LENGTH_LONG).show()
                null
            }
    }

    private fun setModelRenderable(modelState: CompletableFuture<ModelRenderable>) {
        modelState
            .thenAccept { renderable -> modelRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load model renderable", Toast.LENGTH_LONG).show()
                null
            }
    }

    private fun setMaterialRenderable(
        materialState: CompletableFuture<Material>,
        shapeType: ARShapeType
    ) {
        materialState
            .thenAccept { material ->
                modelRenderable = when (shapeType) {
                    ARShapeType.SPHERE -> {
                        ShapeFactory.makeSphere(
                            0.1f,
                            Vector3(0.0f, 0.15f, 0.0f),
                            material
                        )
                    }
                    ARShapeType.CUBE -> {
                        ShapeFactory.makeCube(
                            Vector3(0.2f, 0.2f, 0.2f),
                            Vector3(0.0f, 0.15f, 0.0f),
                            material
                        )
                    }
                    ARShapeType.CYLINDER -> {
                        ShapeFactory.makeCylinder(
                            0.1f,
                            0.3f,
                            Vector3(0.0f, 0.15f, 0.0f),
                            material
                        )
                    }
                }
            }
            .exceptionally {
                Toast.makeText(this, "Unable to load material renderable", Toast.LENGTH_LONG).show()
                null
            }
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
        }
        viewRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
            it.view.setOnClickListener {
                Toast.makeText(this, "Clicked!", Toast.LENGTH_LONG).show()
            }
        }

        modelRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
            }
        }
    }
}
