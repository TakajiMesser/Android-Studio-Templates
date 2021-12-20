package com.github.takajimesser.androidstudiotemplates.templates

import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateDependency
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateDirectory
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile

interface Generator {
    fun saveDependency(dependency: TemplateDependency)
    fun saveDirectory(directory: TemplateDirectory)
    fun saveFile(file: TemplateFile)
}
