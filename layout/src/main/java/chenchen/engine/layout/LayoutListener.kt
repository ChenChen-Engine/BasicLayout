package chenchen.engine.layout

import android.view.View

/**
 * @Author by chenz
 * @Date 2022/4/24 0:25
 * Description :
 */

fun interface OnAttachChangeListener {
    /**
     * 视图被依附或分离的变化
     * @param view 依附的View
     * @param isAttached true = attached , false = detached
     */
    fun onChange(view: View, isAttached: Boolean)
}

fun interface OnVisibleChangeListener {
    /**
     * 可见变化
     * @param view 变化的View
     * @param isVisible 是否可见
     */
    fun onChange(view: View, isVisible: Boolean)
}

fun interface OnCheckedChangeListener {
    /**
     * 选中变化
     * @param view 被操作的View
     * @param isChecked 是否选中
     */
    fun onChange(view: View, isChecked: Boolean)
}