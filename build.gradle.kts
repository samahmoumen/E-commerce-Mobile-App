
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
    }
}


plugins {
    id("com.android.application") version "8.2.1" apply false
    id ("com.android.library") version "7.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false

}

