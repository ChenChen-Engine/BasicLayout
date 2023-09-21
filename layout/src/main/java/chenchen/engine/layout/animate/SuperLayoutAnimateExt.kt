package chenchen.engine.layout.animate

import androidx.annotation.ColorInt
import chenchen.engine.animate.Animate
import chenchen.engine.animate.AnimateScope
import chenchen.engine.layout.SuperLayout

/**
 * SuperLayout的圆角动画
 * @param target 作用到哪个SuperLayout上
 * @param values 修改的值，可以是：0 → ∞
 */
fun AnimateScope.animateCorner(
    target: SuperLayout,
    vararg values: Float,
    scopeContent: SuperLayoutCornerAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutCornerAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的阴影颜色动画
 */
fun AnimateScope.animateShadowColor(
    target: SuperLayout,
    @ColorInt vararg values: Int,
    scopeContent: SuperLayoutShadowColorAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutShadowColorAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的阴影圆角动画
 */
fun AnimateScope.animateShadowRadius(
    target: SuperLayout,
    vararg values: Float,
    scopeContent: SuperLayoutShadowRadiusAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutShadowRadiusAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的阴影x轴偏移动画
 */
fun AnimateScope.animateShadowDx(
    target: SuperLayout,
    vararg values: Float,
    scopeContent: SuperLayoutShadowDxAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutShadowDxAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的阴影y轴偏移动画
 */
fun AnimateScope.animateShadowDy(
    target: SuperLayout,
    vararg values: Float,
    scopeContent: SuperLayoutShadowDyAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutShadowDyAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的阴影颜色动画
 */
fun AnimateScope.animateStrokeColor(
    target: SuperLayout,
    @ColorInt vararg values: Int,
    scopeContent: SuperLayoutShadowColorAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutShadowColorAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的边框宽度动画
 */
fun AnimateScope.animateStrokeWidth(
    target: SuperLayout,
    vararg values: Float,
    scopeContent: SuperLayoutStrokeWidthAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutStrokeWidthAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的比例动画
 */
fun AnimateScope.animateAspectRatio(
    target: SuperLayout,
    vararg values: Float,
    scopeContent: SuperLayoutAspectRatioAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutAspectRatioAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}

/**
 * SuperLayout的比例动画
 */
fun AnimateScope.animateCircle(
    target: SuperLayout,
    values: Boolean,
    scopeContent: SuperLayoutCircleAnimate.() -> Unit = {}
): Animate {
    val itemScope = SuperLayoutCircleAnimate(target, values)
    itemScope.scopeContent()
    this.attachChild(itemScope)
    return itemScope
}