import com.soywiz.korge.gradle.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.korge)
}

korge {
	id = "com.sample.Demo"
	supportBox2d()
// To enable all targets at once

	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets

	targetJvm()
	//targetJs()
	//targetDesktop()
	//targetIos()
	//targetAndroidIndirect() // targetAndroidDirect()
}
dependencies {
    add("commonMainApi", "com.google.code.gson:gson:2.8.6")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    add("commonMainApi", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    add("commonMainApi", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
}
