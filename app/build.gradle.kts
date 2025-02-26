plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    id ("androidx.navigation.safeargs")

}

android {
    namespace = "com.example.tastify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tastify"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        viewBinding = true;
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.core)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.google.android.material:material:1.9.0")

    ///retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.gson)
    implementation (libs.material.v170);

    androidTestImplementation(libs.espresso.core)

    //glider
    implementation (libs.glide)


    ///navigation components
    implementation("androidx.navigation:navigation-fragment:2.8.7")
    implementation("androidx.navigation:navigation-ui:2.8.7")




    //Lottie Animation
    implementation ("com.airbnb.android:lottie:3.4.0")


    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-analytics")

    //fireBase Auth
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.3.0")


    //Room
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    //cardView
    implementation ("androidx.cardview:cardview:1.0.0")

///youtube frame
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")

    //bottomnavigatiuon
    implementation("com.google.android.material:material:1.12.0")


    ///google auth
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    







}