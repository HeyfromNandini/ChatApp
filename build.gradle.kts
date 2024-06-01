

buildscript {

    dependencies{
        var hilt_version = "2.39.1"
        classpath ("com.google.gms:google-services:4.3.13")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
    }


}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
