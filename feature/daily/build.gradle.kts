plugins {
    alias(libs.plugins.mtodo.android.feature)
    alias(libs.plugins.mtodo.android.library.compose)
}

android {
    namespace = "com.colddelight.daily"
}

dependencies {
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.2.1")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.2.1")

}