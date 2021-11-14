package com.github.takajimesser.androidstudiotemplates.templates

import com.intellij.lang.Language
import com.intellij.lang.xml.XMLLanguage
import org.jetbrains.kotlin.idea.KotlinLanguage

enum class TemplateFileType {
    KOTLIN,
    XML;

    fun toLanguage(): Language = when (this) {
        KOTLIN -> KotlinLanguage.INSTANCE
        XML -> XMLLanguage.INSTANCE
    }

    fun toExtension(): String = when (this) {
        KOTLIN -> "kt"
        XML -> "xml"
    }
}
