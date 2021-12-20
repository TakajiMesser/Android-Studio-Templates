package com.github.takajimesser.androidstudiotemplates.templates

import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateDependency
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateDirectory
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile

interface Provider {
    fun getDependencies(): List<TemplateDependency>
    fun getDirectories(): List<TemplateDirectory>
    fun getFiles(): List<TemplateFile>
}
