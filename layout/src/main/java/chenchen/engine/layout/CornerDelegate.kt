package chenchen.engine.layout

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.view.View
import android.widget.FrameLayout
import kotlin.math.abs

class CornerDelegate(private val thisView: View, private val attrs: SuperLayoutAttributes) {

    /**
     * 不包含padding的Path
     */
    private val clipPath = Path()
    private val clipBounds = RectF()
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    /**
     * 计算阴影区域
     * @param contentRect 内容区域
     */
    fun recalculateRectangle(contentRect: RectF) {
        //标准的裁剪矩形
        clipPath.reset()
        clipBounds.set(contentRect)
        //适配状态栏，top要减少一点
        clipBounds.top = if (attrs.isFitSystemBar) {
            clipBounds.top + attrs.statusBarHeight
        } else {
            clipBounds.top
        }
        //适配padding，将裁剪的区域移动到padding内
        if (attrs.isClipPadding) {
            clipBounds.left += attrs.paddingInsets.left
            clipBounds.top += attrs.paddingInsets.top
            clipBounds.right -= attrs.paddingInsets.right
            clipBounds.bottom -= attrs.paddingInsets.bottom
        }
        //适配阴影
        if (attrs.canDrawShadow()) {
            //缩小一圈，不能大于阴影
            clipBounds.inset(attrs.shadowRadius, attrs.shadowRadius)
            //调整阴影偏移量
            if (attrs.shadowDx > 0) {
                clipBounds.right -= attrs.shadowDx /*- thisView.scrollX*/
            } else if (attrs.shadowDx < 0) {
                clipBounds.left += abs(attrs.shadowDx) /*+ thisView.scrollX*/
            }
            if (attrs.shadowDy > 0) {
                clipBounds.bottom -= attrs.shadowDy /*- thisView.scrollY*/
            } else if (attrs.shadowDy < 0) {
                clipBounds.top += abs(attrs.shadowDy) /*+ thisView.scrollY*/
            }
        }
        //兼容瞎几把乱设置
        if (clipBounds.width() <= 0) {
            clipBounds.left = 0f
            clipBounds.right = 0f
        }
        if (clipBounds.height() <= 0) {
            clipBounds.top = 0f
            clipBounds.bottom = 0f
        }
        if (attrs.isCircle) {
            clipPath.addOval(clipBounds, Path.Direction.CW)
        } else {
            clipPath.addRoundRect(clipBounds, attrs.cornerRadii, Path.Direction.CW)
        }
        clipPath.close()
    }

    /**
     * 绘制圆角
     */
    fun drawCorner(canvas: Canvas) {
        //绘制圆角
        if (attrs.canDrawShadow() || attrs.cornerRadii.any { it > 0 }) {
            //调整背景的大小，减少一个padding的范围
            if (thisView.background != null && attrs.isClipPadding) {
                thisView.background.setBounds(thisView.paddingLeft,
                    thisView.paddingTop,
                    thisView.measuredWidth - thisView.paddingRight,
                    thisView.measuredHeight - thisView.paddingBottom)
            }
            canvas.clipPath(clipPath)
        }
    }

    /**
     * 绘制边框
     */
    fun drawBorder(canvas: Canvas) {
        if (attrs.strokeWidth > 0) {
            borderPaint.color = attrs.strokeColor
            borderPaint.style = Paint.Style.STROKE
            borderPaint.strokeWidth = attrs.strokeWidth
            canvas.drawOval(clipBounds, borderPaint)
        }
    }
}