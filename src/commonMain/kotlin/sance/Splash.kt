package sance

import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korio.async.*
import com.soywiz.korma.geom.*
import java.awt.Point


class Splash() : Scene() {
    val textPos = IPoint(128, 128)
    val btnWidth = 256.0
    val btnHeight = 32.0
    val btnPos = IPoint(128, 128 + 32)
    override suspend fun SContainer.sceneInit() {
        text("I,m in ${Splash::class.simpleName}"){
            position(textPos)
        }
        uiTextButton(btnWidth, btnHeight) {
            text = "Go to Menu"
            position(btnPos)
            onClick {
                launchImmediately { sceneContainer.changeTo<Menu>() }
            }
        }
    }
}
