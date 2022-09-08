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
import com.soywiz.korio.file.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import gameplay.*
import java.awt.font.ImageGraphicAttribute

class Rank() : Scene() {

    lateinit var rankImage: Image
    lateinit var resultString: Image
    override suspend fun SContainer.sceneInit() {
        val parentView = this
        val screenWidth = ConfigModule.size.width.toDouble()
        val screenHeight = ConfigModule.size.height.toDouble()
        val font = resourcesVfs["Oswald-VariableFont_wght.ttf"].readTtfFont()
        image(resourcesVfs["game_play_bg_shroom.png"].readBitmap()){
            anchor(0.5, 0.5)
            scaledWidth = screenWidth
            scaledHeight = screenHeight
            position(scaledWidth / 2, scaledHeight / 2)
        }
        val txtStr = "RANK"
        val fontSize = 40.0
        val rankBitMap = NativeImage(width = 120, height = 100).apply {
            getContext2d().fillText(
                txtStr,
                x = 0,
                y = fontSize,
                font = font,
                textSize = fontSize,
                color = Colors.WHITE
            )
        }
        rankImage = image(rankBitMap){
            x = (parentView.width - width) / 2
            y += 50.0
        }
        val myScoreString =  "My Highest Score:"
        val myScoreFontBitmap = NativeImage(width = width.toInt(), height = 100).apply {
            getContext2d().fillText(
                myScoreString,
                x = 150.0,
                y = fontSize,
                font = font,
                textSize = fontSize,
                color = Colors.WHITE
            )
        }
        resultString = image(myScoreFontBitmap){
            alignTopToBottomOf(rankImage)
            x = (parentView.width - width) - 100
            y += 20.0
        }
        Score().apply {
            load()
            parentView.addChild(this)
            initPosition()
            nowValue = getHighestScore()
            update()
            alignTopToBottomOf(rankImage)
//            alignLeftToRightOf(resultString)
            x -= 50
            y += 20
        }
        image(resourcesVfs["next.png"].readBitmap()){
            alignTopToBottomOf(resultString)
            x = (parentView.width - width) / 2
            onClick {
                launchImmediately {
                    sceneContainer.changeTo<Menu>()
                }
            }
        }
    }
    suspend fun getHighestScore(): Int{
        val scoreFile = resourcesVfs["score.txt"]
        if (!scoreFile.exists()){
            scoreFile.open(VfsOpenMode.CREATE_NEW)
        }
        val saveScoreString = scoreFile.readString()
        var saveScore = 0
        if (saveScoreString.isNotEmpty()){
            saveScore = saveScoreString.toInt()
        }
        println("saveScore = $saveScore")
        if (ShareData.gameScore > saveScore){
            saveScore = ShareData.gameScore
            scoreFile.writeString(ShareData.gameScore.toString())
            println("New Score = $saveScore")
        }else{
            scoreFile.writeString(ShareData.gameScore.toString())
        }
        return saveScore
    }
}
