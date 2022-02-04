@file:JvmName("SwingUtil")
@file:Suppress("unused", "unused_parameter")

package me.akhsaul.swing

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.withLock
import me.akhsaul.common.*
import me.akhsaul.common.tools.Sys
import org.apache.logging.log4j.LogManager
import org.slf4j.LoggerFactory
import java.awt.*
import java.awt.Window
import java.awt.event.InvocationEvent
import java.awt.image.BufferedImage
import java.lang.reflect.InvocationTargetException
import javax.imageio.ImageIO
import javax.swing.*

private object AWTInvocationLock

private val LOG = LoggerFactory.getLogger("me.akhsaul.swing.SwingUtil")
private val owner = AWTInvocationLock
private val toolkit = Toolkit.getDefaultToolkit()
private val queue = toolkit.systemEventQueue

fun getIcon(name: String): Icon {
    return ImageIcon(getImage(name))
}

fun getImage(name: String): BufferedImage {
    return ImageIO.read(Sys.getResourceStream(name))
}

fun invokeLater(action: () -> Unit) {
    queue.postEvent(InvocationEvent(toolkit, action))
    LOG.debug("Invocation Done.")
}

fun invokeWait(action: () -> Unit) {
    if (EventQueue.isDispatchThread()) {
        throw IllegalCallerException("Cannot call invokeAndWait from the event dispatcher thread")
    }
    val event = InvocationEvent(toolkit, action, owner, true)
    runBlocking {
        mutex.withLock(owner) {
            queue.postEvent(event)
            while (!event.isDispatched) {
                delay(1L)
            }
        }
    }
    event.throwable?.let {
        throw InvocationTargetException(it)
    }
    LOG.debug("Invocation Done.")
}

fun changeTheme(theme: LookAndFeel, frame: Window): Boolean {
    return catch(false) {
        invokeWait {
            UIManager.setLookAndFeel(theme)
            updateTreeUI(frame)
        }
        true
    }.also {
        if (!it) {
            LOG.warn("Failed to setup look and feel ${theme.javaClass.name}.")
        }
    }
}

@JvmOverloads
fun changeTheme(theme: Theme, frame: Window, mode: Theme.Mode = Theme.Mode.LIGHT): Boolean {
    val selected = if (mode == Theme.Mode.DARK) {
        theme.dark
    } else {
        theme.light
    }
    LOG.info("Selected Theme = ${selected.name}, isDarkMode = ${mode == Theme.Mode.DARK}")
    return changeTheme(selected, frame)
}

fun updateTreeUI(component: Component) {
    if (component is JComponent) {
        component.updateUI()
        val c = component.componentPopupMenu
        if (c != null) {
            updateTreeUI(c)
        }
    }

    when (component) {
        is JMenu -> component.menuComponents
        is Container -> component.components
        else -> null
    }?.forEach {
        updateTreeUI(it)
    }
    updateUI(component)
}

fun updateUI(component: Component) {
    component.invalidate()
    component.validate()
    component.repaint()
}

fun makeTrayIcon() {

}

object Intent {
    private val history: MutableMap<Int, MutableList<Frame>> = mutableMapOf()

    /**
     * use different [id] if you have 2 application that does not tie each other
     * @param id identification for context movement
     * */
    @JvmOverloads
    fun next(from: Frame, to: Frame, id: Int = this.hashCode()) {
        if (history.containsKey(id)){
            history.getValue(id).addNonDuplicate(from).addNonDuplicate(to)
        } else {
            history[id] = mutableListOf(from, to)
        }
        to.location = from.location
        from.dispose()
        to.isVisible = true
        LOG.info(history.toString())
    }

    /**
     * use different [id] if you have 2 application that does not tie each other
     * @param id identification for context movement
     * */
    @JvmOverloads
    fun prev(from: Frame, id: Int = this.hashCode()){
        if (history.containsKey(id)){
            val list = history.getValue(id)
            if (list.isNotEmpty()){
                if (list.last() == from) {
                    val to = list.getOrNull(list.lastIndex - 1)
                    if (to != null){
                        to.location = from.location
                        from.dispose()
                        to.isVisible = true
                        list.removeLast()
                    }
                }
            }
        }
    }
}