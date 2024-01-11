plugins {
    alias(libs.plugins.mtodo.android.library)
    alias(libs.plugins.mtodo.android.hilt)
}

android {
    namespace = "com.colddelight.datastore"
}

dependencies {

    implementation(project(":core:model"))
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.kotlinx.coroutines.android)
}