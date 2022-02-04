package me.akhsaul.swing

import me.akhsaul.common.exception.DeprecatedException
import me.akhsaul.common.logger
import me.akhsaul.common.tools.Sys
import me.akhsaul.swing.Window.Companion.DEFAULT_ICON
import me.akhsaul.swing.Window.Companion.DEFAULT_LOCATION
import me.akhsaul.swing.Window.Companion.DEFAULT_SIZE
import me.akhsaul.swing.Window.Companion.DISPOSE
import java.awt.Frame
import java.awt.Image
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane

open class KFrame @JvmOverloads constructor(
    title: String = "Kotlin Frame",
    theme: Theme = Theme.DARCULA,
    mode: Theme.Mode = Theme.Mode.DARK
) : Window, JFrame(title) {

    companion object {
        private val LOG = logger { }
    }

    init {
        name = this.javaClass.simpleName
        LOG.info("Initialize Components")
        this.initComponents()
        this.pack()
        LOG.info("Initialize Listeners")
        this.initListeners()
        this.init(theme, mode)
    }

    /**
     * initialize default setting, can be override
     * */
    protected open fun init(theme: Theme, mode: Theme.Mode) {
        LOG.info("Initialize $this with default setting")
        defaultCloseOperation = DISPOSE
        location = DEFAULT_LOCATION
        size = DEFAULT_SIZE
        isResizable = false
        setIcons(DEFAULT_ICON)
        changeTheme(theme, mode)
    }

    override fun initListeners() {
        val exitListener: WindowListener = object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                val confirm = JOptionPane.showOptionDialog(
                    this@KFrame,
                    "Are You Sure to Close this Application?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null
                )
                if (confirm == JOptionPane.YES_OPTION) {
                    Sys.exit()
                }
            }
        }
        addWindowListener(exitListener)
    }

    fun setBackground(background: Image) {
        val components = contentPane.components
        contentPane = JLabel(ImageIcon(background))
        components.forEach {
            contentPane.add(it)
        }
        invokeWait {
            revalidate()
            repaint()
        }
    }

    fun setBackground(imagePath: String) {
        setBackground(getImage(imagePath))
    }

    override fun initComponents() {
        // nothing to initialize component
    }

    override fun setIcons(icons: Collection<Image>) {
        super.setIconImages(
            if (icons is List<Image?>) {
                icons
            } else {
                icons.toList()
            }
        )
    }

    open fun hideToTray() {
    }

    override fun close() {
        dispatchEvent(WindowEvent(this, WindowEvent.WINDOW_CLOSING))
    }

    override fun changeTheme(theme: Theme, mode: Theme.Mode) {
        changeTheme(theme, this, mode)
    }

    override fun updateUI() = invokeWait {
        updateTreeUI(this)
    }

    @Deprecated(
        "Use KFrame.setIcon instead",
        ReplaceWith("this.setIcon(image)", "me.akhsaul.swing.KFrame.setIcon"),
        DeprecationLevel.ERROR
    )
    override fun setIconImage(image: Image?) {
        throw DeprecatedException()
    }

    @Deprecated(
        "Use KFrame.setIcon instead",
        ReplaceWith("this.setIcons(icons)", "me.akhsaul.swing.KFrame.setIcons"),
        DeprecationLevel.ERROR
    )
    override fun setIconImages(icons: List<Image?>) {
        throw DeprecatedException()
    }

    open fun move(to: Frame) {
        Intent.next(this, to)
    }

    open fun back() {
        Intent.prev(this)
    }

    override fun toString(): String {
        return "${javaClass.name}(@${hashCode()})"
    }
}
