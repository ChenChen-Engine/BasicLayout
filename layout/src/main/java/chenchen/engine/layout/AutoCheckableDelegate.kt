package chenchen.engine.layout

import android.R
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

/**
 * @Author by chenz
 * @Date 2022/6/3 22:21
 * Description : 自动选中代理
 */
class AutoCheckableDelegate(private val thisView: View, private val attrs: SuperLayoutAttributes) : AutoCheckable {

    override val isReceiveCheckFromParent: Boolean
        get() = attrs.isReceiveCheckFromParent

    override val isTransitiveCheckToChild: Boolean
        get() = attrs.isTransitiveCheckToChild

    private val CHECKED_STATE_SET = intArrayOf(R.attr.state_checked)

    override fun setChecked(checked: Boolean) {
        if (attrs.isTransitiveCheckToChild) {
            if (thisView is ViewGroup) {
                thisView.children
                    .mapNotNull { view -> view as? AutoCheckable }
                    .filter { view -> view.isReceiveCheckFromParent }
                    .forEach { view -> view.isChecked = checked }
            }
        }
        thisView.refreshDrawableState()
    }

    override fun isChecked(): Boolean {
        return attrs.isChecked
    }

    override fun toggle() {
        //这里不实现
    }
}