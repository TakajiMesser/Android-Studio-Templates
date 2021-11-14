package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.templates.TemplateFileType

class ModelBuilder {
    companion object {
        fun getProgressFile(packageName: String) = TemplateFile("Progress", "models", TemplateFileType.KOTLIN, """
            package $packageName.models
    
            enum class ProgressState {
                Loading,
                Success,
                Error
            }
            
            data class Progress(val state: ProgressState, val percent: Int, val message: String?) {
                companion object {
                    fun initial() = Progress(ProgressState.Loading, 0, null)
            
                    fun loading(percent: Int) = Progress(ProgressState.Loading, percent, null)
            
                    fun success() = Progress(ProgressState.Success, 100, null)
            
                    fun error(message: String) = Progress(ProgressState.Error, 100, message)
                }
            
                fun <T> toResult(data: T? = null): DownloadProgressResult<T> = when (state) {
                    ProgressState.Loading -> DownloadProgressResult.loading<T>(percent)
                    ProgressState.Success -> DownloadProgressResult.success<T>(data!!)
                    ProgressState.Error -> DownloadProgressResult.error<T>(false)
                }
            }
        
        """.trimIndent())
    }
}
