package com.github.takajimesser.androidstudiotemplates.templates.projects

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.github.takajimesser.androidstudiotemplates.models.recipes.RecipeInfo
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateCategory
import com.github.takajimesser.androidstudiotemplates.templates.Generator
import com.github.takajimesser.androidstudiotemplates.templates.Provider
import com.github.takajimesser.androidstudiotemplates.templates.TemplateGenerator
import com.github.takajimesser.androidstudiotemplates.templates.TemplateProvider
import com.github.takajimesser.androidstudiotemplates.templates.builders.FragmentBuilder

class RecipeBuilder {
    companion object {
        fun build(recipeInfo: RecipeInfo) {
            val provider = TemplateProvider(recipeInfo.moduleData.packageName)
            val generator = TemplateGenerator(recipeInfo.executor, recipeInfo.moduleData)

            when (recipeInfo.templateInfo.category) {
                TemplateCategory.APP -> {
                    buildApp(recipeInfo.executor, recipeInfo.moduleData, provider, generator)
                }
                TemplateCategory.ACTIVITY -> {
                    val fragmentName = recipeInfo.parameterByName.getValue("Fragment Name")
                    buildFragment(recipeInfo.moduleData, generator, fragmentName.value)
                }
                TemplateCategory.FRAGMENT -> {
                    val fragmentName = recipeInfo.parameterByName.getValue("Fragment Name")
                    buildFragment(recipeInfo.moduleData, generator, fragmentName.value)
                }
            }
        }

        private fun buildApp(executor: RecipeExecutor, moduleData: ModuleTemplateData, provider: Provider, generator: Generator) {
            executor.addAllKotlinDependencies(moduleData)

            executor.setBuildFeature("viewBinding", true)
            executor.setBuildFeature("compose", true)
            //addIncludeToSettings(moduleData.name)
            //setKotlinVersion("")

            provider.getDependencies().forEach {
                generator.saveDependency(it)
            }

            provider.getDirectories().forEach {
                generator.saveDirectory(it)
            }

            provider.getFiles().forEach {
                generator.saveFile(it)
            }

            // TODO - Add activity data to AndroidManifest.xml
            // TODO - Define Koin modules (how do we handle adding "get()" calls to each constructor based on the number of parameters?)
            // TODO - Call open() on any files we want to have opened up on project startup
        }

        private fun buildFragment(moduleData: ModuleTemplateData, generator: Generator, fragmentName: String) {
            generator.saveFile(FragmentBuilder.getFragmentSrcFile(moduleData.packageName, fragmentName))
            generator.saveFile(FragmentBuilder.getFragmentViewModelFile(moduleData.packageName, fragmentName))
            generator.saveFile(FragmentBuilder.getFragmentLayoutFile(fragmentName))
        }
    }
}
