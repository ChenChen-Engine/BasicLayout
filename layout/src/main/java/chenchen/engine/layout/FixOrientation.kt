package chenchen.engine.layout

/**
 * @Author by chenz
 * @Date 2022/6/3 20:16
 * Description : 固定的方向
 */
sealed class FixOrientation {
    object Vertical : FixOrientation()
    object Horizontal : FixOrientation()
}
