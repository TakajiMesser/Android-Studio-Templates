package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.templates.TemplateFileType

class UtilBuilder {
    companion object {
        fun getActivityUtilFile(packageName: String) = TemplateFile("ActivityUtils", "utils", TemplateFileType.KOTLIN, """
            package $packageName.utils
    
            import android.app.Activity
            import androidx.core.view.ViewCompat
            import androidx.core.view.WindowCompat
            import androidx.core.view.WindowInsetsCompat
            import androidx.core.view.WindowInsetsControllerCompat
            
            fun Activity.showKeyboard() =
                WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())
            
            fun Activity.hideKeyboard() =
                WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())
            
            fun Activity.showSystemBars() =
                WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.systemBars())
            
            fun Activity.displayInCutouts() {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
                    val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
                    window.decorView.setPadding(0, imeInsets.top, 0, imeInsets.bottom)
                    insets
                }
            }
            
            fun Activity.enterImmersiveFullscreen() {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                WindowInsetsControllerCompat(window, window.decorView).let {
                    it.hide(WindowInsetsCompat.Type.systemBars())
                    it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        
        """.trimIndent())

        fun getCastUtilFile(packageName: String) = TemplateFile("CastUtils", "utils", TemplateFileType.KOTLIN, """
            package $packageName.utils
    
            inline fun <reified T> Any?.tryCast(block: T.() -> Unit) {
                if (this is T) {
                    block()
                }
            }
        
        """.trimIndent())

        fun getContextUtilFile(packageName: String) = TemplateFile("ContextUtils", "utils", TemplateFileType.KOTLIN, """
            package $packageName.utils
    
            import android.content.Context
    
            fun Context.getVersionText(): String {
                val packageManager = this.packageManager
                val packageName = this.packageName
            
                if (packageManager != null && packageName != null) {
                    val packageInfo = packageManager.getPackageInfo(packageName, 0)
            
                    if (packageInfo != null) {
                        return "v$\{packageInfo.versionName}"
                    }
                }
            
                return ""
            }
        
        """.trimIndent())

        fun getLiveDataUtilFile(packageName: String) = TemplateFile("LiveDataUtils", "utils", TemplateFileType.KOTLIN, """
            package $packageName.utils
    
            import androidx.lifecycle.LiveData
            import androidx.lifecycle.MediatorLiveData
            
            fun <TSource1, TSource2, TResult> LiveData<TSource1>.combineWith(
                liveData: LiveData<TSource2>,
                combine: (TSource1?, TSource2?) -> TResult
            ): LiveData<TResult> {
                val mediator = MediatorLiveData<TResult>()
            
                mediator.addSource(this) {
                    mediator.value = combine(this.value, liveData.value)
                }
            
                mediator.addSource(liveData) {
                    mediator.value = combine(this.value, liveData.value)
                }
            
                return mediator
            }
        
        """.trimIndent())
    }
}
