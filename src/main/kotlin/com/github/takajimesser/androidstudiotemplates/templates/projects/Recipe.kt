package com.github.takajimesser.androidstudiotemplates.templates.projects

import com.android.tools.idea.npw.module.recipes.androidConfig
import com.android.tools.idea.wizard.template.CppStandardType
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.github.takajimesser.androidstudiotemplates.listeners.CustomProjectManagerListener
import com.github.takajimesser.androidstudiotemplates.templates.TemplateFileType
import com.github.takajimesser.androidstudiotemplates.templates.TemplateGenerator
import com.github.takajimesser.androidstudiotemplates.templates.builders.*

fun RecipeExecutor.customProjectSetup(moduleData: ModuleTemplateData) {
    val project = CustomProjectManagerListener.project

    if (project != null) {
        val templateGenerator = TemplateGenerator(project, moduleData)
        addAllKotlinDependencies(moduleData)
        setBuildFeature("viewBinding", true)
        setBuildFeature("compose", true)

        // Create project subdirectories
        templateGenerator.createOrFindDirectory("databases", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("di", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("models/database", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("models/domain", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("models/network/requests", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("models/network/responses", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("networking/adapters", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("networking/converters", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("repositories", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("ui/activities", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("ui/adapters", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("ui/fragments", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("utils", TemplateFileType.KOTLIN)
        templateGenerator.createOrFindDirectory("viewmodels", TemplateFileType.KOTLIN)

        // Generate project-level templates
        templateGenerator.saveFile(AppBuilder.getAppFile(moduleData.packageName))
        templateGenerator.saveFile(AdapterBuilder.getProgressFile(moduleData.packageName))
        templateGenerator.saveFile(ActivityBuilder.getMainActivitySrcFile(moduleData.packageName))
        templateGenerator.saveFile(ActivityBuilder.getActivityLayoutFile())
        templateGenerator.saveFile(DatabaseBuilder.getBaseDaoFile(moduleData.packageName))
        templateGenerator.saveFile(ModelBuilder.getProgressFile(moduleData.packageName))
        templateGenerator.saveFile(NetworkingBuilder.getResponseAdapterFactoryFile(moduleData.packageName))
        templateGenerator.saveFile(NetworkingBuilder.getResponseAdapterFile(moduleData.packageName))
        templateGenerator.saveFile(NetworkingBuilder.getResponseCallbackFile(moduleData.packageName))
        templateGenerator.saveFile(NetworkingBuilder.getResponseCallFile(moduleData.packageName))
        templateGenerator.saveFile(NetworkingBuilder.getApiResponseFile(moduleData.packageName))
        templateGenerator.saveFile(UtilBuilder.getActivityUtilFile(moduleData.packageName))
        templateGenerator.saveFile(UtilBuilder.getCastUtilFile(moduleData.packageName))
        templateGenerator.saveFile(UtilBuilder.getContextUtilFile(moduleData.packageName))
        templateGenerator.saveFile(UtilBuilder.getLiveDataUtilFile(moduleData.packageName))
        templateGenerator.saveFile(ViewModelBuilder.getLoadStateFile(moduleData.packageName))

        // TODO - Fix UI freeze when scrolling to "New Project" template
        // TODO - Add dependencies to app-level build.gradle file
        // TODO - Add activity data to AndroidManifest.xml
        // TODO - Define Koin modules (how do we handle adding "get()" calls to each constructor based on the number of parameters?)

        /*this.addClasspathDependency("mavenCoordinate", "minRev")
        this.addDependency("mavenCoordinate", "configuration", "minRev", File("moduleDir"), true)
        this.addModuleDependency("configuration", "moduleName", File("toModule"))*/
        //this.addDependency("com.squareup.retrofit2", "retrofit", "2.9.0")

        // What does this do?
        this.addIncludeToSettings(moduleData.name)

        // TODO - This doesn't seem to work...
        // Save Gradle file
        this.save(
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
        )
    }
}

fun RecipeExecutor.customFragmentSetup(moduleData: ModuleTemplateData, fragmentName: String) {
    val project = CustomProjectManagerListener.project

    if (project != null) {
        val templateGenerator = TemplateGenerator(project, moduleData)

        templateGenerator.saveFile(FragmentBuilder.getFragmentSrcFile(moduleData.packageName, fragmentName))
        templateGenerator.saveFile(FragmentBuilder.getFragmentViewModelFile(moduleData.packageName, fragmentName))
        templateGenerator.saveFile(FragmentBuilder.getFragmentLayoutFile(fragmentName))
    }
}
