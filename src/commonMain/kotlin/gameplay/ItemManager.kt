package gameplay

import com.soywiz.korge.view.*
import com.soywiz.korma.geom.shape.*

object ItemManager {
    val baseWidth= 70.0   //物件寬度
    val baseHeight = 70.0  //物件高度
    var baseFloor: Floor? = null
    var scoreItem = ArrayList<View>()
    var hurtItem = ArrayList<View>()
    var items = ArrayList<Item>()
    suspend fun init(){
        items.clear()
        scoreItem.clear()
        hurtItem.clear()
        val stageValue = listOf(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 2, 2, 2, 3, 2, 2, 4, 2, 0, 0, 0, 0, 0, 0, 2, 0, 4, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
        )
        var index = 0
        for (y in 0..7){
            for (x in 0..23){
                val itemType = ItemType.values()[stageValue[index++]]
                val item = when(itemType){
                    ItemType.FLOOR ->{
                        Floor().apply {
                            load()
                            position(x, y)
                        }
                    }
                    ItemType.COIN ->{
                        Coin().apply {
                            load()
                            position(x, y)
                        }
                    }
                    ItemType.OBSTACLE ->{
                        Obstacle().apply {
                            load()
                            position(x, y)
                        }
                    }
                    ItemType.ENEMY ->{
                        Enemy().apply {
                            load()
                            position(x, y)
                        }
                    }
                    ItemType.NONE ->{
                        null
                    }
                }
                item?.also {
                    if (itemType == ItemType.COIN){
                        scoreItem.add(item.root)
                    }
                    if (itemType == ItemType.ENEMY || itemType == ItemType.OBSTACLE){
                        hurtItem.add(item.root)
                    }
                    items.add(item)
                }
            }
        }
    }

    fun load(parentView: Container){
        items.forEach{
            it?.run {
                parentView.addChild(this)
            }
        }
    }
    fun move(){
        items.forEach {
            it?.move()
        }
    }
    fun stop(){
        items.forEach {
            it?.stop()
        }
        items.clear()
        scoreItem.clear()
        hurtItem.clear()
    }
    fun setCollision(alien: Alien, parentView: Container){
        items.forEach {
            when(it){
                is Coin ->{
                    it.addUpdater {
                        if (collidesWith(alien.sprite)){
                            parentView.removeChild(this)
                        }
                    }
                }
                is Enemy ->{
                    it.addUpdater {
                        if (collidesWithShape(alien.sprite)){
                            hurtItem.remove(this)
                        }
                    }
                }
                is Obstacle ->{
                    it.addUpdater {
                        if (collidesWithShape(alien.sprite)){
                            hurtItem.remove(this)
                        }
                    }
                }
            }
        }
    }
}
