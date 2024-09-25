package io.tayviscon.theme.jetbrains.listeners

import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.LafManagerListener
import com.intellij.openapi.editor.colors.EditorColorsManager
import io.tayviscon.theme.jetbrains.enums.TayvisconVariant

class TayvisconThemeChangeListener : LafManagerListener {
    private val editorColorsManager = EditorColorsManager.getInstance()

    private var previousUI = LafManager.getInstance().currentUIThemeLookAndFeel.name

    override fun lookAndFeelChanged(lafManager: LafManager) {
        val currentUI = lafManager.currentUIThemeLookAndFeel.name
        if (previousUI != currentUI) {
            if (currentUI == TayvisconVariant.TayvisconDark.label) {
                editorColorsManager.setGlobalScheme(editorColorsManager.getScheme("_@user_$currentUI"))
            }
        }
        previousUI = currentUI
    }
}