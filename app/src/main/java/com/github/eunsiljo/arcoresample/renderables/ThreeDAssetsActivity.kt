package com.github.eunsiljo.arcoresample.renderables

import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import com.github.eunsiljo.arcoresample.ARActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable

class ThreeDAssetsActivity : ARActivity() {

    companion object {
        private const val HEART_ASSET = "heart.sfb"
        private const val DUCK_ASSET =
            "https://github.com/KhronosGroup/glTF-Sample-Models/raw/master/2.0/Duck/glTF/Duck.gltf"
    }

    private var heartRenderable: ModelRenderable? = null
    private var duckRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ModelRenderable.builder()
            .setSource(this, Uri.parse(HEART_ASSET))
            .build()
            .thenAccept { renderable -> heartRenderable = renderable }
            .exceptionally {
                showToast("Unable to load model renderable")
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
                showToast("Unable to load model renderable")
                null
            }
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
                localScale = Vector3(0.2f, 0.2f, 0.2f)
            }
        }
        duckRenderable?.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(-0.2f, 0f, 0f)
            }
        }
    }
}
