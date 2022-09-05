package gameplay

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Image

abstract class Item : Container(){
    val baseWidth= 70.0   //物件寬度
    val baseHeight = 70.0  //物件高度
    var moveSpeed = 4    //移動速度
    var defaultX = 0.0     //初始X軸位置
    var mImage: Image? = null   //物件圖片


    abstract suspend fun load() //載入資料
    abstract suspend fun position(x: Int, y: Int)   //擺放位置
    fun move() {
        x -= moveSpeed
    }
    fun stop(){
        moveSpeed = 0
    }
}
