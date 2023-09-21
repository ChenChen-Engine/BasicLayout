package chenchen.engine.layout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @Author by chenz
 * @Date 2022/4/23 20:58
 * Description :
 */
open class SuperConstraintLayout private constructor(
    context: Context,
    attrs: AttributeSet?,
    private val layoutDelegate: SuperLayoutDelegate = SuperLayoutDelegate()
) : ConstraintLayout(context, attrs), SuperLayout by layoutDelegate,
    SuperLayoutDelegate.OnCallSuper {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, SuperLayoutDelegate())

    init {
        setWillNotDraw(false)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        layoutDelegate.constructor(this, attrs, this)
    }

    /*----------------------------------重写功能----------------------------------*/

    override fun isOpaque(): Boolean {
        //28及以下版本默认创建黑色的Bitmap，在裁剪等功能的时候会留下一片黑色区域
        //重写该方法可以创建透明Bitmap，28以上再考虑要不要单独判断
        return false
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        layoutDelegate.setPaddingOverride(left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        layoutDelegate.onMeasureOverride(widthMeasureSpec, heightMeasureSpec)
    }

    @SuppressLint("MissingSuperCall")
    override fun draw(canvas: Canvas) {
        layoutDelegate.drawOverride(canvas)
    }

    override fun onDraw(canvas: Canvas) {
        layoutDelegate.onDrawOverride(canvas)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return layoutDelegate.dispatchTouchEventOverride(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return layoutDelegate.onInterceptTouchEventOverride(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return layoutDelegate.onTouchEventOverride(event)
    }

    override fun performClick(): Boolean {
        return layoutDelegate.performClick()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        layoutDelegate.onVisibleChange(changedView, visibility == VISIBLE)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutDelegate.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        layoutDelegate.onDetachedFromWindow()
    }

    override fun onViewAdded(view: View) {
        super.onViewAdded(view)
        layoutDelegate.onViewAdded(view)
    }

    override fun superSetPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
    }

    @SuppressLint("WrongCall")
    override fun superOnMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun superSetMeasuredDimension(width: Int, height: Int) {
        setMeasuredDimension(width, height)
    }

    override fun superDraw(canvas: Canvas) {
        super.draw(canvas)
    }

    override fun superDispatchTouchEvent(event: MotionEvent): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun superOnInterceptTouchEvent(event: MotionEvent): Boolean {
        return super.onInterceptTouchEvent(event)
    }

    override fun superOnTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)
    }

    override fun superPerformClick(): Boolean {
        return super.performClick()
    }
}