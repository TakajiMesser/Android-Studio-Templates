package com.github.takajimesser.androidstudiotemplates.services

import com.intellij.openapi.project.Project
import com.github.takajimesser.androidstudiotemplates.MyBundle

class CustomProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
