package chenchen.engine.layout

/**
 * 比例
 * @Author by chenz
 * @Date 2022/5/27 11:55
 */
enum class AspectRatio(val ratio: Float) {
    Ratio1_1(1f),
    Ratio3_4(3f / 4),
    Ratio4_3(4f / 3),
    Ratio9_16(9f / 16),
    Ratio16_9(16f / 9),
    Ratio9_21(9f / 21),
    Ratio21_9(21f / 9),
}