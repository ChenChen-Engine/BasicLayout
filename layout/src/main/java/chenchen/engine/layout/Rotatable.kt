package chenchen.engine.layout

/**
 * 旋转
 * @Author by chenz
 * @Date 2022/5/27 11:55
 */
interface Rotatable {
    /**
     * 开始旋转
     */
    fun startRotate(): Rotatable

    /**
     * 停止旋转
     */
    fun stopRotate(): Rotatable

    /**
     * 重置旋转，恢复View默认状态
     */
    fun resetRotate(): Rotatable
}