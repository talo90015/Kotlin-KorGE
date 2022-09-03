package sance

import ConfigModule
import com.soywiz.klock.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.async.delay
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import java.io.ObjectInputFilter.Config

class GamePlay() : Scene() {
    val textPos = IPoint(10, 450)
    val btnWidth = 256.0
    val btnHeight = 32.0
    val btnPos = IPoint(128, 450)
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
        image(resourcesVfs["isLogo.png"].readBitmap()){
            anchor(0.5, 0.5)
            scaledWidth = ConfigModule.size.width.toDouble()
            scaledHeight = ConfigModule.size.height.toDouble()
            position(scaledWidth / 2, scaledHeight / 2)
        }
        val spriteMap = resourcesVfs["green_alien_walk.png"].readBitmap()
        val alienWalkAnimation = SpriteAnimation(
            spriteMap = spriteMap,
            spriteWidth = 72,
            spriteHeight = 97,
            marginTop = 0,
            marginLeft = 0,
            columns = 11,
            rows = 1,
            offsetBetweenColumns = 0,
            offsetBetweenRows = 0
        )
        val alien = sprite(alienWalkAnimation)
        alien.spriteDisplayTime = 0.1.seconds
        alien.playAnimationLooped()
        while (true){
            alien.tween(alien::x[-100.0, 800.0], time = 5.seconds)
            delay(1.seconds)
        }
    }
}
