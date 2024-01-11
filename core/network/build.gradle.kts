plugins {
    alias(libs.plugins.mtodo.android.library)
    alias(libs.plugins.mtodo.android.hilt)
}

android {
    namespace = "com.colddelight.network"

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}