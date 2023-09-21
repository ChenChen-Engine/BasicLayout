package chenchen.engine.layout

import android.graphics.Outline
import android.graphics.Path
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider

/**
 * @Author by chenz
 * @Date 2022/4/23 20:28
 * Description : 圆角样式
 */
class CornerOutline : ViewOutlineProvider() {

    internal var radius = 0f
        private set
    internal var leftTopRadius = 0f
    internal var rightTopRadius = 0f
    internal var leftBottomRadius = 0f
    internal var rightBottomRadius = 0f
    private val cornerPath = Path()
    private val corners = FloatArray(8)

    override fun getOutline(view: View, outline: Outline) {
        //>=33版本才能使用path功能，目前最高能获取到31的版本号，只能这样判断了
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            cornerPath.rewind()
            cornerPath.addRoundRect(0f, 0f, view.width.toFloat(), view.height.toFloat(), corners,
                Path.Direction.CW)
            outline.setPath(cornerPath)
        } else {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }

    /**
     * 设置统一圆角
     * @param radius 圆角半径
     */
    fun setCornerRadius(radius: Float) {
        this.radius = radius
        this.leftTopRadius = 0f
        this.rightTopRadius = 0f
        this.leftBottomRadius = 0f
        this.rightBottomRadius = 0f
        updateCorner()
    }

    /**
     * 单独设置圆角
     * @param  leftTopRadius 左上圆角半径
     * @param rightTopRadius 右上圆角半径
     * @param leftBottomRadius 左下圆角半径
     * @param rightBottomRadius 右下圆角半径
     */
    fun setCornerRadius(leftTopRadius: Float, rightTopRadius: Float,
        leftBottomRadius: Float, rightBottomRadius: Float) {
        this.radius = 0f
        this.leftTopRadius = leftTopRadius
        this.rightTopRadius = rightTopRadius
        this.leftBottomRadius = leftBottomRadius
        this.rightBottomRadius = rightBottomRadius
        updateCorner()
    }

    private fun updateCorner() {
        corners[0] = if (radius != 0f) radius else leftTopRadius
        corners[1] = if (radius != 0f) radius else leftTopRadius
        corners[2] = if (radius != 0f) radius else rightTopRadius
        corners[3] = if (radius != 0f) radius else rightTopRadius
        corners[4] = if (radius != 0f) radius else leftBottomRadius
        corners[5] = if (radius != 0f) radius else leftBottomRadius
        corners[6] = if (radius != 0f) radius else rightBottomRadius
        corners[7] = if (radius != 0f) radius else rightBottomRadius
    }

    /**
     * 是否有必要开启
     */
    fun isEnableClipOutline(): Boolean {
        return radius == 0f && leftTopRadius + rightTopRadius + leftBottomRadius + rightBottomRadius == 0f
    }

}