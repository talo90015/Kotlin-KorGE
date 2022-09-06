package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameTime: Container() {
    val count = 2
    val timeWidth = 32
    val timeHeight = 40

    val defaultX = ItemManager.baseWidth * 10
    val defaultY = ItemManager.baseHeight / 2

    lateinit var timerHead: Image
    lateinit var timerBitmapSlice: Bitmap
    var timer = arrayListOf<Image>()
    val initTimer = 60
    var totalTime = initTimer
    var isStop = true

    suspend fun load(){
        timerHead = image(resourcesVfs["clock.png"].readBitmap())
        timerBitmapSlice = resourcesVfs["numbers.png"].readBitmap()
        timer.add(image(loadScore(initTimer / 10)))
        timer.add(image(loadScore(0)))
    }

    private fun loadScore(value: Int): Bitmap {
        return timerBitmapSlice.extract(value * timeWidth, 0, timeWidth, timeHeight)
    }
    fun initPosition(){
        timerHead.position(defaultX, defaultY)
        timer.forEachIndexed { index, image ->
            image.apply {
                if (index == 0){
                    centerOn(timerHead)
                    alignLeftToRightOf(timerHead)
                    x += timeWidth / 2
                }else{
                    alignLeftToRightOf(timer[index - 1])
                    alignTopToTopOf(timer[index - 1])
                }
            }
        }
    }
    fun minus(){
        if (totalTime != 0 && !isStop)
            totalTime -= 1
    }
    fun start(){
        isStop = false
    }
    fun stop(){
        isStop = true
    }
    fun update(){
        timer[0].bitmap = loadScore((totalTime / 10)).slice()
        timer[1].bitmap = loadScore((totalTime % 10)).slice()
    }
}
