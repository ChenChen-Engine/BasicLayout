package chenchen.engine.layout

import android.widget.Checkable

/**
 * @Author by chenz
 * @Date 2022/6/3 22:20
 * Description :
 */
interface AutoCheckable : Checkable {

    /**
     * 是否接受父View的选中状态，即使是动态添加的也会接受，暂不考虑Checkable的其他实现类，比如CheckBox
     */
    val isReceiveCheckFromParent: Boolean

    /**
     * 是否将选中状态传递给子View，暂不考虑Checkable的其他实现类，比如CheckBox
     */
    val isTransitiveCheckToChild: Boolean

}