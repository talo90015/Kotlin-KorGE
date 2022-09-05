package gameplay

import com.soywiz.klock.seconds
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.SpriteAnimation
import com.soywiz.korge.view.sprite
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Alien : Container(){
    enum class Status {
        WALK,
        JUMP,
        FALL,
        HURT,
        DEAD,
        GOAL
    }
    var status = Status.WALK
    lateinit var spriteMap: Bitmap
    lateinit var jump: Bitmap
    lateinit var walkAnimation: SpriteAnimation
    lateinit var walkSprite: Sprite
    lateinit var sprite: Sprite
    var defaultY = 395.0
    suspend fun load() {
        spriteMap = resourcesVfs["green_alien_walk.png"].readBitmap()
        jump = resourcesVfs["green_alien_jump.png"].readBitmap()
        walkAnimation = SpriteAnimation(
            spriteMap = spriteMap,
            spriteWidth = 72,
            spriteHeight = 97,
            marginTop = 0,
            marginLeft = 0,
            columns = 11,
            rows = 1,
            offsetBetweenColumns = 0,
            offsetBetweenRows = 0
        )
        walkSprite = sprite(walkAnimation) {
            spriteDisplayTime = 0.1.seconds
        }
    }

    fun changeStatus() {
        sprite.stopAnimation()
        sprite.removeFromParent()
    }
    fun walk() {
        status = Status.WALK
        sprite = sprite(walkAnimation) {
            spriteDisplayTime = 0.1.seconds
        }.apply {
            playAnimationLooped()
        }
    }

    fun jump() {
        if (status != Status.JUMP && status != Status.FALL) {
            changeStatus()
            sprite = sprite(jump)
            status = Status.JUMP
        }
    }
    fun fail(){
        status = Status.FALL
    }
    fun update(){
        when(status) {
            Status.JUMP -> {
                y -= 4
                if (y <= defaultY - 100){
                    fail()
                }
            }
            Status.FALL ->{
                y += 5
                if (y >= defaultY){
                    changeStatus()
                    walk()
                }
            }
            else -> {
                null
            }
        }
    }
}
