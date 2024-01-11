plugins {
    alias(libs.plugins.mtodo.android.library)
    alias(libs.plugins.mtodo.android.library.compose)
}

android {
    namespace = "com.colddelight.designsystem"

}

dependencies {
    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.core.ktx)
}