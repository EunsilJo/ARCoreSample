package com.github.eunsiljo.arcoresample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment

open class ARActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener {

    val arFragment: ArFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_ar) as ArFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)
        arFragment.setOnTapArPlaneListener(this@ARActivity)
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
