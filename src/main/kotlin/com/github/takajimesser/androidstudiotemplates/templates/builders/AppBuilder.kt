package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.templates.TemplateFileType

class AppBuilder {
    companion object {
        fun getAppFile(packageName: String) = TemplateFile("App", "", TemplateFileType.KOTLIN, """
            package $packageName
            
            import android.app.Application
            import android.content.pm.ApplicationInfo
            import android.os.Build
            import android.util.Log
            import androidx.lifecycle.Lifecycle
            import androidx.lifecycle.LifecycleObserver
            import androidx.lifecycle.OnLifecycleEvent
            import androidx.lifecycle.ProcessLifecycleOwner
            import $packageName.di.*
            import org.koin.android.ext.koin.androidContext
            import org.koin.core.context.startKoin
            
            class App : Application(), LifecycleObserver {
                companion object {
                    private const val TAG = "App"
                }
            
                override fun onCreate() {
                    super.onCreate()
            
                    ProcessLifecycleOwner.get()
                        .lifecycle
                        .addObserver(this)
            
                    startKoin {
                        androidContext(this@App)
                        modules(listOf(viewModelModule, repositoryModule, databaseModule, serviceModule, retrofitModule))
                    }
                }
            
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onForeground() {
                    
                }
            
                @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
                fun onBackground() {
                    
                }
            
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onClose() {
                    
                }
            }
        
        """.trimIndent())
    }
}
