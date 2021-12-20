package com.github.takajimesser.androidstudiotemplates.models.templates

data class TemplateDependency(
    val name: String,
    val version: String,
    val extName: String,
    val dependencyType: DependencyType
)