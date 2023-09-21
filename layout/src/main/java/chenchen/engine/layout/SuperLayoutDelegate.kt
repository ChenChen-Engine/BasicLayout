package chenchen.engine.layout

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import kotlin.math.abs

/**
 * @Author by chenz
 * @Date 2022/4/23 21:58
 * Description : 超级强大的View的实现
 * @param thisView 代理的View
 * @param attrs View的属性
 * @param onCallSuper 代理View的super方法
 */
class SuperLayoutDelegate : SuperLayout, LayoutOverride {


    private lateinit var thisView: View

    private lateinit var layoutAttrs: SuperLayoutAttributes

    private lateinit var onCallSuper: OnCallSuper

    /**
     * View的大小区域，每次measure时记录
     */
    private val bounds = RectF()

    /**
     * 旋转实现
     */
    private val rotateDelegate by lazy { RotatableDelegate(this.thisView, layoutAttrs) }

    /**
     * 阴影实现
     */
    private val shadowDelegate by lazy { ShadowDelegate(this.thisView, layoutAttrs) }

    /**
     * 圆角实现
     */
    private val cornerDelegate by lazy { CornerDelegate(this.thisView, layoutAttrs) }

    /**
     * 选中实现
     */
    private val checkableDelegate by lazy { AutoCheckableDelegate(this.thisView, layoutAttrs) }

    /**
     * 可见监听
     */
    private val onVisibleChangeListeners = arrayListOf<OnVisibleChangeListener>()

    /**
     * 依附监听
     */
    private val onAttachChangeListeners = arrayListOf<OnAttachChangeListener>()

    /**
     * 选中监听
     */
    private val onCheckedChangeListeners = arrayListOf<OnCheckedChangeListener>()

    /**
     * 构造方法
     */
    fun constructor(thisView: View, attrs: AttributeSet?, onCallSuper: OnCallSuper) {
        this.thisView = thisView
        this.onCallSuper = onCallSuper
        layoutAttrs = SuperLayoutAttributes(thisView, attrs)
        layoutAttrs.initAttrs()
    }


    /*----------------------------------重写功能----------------------------------*/

    override fun setPaddingOverride(left: Int, top: Int, right: Int, bottom: Int) {
        layoutAttrs.paddingInsets = Insets.of(left, top, right, bottom)
        var leftPadding = left
        var topPadding = top
        var rightPadding = right
        var bottomPadding = bottom
        //适配状态栏
        if (layoutAttrs.isFitSystemBar && !this.thisView.fitsSystemWindows) {
            topPadding += layoutAttrs.statusBarHeight
        }
        //适配阴影
        if (layoutAttrs.canDrawShadow()) {
            //缩小一圈，以便能完全展示阴影
            leftPadding += layoutAttrs.shadowRadius.toInt()
            topPadding += layoutAttrs.shadowRadius.toInt()
            rightPadding += layoutAttrs.shadowRadius.toInt()
            bottomPadding += layoutAttrs.shadowRadius.toInt()
            //调整阴影偏移量
            if (layoutAttrs.shadowDx > 0) {
                rightPadding += layoutAttrs.shadowDx.toInt()
            } else if (layoutAttrs.shadowDx < 0) {
                leftPadding += abs(layoutAttrs.shadowDx).toInt()
            }
            if (layoutAttrs.shadowDy > 0) {
                bottomPadding += layoutAttrs.shadowDy.toInt()
            } else if (layoutAttrs.shadowDy < 0) {
                topPadding += abs(layoutAttrs.shadowDy).toInt()
            }
        }
        onCallSuper.superSetPadding(leftPadding, topPadding, rightPadding, bottomPadding)
    }

