package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFileType

class NetworkingBuilder {
    companion object {
        fun getResponseAdapterFile(packageName: String) = TemplateFile("ResponseAdapter", "networking/adapters", TemplateFileType.KOTLIN, """
            package $packageName.networking.adapters
    
            import $packageName.networking.ApiResponse
            import retrofit2.Call
            import retrofit2.CallAdapter
            import java.lang.reflect.Type
            
            class ResponseAdapter<T>(private val responseType: Type) : CallAdapter<T, Call<ApiResponse<T>>> {
                override fun responseType() = responseType
            
                override fun adapt(call: Call<T>): Call<ApiResponse<T>> {
                    return ResponseCall(call)
                }
            }
        
        """.trimIndent())

        fun getResponseAdapterFactoryFile(packageName: String) = TemplateFile("ResponseAdapterFactory", "networking/adapters", TemplateFileType.KOTLIN, """
            package $packageName.networking.adapters
    
            import $packageName.networking.ApiResponse
            import retrofit2.Call
            import retrofit2.CallAdapter
            import retrofit2.Retrofit
            import java.lang.reflect.ParameterizedType
            import java.lang.reflect.Type
            
            class ResponseAdapterFactory : CallAdapter.Factory() {
                override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
                    if (getRawType(returnType) != Call::class.java) return null
            
                    val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
                    val rawObservableType = getRawType(observableType)
            
                    if (rawObservableType != ApiResponse::class.java) return null
                    if (observableType !is ParameterizedType) return null
            
                    val bodyType = getParameterUpperBound(0, observableType)
                    return ResponseAdapter<Any>(bodyType)
                }
            }
        
        """.trimIndent())

        fun getResponseCallFile(packageName: String) = TemplateFile("ResponseCall", "networking/adapters", TemplateFileType.KOTLIN, """
            package $packageName.networking.adapters
    
            import $packageName.networking.ApiResponse
            import okhttp3.Request
            import okio.Timeout
            import retrofit2.Call
            import retrofit2.Callback
            import retrofit2.Response
            
            class ResponseCall<T>(private val backingCall: Call<T>) : Call<ApiResponse<T>> {
                override fun enqueue(callback: Callback<ApiResponse<T>>) {
                    backingCall.enqueue(object : Callback<T> {
                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            callback.onResponse(this@ResponseCall, Response.success(ApiResponse.create(response)))
                        }
            
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            callback.onResponse(this@ResponseCall, Response.success(ApiResponse.create(t)))
                        }
                    })
                }
                
                override fun execute(): Response<ApiResponse<T>> =
                    throw UnsupportedOperationException("Synchronous execution is unsupported")
            
                override fun isExecuted() = backingCall.isExecuted
            
                override fun clone() = ResponseCall(backingCall.clone())
            
                override fun isCanceled() = backingCall.isCanceled
            
                override fun cancel() = backingCall.cancel()
            
                override fun request(): Request = backingCall.request()
            
                override fun timeout(): Timeout = backingCall.timeout()
            }
        
        """.trimIndent())

        fun getResponseCallbackFile(packageName: String) = TemplateFile("ResponseCallback", "networking/adapters", TemplateFileType.KOTLIN, """
            package $packageName.networking.adapters
    
            import $packageName.networking.ApiResponse
            import retrofit2.Call
            import retrofit2.Callback
            import retrofit2.Response
            
            class ResponseCallback<T>(private val parentCall: ResponseCall<T>, private val parentCallback: Callback<ApiResponse<T>>) : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    parentCallback.onResponse(parentCall, Response.success(ApiResponse.create(response)))
                }
            
                override fun onFailure(call: Call<T>, t: Throwable) {
                    parentCallback.onResponse(parentCall, Response.success(ApiResponse.create(t)))
                }
            }
        
        """.trimIndent())

        fun getApiResponseFile(packageName: String) = TemplateFile("ApiResponse", "networking", TemplateFileType.KOTLIN, """
            package $packageName.networking
    
            import retrofit2.Response
            import java.io.IOException
            
            sealed class ApiResponse<T> {
                companion object {
                    fun <T> create(response: Response<T>): ApiResponse<T> {
                        return if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null && response.code() != 204) {
                                ApiSuccessResponse(body)
                            } else {
                                ApiErrorResponse("Unexpected empty body")
                            }
                        } else {
                            ApiErrorResponse(response.errorBody()?.string() ?: response.message())
                        }
                    }
            
                    fun <T> create(error: Throwable): ApiErrorResponse<T> {
                        return ApiErrorResponse(error.message ?: "", (error is IOException))
                    } 
                }
            }
            
            class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
            
            class ApiErrorResponse<T>(val errorMessage: String, val isNetworkError: Boolean = false) : ApiResponse<T>()
        
        """.trimIndent())

         fun getServiceFile(packageName: String, serviceName: String) = TemplateFile("${serviceName}Service", "networking", TemplateFileType.KOTLIN, """
            package $packageName.networking
            
            import retrofit2.http.*
            
            interface ${serviceName}Service {
                
            }
            
        """.trimIndent())
    }
}
