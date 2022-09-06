package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.vector.*

class Coin : Item(){
    val offsetX = 70.0
    val offsetY = 70.0
    override suspend fun load() {
        val bitmap = resourcesVfs["coin.png"].readBitmap()
        image(bitmap)
        hitShape {
            rect(offsetX, offsetY, bitmap.width.toDouble(), bitmap.height.toDouble())
        }
    }


    override suspend fun position(x: Int, y: Int) {
        defaultX = x * baseWidth + (baseWidth - (mImage?.width ?: 70.0)) / 2
        position(defaultX, y * baseHeight + (baseHeight - (mImage?.height ?: 70.0)) / 2)

    }

}
