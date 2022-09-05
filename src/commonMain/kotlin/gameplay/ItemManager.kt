package gameplay

import com.soywiz.korge.view.*

object ItemManager {
    val baseWidth= 70.0   //物件寬度
    val baseHeight = 70.0  //物件高度
    var baseFloor: Floor? = null

    var items = arrayListOf<Item?>()
    suspend fun init(){
        items.clear()
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
                val item = when(ItemType.values()[stageValue[index++]]){
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
                    else ->{
                        null
                    }
                }
                items.add(item)
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
    }

}
