package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class Score: Container() {
    val count = 5
    val scoreWidth = 32
    val scoreHeight = 40

    val defaultX = ItemManager.baseWidth * 6
    val defaultY = ItemManager.baseHeight / 2

    lateinit var scoreHead: Image
    lateinit var scoreBitmapSlice: Bitmap
    var scores = arrayListOf<Image>()
    val maxValue = 9999
    val initValue = 0
    var nowValue = initValue

    suspend fun load(){
        scoreHead = image(resourcesVfs["coin.png"].readBitmap())
        scoreBitmapSlice = resourcesVfs["numbers.png"].readBitmap()
        for (i in 0 until count)
            scores.add(image(loadNumber(0)))
    }
    private fun loadNumber(value: Int): Bitmap{
        return scoreBitmapSlice.extract(value * scoreWidth, 0, scoreWidth, scoreHeight)
    }

    fun initPosition(){
        scoreHead.position(defaultX, defaultY)

        scores.forEachIndexed { index, image ->
            image.apply {
                if (index == 0){
                    centerOn(scoreHead)
                    alignLeftToRightOf(scoreHead)
                    x += scoreWidth / 2
                }else{
                    alignLeftToRightOf(scores[index - 1])
                    alignTopToTopOf(scores[index - 1])
                }
            }
        }
    }
    fun plus(){
        if (nowValue < maxValue){
            nowValue++
        }

    }
    fun update(){
        scores[0].bitmap = loadNumber((nowValue / 10000)).slice()
        scores[1].bitmap = loadNumber((nowValue / 10000 / 1000)).slice()
        scores[2].bitmap = loadNumber((nowValue / 1000) / 100).slice()
        scores[3].bitmap = loadNumber((nowValue / 100) / 10).slice()
        scores[4].bitmap = loadNumber((nowValue / 10)).slice()
    }
}
