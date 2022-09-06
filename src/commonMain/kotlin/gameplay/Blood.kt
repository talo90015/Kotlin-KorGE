package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class Blood: Container() {
    val count = 5
    val defaultY = ItemManager.baseWidth
    val defaultX = ItemManager.baseHeight / 2
    var hearts = arrayListOf<Image>()
    lateinit var imageHeartEnemy: Bitmap
    lateinit var imageHeartFull: Bitmap

    val maxValue = 5
    val initValue = 5
    var nowValue = initValue

    suspend fun load(){

        imageHeartEnemy = resourcesVfs["hud_heartEmpty.png"].readBitmap()
        imageHeartFull = resourcesVfs["hud_heartFull.png"].readBitmap()
        for (i in 0 until count){
            val heart = image(imageHeartFull)
            hearts.add(heart)
        }
    }
    fun initPosition(){
        hearts.forEachIndexed { index, image ->
            image.apply {
                if (index == 0){
                    position(defaultX, defaultY)
                }else{
                    alignLeftToRightOf(hearts[index - 1])
                    alignTopToTopOf(hearts[index - 1])
                }
            }
        }
    }
    fun plus(){
        if (nowValue < maxValue)
            nowValue++
    }
    fun minus(){
        if (nowValue > 0)
            nowValue--
    }
    fun full(){
        nowValue == maxValue
    }
    fun empty(){
        nowValue == 0
    }
    fun update(){
        for (i in 0 until count){
            if (i < nowValue){
                hearts[i].bitmap = imageHeartFull.slice()
            }else{
                hearts[i].bitmap = imageHeartEnemy.slice()
            }
        }
    }
}
