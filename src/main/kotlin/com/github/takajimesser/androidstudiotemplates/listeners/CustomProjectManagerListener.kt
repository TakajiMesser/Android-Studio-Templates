package com.github.takajimesser.androidstudiotemplates.listeners

import com.github.takajimesser.androidstudiotemplates.services.CustomProjectService
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class CustomProjectManagerListener : ProjectManagerListener {
    companion object {
        var project: Project? = null
    }

    override fun projectOpened(project: Project) {
        CustomProjectManagerListener.project = project
        project.getService(CustomProjectService::class.java)
    }

    override fun projectClosing(project: Project) {
        CustomProjectManagerListener.project = null
        super.projectClosing(project)
    }
}
