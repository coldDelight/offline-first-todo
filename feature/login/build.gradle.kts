plugins {
    alias(libs.plugins.mtodo.android.feature)
    alias(libs.plugins.mtodo.android.library.compose)
}
android {
    namespace = "com.colddelight.login"

}

dependencies {

    implementation(project(":core:network"))

    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.compose.auth.ui)

}