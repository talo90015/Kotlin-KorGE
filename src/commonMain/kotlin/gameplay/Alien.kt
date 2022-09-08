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
        GOAL,
        STAND
    }
    enum class Character{
        GREEN,
        PURPLE,
        PINK,
        BEIGE,
        YELLOW
    }
    var status = Status.WALK
    var lastStatus = status
    lateinit var spriteMap: Bitmap
    lateinit var hurtMap: Bitmap
    lateinit var jump: Bitmap
    lateinit var standBitmap: Bitmap
    lateinit var headBitmap: Bitmap
    lateinit var failBitmap:Bitmap
    lateinit var goalBitmap: Bitmap
    lateinit var walkAnimation: SpriteAnimation
    lateinit var goalAnimation: SpriteAnimation
    lateinit var walkSprite: Sprite
    lateinit var hurtAnimation: SpriteAnimation
    lateinit var sprite: Sprite
    var defaultY = 395.0
    var alienWalkCount = 11
    var alienWalkSpeed = 0.1
    suspend fun load(index: Character) {
        when(index){
            Character.GREEN ->{
                alienWalkCount = 11
                alienWalkSpeed = 0.1
                standBitmap = resourcesVfs["green_alien_stand.png"].readBitmap()
                spriteMap = resourcesVfs["green_alien_walk.png"].readBitmap()
                hurtMap = resourcesVfs["green_alien_hurt.png"].readBitmap()
                jump = resourcesVfs["green_alien_jump.png"].readBitmap()
                headBitmap = resourcesVfs["green_alien_head.png"].readBitmap()
                failBitmap = resourcesVfs["green_alien_hurt.png"].readBitmap()
                goalBitmap = resourcesVfs["green_alien_goal.png"].readBitmap()
            }
            Character.PURPLE ->{
                alienWalkCount = 11
                alienWalkSpeed = 0.1
                standBitmap = resourcesVfs["purple_alien_stand.png"].readBitmap()
                spriteMap = resourcesVfs["purple_alien_walk.png"].readBitmap()
                hurtMap = resourcesVfs["purple_alien_hurt.png"].readBitmap()
                jump = resourcesVfs["purple_alien_jump.png"].readBitmap()
                headBitmap = resourcesVfs["purple_alien_head.png"].readBitmap()
                failBitmap = resourcesVfs["purple_alien_hurt.png"].readBitmap()
                goalBitmap = resourcesVfs["purple_alien_goal.png"].readBitmap()
            }
            Character.PINK ->{
                alienWalkCount = 11
                alienWalkSpeed = 0.1
                standBitmap = resourcesVfs["pink_alien_stand.png"].readBitmap()
                spriteMap = resourcesVfs["pink_alien_walk.png"].readBitmap()
                hurtMap = resourcesVfs["pink_alien_hurt.png"].readBitmap()
                jump = resourcesVfs["pink_alien_jump.png"].readBitmap()
                headBitmap = resourcesVfs["pink_alien_head.png"].readBitmap()
                failBitmap = resourcesVfs["pink_alien_hurt.png"].readBitmap()
                goalBitmap = resourcesVfs["pink_alien_goal.png"].readBitmap()
            }
            Character.BEIGE ->{
                alienWalkCount = 2
                alienWalkSpeed = 0.3
                standBitmap = resourcesVfs["beige_alien_stand.png"].readBitmap()
                spriteMap = resourcesVfs["beige_alien_walk.png"].readBitmap()
                hurtMap = resourcesVfs["beige_alien_hurt.png"].readBitmap()
                jump = resourcesVfs["beige_alien_jump.png"].readBitmap()
                headBitmap = resourcesVfs["beige_alien_head.png"].readBitmap()
                failBitmap = resourcesVfs["beige_alien_hurt.png"].readBitmap()
                goalBitmap = resourcesVfs["beige_alien_goal.png"].readBitmap()
            }
            Character.YELLOW ->{
                alienWalkCount = 2
                alienWalkSpeed = 0.3
                standBitmap = resourcesVfs["yellow_alien_stand.png"].readBitmap()
                spriteMap = resourcesVfs["yellow_alien_walk.png"].readBitmap()
                hurtMap = resourcesVfs["yellow_alien_hurt.png"].readBitmap()
                jump = resourcesVfs["yellow_alien_jump.png"].readBitmap()
                headBitmap = resourcesVfs["yellow_alien_head.png"].readBitmap()
                failBitmap = resourcesVfs["yellow_alien_hurt.png"].readBitmap()
                goalBitmap = resourcesVfs["yellow_alien_goal.png"].readBitmap()
            }
        }
        hurtAnimation = SpriteAnimation(
            spriteMap = hurtMap,
            spriteWidth = hurtMap.width,
            spriteHeight = hurtMap.height
        )
        walkAnimation = SpriteAnimation(
            spriteMap = spriteMap,
            spriteWidth = spriteMap.width / alienWalkCount,
            spriteHeight = spriteMap.height,
            marginTop = 0,
            marginLeft = 0,
            columns = alienWalkCount,
            rows = 1,
            offsetBetweenColumns = 0,
            offsetBetweenRows = 0
        )
        goalAnimation = SpriteAnimation(
            spriteMap = spriteMap,
            spriteWidth = goalBitmap.width / alienWalkCount,
            spriteHeight = goalBitmap.height,
            marginTop = 0,
            marginLeft = 0,
            offsetBetweenRows = 0,
            offsetBetweenColumns = 0
        )


        sprite = sprite(standBitmap)
//        walkSprite = sprite(walkAnimation) {
//            spriteDisplayTime = 0.1.seconds
//        }
    }

    fun changeStatus() {
        sprite.stopAnimation()
        sprite.removeFromParent()
    }
    fun hurt(): Boolean{
        if (status == Status.HURT){
            return false
        }else{
            changeStatus()
            sprite = sprite(hurtAnimation){
                spriteDisplayTime = 0.8.seconds
            }.apply {
                playAnimation(1)
                onAnimationCompleted{
                    status = lastStatus
                    if (status == Status.WALK){
                        walk()
                    }
                }
            }
            lastStatus = status
            status = Status.HURT
            return true
        }
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
    fun stop(){
        changeStatus()
        status = Status.STAND
        sprite = sprite(standBitmap)
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
            Status.HURT ->{
                if (sprite.alpha >0){
                    sprite.alpha -= 0.1
                }else if(sprite.alpha <= 0.5){
                    sprite.alpha = 1.0
                }
            }
            else -> {
                null
            }
        }
    }
    fun dead(){
        if (status != Status.GOAL && status != Status.DEAD){
            changeStatus()
            sprite = sprite(failBitmap)
            status = Status.DEAD
        }
    }
    fun goal(){
        if (status != Status.GOAL && status != Status.DEAD){
            changeStatus()
            sprite = sprite(goalAnimation){
                spriteDisplayTime = alienWalkSpeed.seconds
            }.apply {
                playAnimationLooped()
            }
            status = Status.GOAL
        }
    }
}
