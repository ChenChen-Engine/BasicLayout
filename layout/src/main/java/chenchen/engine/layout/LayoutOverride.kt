package chenchen.engine.layout

import android.graphics.Canvas
import android.view.MotionEvent

/**
 * @Author by chenz
 * @Date 2022/4/23 22:01
 * Description : 需要重写View的实现
 */
interface LayoutOverride {

    fun setPaddingOverride(left: Int, top: Int, right: Int, bottom: Int)

    fun onMeasureOverride(widthMeasureSpec: Int, heightMeasureSpec: Int)

    fun drawOverride(canvas: Canvas)

    fun onDrawOverride(canvas: Canvas)

    fun onTouchEventOverride(event: MotionEvent): Boolean

    fun onInterceptTouchEventOverride(event: MotionEvent): Boolean

    fun dispatchTouchEventOverride(event: MotionEvent): Boolean

    fun performClick():Boolean
}