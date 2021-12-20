package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFileType

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

        fun getProgressDataFile(packageName: String) = TemplateFile("Progress", "models", TemplateFileType.KOTLIN, """
            package $packageName.models
    
            sealed class ProgressData<T> {
                companion object {
                    fun <T> success(data: T) = ProgressDataSuccess(data)

                    fun <T> initial() = ProgressDataProgress<T>(0)

                    fun <T> loading(percent: Int) = ProgressDataProgress<T>(percent)

                    fun <T> failure(error: FetchError) = ProgressDataFailure<T>(error)
                }
            }

            class ProgressDataSuccess<T>(val data: T) : ProgressData<T>()

            class ProgressDataProgress<T>(val percent: Int) : ProgressData<T>()

            class ProgressDataFailure<T>(val error: FetchError) : ProgressData<T>()
        
        """.trimIndent())

        fun getResultFile(packageName: String) = TemplateFile("Progress", "models", TemplateFileType.KOTLIN, """
            package $packageName.models
    
            enum class FetchResultState {
                Success,
                Failure
            }

            data class FetchResult(val state: FetchResultState, val error: FetchError?) {
                companion object {
                    fun success() = FetchResult(FetchResultState.Success, null)

                    fun failure(error: FetchError) = FetchResult(FetchResultState.Failure, error)
                }

                fun <T> toFetchData(data: T? = null, error: FetchError? = null): FetchData<T> = when (state) {
                    FetchResultState.Success -> FetchData.success(data!!)
                    FetchResultState.Failure -> FetchData.failure(error!!)
                }
            }
        
        """.trimIndent())

        fun getResultDataFile(packageName: String) = TemplateFile("Progress", "models", TemplateFileType.KOTLIN, """
            package $packageName.models
    
            sealed class FetchData<T> {
                companion object {
                    fun <T> success(data: T) = FetchDataSuccess(data)

                    fun <T> failure(error: FetchError) = FetchDataFailure<T>(error)
                }
            }

            class FetchDataSuccess<T>(val data: T) : FetchData<T>()

            class FetchDataFailure<T>(val error: FetchError) : FetchData<T>()
        
        """.trimIndent())

        fun getResultErrorFile(packageName: String) = TemplateFile("Progress", "models", TemplateFileType.KOTLIN, """
            package $packageName.models
    
            data class FetchError(
                val message: String
            )
        
        """.trimIndent())
    }
}
