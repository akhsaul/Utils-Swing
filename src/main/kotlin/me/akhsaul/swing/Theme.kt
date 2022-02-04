package me.akhsaul.swing

import com.formdev.flatlaf.FlatDarculaLaf
import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.intellijthemes.*
import javax.swing.LookAndFeel
import javax.swing.plaf.metal.MetalLookAndFeel
import javax.swing.plaf.nimbus.NimbusLookAndFeel

@Suppress("unused")
enum class Theme(val dark: LookAndFeel, val light: LookAndFeel) {
    DARCULA(FlatDarculaLaf(), FlatLightLaf()),
    FLAT_LAF(FlatDarkLaf(), FlatLightLaf()),
    GRADIANTO_DEEP_OCEAN(FlatGradiantoDeepOceanIJTheme(), FlatLightLaf()),
    ARC(FlatArcDarkIJTheme(), FlatArcIJTheme()),
    ARC_ORANGE(FlatArcDarkOrangeIJTheme(), FlatArcOrangeIJTheme()),
    METAL(FlatDarkLaf(), MetalLookAndFeel()),
    NIMBUS(FlatDarkLaf(), NimbusLookAndFeel());

    enum class Mode {
        DARK,
        LIGHT
    }
}