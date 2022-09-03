package sance

import ConfigModule
import com.soywiz.klock.*
import com.soywiz.korau.sound.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.time.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korim.paint.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korio.stream.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*


class Splash() : Scene() {
    val textPos = IPoint(128, 128)
    val btnWidth = 256.0
    val btnHeight = 32.0
    val btnPos = IPoint(128, 128 + 32)
    val rotationDegrees = (45).degrees
    val textStr = "Top to Start"
    val fontSize = 30.0
    val screenWidth = ConfigModule.size.width.toDouble()
    val screenHeight = ConfigModule.size.height.toDouble()
    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["Oswald-VariableFont_wght.ttf"].readTtfFont()
//        val bgMusic = resourcesVfs["music.mp3"].readMusic()
//        bgMusic.play(PlaybackTimes.INFINITE)
        text("I,m in ${Splash::class.simpleName}"){
            position(textPos)
        }
        image(resourcesVfs["isLogo.png"].readBitmap()){
            anchor(0.5, 0.5)
            scaledWidth = ConfigModule.size.width.toDouble()
            scaledHeight = ConfigModule.size.height.toDouble()
            position(scaledWidth / 2, scaledHeight / 2)
//            rotation = rotationDegrees
        }
        text("v0.0.1"){
            position(470, 490)
        }
        val bitmap = NativeImage(width = 300, height = 150).apply {
            getContext2d().fillText(textStr,
                x = 100,
                y = fontSize,
                font = font,
                textSize = fontSize,
                color = Colors.WHITE)
        }
        var moveHeight = (scaledHeight - scaledHeight / 4)
        val tapString = image(bitmap){
            position((screenWidth - scaledWidth) / 2, 0.0)
        }.onClick {
            launchImmediately { sceneContainer.changeTo<Menu>() }
        }
//        launchImmediately {
//            while (true){
//                tapString!!.alpha -= 0.1
//                if (tapString?.alpha!! <= 0){
//                    tapString?.alpha = 1.0
//                }
//                kotlinx.coroutines.delay(100)
//            }
//        }
        tapString?.addOptFixedUpdater(100.milliseconds){
            tapString.alpha -= 0.1
                if (tapString.alpha <= 0){
                    tapString.alpha = 1.0
                }
                if (tapString.y < moveHeight){
                    tapString.y += 30
                }
        }

//        uiTextButton(btnWidth, btnHeight) {
//            text = "Go to Menu"
//            position(btnPos)
//            onClick {
//                launchImmediately { sceneContainer.changeTo<Menu>() }
//            }
//        }

    }
}
