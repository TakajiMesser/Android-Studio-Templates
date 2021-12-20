package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFileType

class RepositoryBuilder {
    companion object {
        fun getRepositoryFile(
            packageName: String,
            repositoryName: String,
            serviceName: String,
            daoName: String
        ) = TemplateFile("${repositoryName}Repository", "repositories", TemplateFileType.KOTLIN, """
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
            
            class ${repositoryName}Repository(
                private val service: $serviceName,
                private val dao: $daoName
            ) {
                companion object {
                    private const val TAG = "${repositoryName}Repository"
                }
            
                fun eventFlow() = dao.asFlow().map {
                    it.map { dbModel ->
                        CalendarEvent.fromDBModel(dbModel)
                    }
                }
            
                suspend fun fetchEvents(after: String, before: String): ApiResponse<CalendarEventsResponse> {
                    //val response = service.getCalendarEvents("2021-11-29T00:00:00Z", "2021-12-05T23:59:59Z")
                    val response = service.getCalendarEvents(after, before)
            
                    when (response) {
                        is ApiSuccessResponse -> dao.replaceAll(response.body.data.map { it.toDBModel() })
                        is ApiErrorResponse -> Log.e(TAG, "Failed to fetch calendar events from the server: ${"$"}{response.errorMessage}")
                    }
            
                    return response
                }
            
                suspend fun deleteEvent(eventId: String): ApiResponse<CalendarEventResponse> {
                    val response = service.deleteCalendarEvent(eventId)
            
                    when (response) {
                        is ApiSuccessResponse -> dao.delete(eventId)
                        is ApiErrorResponse -> Log.e(TAG, "Failed to delete calendar event ${"$"}eventId from the server: ${"$"}{response.errorMessage}")
                    }
            
                    return response
                }
            }
        
        """.trimIndent())
    }
}
