package chenchen.engine.layout

import android.graphics.Canvas
import android.view.View

/**
 *
 * @Author by chenz
 * @Date 2022/5/27 11:55
 * Description : 旋转实现
 * @param thisView view
 * @param attrs 属性
 */
internal class RotatableDelegate(private val thisView: View, private val attrs: SuperLayoutAttributes) : Rotatable {

    /**
     * 旋转之前角度
     */
    private var preRotation = 0f

    /**
     * 上次旋转时间戳，开始旋转时间戳
     */
    private var preRotationTime: Long = 0

    /**
     * 上次旋转时间戳，开始旋转时间戳
     */
    private var rotateStartTime: Long = 0

    /**
     * 是否活动的，是否开始的
     */
    private var rotateAlive = false

    /**
     * 是否活动的，是否开始的
     */
    private var rotateStarted = false

    fun onVisibilityChanged(view: View, isVisible: Boolean) {
        if (isVisible) {
            rotateAlive = true
            if (attrs.isAutoStartRotate) {
                startRotate()
            }
            view.invalidate()
        } else {
            stopRotate()
        }
    }

    /**
     * @param canvas  画布
     */
    fun onDraw(canvas: Canvas) {
        if (!rotateStarted || !rotateAlive) {
            return
        }
        if (0L == rotateStartTime) {
            rotateStartTime = System.currentTimeMillis()
        }
        val current = System.currentTimeMillis()
        var rotation = 0f
        if (attrs.isSmoothRotate) {
            val delta: Float =
                (current - rotateStartTime) % attrs.rotateDuration * 1f / attrs.rotateDuration * 360
            rotation = if (attrs.isClockwiseRotate) delta else 360 - delta
        } else {
            if (0L == preRotationTime || current - preRotationTime > attrs.rotateIntervalTime) {
                preRotation += (if (attrs.isClockwiseRotate) 1 else -1) * attrs.rotateIntervalDegree
                rotation = preRotation
                preRotationTime = System.currentTimeMillis()
            }
        }
        if (attrs.isCanvasRotate) {
            val c = rotation / 360 * 2 * Math.PI.toFloat()
            // 转换为弧度2π为360度
            canvas.rotate(c, thisView.measuredWidth / 2f, thisView.measuredHeight / 2f)
        } else {
            thisView.rotation = rotation
        }
        thisView.invalidate()
    }


    /**
     * 开始旋转
     */
    override fun startRotate() = apply {
        rotateStarted = true
        thisView.invalidate()
        rotateStartTime = 0
    }

    /**
     * 停止旋转
     */
    override fun stopRotate() = apply {
        rotateStarted = false
        thisView.invalidate()
    }


    /**
     * 重置旋转，恢复View默认状态
     */
    override fun resetRotate() = apply {
        thisView.rotation = 0f
        thisView.invalidate()
    }
}