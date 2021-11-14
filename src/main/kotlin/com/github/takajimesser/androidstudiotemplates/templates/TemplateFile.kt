package com.github.takajimesser.androidstudiotemplates.templates

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory

data class TemplateFile(
    val name: String,
    val directoryPath: String,
    val fileType: TemplateFileType,
    val contents: String
) {
    fun toPsiFile(project: Project): PsiFile = PsiFileFactory.getInstance(project).createFileFromText(
        "$name.${fileType.toExtension()}",
        fileType.toLanguage(),
        contents
    )
}