package com.github.eunsiljo.arcoresample.renderables

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.github.eunsiljo.arcoresample.R
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment

class ThreeDAssetsActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener {

    companion object {
        private const val HEART_ASSET = "heart.sfb"
        private const val DUCK_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf"
    }

    private val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }

    private var heartRenderable: ModelRenderable? = null
    private var duckRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        ModelRenderable.builder()
            .setSource(this, Uri.parse(HEART_ASSET))
            .build()
            .thenAccept { renderable -> heartRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load model renderable", Toast.LENGTH_LONG).show()
                null
            }

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
            .thenAccept { renderable -> duckRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load model renderable", Toast.LENGTH_LONG).show()
                null
            }

        arFragment.setOnTapArPlaneListener(this@ThreeDAssetsActivity)
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        val anchorNode = AnchorNode(hitResult.createAnchor()).apply {
            setParent(arFragment.arSceneView.scene)
        }

        heartRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(0.2f, 0f, 0f)
            }
        }
        duckRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(-0.2f, 0.1f, 0f)
            }
        }
    }
}
