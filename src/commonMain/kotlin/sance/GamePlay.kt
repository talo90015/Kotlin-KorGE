package sance

import com.soywiz.korge.input.*
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korio.async.*
import com.soywiz.korma.geom.*

class GamePlay() : Scene() {
    val textPos = IPoint(128, 128)
    val btnWidth = 256.0
    val btnHeight = 32.0
    val btnPos = IPoint(128, 128 + 32)
    override suspend fun SContainer.sceneInit() {
        text("I,m in ${GamePlay::class.simpleName}"){
            position(textPos)
        }
        uiTextButton(btnWidth, btnHeight) {
            text = "Go GameOver"
            position(btnPos)
            onClick {
                launchImmediately { sceneContainer.changeTo<GameOver>() }
            }
        }
    }
}
