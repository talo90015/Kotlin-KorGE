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

        val parentView = this
        ItemManager.run {
            init()
            load(parentView)
        }
        suspend fun loadCharacter(): Alien {
            return Alien().apply {
                load()
                walk()
            }
        }
        val alien = Alien()
        alien.apply {
            load()
            parentView.addChild(this)
//            alignTopToBottomOf(ItemManager.baseFloor!!)
            x = ItemManager.baseWidth
            position(100.0, 395.0)
//            defaultY = alien.y
            walk()
        }
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
        }
    }
}
