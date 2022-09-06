package sance

import com.soywiz.klock.*
import com.soywiz.korev.Key
import com.soywiz.korge.animate.*
import com.soywiz.korge.input.keys
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import gameplay.*


class GamePlay() : Scene(){

    override suspend fun SContainer.sceneInit(){

        addChild(Background().apply { load() })
        val blood = Blood().apply { load()}
        addChild(blood)
        blood.initPosition()
        blood.update()

        val score = Score().apply { load() }
        addChild(score)
        score.initPosition()
        score.update()

        val timer = GameTime().apply { load() }
        addChild(timer)
        timer.initPosition()
        timer.update()

        val parentView = this
        ItemManager.run {
            init()
            load(parentView)
        }
        val alien = Alien().apply {
            load(index = Alien.Character.GREEN)
            parentView.addChild(this)
            position(100.0, 395.0)
            walk()
        }
        alien.addUpdater {
            if (collidesWithShape(ItemManager.scoreItem)){
                score.plus()
            }
            if (collidesWithShape(ItemManager.hurtItem)){
                if (hurt()){
                    blood.minus()
                }
            }
        }
        ItemManager.setCollision(alien, this)
//        alien.apply {
//            load()
//            parentView.addChild(this)
////            alignTopToBottomOf(ItemManager.baseFloor!!)
//            x = ItemManager.baseWidth
//            position(100.0, 395.0)
////            defaultY = alien.y
//            walk()
//        }

//        alien = loadCharacter()
//        alien.addChild(alien)
//        alien.alignBottomToTopOf(ItemManager.baseFloor!!)
//        alien.defaultY = alien.y
//        alien.x = ItemManager.baseWidth

        keys {
            down (Key.UP){
                alien.jump()
            }
        }
        addUpdater{
            ItemManager.move()
            alien.update()
            blood.update()
            score.update()
            timer.update()
        }
        timer.start()
        addOptFixedUpdater(1.seconds) {
            timer.minus()
        }
    }
}
