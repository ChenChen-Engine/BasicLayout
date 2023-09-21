package chenchen.engine.layout

import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.Insets
import kotlin.math.max

/**
 * @Author by chenz
 * @Date 2022/4/23 20:59
 * Description : 存储所有Layout公用的属性或者字段
 */
class SuperLayoutAttributes(
    /*托管的View*/
    private val thisView: View,
    /*托管的View的自定义属性*/
    private val attrs: AttributeSet?) : LayoutAttrs {

    /**
     * 系统状态栏高度
     */
    val statusBarHeight by lazy { thisView.statusBarHeight }

    /**
     * 屏幕宽度
     */
    val screenWidth by lazy { thisView.resources.displayMetrics.widthPixels }

    /**
     * 屏幕高度
     */
    val screenHeight by lazy { thisView.resources.displayMetrics.heightPixels }

    /**
     * 记录真实的padding
     */
    internal var paddingInsets = Insets.of(this.thisView.paddingLeft,
        this.thisView.paddingTop, this.thisView.paddingRight, this.thisView.paddingBottom)

    /**
     * 8个方向的原角度，不直接赋值
     */
    val cornerRadii = FloatArray(8)

    /**
     * 设置圆形，优先级比圆角高，设置原型后无法看到圆角
     */
    override var isCircle: Boolean = false
        private set

    /**
     * 四个角的原角度
     */
    override var cornerRadius: Float = 0f
        private set

    /**
     * 边框宽度
     */
    override var strokeWidth: Float = 0f
        private set

    /**
     * 边框颜色
     */
    override var strokeColor: Int = Color.TRANSPARENT
        private set

    /**
     * 是否可以触摸
     */
    override var isTouchable: Boolean = true
        private set

    /**
     * 裁剪圆角的时候是否考虑padding在内，默认true
     */
    override var isClipPadding: Boolean = true
        private set

    /**
     * 按比例设置容器的大小
     */
    override var aspectRatio: Float = 0f
        private set

    /**
     * 设置容器比例的方向，按垂直或水平的方向设置比例，配合[aspectRatio]使用，单独使用无效
     */
    override var fixOrientation: FixOrientation = FixOrientation.Horizontal
        private set

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度
     */
    override var isFitSystemBar: Boolean = false
        private set

    /**
     * 阴影颜色
     */
    override var shadowColor = Color.TRANSPARENT
        private set

    /**
     * 阴影半径
     */
    override var shadowRadius: Float = 0f
        private set

    /**
     * 阴影x轴偏移量
     */
    override var shadowDx: Float = 0f
        private set

    /**
     * 阴影y轴偏移量
     */
    override var shadowDy: Float = 0f
        private set

    /**
     * 是否画布旋转模式
     */
    override var isCanvasRotate: Boolean = false
        private set

    /**
     * 时间间隔
     */
    override var rotateIntervalTime: Int = 100
        private set

    /**
     * 旋转角度
     */
    override var rotateIntervalDegree: Float = 30.0f
        private set

    /**
     * 是否连续旋转
     */
    override var isSmoothRotate: Boolean = true
        private set

    /**
     * 是否顺时针
     */
    override var isClockwiseRotate: Boolean = true
        private set

    /**
     * 持续时间
     */
    override var rotateDuration: Int = 500
        private set

    /**
     * 是否自动启动
     */
    override var isAutoStartRotate: Boolean = false
        private set

    /**
     * 是否启用选中
     */
    override var isAutoCheckable: Boolean = false
        private set

    /**
     * 是否接受父View的选中状态，即使是动态添加的也会接受
     */
    override var isReceiveCheckFromParent: Boolean = false
        private set

    /**
     * 是否将选中状态传递给子View
     */
    override var isTransitiveCheckToChild: Boolean = false
        private set

    /**
     * 是否选中
     */
    private var isCheck: Boolean = false
        private set

    fun initAttrs() {
        val ta = thisView.context.obtainStyledAttributes(attrs, R.styleable.SuperLayoutAttrs).also { ta ->
            setCircle(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_isCircle, false))
            setCornerRadius(ta.getDimension(R.styleable.SuperLayoutAttrs_sla_cornerRadius, 0f))
            if (cornerRadius <= 0) {
                setCornerRadius(ta.getDimension(R.styleable.SuperLayoutAttrs_sla_leftTopRadius, 0f),
                    ta.getDimension(R.styleable.SuperLayoutAttrs_sla_rightTopRadius, 0f),
                    ta.getDimension(R.styleable.SuperLayoutAttrs_sla_leftBottomRadius, 0f),
                    ta.getDimension(R.styleable.SuperLayoutAttrs_sla_rightBottomRadius, 0f))
            }
            setBorder(ta.getDimension(R.styleable.SuperLayoutAttrs_sla_strokeWidth, 0f),
                ta.getColor(R.styleable.SuperLayoutAttrs_sla_strokeColor, Color.TRANSPARENT))
            setAspectRatio(ta.getFloat(R.styleable.SuperLayoutAttrs_sla_aspectRatio, aspectRatio))
            setFixOrientation(if (ta.getInt(R.styleable.SuperLayoutAttrs_sla_fixOrientation, 0) == 1)
                FixOrientation.Vertical else FixOrientation.Horizontal)
            setTouchable(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_isTouchable, isTouchable))
            setClipPadding(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_isClipPadding, isClipPadding))
            setInnerFitSystemBar(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_isFitSystemBar, isFitSystemBar))
            setShadowStyle(
                ta.getColor(R.styleable.SuperLayoutAttrs_sla_shadowColor, shadowColor),
                ta.getDimension(R.styleable.SuperLayoutAttrs_sla_shadowRadius, shadowRadius),
                ta.getDimension(R.styleable.SuperLayoutAttrs_sla_shadowDx, shadowDx),
                ta.getDimension(R.styleable.SuperLayoutAttrs_sla_shadowDy, shadowDy))
            setRotateIntervalTime(ta.getInteger(R.styleable.SuperLayoutAttrs_sla_rotate_interval_time, rotateIntervalTime))
            setRotateIntervalDegree(ta.getFloat(R.styleable.SuperLayoutAttrs_sla_rotate_interval_degree, rotateIntervalDegree))
            setRotateDuration(ta.getInteger(R.styleable.SuperLayoutAttrs_sla_rotate_duration, rotateDuration))
            setClockwiseRotate(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_rotate_clockwise, isClockwiseRotate))
            setSmoothRotate(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_rotate_smooth, isSmoothRotate))
            setAutoStartRotate(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_rotate_auto_start, isAutoStartRotate))
            setAutoCheckable(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_isAutoCheckable, isAutoCheckable))
            setReceiveCheckFromParent(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_isReceiveCheckFromParent, isReceiveCheckFromParent))
            setTransitiveCheckToChild(ta.getBoolean(R.styleable.SuperLayoutAttrs_sla_isTransitiveCheckToChild, isTransitiveCheckToChild))
        }
        ta.recycle()
    }

    /**
     * 获取左上圆角
     */
    override val leftTopRadius: Float
        get() = max(cornerRadii[0], cornerRadii[1])

    /**
     * 获取右上圆角
     */
    override val rightTopRadius: Float
        get() = max(cornerRadii[2], cornerRadii[3])

    /**
     * 获取左下圆角
     */
    override val leftBottomRadius: Float
        get() = max(cornerRadii[4], cornerRadii[5])

    /**
     * 获取右下圆角
     */
    override val rightBottomRadius: Float
        get() = max(cornerRadii[6], cornerRadii[7])

    /**
     * 左边真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val leftPadding: Int
        get() = paddingInsets.left

    /**
     * 顶部真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val topPadding: Int
        get() = paddingInsets.top

    /**
     * 右边真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val rightPadding: Int
        get() = paddingInsets.right

    /**
     * 底部真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    override val bottomPadding: Int
        get() = paddingInsets.bottom

    /**
     * 是否可以触摸
     * @param isTouchable 是否可以触摸
     */
    override fun setTouchable(isTouchable: Boolean) = apply {
        this.isTouchable = isTouchable
    }

    /**
     * 裁剪圆角的时候是否包含padding，默认true
     * @param isClipPadding 是否包含padding
     */
    override fun setClipPadding(isClipPadding: Boolean) = apply {
        this.isClipPadding = isClipPadding
        thisView.requestLayout()
    }

    /**
     * 按比例设置容器的大小
     * @param ratio 比例，例如9/16
     */
    override fun setAspectRatio(ratio: Float) = apply {
        this.aspectRatio = ratio
        thisView.requestLayout()
    }

    /**
     * 按比例设置容器的大小
     */
    override fun setAspectRatio(ratio: AspectRatio) = apply {
        this.aspectRatio = ratio.ratio
        thisView.requestLayout()
    }

    /**
     * 设置容器比例的方向，按垂直或水平的方向设置比例，配合[setAspectRatio]使用，单独使用无效
     * @param isVertical 是否按垂直方向设置比例
     */
    override fun setFixOrientation(orientation: FixOrientation) = apply {
        fixOrientation = orientation
        thisView.requestLayout()
    }

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度，不是全屏的容器，不建议使用，不然会多出一个状态栏高度
     * @param isFitSystemBar 是否适配状态栏
     */
    override fun setFitSystemBar(isFitSystemBar: Boolean) = apply {
        this.isFitSystemBar = isFitSystemBar
    }

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度，不是全屏的容器，不建议使用，不然会多出一个状态栏高度
     * @param isFitSystemBar 是否适配状态栏
     */
    private fun setInnerFitSystemBar(isFitSystemBar: Boolean) = apply {
        this.isFitSystemBar = isFitSystemBar
        thisView.setPadding(paddingInsets.left, paddingInsets.top,
            paddingInsets.right, paddingInsets.bottom)
    }

    /**
     * 设置圆形，优先级比圆角高，设置原型后无法看到圆角
     * @param isCircle 是否圆形
     */
    override fun setCircle(isCircle: Boolean) = apply {
        this.isCircle = isCircle
        thisView.invalidate()
    }

    /**
     * 设置统一圆角
     * @param radius 圆角半径
     */
    override fun setCornerRadius(radius: Float) = apply {
        this.cornerRadius = radius
        updateCorner(radius, radius, radius, radius)
        thisView.invalidate()
//        新方案，需要sdk>33才能用
//        cornerOutline.setCornerRadius(radius)
//        viewGroup.clipToOutline = cornerOutline.isEnableClipOutline()
    }

    /**
     * 单独设置圆角，SDK必须大于等于33才能使用
     * @param  leftTopRadius 左上圆角半径
     * @param rightTopRadius 右上圆角半径
     * @param leftBottomRadius 左下圆角半径
     * @param rightBottomRadius 右下圆角半径
     */
    override fun setCornerRadius(leftTopRadius: Float, rightTopRadius: Float,
        leftBottomRadius: Float, rightBottomRadius: Float) = apply {
        this.cornerRadius = 0f
        updateCorner(leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius)
        thisView.invalidate()
//        新方案，需要sdk>33才能用
//        cornerOutline.setCornerRadius(leftTopRadius, rightTopRadius,
//            leftBottomRadius, rightBottomRadius)
//        viewGroup.clipToOutline = cornerOutline.isEnableClipOutline()
    }

    private fun updateCorner(leftTopRadius: Float, rightTopRadius: Float,
        leftBottomRadius: Float, rightBottomRadius: Float) {
        cornerRadii[0] = if (cornerRadius != 0f) cornerRadius else leftTopRadius
        cornerRadii[1] = if (cornerRadius != 0f) cornerRadius else leftTopRadius
        cornerRadii[2] = if (cornerRadius != 0f) cornerRadius else rightTopRadius
        cornerRadii[3] = if (cornerRadius != 0f) cornerRadius else rightTopRadius
        cornerRadii[4] = if (cornerRadius != 0f) cornerRadius else rightBottomRadius
        cornerRadii[5] = if (cornerRadius != 0f) cornerRadius else rightBottomRadius
        cornerRadii[6] = if (cornerRadius != 0f) cornerRadius else leftBottomRadius
        cornerRadii[7] = if (cornerRadius != 0f) cornerRadius else leftBottomRadius
    }

    /**
     * 设置边框
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     */
    override fun setBorder(strokeWidth: Float, @ColorInt strokeColor: Int) = apply {
        this.strokeWidth = strokeWidth
        this.strokeColor = strokeColor
        thisView.invalidate()
    }

    /**
     * 设置阴影颜色
     * @param color 阴影颜色，十六进制，0x0000000
     * @param shadowRadius 阴影半径，决定模糊扩散大小和模糊清晰度
     * @param shadowDx 阴影x偏移量
     * @param shadowDy 阴影y偏移量
     */
    override fun setShadowStyle(color: Int, shadowRadius: Float, shadowDx: Float, shadowDy: Float) = apply {
        this.shadowColor = color
        this.shadowRadius = shadowRadius
        this.shadowDx = shadowDx
        this.shadowDy = shadowDy
        thisView.setPadding(paddingInsets.left, paddingInsets.top, paddingInsets.right, paddingInsets.bottom)
        thisView.invalidate()
    }

    /**
     * 是否有阴影，如果有阴影则需要绘制
     */
    fun canDrawShadow(): Boolean {
        return shadowColor != 0 && shadowRadius != 0f
    }

    /**
     * 设置旋转间隔时间
     *
     * @param intervalTime 时间间隔 in ms
     */
    override fun setRotateIntervalTime(intervalTime: Int) = apply {
        this.rotateIntervalTime = intervalTime
    }

    /**
     * 设置旋转间隔角度
     *
     * @param intervalDegree 间隔角度
     */
    override fun setRotateIntervalDegree(intervalDegree: Float) = apply {
        this.rotateIntervalDegree = intervalDegree
    }

    /**
     * 设置旋转一周持续时长
     *
     * @param duration in ms
     */
    override fun setRotateDuration(duration: Int) = apply {
        this.rotateDuration = duration
    }

    /**
     * 设置是否循环旋转
     *
     * @param smooth 是否转圈
     */
    override fun setSmoothRotate(smooth: Boolean) = apply {
        isSmoothRotate = smooth
    }

    /**
     * 设置是否顺时针旋转
     *
     * @param clockwise true 顺时针；false 逆时针
     */
    override fun setClockwiseRotate(clockwise: Boolean) = apply {
        isClockwiseRotate = clockwise
    }

    /**
     * 是否旋转画布
     * @param isRotate true 旋转
     */
    override fun setCanvasRotate(isRotate: Boolean) = apply {
        this.isCanvasRotate = isRotate
    }

    /**
     * 是否自动旋转
     * @param isAudio true 自动旋转
     */
    override fun setAutoStartRotate(isAudio: Boolean) = apply {
        this.isAutoStartRotate = isAudio
    }

    /**
     * 是否开启选中功能，开启后就像CheckBox一样使用，不需要设置点击事件改变状态
     * @param isCheckable true 启用
     */
    override fun setAutoCheckable(isCheckable: Boolean) = apply {
        this.isAutoCheckable = isCheckable
        if (isCheckable) {
            thisView.isClickable = isCheckable
        }
        thisView.requestLayout()
    }

    /**
     *  是否接受父View的选中状态，即使是动态添加的也会接受
     *  @param isReceive true 接受
     */
    override fun setReceiveCheckFromParent(isReceive: Boolean) = apply {
        this.isReceiveCheckFromParent = isReceive
        thisView.requestLayout()
    }

    /**
     * 是否将选中状态传递给子View
     * @param isTransitive true 传递
     */
    override fun setTransitiveCheckToChild(isTransitive: Boolean) = apply {
        this.isTransitiveCheckToChild = isTransitive
        thisView.requestLayout()
    }

    /**
     * 设置选中，[isAutoCheckable]为false时不可修改选中状态
     * @param isCheck true 选中
     */
    override fun setChecked(isCheck: Boolean) {
        this.isCheck = isCheck
        thisView.requestLayout()
    }

    /**
     * 是否选中
     */
    override fun isChecked(): Boolean {
        return this.isCheck
    }

    /**
     * 翻转选中，[isAutoCheckable]为false时不可修改选中状态
     */
    override fun toggle() {
        //这里不实现
    }
}