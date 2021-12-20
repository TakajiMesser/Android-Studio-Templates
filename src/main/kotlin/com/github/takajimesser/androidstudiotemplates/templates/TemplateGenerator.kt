package com.github.takajimesser.androidstudiotemplates.templates

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.github.takajimesser.androidstudiotemplates.models.templates.*

class TemplateGenerator(private val executor: RecipeExecutor, moduleData: ModuleTemplateData) : Generator {
    private val packageName = moduleData.packageName // "com.example.myapplication3"
    //private val srcDirectory = moduleData.srcDir // "C:\Users\takaj\MyApplication3\app\src\main\java\com\example\myapplication3"
    private val srcDirectory = moduleData.rootDir.resolve(
        "src/main/java/${moduleData.packageName.split('.').joinToString("/")}"
    )
    private val resDirectory = moduleData.resDir // "C:\Users\takaj\MyApplication3\app\src\main\res"
    private val extVarByName = mutableMapOf<String, String>()

    val manifestDirectory = moduleData.manifestDir // "C:\Users\takaj\MyApplication4\app\src\main"
    val moduleRootDirectory = moduleData.rootDir // "C:\Users\takaj\MyApplication3\app"
    val projectRootDirectory = moduleData.projectTemplateData.rootDir // "C:\Users\takaj\MyApplication3"
    val moduleName = moduleData.name // "app"

    override fun saveDependency(dependency: TemplateDependency) {
        val coordinate = if (dependency.extName.isNotEmpty()) {
            if (!extVarByName.containsKey(dependency.extName)) {
                extVarByName[dependency.extName] = dependency.version
                executor.setExtVar(dependency.extName, dependency.version)
            }

            "${dependency.name}:\$${dependency.extName}"
        } else {
            "${dependency.name}:${dependency.version}"
        }

        when (dependency.dependencyType) {
            DependencyType.APPLICATION -> executor.addDependency(coordinate)
            DependencyType.CLASSPATH -> executor.addClasspathDependency(coordinate)
        }
    }

    override fun saveDirectory(directory: TemplateDirectory) {
        var directoryFile = when (directory.fileType) {
            TemplateFileType.KOTLIN -> srcDirectory
            TemplateFileType.XML -> resDirectory
        }

        directory.directoryPath.split("/").forEach {
            directoryFile = directoryFile.resolve(it)

            if (!directoryFile.exists()) {
                executor.createDirectory(directoryFile)
            }
        }
    }

    override fun saveFile(file: TemplateFile) {
        var outputFile = when (file.fileType) {
            TemplateFileType.KOTLIN -> srcDirectory
            TemplateFileType.XML -> resDirectory
        }

        file.directoryPath.split("/").forEach {
            outputFile = outputFile.resolve(it)
        }

        outputFile = outputFile.resolve("${file.name}.${file.fileType.toExtension()}")
        executor.save(file.contents, outputFile)
    }

    fun saveXml(xml: TemplateXML) {
        var outputFile = resDirectory

        xml.directoryPath.split("/").forEach {
            outputFile = outputFile.resolve(it)
        }

        outputFile = outputFile.resolve("${xml.name}.xml")
        executor.mergeXml(xml.contents, outputFile)
    }

    // TODO - This doesn't seem to work...
    // Save Gradle file
    /*this.save(
        androidConfig(
            gradlePluginVersion = "7.2",
            buildApiString = "30",
            minApi = "21",
            targetApi = "30",
            useAndroidX = true,
            isLibraryProject = false,
            explicitApplicationId = true,
            applicationId = templateGenerator.packageName,
            hasTests = true,
            canUseProguard = true,
            addLintOptions = false,
            enableCpp = false,
            cppStandard = CppStandardType.`Toolchain Default`
        ),
        moduleData.rootDir.resolve("build.gradle")
    )*/

    /*fun createOrFindDirectory(directoryPath: String, fileType: TemplateFileType): PsiDirectory {
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

    private fun getDirectory(file: TemplateFile): PsiDirectory = createOrFindDirectory(file.directoryPath, file.fileType)*/
}
