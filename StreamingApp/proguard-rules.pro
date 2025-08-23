# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep main activities and classes
-keep public class com.streamingapp.MainActivity
-keep public class com.streamingapp.VideoPlayerActivity
-keep public class com.streamingapp.MoviesActivity
-keep public class com.streamingapp.LiveTvActivity
-keep public class com.streamingapp.TvActivity

# Keep model classes
-keep class com.streamingapp.Movie { *; }
-keep class com.streamingapp.TvChannel { *; }
-keep class com.streamingapp.Movie$StreamingServer { *; }

# Keep adapters
-keep class com.streamingapp.MovieAdapter { *; }
-keep class com.streamingapp.ChannelAdapter { *; }
-keep class com.streamingapp.ServerAdapter { *; }

# Keep WebView JavaScript interface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep ExoPlayer classes
-keep class com.google.android.exoplayer2.** { *; }
-dontwarn com.google.android.exoplayer2.**

# Keep Glide classes
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Keep Retrofit and OkHttp
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**

# Keep Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Keep Room database classes
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Keep Leanback TV classes
-keep class androidx.leanback.** { *; }
-dontwarn androidx.leanback.**

# Keep Material Design classes
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# Keep AndroidX classes
-keep class androidx.** { *; }
-dontwarn androidx.**

# Keep support library classes
-keep class android.support.** { *; }
-dontwarn android.support.**

# Remove logging
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep classes with custom constructors
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Keep custom views
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    *** get*();
}

# Keep R class
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep serializable classes
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keepattributes InnerClasses,EnclosingMethod