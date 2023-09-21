package chenchen.engine.layout.animate

import android.animation.ObjectAnimator
import androidx.annotation.ColorInt
import chenchen.engine.animate.ObjectAnimate
import chenchen.engine.layout.SuperLayout
import kotlin.math.max

/**
 * SuperLayout的圆角动画
 */
class SuperLayoutCornerAnimate(
    target: SuperLayout, values: FloatArray
) : ObjectAnimate<SuperLayout, FloatArray>(
    target, values,
    ObjectAnimator.ofFloat(target, SuperLayoutCornerProperty.INSTANCE, *values)
)

/**
 * SuperLayout的阴影颜色动画
 */
class SuperLayoutShadowColorAnimate(
    target: SuperLayout, @ColorInt values: IntArray
) : ObjectAnimate<SuperLayout, IntArray>(
    target, values,
    ObjectAnimator.ofInt(target, SuperLayoutShadowColorProperty.INSTANCE, *values)
)

/**
 * SuperLayout的阴影圆角动画
 */
class SuperLayoutShadowRadiusAnimate(
    target: SuperLayout, values: FloatArray
) : ObjectAnimate<SuperLayout, FloatArray>(
    target, values,
    ObjectAnimator.ofFloat(target, SuperLayoutShadowRadiusProperty.INSTANCE, *values)
)

/**
 * SuperLayout的阴影x轴偏移动画
 */
class SuperLayoutShadowDxAnimate(
    target: SuperLayout, values: FloatArray
) : ObjectAnimate<SuperLayout, FloatArray>(
    target, values,
    ObjectAnimator.ofFloat(target, SuperLayoutShadowDxProperty.INSTANCE, *values)
)

/**
 * SuperLayout的阴影y轴偏移动画
 */
class SuperLayoutShadowDyAnimate(
    target: SuperLayout, values: FloatArray
) : ObjectAnimate<SuperLayout, FloatArray>(
    target, values,
    ObjectAnimator.ofFloat(target, SuperLayoutShadowDyProperty.INSTANCE, *values)
)

/**
 * SuperLayout的边框颜色动画
 */
class SuperLayoutStrokeColorAnimate(
    target: SuperLayout, @ColorInt values: IntArray
) : ObjectAnimate<SuperLayout, IntArray>(
    target, values,
    ObjectAnimator.ofInt(target, SuperLayoutStrokeColorProperty.INSTANCE, *values)
)

/**
 * SuperLayout的边框宽度动画
 */
class SuperLayoutStrokeWidthAnimate(
    target: SuperLayout, values: FloatArray
) : ObjectAnimate<SuperLayout, FloatArray>(
    target, values,
    ObjectAnimator.ofFloat(target, SuperLayoutStrokeWidthProperty.INSTANCE, *values)
)

/**
 * SuperLayout的比例动画
 */
class SuperLayoutAspectRatioAnimate(
    target: SuperLayout, values: FloatArray
) : ObjectAnimate<SuperLayout, FloatArray>(
    target, values,
    ObjectAnimator.ofFloat(target, SuperLayoutAspectRatioProperty.INSTANCE, *values)
)

/**
 * SuperLayout的比例动画
 */
class SuperLayoutCircleAnimate(
    target: SuperLayout, values: Boolean
) : ObjectAnimate<SuperLayout, FloatArray>(
    target, getCircleValues(target, values),
    ObjectAnimator.ofFloat(target, SuperLayoutCornerProperty.INSTANCE, *getCircleValues(target, values))
)

/**
 * 获取圆角的进度值
 */
private fun getCircleValues(target: SuperLayout, isCircle: Boolean): FloatArray {
    return if (isCircle) {
        floatArrayOf(target.cornerRadius, (max(target.delegateView.width, target.delegateView.height) / 2f))
    } else {
        floatArrayOf(target.cornerRadius, 0f)
    }
}