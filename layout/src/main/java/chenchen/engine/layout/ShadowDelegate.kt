package chenchen.engine.layout

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.FrameLayout
import androidx.core.graphics.withSave
import kotlin.math.abs

/**
 * @Author by chenz
 * @Date 2022/5/27 11:55
 * Description : 阴影实现
 * @param thisView view
 * @param attrs 属性
 */
class ShadowDelegate(private val thisView: View, private val attrs: SuperLayoutAttributes) {

    private val shadowPath = Path()
    private val shadowBounds = RectF()
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    /**
     * 计算阴影区域
     * @param contentRect 内容区域
     */
    fun recalculateRectangle(contentRect: RectF) {
        //阴影矩形
        shadowPath.reset()
        shadowBounds.set(contentRect)
        shadowBounds.inset(attrs.shadowRadius, attrs.shadowRadius)
        //阴影需要考虑到适配状态栏
        shadowBounds.top = if (attrs.isFitSystemBar) {
            shadowBounds.top + attrs.statusBarHeight
        } else {
            shadowBounds.top
        }
        //适配padding，将阴影的区域移动到padding内
        if (attrs.isClipPadding) {
            shadowBounds.left += attrs.paddingInsets.left
            shadowBounds.top += attrs.paddingInsets.top
            shadowBounds.right -= attrs.paddingInsets.right
            shadowBounds.bottom -= attrs.paddingInsets.bottom
        }
        //调整阴影偏移量
        if (attrs.shadowDx > 0) {
            shadowBounds.right -= attrs.shadowDx
        } else if (attrs.shadowDx < 0) {
            shadowBounds.left += abs(attrs.shadowDx)
        }
        if (attrs.shadowDy > 0) {
            shadowBounds.bottom -= attrs.shadowDy
        } else if (attrs.shadowDy < 0) {
            shadowBounds.top += abs(attrs.shadowDy)
        }
        //兼容瞎几把乱设置
        if (shadowBounds.width() <= 0) {
            shadowBounds.left = 0f
            shadowBounds.right = 0f
        }
        if (shadowBounds.height() <= 0) {
            shadowBounds.top = 0f
            shadowBounds.bottom = 0f
        }
        if (attrs.isCircle) {
            shadowPath.addOval(shadowBounds, Path.Direction.CW)
        } else {
            shadowPath.addRoundRect(shadowBounds, attrs.cornerRadii, Path.Direction.CW)
        }
        shadowPath.close()
    }

    /**
     * 绘制阴影
     */
    fun drawShadow(canvas: Canvas) {
        if (attrs.canDrawShadow()) {
//            shadowPaint.color = (thisView.background as? ColorDrawable)?.color ?: attrs.shadowColor
//            shadowPaint.color = attrs.shadowColor
            shadowPaint.style = Paint.Style.FILL
            shadowPaint.setShadowLayer(attrs.shadowRadius, attrs.shadowDx, attrs.shadowDy, attrs.shadowColor)
            canvas.drawPath(shadowPath, shadowPaint)
        }
    }
}