package me.akhsaul.swing

import me.akhsaul.common.exception.DeprecatedException
import me.akhsaul.common.logger
import me.akhsaul.swing.Window.Companion.DEFAULT_ICON
import me.akhsaul.swing.Window.Companion.DEFAULT_LOCATION
import me.akhsaul.swing.Window.Companion.DEFAULT_SIZE
import me.akhsaul.swing.Window.Companion.DISPOSE
import java.awt.Image
import java.awt.event.WindowEvent
import javax.swing.JDialog

open class KDialog @JvmOverloads constructor(
    title: String = "Kotlin Dialog",
    owner: java.awt.Window? = null,
    modal: ModalityType = ModalityType.MODELESS,
    theme: Theme = Theme.DARCULA,
    mode: Theme.Mode = Theme.Mode.DARK
) : Window, JDialog(owner, title, modal) {
    companion object {
        private val LOG = logger { }
    }

    @JvmOverloads
    constructor(
        title: String,
        owner: java.awt.Window?,
        modal: Boolean,
        theme: Theme = Theme.DARCULA,
        mode: Theme.Mode = Theme.Mode.DARK
    ) : this(
        title, owner, if (modal) DEFAULT_MODALITY_TYPE else ModalityType.MODELESS, theme, mode
    )

    init {
        name = this.javaClass.simpleName
        LOG.info("Initialize Components")
        this.initComponents()
        this.pack()
        LOG.info("Initialize Listeners")
        this.initListeners()
        this.init(theme, mode)
        if (owner == null) {
            location = DEFAULT_LOCATION
        }
    }

    /**
     * initialize default setting, can be override
     * */
    protected open fun init(theme: Theme, mode: Theme.Mode) {
        LOG.info("Initialize $this with default setting")
        defaultCloseOperation = DISPOSE
        size = DEFAULT_SIZE
        isResizable = false
        setIcons(DEFAULT_ICON)
        changeTheme(theme, mode)
    }

    override fun initComponents() {
        // nothing to initialize component
    }

    override fun initListeners() {
        // nothing to initialize listener
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

    override fun changeTheme(theme: Theme, mode: Theme.Mode) {
        changeTheme(theme, this, mode)
    }

    override fun updateUI() = invokeWait {
        updateTreeUI(this)
    }

    override fun close() {
        dispatchEvent(WindowEvent(this, WindowEvent.WINDOW_CLOSING))
    }

    @Deprecated(
        "Use KDialog.setIcon instead",
        ReplaceWith("this.setIcon(image)", "me.akhsaul.swing.KDialog.setIcon"),
        DeprecationLevel.ERROR
    )
    override fun setIconImage(image: Image?) {
        throw DeprecatedException()
    }

    @Deprecated(
        "Use KDialog.setIcon instead",
        ReplaceWith("this.setIcons(icons)", "me.akhsaul.swing.KDialog.setIcons"),
        DeprecationLevel.ERROR
    )
    override fun setIconImages(icons: List<Image?>) {
        throw DeprecatedException()
    }
}