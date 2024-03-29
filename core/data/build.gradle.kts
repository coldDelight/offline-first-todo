plugins {
    alias(libs.plugins.mtodo.android.library)
    alias(libs.plugins.mtodo.android.hilt)
}

android {
    namespace = "com.colddelight.data"
}

dependencies {


    implementation(project(":core:datastore"))
    implementation(project(":core:database"))

    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.ext.work)
    implementation(libs.androidx.work.ktx)
}