package chenchen.engine.layout

import androidx.annotation.ColorInt

/**
 * @Author by chenz
 * @Date 2022/4/23 21:40
 * Description : Layout属性或字段的设置，提供代码设置方式
 */
interface LayoutAttrs : AutoCheckable {

    /**
     * 获取左上圆角
     */
    val leftTopRadius: Float

    /**
     * 获取右上圆角
     */
    val rightTopRadius: Float

    /**
     * 获取左下圆角
     */
    val leftBottomRadius: Float

    /**
     * 获取右下圆角
     */
    val rightBottomRadius: Float

    /**
     * 左边真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    val leftPadding: Int

    /**
     * 顶部真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    val topPadding: Int

    /**
     * 右边真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    val rightPadding: Int

    /**
     * 底部真实的Padding，就是设置多少是多少，不包含内部处理过的
     */
    val bottomPadding: Int

    /**
     * 设置圆形，优先级比圆角高，设置原型后无法看到圆角
     */
    val isCircle: Boolean

    /**
     * 四个角的原角度
     */
    val cornerRadius: Float

    /**
     * 边框宽度
     */
    val strokeWidth: Float

    /**
     * 边框颜色
     */
    val strokeColor: Int

    /**
     * 是否可以触摸
     */
    val isTouchable: Boolean

    /**
     * 裁剪圆角的时候是否考虑padding在内，默认true
     */
    val isClipPadding: Boolean

    /**
     * 按比例设置容器的大小
     */
    val aspectRatio: Float

    /**
     * 设置容器比例的方向，按垂直或水平的方向设置比例，配合[aspectRatio]使用，单独使用无效
     */
    val fixOrientation: FixOrientation

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度
     */
    val isFitSystemBar: Boolean

    /**
     * 阴影颜色
     */
    val shadowColor: Int

    /**
     * 阴影半径
     */
    val shadowRadius: Float

    /**
     * 阴影x轴偏移量
     */
    val shadowDx: Float

    /**
     * 阴影y轴偏移量
     */
    val shadowDy: Float

    /**
     * 是否画布旋转模式
     */
    val isCanvasRotate: Boolean

    /**
     * 时间间隔
     */
    val rotateIntervalTime: Int

    /**
     * 旋转角度
     */
    val rotateIntervalDegree: Float

    /**
     * 是否连续旋转
     */
    val isSmoothRotate: Boolean

    /**
     * 是否顺时针
     */
    val isClockwiseRotate: Boolean

    /**
     * 持续时间
     */
    val rotateDuration: Int

    /**
     * 是否自动启动
     */
    val isAutoStartRotate: Boolean

    /**
     * 是否启用选中
     */
    val isAutoCheckable: Boolean

    /**
     * 是否接受父View的选中状态，即使是动态添加的也会接受
     */
    override val isReceiveCheckFromParent: Boolean

    /**
     * 是否将选中状态传递给子View
     */
    override val isTransitiveCheckToChild: Boolean

    /**
     * 是否可以触摸
     * @param isTouchable 是否可以触摸
     */
    fun setTouchable(isTouchable: Boolean): LayoutAttrs

    /**
     * 裁剪圆角的时候是否包含padding，默认true
     * @param isClipPadding 是否包含padding
     */
    fun setClipPadding(isClipPadding: Boolean): LayoutAttrs

    /**
     * 按比例设置容器的大小
     * @param ratio 比例，例如9/16
     */
    fun setAspectRatio(ratio: Float): LayoutAttrs

    /**
     * 按比例设置容器的大小
     */
    fun setAspectRatio(ratio: AspectRatio): LayoutAttrs

    /**
     * 设置容器比例的方向，按垂直或水平的方向设置比例，配合[setAspectRatio]使用，单独使用无效
     * @param orientation 是否按垂直方向设置比例
     */
    fun setFixOrientation(orientation: FixOrientation): LayoutAttrs

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度，不是全屏的容器，不建议使用，不然会多出一个状态栏高度
     * @param isFitSystemBar 是否适配状态栏
     */
    fun setFitSystemBar(isFitSystemBar: Boolean): LayoutAttrs

    /**
     * 设置圆形，优先级比圆角高，设置原型后无法看到圆角
     * @param isCircle 是否圆形
     */
    fun setCircle(isCircle: Boolean): LayoutAttrs

    /**
     * 设置统一圆角
     * @param radius 圆角半径
     */
    fun setCornerRadius(radius: Float): LayoutAttrs

    /**
     * 单独设置圆角
     * @param  leftTopRadius 左上圆角半径
     * @param rightTopRadius 右上圆角半径
     * @param leftBottomRadius 左下圆角半径
     * @param rightBottomRadius 右下圆角半径
     */
    fun setCornerRadius(leftTopRadius: Float, rightTopRadius: Float,
        leftBottomRadius: Float, rightBottomRadius: Float): LayoutAttrs

    /**
     * 设置边框
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     */
    fun setBorder(strokeWidth: Float, @ColorInt strokeColor: Int): LayoutAttrs

    /**
     * 设置阴影颜色
     * @param color 阴影颜色，十六进制，0x0000000
     * @param shadowRadius 阴影半径，决定模糊扩散大小和模糊清晰度
     * @param shadowDx 阴影x偏移量
     * @param shadowDy 阴影y偏移量
     */
    fun setShadowStyle(color: Int, shadowRadius: Float, shadowDx: Float, shadowDy: Float): LayoutAttrs

    /**
     * 设置旋转间隔时间
     *
     * @param intervalTime 时间间隔 in ms
     */
    fun setRotateIntervalTime(intervalTime: Int): LayoutAttrs

    /**
     * 设置旋转间隔角度
     *
     * @param intervalDegree 间隔角度
     */
    fun setRotateIntervalDegree(intervalDegree: Float): LayoutAttrs

    /**
     * 设置旋转一周持续时长
     *
     * @param duration in ms
     */
    fun setRotateDuration(duration: Int): LayoutAttrs

    /**
     * 设置是否循环旋转
     *
     * @param smooth 是否转圈
     */
    fun setSmoothRotate(smooth: Boolean): LayoutAttrs

    /**
     * 设置是否顺时针旋转
     *
     * @param clockwise true 顺时针；false 逆时针
     */
    fun setClockwiseRotate(clockwise: Boolean): LayoutAttrs

    /**
     * 是否旋转画布
     * @param isRotate true 旋转
     */
    fun setCanvasRotate(isRotate: Boolean): LayoutAttrs

    /**
     * 是否自动旋转
     * @param isAudio true 自动旋转
     */
    fun setAutoStartRotate(isAudio: Boolean): LayoutAttrs

    /**
     * 是否开启选中功能，开启后就像CheckBox一样使用，不需要设置点击事件改变状态
     * @param isCheckable true 启用
     */
    fun setAutoCheckable(isCheckable: Boolean): LayoutAttrs

    /**
     *  是否接受父View的选中状态，即使是动态添加的也会接受
     *  @param isReceive true 接受
     */
    fun setReceiveCheckFromParent(isReceive: Boolean): AutoCheckable

    /**
     * 是否将选中状态传递给子View
     * @param isTransitive true 传递
     */
    fun setTransitiveCheckToChild(isTransitive: Boolean): AutoCheckable

    /**
     * 设置选中，[isAutoCheckable]为false时不可修改选中状态
     * @param isCheck true 选中
     */
    override fun setChecked(isCheck: Boolean)

    /**
     * 是否选中
     */
    override fun isChecked(): Boolean

    /**
     * 翻转选中，[isAutoCheckable]为false时不可修改选中状态
     */
    override fun toggle()
}