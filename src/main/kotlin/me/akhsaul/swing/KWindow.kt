package me.akhsaul.swing

import me.akhsaul.common.exception.DeprecatedException
import me.akhsaul.common.logger
import me.akhsaul.common.tools.Sys
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.image.BufferedImage
import javax.swing.*

interface Window {
    companion object {
        @JvmField
        val EXIT = JFrame.EXIT_ON_CLOSE

        @JvmField
        val DISPOSE = JFrame.DISPOSE_ON_CLOSE

        @JvmField
        val HIDE = JFrame.HIDE_ON_CLOSE

        @JvmField
        val NOTHING = JFrame.DO_NOTHING_ON_CLOSE

        @JvmField
        val DEFAULT_ICON: List<Image> = listOf(getImage("k_icon.png"))

        @JvmField
        val EMPTY_ICON = listOf(BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE))

        @JvmField
        val DEFAULT_SIZE = Dimension(250, 250)

        @JvmField
        val DEFAULT_LOCATION: Point = run {
            val centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().centerPoint
            val dx = centerPoint.x - Window.DEFAULT_SIZE.width / 2
            val dy = centerPoint.y - Window.DEFAULT_SIZE.height / 2
            Point(dx, dy)
        }
    }

    fun setVisible(b: Boolean)
    fun initComponents()
    fun initListeners()
    fun changeTheme(theme: Theme, mode: Theme.Mode)

    fun getTheme(): LookAndFeel {
        return UIManager.getLookAndFeel()
    }

    fun setIcon(icon: Image) {
        setIcons(listOf(icon))
    }

    fun setIcons(icons: Collection<Image>)

    fun removeIcon() {
        setIcons(EMPTY_ICON)
    }

    fun showWindow() {
        setVisible(true)
    }

    fun hideWindow() {
        setVisible(false)
    }

    fun close()
    fun updateUI()
}
