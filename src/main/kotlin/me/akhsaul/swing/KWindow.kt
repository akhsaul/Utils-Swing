package me.akhsaul.swing

import java.awt.*
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import javax.swing.JFrame
import javax.swing.LookAndFeel
import javax.swing.UIManager
import kotlin.io.path.appendLines

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
        val DEFAULT_ICON: List<Image> = listOf(getImage("k_icon_64x64.png"))

        @JvmField
        val EMPTY_ICON = listOf(BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE))

        @JvmField
        val DEFAULT_SIZE = Dimension(250, 250)

        @JvmField
        val MAX_SIZE = Toolkit.getDefaultToolkit().screenSize

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