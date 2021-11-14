package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.templates.*

class ActivityBuilder {
    companion object {
        fun getMainActivitySrcFile(packageName: String) = TemplateFile("MainActivity", "ui/activities", TemplateFileType.KOTLIN, """
            package $packageName.ui.activities
            
            import android.graphics.Rect
            import android.os.Bundle
            import android.view.MotionEvent
            import android.widget.EditText
            import androidx.appcompat.app.AppCompatActivity
            import $packageName.R
            import $packageName.utils.displayInCutouts
            import $packageName.utils.hideKeyboard
            import $packageName.utils.showSystemBars
            
            class MainActivity : AppCompatActivity() {
                private val viewRect = Rect()
            
                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContentView(R.layout.activity_main)
                    displayInCutouts()
                    showSystemBars()
                }
            
                override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
                    ev?.let {
                        if (ev.action == MotionEvent.ACTION_UP) {
                            currentFocus?.let { view ->
                                if (view is EditText
                                    && view.getGlobalVisibleRect(viewRect)
                                    && !viewRect.contains(ev.rawX.toInt(), ev.rawY.toInt())
                                ) {
                                    // Handle touch beforehand, since hiding keyboard can alter the layout
                                    val dispatchResult = super.dispatchTouchEvent(ev)
            
                                    // If the touch dispatch didn't already clear focus from the original view, do so now
                                    if (view.hasFocus()) {
                                        view.clearFocus()
                                    }
            
                                    // Only hide the keyboard if the newly focused view is not also another EditText
                                    if (currentFocus !is EditText) {
                                        hideKeyboard()
                                    }
            
                                    return dispatchResult
                                }
                            }
                        }
                    }
            
                    return super.dispatchTouchEvent(ev)
                }
            }
        
        """.trimIndent())

        fun getActivityLayoutFile() = TemplateFile("layouts_main", "", TemplateFileType.XML, """
            <?xml version="1.0" encoding="utf-8"?>
            <fragment
                xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                xmlns:tools="http://schemas.android.com/tools"
                android:id="@+id/nav_host_fragment"
                android:name="androidx.navigation.fragment.NavHostFragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:defaultNavHost="true"
                app:navGraph="@navigation/nav_graph"
                tools:context=".activities.MainActivity" />
        """.trimIndent())
    }

    /*private fun saveManifestEntry(name: String, isLauncher: Boolean) = projectInfo.executor.generateManifest(
        projectInfo.moduleData,
        "${name}Activity",
        projectInfo.packageName,
        isLauncher,
        true,
        generateActivityTitle = true
    )*/

    /*public fun com.android.tools.idea.wizard.template.RecipeExecutor.generateManifest(
        moduleData: com.android.tools.idea.wizard.template.ModuleTemplateData,
        activityClass: kotlin.String,
        packageName: kotlin.String,
        isLauncher: kotlin.Boolean,
        hasNoActionBar: kotlin.Boolean,
        noActionBarTheme: com.android.tools.idea.wizard.template.ThemeData,
        isNewModule: kotlin.Boolean,
        isLibrary: kotlin.Boolean,
        manifestOut: java.io.File,
        baseFeatureResOut: java.io.File,
        generateActivityTitle: kotlin.Boolean,
        isResizeable: kotlin.Boolean)*/
}
