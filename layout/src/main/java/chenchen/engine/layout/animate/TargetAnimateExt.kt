package chenchen.engine.layout.animate

import androidx.annotation.ColorInt
import chenchen.engine.animate.buildAnimate
import chenchen.engine.layout.SuperLayout


/**
 * SuperLayout的圆角动画
 */
fun SuperLayout.animateCorner(toCorner: Float, duration: Long = 300) = delegateView.buildAnimate {
    animateCorner(this@animateCorner, toCorner) {
        this.duration = duration
    }
}

/**
 * SuperLayout的阴影颜色动画
 */
fun SuperLayout.animateShadowColor(@ColorInt toColor: Int, duration: Long = 300) = delegateView.buildAnimate {
    animateShadowColor(this@animateShadowColor,toColor){
        this.duration = duration
    }
}

/**
 * SuperLayout的阴影圆角动画
 */
fun SuperLayout.animateShadowRadius(toRadius: Float, duration: Long = 300) = delegateView.buildAnimate {
    animateShadowRadius(this@animateShadowRadius,toRadius){
        this.duration = duration
    }
}

/**
 * SuperLayout的阴影x轴偏移动画
 */
fun SuperLayout.animateShadowDx(toDx: Float, duration: Long = 300) = delegateView.buildAnimate {
    animateShadowDx(this@animateShadowDx,toDx){
        this.duration = duration
    }
}

/**
 * SuperLayout的阴影y轴偏移动画
 */
fun SuperLayout.animateShadowDy(toDy: Float, duration: Long = 300) = delegateView.buildAnimate {
    animateShadowDy(this@animateShadowDy,toDy){
        this.duration = duration
    }
}

/**
 * SuperLayout的阴影颜色动画
 */
fun SuperLayout.animateStrokeColor(@ColorInt toColor: Int, duration: Long = 300) = delegateView.buildAnimate {
    animateStrokeColor(this@animateStrokeColor,toColor){
        this.duration = duration
    }
}

/**
 * SuperLayout的边框宽度动画
 */
fun SuperLayout.animateStrokeWidth(toWidth: Float, duration: Long = 300) = delegateView.buildAnimate {
    animateStrokeWidth(this@animateStrokeWidth,toWidth){
        this.duration = duration
    }
}

/**
 * SuperLayout的比例动画
 */
fun SuperLayout.animateAspectRatio(toRatio: Float, duration: Long = 300) = delegateView.buildAnimate {
    animateAspectRatio(this@animateAspectRatio,toRatio){
        this.duration = duration
    }
}

/**
 * SuperLayout的比例动画
 */
fun SuperLayout.animateCircle(isCircle: Boolean, duration: Long = 300) = delegateView.buildAnimate {
    animateCircle(this@animateCircle,isCircle){
        this.duration = duration
    }
}