package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class Coin : Item(){
    override suspend fun load() {
        mImage = image(resourcesVfs["coin.png"].readBitmap())
    }

    override suspend fun position(x: Int, y: Int) {
        defaultX = x * baseWidth + (baseWidth - (mImage?.width ?: 0.0)) / 2
        position(defaultX, y * baseHeight + (baseHeight - (mImage?.height ?: 0.0)) / 2)
    }

}
