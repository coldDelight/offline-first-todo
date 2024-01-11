plugins {
    alias(libs.plugins.mtodo.android.library)
    alias(libs.plugins.mtodo.android.hilt)
}

android {
    namespace = "com.colddelight.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)

}