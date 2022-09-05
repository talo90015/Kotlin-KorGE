
import com.soywiz.klock.*
import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korinject.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.interpolation.*
import sance.*

suspend fun main() = Korge(Korge.Config(module = ConfigModule)) object ConfigModule : Module() {

        override val bgcolor: RGBA = Colors["#2b2b2b"]
        override val size = SizeInt(960, 540)
        override val windowSize = SizeInt(1280, 768)
        override val mainScene = GamePlay::class
        override suspend fun AsyncInjector.configure(){
            mapPrototype { Splash() }
            mapPrototype { Menu() }
            mapPrototype { GamePlay() }
            mapPrototype { GameOver() }
            mapPrototype { Rank() }
        }

//        val sceneContainer = sceneContainer()
//        sceneContainer.changeTo({ MyScene() })
    }




class MyScene : Scene() {
	override suspend fun SContainer.sceneMain() {
		val minDegrees = (-16).degrees
		val maxDegrees = (+16).degrees

		val image = image(resourcesVfs["isLogo.png"].readBitmap()) {
			rotation = maxDegrees
			anchor(.5, .5)
			scale(0.8)
			position(256, 256)
		}

		while (true) {
			image.tween(image::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
			image.tween(image::rotation[maxDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
		}
	}
}
