package chenchen.engine.layout

import android.view.View

/**
 * @Author by chenz
 * @Date 2022/4/23 22:01
 * Description : 超级View的功能
 */
interface SuperLayout : LayoutAttrs, Rotatable {

    /**
     * 代理的对象
     */
    val delegateView: View

    /**
     * 是否可以触摸
     * @param isTouchable 是否可以触摸
     */
    override fun setTouchable(isTouchable: Boolean): SuperLayout

    /**
     * 裁剪圆角的时候是否包含padding，默认true
     * @param isClipPadding 是否包含padding
     */
    override fun setClipPadding(isClipPadding: Boolean): SuperLayout

    /**
     * 按比例设置容器的大小
     * @param ratio 比例，例如9/16
     */
    override fun setAspectRatio(ratio: Float): SuperLayout

    /**
     * 按比例设置容器的大小
     */
    override fun setAspectRatio(ratio: AspectRatio): SuperLayout

    /**
     * 设置容器比例的方向，按垂直或水平的方向设置比例，配合[setAspectRatio]使用，单独使用无效
     * @param orientation 是否按垂直方向设置比例
     */
    override fun setFixOrientation(orientation: FixOrientation): SuperLayout

    /**
     * 适配状态栏，增加一个状态栏的topPadding高度，不是全屏的容器，不建议使用，不然会多出一个状态栏高度
     * @param isFitSystemBar 是否适配状态栏
     */
    override fun setFitSystemBar(isFitSystemBar: Boolean): SuperLayout

    /**
     * 设置圆形，优先级比圆角高，设置原型后无法看到圆角
     * @param isCircle 是否圆形
     */
    override fun setCircle(isCircle: Boolean): SuperLayout

    /**
     * 设置统一圆角
     * @param radius 圆角半径
     */
    override fun setCornerRadius(radius: Float): SuperLayout

    /**
     * 单独设置圆角
     * @param  leftTopRadius 左上圆角半径
     * @param rightTopRadius 右上圆角半径
     * @param leftBottomRadius 左下圆角半径
     * @param rightBottomRadius 右下圆角半径
     */
    override fun setCornerRadius(leftTopRadius: Float, rightTopRadius: Float, leftBottomRadius: Float, rightBottomRadius: Float): SuperLayout

    /**
     * 设置边框
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     */
    override fun setBorder(strokeWidth: Float, strokeColor: Int): SuperLayout

    /**
     * 设置阴影颜色
     * @param color 阴影颜色，十六进制，0x0000000
     * @param shadowRadius 阴影半径，决定模糊扩散大小和模糊清晰度
     * @param shadowDx 阴影x偏移量
     * @param shadowDy 阴影y偏移量
     */
    override fun setShadowStyle(color: Int, shadowRadius: Float, shadowDx: Float, shadowDy: Float): SuperLayout

    /**
     * 设置旋转间隔时间
     *
     * @param intervalTime 时间间隔 in ms
     */
    override fun setRotateIntervalTime(intervalTime: Int): SuperLayout

    /**
     * 设置旋转间隔角度
     *
     * @param intervalDegree 间隔角度
     */
    override fun setRotateIntervalDegree(intervalDegree: Float): SuperLayout

    /**
     * 设置旋转一周持续时长
     *
     * @param duration in ms
     */
    override fun setRotateDuration(duration: Int): SuperLayout

    /**
     * 设置是否循环旋转
     *
     * @param smooth 是否转圈
     */
    override fun setSmoothRotate(smooth: Boolean): SuperLayout

    /**
     * 设置是否顺时针旋转
     *
     * @param clockwise true 顺时针；false 逆时针
     */
    override fun setClockwiseRotate(clockwise: Boolean): SuperLayout

    /**
     * 是否旋转画布
     * @param isRotate true 旋转
     */
    override fun setCanvasRotate(isRotate: Boolean): SuperLayout

    /**
     * 是否自动旋转
     * @param isAudio true 自动旋转
     */
    override fun setAutoStartRotate(isAudio: Boolean): SuperLayout

    /**
     * 开始旋转
     */
    override fun startRotate(): SuperLayout

    /**
     * 停止旋转
     */
    override fun stopRotate(): SuperLayout

    /**
     * 重置旋转，恢复View默认状态
     */
    override fun resetRotate(): SuperLayout

    /**
     * 是否开启选中功能，开启后就像CheckBox一样使用，不需要设置点击事件改变状态
     * @param isCheckable true 启用
     */
    override fun setAutoCheckable(isCheckable: Boolean): SuperLayout

    /**
     *  是否接受父View的选中状态，即使是动态添加的也会接受
     *  @param isReceive true 接受
     */
    override fun setReceiveCheckFromParent(isReceive: Boolean): SuperLayout

    /**
     * 是否将选中状态传递给子View
     * @param isTransitive true 传递
     */
    override fun setTransitiveCheckToChild(isTransitive: Boolean): SuperLayout

    /**
     * 添加一个可见监听，无法添加重复对象
     * @param listener
     */
    fun addVisibleChangeListener(listener: OnVisibleChangeListener): SuperLayout

    /**
     * 移除一个可见监听
     * @param listener 监听器
     */
    fun removeVisibleChangeListener(listener: OnVisibleChangeListener): SuperLayout

    /**
     * 清空所有可见监听
     */
    fun clearVisibleChangeListener(): SuperLayout

    /**
     * 添加一个视图依附监听，无法添加重复对象
     * @param listener 监听器
     */
    fun addAttachChangeListener(listener: OnAttachChangeListener): SuperLayout

    /**
     * 移除一个视图依附监听
     * @param listener 监听器
     */
    fun removeAttachChangeListener(listener: OnAttachChangeListener): SuperLayout

    /**
     * 清空所有视图依附监听
     */
    fun clearAttachChangeListener(): SuperLayout

    /**
     * 添加一个选中监听
     * @param listener 监听器
     */
    fun addCheckedChangeListener(listener: OnCheckedChangeListener): SuperLayout

    /**
     * 移除一个选中监听
     * @param listener 监听器
     */
    fun removeCheckedChangeListener(listener: OnCheckedChangeListener): SuperLayout

    /**
     * 清除所有选中监听
     */
    fun clearCheckedChangeListener(): SuperLayout
}