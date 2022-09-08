package sance

import ConfigModule
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import gameplay.*

class GameOver() : Scene() {
    lateinit var bgImage: Image
    val successTxt = "You're Success"
    val failTxt = "You're Failure"
    val fontSize = 40.0
    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["Oswald-VariableFont_wght.ttf"].readTtfFont()
        if (ShareData.isGameOverSuccess){
            bgImage = image(resourcesVfs["bg_success.png"].readBitmap()){
                anchor(0.5, 0.5)
                scaledWidth = ConfigModule.size.width.toDouble()
                scaledHeight = ConfigModule.size.height.toDouble()
                position(scaledWidth / 2, scaledHeight / 2)
            }
        }else{
            bgImage = image(resourcesVfs["bg_fail.png"].readBitmap()){
                anchor(0.5, 0.5)
                scaledWidth = ConfigModule.size.width.toDouble()
                scaledHeight = ConfigModule.size.height.toDouble()
                position(scaledWidth / 2, scaledHeight / 2)
            }
        }
        val txtStr = if (ShareData.isGameOverSuccess){successTxt}else{failTxt}
        val bitmap = NativeImage(width = width.toInt(), height = 100).apply {
            getContext2d().fillText(
                txtStr,
                x = 0.0,
                y = fontSize,
                font = font,
                textSize = fontSize,
                color = Colors.WHITE
            )
        }
        val resultString = image(bitmap){
            position(scaledWidth / 2 - (txtStr.count() * fontSize) / 4, 50.0)
        }
        val score = Score().apply { load() }
        addChild(score)
        score.initPosition()
        score.alignTopToBottomOf(resultString)
        score.nowValue = ShareData.gameScore
        score.update()
        val alien = Alien().apply {load(ShareData.selectRunAlien)}
        addChild(alien)
        alien.alignTopToBottomOf(resultString)
        alien.alignRightToLeftOf(score)
        alien.x -= 30
        if (ShareData.isGameOverSuccess){
            alien.goal()
        }else{
            alien.fail()
        }
        image(resourcesVfs["next.png"].readBitmap()){
            anchor(0.5, 0.5)
            position(bgImage.width / 2, bgImage.height - 200)
            onClick {
                sceneContainer.changeTo<Rank>()
            }
        }
    }
}
