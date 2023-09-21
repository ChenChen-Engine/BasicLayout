package chenchen.engine.layout.animate

import android.util.Property
import chenchen.engine.layout.SuperLayout

/**
 * @Author by chenz
 * @Date 2022/6/3 22:20
 * Description :SuperLayout的动画属性
 */
/**
 * 圆角属性
 */
class SuperLayoutCornerProperty private constructor(name: String) :
    Property<SuperLayout, Float>(Float::class.java, name) {
    override fun get(target: SuperLayout): Float {
        return target.cornerRadius
    }

    override fun set(target: SuperLayout, value: Float) {
        target.setCornerRadius(value)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Float> = SuperLayoutCornerProperty(PropertyNames.cornerRadius)
    }
}

/**
 * 比例属性
 */
class SuperLayoutAspectRatioProperty private constructor(name: String) :
    Property<SuperLayout, Float>(Float::class.java, name) {
    override fun get(target: SuperLayout): Float {
        return target.aspectRatio
    }

    override fun set(target: SuperLayout, value: Float) {
        target.setAspectRatio(value)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Float> = SuperLayoutAspectRatioProperty(PropertyNames.aspectRatio)
    }
}

/**
 * 阴影颜色属性
 */
class SuperLayoutShadowColorProperty private constructor(name: String) :
    Property<SuperLayout, Int>(Int::class.java, name) {
    override fun get(target: SuperLayout): Int {
        return target.shadowColor
    }

    override fun set(target: SuperLayout, value: Int) {
        target.setShadowStyle(value, target.shadowRadius, target.shadowDx, target.shadowDy)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Int> = SuperLayoutShadowColorProperty(PropertyNames.shadowColor)
    }
}

/**
 * 阴影圆角属性
 */
class SuperLayoutShadowRadiusProperty private constructor(name: String) :
    Property<SuperLayout, Float>(Float::class.java, name) {
    override fun get(target: SuperLayout): Float {
        return target.shadowRadius
    }

    override fun set(target: SuperLayout, value: Float) {
        target.setShadowStyle(target.shadowColor, value, target.shadowDx, target.shadowDy)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Float> = SuperLayoutShadowRadiusProperty(PropertyNames.shadowRadius)
    }
}

/**
 * 阴影x轴偏移属性
 */
class SuperLayoutShadowDxProperty private constructor(name: String) :
    Property<SuperLayout, Float>(Float::class.java, name) {
    override fun get(target: SuperLayout): Float {
        return target.shadowDx
    }

    override fun set(target: SuperLayout, value: Float) {
        target.setShadowStyle(target.shadowColor, target.shadowRadius, value, target.shadowDy)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Float> = SuperLayoutShadowDxProperty(PropertyNames.shadowDx)
    }
}

/**
 * 阴影y轴偏移属性
 */
class SuperLayoutShadowDyProperty private constructor(name: String) :
    Property<SuperLayout, Float>(Float::class.java, name) {
    override fun get(target: SuperLayout): Float {
        return target.shadowDy
    }

    override fun set(target: SuperLayout, value: Float) {
        target.setShadowStyle(target.shadowColor, target.shadowRadius, target.shadowDx, value)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Float> = SuperLayoutShadowDyProperty(PropertyNames.shadowDy)
    }
}

/**
 * 阴影边框颜色属性
 */
class SuperLayoutStrokeColorProperty private constructor(name: String) :
    Property<SuperLayout, Int>(Int::class.java, name) {
    override fun get(target: SuperLayout): Int {
        return target.strokeColor
    }

    override fun set(target: SuperLayout, value: Int) {
        target.setBorder(target.strokeWidth, value)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Int> = SuperLayoutStrokeColorProperty(PropertyNames.strokeColor)
    }
}

/**
 * 阴影边框宽度属性
 */
class SuperLayoutStrokeWidthProperty private constructor(name: String) :
    Property<SuperLayout, Float>(Float::class.java, name) {
    override fun get(target: SuperLayout): Float {
        return target.strokeWidth
    }

    override fun set(target: SuperLayout, value: Float) {
        target.setBorder(value, target.strokeColor)
    }

    companion object {
        val INSTANCE: Property<SuperLayout, Float> = SuperLayoutStrokeWidthProperty(PropertyNames.strokeWidth)
    }
}