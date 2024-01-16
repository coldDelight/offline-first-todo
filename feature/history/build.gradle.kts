plugins {
    alias(libs.plugins.mtodo.android.feature)
    alias(libs.plugins.mtodo.android.library.compose)
}
android {
    namespace = "com.colddelight.history"

}

dependencies {

    implementation ("com.kizitonwose.calendar:compose:2.4.1")
}