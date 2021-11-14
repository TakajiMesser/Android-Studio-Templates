package com.github.takajimesser.androidstudiotemplates.templates

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import org.jetbrains.kotlin.idea.KotlinLanguage
import java.io.File

class TemplateGenerator(val project: Project, val moduleData: ModuleTemplateData) {
    val packageName = moduleData.packageName // "com.example.myapplication3"
    val manifestDirectory = moduleData.manifestDir // "C:\Users\takaj\MyApplication4\app\src\main"
    val moduleRootDirectory = moduleData.rootDir // "C:\Users\takaj\MyApplication3\app"
    val projectRootDirectory = moduleData.projectTemplateData.rootDir // "C:\Users\takaj\MyApplication3"
    val moduleName = moduleData.name // "app"

    private val srcDirectory: PsiDirectory
    private val resDirectory: PsiDirectory
    //val srcDirectory = moduleData.srcDir // "C:\Users\takaj\MyApplication3\app\src\main\java\com\example\myapplication3"
    //val resDirectory = moduleData.resDir // "C:\Users\takaj\MyApplication3\app\src\main\res"

    init {
        val rootManager = ProjectRootManager.getInstance(project)
        val psiManager = PsiManager.getInstance(project)
        val virtualFiles = rootManager.contentSourceRoots

        val virtualSrcDirectory = virtualFiles.first { it.path.contains("src") }
        val virtualResDirectory = virtualFiles.first { it.path.contains("res") }

        srcDirectory = getSrcDirectory(psiManager, virtualSrcDirectory)
        resDirectory = getResDirectory(psiManager, virtualResDirectory)
    }

    fun saveFile(file: TemplateFile) {
        val psiFile = file.toPsiFile(project)
        val psiDirectory = getDirectory(file)

        psiDirectory.add(psiFile)
    }

    fun createOrFindDirectory(directoryPath: String, fileType: TemplateFileType): PsiDirectory {
        var directory = when (fileType) {
            TemplateFileType.KOTLIN -> srcDirectory
            TemplateFileType.XML -> resDirectory
        }

        val pathParts = directoryPath.split("/")

        for (pathPart in pathParts) {
            directory = directory.findSubdirectory(pathPart) ?: directory.createSubdirectory(pathPart)
        }

        return directory
    }

    private fun getDirectory(file: TemplateFile): PsiDirectory = createOrFindDirectory(file.directoryPath, file.fileType)

    private fun getSrcDirectory(psiManager: PsiManager, virtualDirectory: VirtualFile): PsiDirectory {
        var directory = psiManager.findDirectory(virtualDirectory)!!

        packageName.split(".").forEach {
            directory = directory.findSubdirectory(it) ?: directory.createSubdirectory(it)
        }

        return directory
    }

    private fun getResDirectory(psiManager: PsiManager, virtualDirectory: VirtualFile) = psiManager.findDirectory(virtualDirectory)!!
}
