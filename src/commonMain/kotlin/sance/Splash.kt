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
    val textStr = "Top to Start"
    val fontSize = 30.0
    val screenWidth = ConfigModule.size.width.toDouble()
    val screenHeight = ConfigModule.size.height.toDouble()
    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["Oswald-VariableFont_wght.ttf"].readTtfFont()
        image(resourcesVfs["splash_bg.png"].readBitmap()){
            scaledWidth = ConfigModule.size.width.toDouble()
            scaledHeight = ConfigModule.size.height.toDouble()
        }
        val bitmap = NativeImage(width = 300, height = 150).apply {
            getContext2d().fillText(
                x = 100,
                y = fontSize,
                text = textStr,
                font = font,
                textSize = fontSize,
                color = Colors.WHITE)
        }
        val moveHeight = (scaledHeight - scaledHeight / 5)
        val tapString = image(bitmap){
            position(400.0, 0.0).onClick {
                launchImmediately { sceneContainer.changeTo<Menu>() }
            }
        }
        tapString.addOptFixedUpdater(100.milliseconds) {
            tapString.alpha -= 0.1
            if (tapString.alpha <= 0){
                tapString.alpha = 1.0
            }
            if (tapString.y < moveHeight){
                tapString.y += 25
            }
        }
    }
}