    override fun onMeasureOverride(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        val specWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        when {
            layoutAttrs.aspectRatio > 0 -> {
                val width = if (layoutAttrs.fixOrientation is FixOrientation.Horizontal) {
                    specWidth
                } else {
                    (specHeight * layoutAttrs.aspectRatio).toInt()
                }
                val height = if (layoutAttrs.fixOrientation is FixOrientation.Horizontal) {
                    (width / layoutAttrs.aspectRatio).toInt()
                } else {
                    specHeight
                }
                widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    width, View.MeasureSpec.EXACTLY)
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                    height, View.MeasureSpec.EXACTLY)
                bounds.set(0f, 0f, width.toFloat(), height.toFloat())
                onCallSuper.superOnMeasure(widthMeasureSpec, heightMeasureSpec)
            }
            layoutAttrs.aspectRatio < 0 -> {
                val params: ViewGroup.LayoutParams = this.thisView.layoutParams
                if (params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        specHeight, View.MeasureSpec.EXACTLY)
                }
                if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                        specWidth, View.MeasureSpec.EXACTLY)
                }
                onCallSuper.superOnMeasure(widthMeasureSpec, heightMeasureSpec)
            }
            else -> {
                onCallSuper.superOnMeasure(widthMeasureSpec, heightMeasureSpec)
                bounds.set(0f, 0f, thisView.measuredWidth.toFloat(), thisView.measuredHeight.toFloat())
            }
        }
        recalculateRectangle(bounds)
    }

    /**
     * 重新计算各种矩形
     */
    private fun recalculateRectangle(bounds: RectF) {
        //计算圆角区域
        cornerDelegate.recalculateRectangle(bounds)
        //计算阴影区域
        shadowDelegate.recalculateRectangle(bounds)
    }

    override fun drawOverride(canvas: Canvas) {
        //绘制阴影
        shadowDelegate.drawShadow(canvas)
        //绘制圆角
        cornerDelegate.drawCorner(canvas)
        //调用super.draw
        onCallSuper.superDraw(canvas)
        //绘制边框
        cornerDelegate.drawBorder(canvas)
        //绘制旋转
        rotateDelegate.onDraw(canvas)
    }

    override fun onDrawOverride(canvas: Canvas) {
    }

    override fun dispatchTouchEventOverride(event: MotionEvent): Boolean {
        return layoutAttrs.isTouchable && onCallSuper.superDispatchTouchEvent(event)
    }

    override fun onInterceptTouchEventOverride(event: MotionEvent): Boolean {
        return layoutAttrs.isTouchable && onCallSuper.superOnInterceptTouchEvent(event)
    }

    override fun onTouchEventOverride(event: MotionEvent): Boolean {
        return layoutAttrs.isTouchable && onCallSuper.superOnTouchEvent(event)
    }

    override fun performClick(): Boolean {
        toggle()
        return onCallSuper.superPerformClick()
    }

    /**
     * 是否可见
     * @param view 可见性变化的View
     * @param isVisible 是否可见
     */
    fun onVisibleChange(view: View, isVisible: Boolean) {
        onVisibleChangeListeners.forEach { listener ->
            listener.onChange(view, isVisible)
        }
        rotateDelegate.onVisibilityChanged(view, isVisible)
    }

    /**
     * 依附到window
     */
    fun onAttachedToWindow() {
        onAttachChangeListeners.forEach { listener ->
            listener.onChange(this.thisView, true)
        }
    }

    /**
     * 从window脱离
     */
    fun onDetachedFromWindow() {
        onAttachChangeListeners.forEach { listener ->
            listener.onChange(this.thisView, false)
        }
    }

    /**
     * 添加子View
     */
    fun onViewAdded(view: View) {
        //添加子View的时候确认是不是AutoCheckable类型并且接受父级的选中状态
        if (view is AutoCheckable && view.isReceiveCheckFromParent) {
            view.isChecked = layoutAttrs.isChecked
        }
    }

    /*----------------------------------重写功能----------------------------------*/

    /*----------------------------------通用功能----------------------------------*/

    /**
     * 代理的对象
     */
    override val delegateView: View
        get() = thisView

    /**
     * 获取左上圆角
     */
    override val leftTopRadius: Float
        get() = layoutAttrs.leftTopRadius

    /**
     * 获取右上圆角
     */
    override val rightTopRadius: Float
        get() = layoutAttrs.rightTopRadius

    /**
     * 获取左下圆角
     */
    override val leftBottomRadius: Float
        get() = layoutAttrs.leftBottomRadius

    /**
     * 获取右下圆角
     */
    override val rightBottomRadius: Float
        get() = layoutAttrs.rightBottomRadius

    /**
     * 左边真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val leftPadding: Int
        get() = layoutAttrs.leftPadding

    /**
     * 顶部真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val topPadding: Int
        get() = layoutAttrs.topPadding

    /**
     * 右边真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val rightPadding: Int
        get() = layoutAttrs.rightPadding

    /**
     * 底部真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val bottomPadding: Int
        get() = layoutAttrs.bottomPadding

    /**
     * 设置圆形，优先级比圆角高，设置原型后无法看到圆角
     */
    override val isCircle: Boolean
        get() = layoutAttrs.isCircle

    /**
     * 四个角的原角度
     */
    override val cornerRadius: Float
        get() = layoutAttrs.cornerRadius

    /**
     * 边框宽度
     */
    override val strokeWidth: Float
        get() = layoutAttrs.strokeWidth

    /**
     * 边框颜色
     */
    override val strokeColor: Int
        get() = layoutAttrs.strokeColor

    /**
     * 是否可以触摸
     */
    override val isTouchable: Boolean
        get() = layoutAttrs.isTouchable

    /**
     * 裁剪圆角的时候是否考虑padding在内，默认true
     */
    override val isClipPadding: Boolean
        get() = layoutAttrs.isClipPadding

    /**
     * 按比例设置容器的大小
     */
    override val aspectRatio: Float
        get() = layoutAttrs.aspectRatio

    /**
     * 设置容器比例的方向，按垂直或水平的方向设置比例，配合[aspectRatio]使用，单独使用无效
     */
    override val fixOrientation: FixOrientation
        get() = layoutAttrs.fixOrientation

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度
     */
    override val isFitSystemBar: Boolean
        get() = layoutAttrs.isFitSystemBar

    /**
     * 阴影颜色
     */
    override val shadowColor: Int
        get() = layoutAttrs.shadowColor

    /**
     * 阴影半径
     */
    override val shadowRadius: Float
        get() = layoutAttrs.shadowRadius

    /**
     * 阴影x轴偏移量
     */
    override val shadowDx: Float
        get() = layoutAttrs.shadowDx

    /**
     * 阴影y轴偏移量
     */
    override val shadowDy: Float
        get() = layoutAttrs.shadowDy

    /**
     * 是否画布旋转模式
     */
    override val isCanvasRotate: Boolean
        get() = layoutAttrs.isCanvasRotate

    /**
     * 时间间隔
     */
    override val rotateIntervalTime: Int
        get() = layoutAttrs.rotateIntervalTime

    /**
     * 旋转角度
     */
    override val rotateIntervalDegree: Float
        get() = layoutAttrs.rotateIntervalDegree

    /**
     * 是否连续旋转
     */
    override val isSmoothRotate: Boolean
        get() = layoutAttrs.isSmoothRotate

    /**
     * 是否顺时针
     */
    override val isClockwiseRotate: Boolean
        get() = layoutAttrs.isClockwiseRotate

    /**
     * 持续时间
     */
    override val rotateDuration: Int
        get() = layoutAttrs.rotateDuration

    /**
     * 是否自动启动
     */
    override val isAutoStartRotate: Boolean
        get() = layoutAttrs.isAutoStartRotate

    /**
     * 是否启用选中
     */
    override val isAutoCheckable: Boolean
        get() = layoutAttrs.isAutoCheckable

    /**
     * 是否接受父View的选中状态，即使是动态添加的也会接受
     */
    override val isReceiveCheckFromParent: Boolean
        get() = layoutAttrs.isReceiveCheckFromParent

    /**
     * 是否将选中状态传递给子View
     */
    override val isTransitiveCheckToChild: Boolean
        get() = layoutAttrs.isTransitiveCheckToChild

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度
     * @param isFitSystemBar 是否适配状态栏
     */
    override fun setFitSystemBar(isFitSystemBar: Boolean) = apply {
        if (layoutAttrs.isFitSystemBar xor isFitSystemBar) {
            layoutAttrs.setFitSystemBar(isFitSystemBar)
            setPaddingOverride(layoutAttrs.paddingInsets.left, layoutAttrs.paddingInsets.top,
                layoutAttrs.paddingInsets.right, layoutAttrs.paddingInsets.bottom)
        }
    }

    /**
     * 是否可以触摸
     * @param isTouchable 是否可以触摸
     */
    override fun setTouchable(isTouchable: Boolean) = apply {
        layoutAttrs.setTouchable(isTouchable)
    }

    /**
     * 裁剪圆角的时候是否包含padding，默认true
     * @param isClipPadding 是否包含padding
     */
    override fun setClipPadding(isClipPadding: Boolean) = apply {
        layoutAttrs.setClipPadding(isClipPadding)
    }

    /**
     * 按比例设置容器的大小
     * @param ratio 比例，例如9/16
     */
    override fun setAspectRatio(ratio: Float) = apply {
        layoutAttrs.setAspectRatio(ratio)
        thisView.requestLayout()
    }

    /**
     * 按比例设置容器的大小
     */
    override fun setAspectRatio(ratio: AspectRatio) = apply {
        layoutAttrs.setAspectRatio(ratio)
        thisView.requestLayout()
    }

    /**
     * 设置容器比例的方向，按垂直或水平的方向设置比例，配合[setAspectRatio]使用，单独使用无效
     * @param orientation 是否按垂直方向设置比例
     */
    override fun setFixOrientation(orientation: FixOrientation) = apply {
        layoutAttrs.setFixOrientation(orientation)
        thisView.requestLayout()
    }

    /**
     * 设置圆形，优先级比圆角高，设置原型后无法看到圆角
     * @param isCircle 是否圆形
     */
    override fun setCircle(isCircle: Boolean) = apply {
        layoutAttrs.setCircle(isCircle)
        thisView.requestLayout()
    }

    /**
     * 设置统一圆角
     * @param radius 圆角半径
     */
    override fun setCornerRadius(radius: Float) = apply {
        layoutAttrs.setCornerRadius(radius)
        thisView.requestLayout()
    }

    /**
     * 单独设置圆角
     * @param  leftTopRadius 左上圆角半径
     * @param rightTopRadius 右上圆角半径
     * @param leftBottomRadius 左下圆角半径
     * @param rightBottomRadius 右下圆角半径
     */
    override fun setCornerRadius(leftTopRadius: Float, rightTopRadius: Float, leftBottomRadius: Float, rightBottomRadius: Float) = apply {
        layoutAttrs.setCornerRadius(leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius)
        thisView.requestLayout()
    }

    /**
     * 设置边框
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     */
    override fun setBorder(strokeWidth: Float, strokeColor: Int) = apply {
        layoutAttrs.setBorder(strokeWidth, strokeColor)
        thisView.requestLayout()
    }

    /**
     * 设置阴影颜色
     * @param color 阴影颜色，十六进制，0x0000000
     * @param shadowRadius 阴影半径，决定模糊扩散大小和模糊清晰度
     * @param shadowDx 阴影x偏移量
     * @param shadowDy 阴影y偏移量
     */
    override fun setShadowStyle(color: Int, shadowRadius: Float, shadowDx: Float, shadowDy: Float) = apply {
        layoutAttrs.setShadowStyle(color, shadowRadius, shadowDx, shadowDy)
        thisView.invalidate()
    }

    /**
     * 开始旋转
     */
    override fun startRotate() = apply {
        rotateDelegate.startRotate()
    }

    /**
     * 停止旋转
     */
    override fun stopRotate() = apply {
        rotateDelegate.stopRotate()
    }

    /**
     * 重置旋转，恢复View默认状态
     */
    override fun resetRotate() = apply {
        rotateDelegate.resetRotate()
    }

    /**
     * 设置旋转间隔时间
     *
     * @param intervalTime 时间间隔 in ms
     */
    override fun setRotateIntervalTime(intervalTime: Int) = apply {
        layoutAttrs.setRotateIntervalTime(intervalTime)
    }

    /**
     * 设置旋转间隔角度
     *
     * @param intervalDegree 间隔角度
     */
    override fun setRotateIntervalDegree(intervalDegree: Float) = apply {
        layoutAttrs.setRotateIntervalDegree(intervalDegree)
    }

    /**
     * 设置旋转一周持续时长
     *
     * @param duration in ms
     */
    override fun setRotateDuration(duration: Int) = apply {
        layoutAttrs.setRotateDuration(duration)
    }

    /**
     * 设置是否循环旋转
     *
     * @param smooth 是否转圈
     */
    override fun setSmoothRotate(smooth: Boolean) = apply {
        layoutAttrs.setSmoothRotate(smooth)
    }

    /**
     * 设置是否顺时针旋转
     *
     * @param clockwise true 顺时针；false 逆时针
     */
    override fun setClockwiseRotate(clockwise: Boolean) = apply {
        layoutAttrs.setClockwiseRotate(clockwise)
    }

    /**
     * 是否旋转画布
     * @param isRotate true 旋转
     */
    override fun setCanvasRotate(isRotate: Boolean) = apply {
        layoutAttrs.setCanvasRotate(isRotate)
    }

    /**
     * 是否自动旋转
     * @param isAudio true 自动旋转
     */
    override fun setAutoStartRotate(isAudio: Boolean) = apply {
        layoutAttrs.setAutoStartRotate(isAudio)
    }

    /**
     * 是否开启选中功能，开启后就像CheckBox一样使用，不需要设置点击事件改变状态
     * @param isCheckable true 启用
     */
    override fun setAutoCheckable(isCheckable: Boolean) = apply {
        layoutAttrs.setAutoCheckable(isCheckable)
    }

    /**
     *  是否接受父View的选中状态，即使是动态添加的也会接受
     *  @param isReceive true 接受
     */
    override fun setReceiveCheckFromParent(isReceive: Boolean) = apply {
        layoutAttrs.setReceiveCheckFromParent(isReceive)
    }

    /**
     * 是否将选中状态传递给子View
     * @param isTransitive true 传递
     */
    override fun setTransitiveCheckToChild(isTransitive: Boolean) = apply {
        layoutAttrs.setTransitiveCheckToChild(isTransitive)
    }

    /**
     * 设置选中，[isAutoCheckable]为false时不可修改选中状态
     * @param isChecked true 选中
     */
    override fun setChecked(isCheck: Boolean) {
        layoutAttrs.isChecked = isCheck
        checkableDelegate.isChecked = isCheck
        onCheckedChangeListeners.forEach { listener ->
            listener.onChange(this.thisView, isCheck)
        }
    }

    /**
     * 是否选中
     */
    override fun isChecked(): Boolean {
        return layoutAttrs.isChecked
    }

    /**
     * 翻转选中，[isAutoCheckable]为false时不可修改选中状态
     */
    override fun toggle() {
        isChecked = !layoutAttrs.isChecked
    }

    /**
     * 添加一个可见监听，无法添加重复对象
     * @param listener
     */
    override fun addVisibleChangeListener(listener: OnVisibleChangeListener) = apply {
        if (!onVisibleChangeListeners.contains(listener)) {
            onVisibleChangeListeners.add(listener)
        }
    }

    /**
     * 移除一个可见监听
     * @param listener 监听器
     */
    override fun removeVisibleChangeListener(listener: OnVisibleChangeListener) = apply {
        onVisibleChangeListeners.remove(listener)
    }

    /**
     * 清空所有可见监听
     */
    override fun clearVisibleChangeListener() = apply {
        onVisibleChangeListeners.clear()
    }

    /**
     * 添加一个视图依附监听，无法添加重复对象
     * @param listener 监听器
     */
    override fun addAttachChangeListener(listener: OnAttachChangeListener) = apply {
        if (!onAttachChangeListeners.contains(listener)) {
            onAttachChangeListeners.add(listener)
        }
    }

    /**
     * 移除一个视图依附监听
     * @param listener 监听器
     */
    override fun removeAttachChangeListener(listener: OnAttachChangeListener) = apply {
        onAttachChangeListeners.remove(listener)
    }

    /**
     * 清空所有视图依附监听
     */
    override fun clearAttachChangeListener() = apply {
        onAttachChangeListeners.clear()
    }

    /**
     * 添加一个选中监听
     * @param listener 监听器
     */
    override fun addCheckedChangeListener(listener: OnCheckedChangeListener) = apply {
        if (!onCheckedChangeListeners.contains(listener)) {
            onCheckedChangeListeners.add(listener)
        }
    }

    /**
     * 移除一个选中监听
     * @param listener 监听器
     */
    override fun removeCheckedChangeListener(listener: OnCheckedChangeListener) = apply {
        onCheckedChangeListeners.remove(listener)
    }

    /**
     * 清除所有选中监听
     */
    override fun clearCheckedChangeListener() = apply {
        onCheckedChangeListeners.clear()
    }

    /*----------------------------------通用功能----------------------------------*/

    interface OnCallSuper {
        fun superSetPadding(left: Int, top: Int, right: Int, bottom: Int)
        fun superOnMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
        fun superSetMeasuredDimension(width: Int, height: Int)
        fun superDraw(canvas: Canvas)
        fun superDispatchTouchEvent(event: MotionEvent): Boolean
        fun superOnInterceptTouchEvent(event: MotionEvent): Boolean
        fun superOnTouchEvent(event: MotionEvent): Boolean
        fun superPerformClick(): Boolean
    }
}