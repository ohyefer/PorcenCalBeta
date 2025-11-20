# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Reglas recomendadas para aumentar la protección de tu app

# Ofusca todos los nombres de clases, métodos y campos
#-obfuscationdictionary proguard_dictionary.txt
#-classobfuscationdictionary proguard_dictionary.txt
#-packageobfuscationdictionary proguard_dictionary.txt

# Elimina información de depuración
-dontusemixedcaseclassnames
-dontpreverify
-dontoptimize
-dontwarn java.lang.invoke.*
-dontwarn sun.misc.Unsafe
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn org.conscrypt.**
-dontwarn javax.annotation.**
-dontwarn kotlin.**
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn com.google.gson.**
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Mantén las clases y métodos usados por Android
-keep class android.** { *; }
-keep interface android.** { *; }
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Mantén las clases de actividades, servicios, receptores y proveedores
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Mantén los métodos nativos
-keepclasseswithmembernames class * {
    native <methods>;
}

# Mantén los constructores usados en la reflexión
-keepclassmembers class * {
    public <init>(...);
}

# Mantén los métodos usados en la reflexión
-keepclassmembers class * {
    public *;
}

# Mantén los recursos de anotaciones
-keepattributes *Annotation*

# Mantén los nombres de los métodos para la serialización/deserialización
-keepclassmembers class * {
    #noinspection ShrinkerUnresolvedReference
    @com.google.gson.annotations.SerializedName <fields>;
}

# Elimina los logs
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# --- Reglas específicas para Retrofit ---
# Mantén las clases y métodos de Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# --- Reglas específicas para Gson ---
# Mantén las clases y campos anotados para la serialización/deserialización
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.** { *; }
-keepclassmembers class * {
    #noinspection ShrinkerUnresolvedReference
    @com.google.gson.annotations.SerializedName <fields>;
}

# --- Reglas específicas para Room ---
# Mantén las entidades, DAOs y bases de datos de Room
-keep class androidx.room.** { *; }
#noinspection ShrinkerUnresolvedReference
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.RoomOpenHelper
-keep class * extends androidx.room.RoomOpenHelper$OpenHelper
-keep class * implements androidx.room.InvalidationTracker$Observer
-keepclassmembers class * {
    @androidx.room.* <methods>;
    @androidx.room.* <fields>;
}

# --- Reglas específicas para Firebase ---
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Puedes agregar reglas específicas para otras librerías aquí.
