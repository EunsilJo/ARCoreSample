package com.github.eunsiljo.arcoresample

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment

class MainActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener {

    private val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }

    private var andyRenderable: ModelRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDevice()) {
            finish()
        }
        setContentView(R.layout.activity_main)

        ModelRenderable.builder()
            .setSource(this, Uri.parse("andy.sfb"))
            .build()
            .thenAccept { renderable -> andyRenderable = renderable }
            .exceptionally {
                Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG).show()
                null
            }

        arFragment.setOnTapArPlaneListener(this)
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
        andyRenderable?.let {
            Node().apply {
                setParent(arFragment.arSceneView.scene)
                renderable = andyRenderable
            }
        }
    }
}
