plugins {
    alias(libs.plugins.mtodo.android.library)
    alias(libs.plugins.mtodo.android.hilt)
//    kotlin("kapt")
}

android {
    namespace = "com.colddelight.database"
}

dependencies {

    implementation(project(":core:model"))
    implementation(project(":core:network"))


    implementation(libs.kotlinx.coroutines.android)


    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

}