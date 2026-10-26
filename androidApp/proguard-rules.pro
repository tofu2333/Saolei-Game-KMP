# Keep model data classes
-keep class com.exp.saolei.model.** { *; }

# Compose Multiplatform / Coroutines rules
-keepattributes *Annotation*
-dontwarn java.lang.invoke.MethodHandles$Lookup
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
