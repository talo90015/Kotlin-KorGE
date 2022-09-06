package sance

import ConfigModule
import com.soywiz.kds.iterators.*
import com.soywiz.klock.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.async.delay
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import gameplay.*

class Menu() : Scene() {

    val textStr = "Choose An Alien to GO"
    val fontSize = 30.0
    val parentView = this
    val screenWidth = ConfigModule.size.width.toDouble()
    val screenHeight = ConfigModule.size.height.toDouble()
    var selectRunAlien: Alien? = null
    var headImage: Image? = null
    var goImage: Image? = null
    var statToGo = false

    override suspend fun SContainer.sceneInit() {
        val font = resourcesVfs["Oswald-VariableFont_wght.ttf"].readTtfFont()
        image(resourcesVfs["menu_bg.png"].readBitmap()) {
            scaledWidth = ConfigModule.size.width.toDouble()
            scaledHeight = ConfigModule.size.height.toDouble()
        }
        val bitmap = NativeImage(width = 300, height = 150).apply {
            getContext2d().fillText(
                text = textStr,
                x = 0.0,
                y = 40.0,
                font = font,
                textSize = fontSize,
                color = Colors.WHITE
            )
        }
        val tapString = image(bitmap) {
            position((screenWidth - bitmap.width) / 2, 50.0)
        }
        val alienArray = arrayListOf<Alien>()
        for (i in 0 until Alien.Character.values().count()) {
            val alien = Alien().apply {
                load(Alien.Character.GREEN)
                onClick {
                    selectRunAlien?.stop()
                    selectRunAlien = this
                    walk()
                }
            }
            val bg = this
            var totalAlienWidth = 0.0
            alienArray.add(alien)
            alienArray.fastForEachReverse {
                totalAlienWidth += it.width
                addChild(it)
            }
            val alienSpace = (bg.width - totalAlienWidth) / (alienArray.count() + 1)
            for (i in 0 until alienArray.count()) {
                alienArray[i].apply {
                    when (i) {
                        0 -> {
                            alignBottomToBottomOf(bg)
                            alignLeftToRightOf(bg)
                            y -= 42
                        }
                        else -> {
                            alignBottomToBottomOf(alienArray[i - 1])
                            alignLeftToRightOf(alienArray[i - 1])
                        }
                    }
                    x += alienSpace
                }
            }
            val minDegrees = (-16).degrees
            val maxDegrees = (+16).degrees
            val goBitmap = image(resourcesVfs["go.png"].readBitmap()){
                rotation = maxDegrees
                anchor(0.5,0.5)
                scale = 1.5
                position(650.0, 160.0)
            }.onClick{
                sceneContainer.changeTo<GamePlay>()
            }
            while (true){
                goBitmap.tween(goBitmap!!::rotation[minDegrees], time = 0.8.seconds, easing = Easing.EASE_IN_OUT)
                goBitmap.tween(goBitmap!!::rotation[maxDegrees], time = 0.8.seconds, easing = Easing.EASE_IN_OUT)
            }
//            addUpdater {
//                selectRunAlien?.let {
//                    if (headImage == null){
//                        headImage = image(it.headBitmap){
//                            anchor(0.5,0.5)
//                            alignTopToBottomOf(tapString)
//                            centerXOn(bg)
//                            scale = 1.5
//                            x -= 100
//                            y += 10
//                        }
//                          goImage = image(goBitmap){
//                            anchor(0.5, 0.5)
//                            alignTopToBottomOf(tapString)
//                            centerXOn(bg)
//                            y += 10
//                            onClick {
//                                launchImmediately {
//                                    sceneContainer.changeTo<GamePlay>()
////                                    if (!statToGo){
////                                        statToGo = true
////                                        delay(1.seconds)
////                                    }
//                                }
//                            }
////                            launch {
////                                while (true){
////                                    com.soywiz.korio.async.delay(200.milliseconds)
////                                    tween(this::rotation[(-16).degrees], time = 100.milliseconds, easing = Easing.EASE_IN_OUT)
////                                    tween(this::rotation[(+16).degrees], time = 100.milliseconds, easing = Easing.EASE_IN_OUT)
////                                }
////                            }
//                        }
//                    }else{
//                        headImage!!.bitmap = it.headBitmap.slice()
//                    }
//                }
        }
    }
//        addUpdater {
//            if (statToGo){
//                selectRunAlien?.also {
//                    it.x += 6
//                }
//            }
//        }

}
