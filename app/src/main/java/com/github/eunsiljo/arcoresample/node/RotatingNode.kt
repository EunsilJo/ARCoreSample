package com.github.eunsiljo.arcoresample.node

import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.QuaternionEvaluator
import com.google.ar.sceneform.math.Vector3

class RotatingNode(
    private val duration: Long,
    private val isBackward: Boolean = false
) : Node() {

    private val forwardAnimation: ObjectAnimator by lazy {
        ObjectAnimator().apply {
            setObjectValues(
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 0f),
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 120f),
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 240f),
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 360f)
            )
            propertyName = "localRotation"
            setEvaluator(QuaternionEvaluator())
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
            interpolator = LinearInterpolator()
            setAutoCancel(true)
            target = this@RotatingNode
            duration = this@RotatingNode.duration
        }
    }
    private val backwardAnimation: ObjectAnimator by lazy {
        ObjectAnimator().apply {
            setObjectValues(
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 360f),
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 240f),
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 120f),
                Quaternion.axisAngle(Vector3(0.0f, 1.0f, 0.0f), 0f)
            )
            propertyName = "localRotation"
            setEvaluator(QuaternionEvaluator())
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.RESTART
            interpolator = LinearInterpolator()
            setAutoCancel(true)
            target = this@RotatingNode
            duration = this@RotatingNode.duration
        }
    }

    override fun onActivate() {
        when (isBackward) {
            true -> backwardAnimation.start()
            false -> forwardAnimation.start()
        }
    }

    override fun onUpdate(frameTime: FrameTime) {
    }
}