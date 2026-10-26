# Ignore warnings for missing third-party and platform classes
-dontwarn **
-ignorewarnings

# Project entrypoint and models
-keep class com.exp.saolei.** { *; }

# Skiko & Skia native bindings
-keep class org.jetbrains.skiko.** { *; }
-keep class org.jetbrains.skia.** { *; }

# Kotlin coroutines Swing
-keep class kotlinx.coroutines.swing.** { *; }
-keep class kotlinx.coroutines.** { *; }

# Compose Multiplatform internals
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-dontwarn java.lang.invoke.MethodHandles$Lookup
-dontwarn sun.misc.Unsafe
-dontwarn java.awt.**
-dontwarn javax.swing.**
