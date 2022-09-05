package gameplay

import com.soywiz.klock.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class Enemy : Item(){
    lateinit var spriteMap: Bitmap
    lateinit var walkAnimation: SpriteAnimation
    lateinit var walkSprite: Sprite
    override suspend fun load() {
        spriteMap = resourcesVfs["pink_enemy_walk.png"].readBitmap()
        walkAnimation = SpriteAnimation(
            spriteMap = spriteMap,
            spriteWidth = 51,
            spriteHeight = 28,
            marginTop = 0,
            marginLeft = 0,
            columns = 2,
            rows = 1,
            offsetBetweenRows = 0,
            offsetBetweenColumns = 0
        )
        walkSprite = sprite(walkAnimation) {
            spriteDisplayTime = 0.1.seconds
        }
        walkSprite.playAnimationLooped()
    }

    override suspend fun position(x: Int, y: Int) {
        defaultX = x * baseWidth + (baseWidth - (walkSprite.width)) / 2
        position(defaultX, y * baseHeight + (baseHeight - (walkSprite.height)) / 2)
    }

}
