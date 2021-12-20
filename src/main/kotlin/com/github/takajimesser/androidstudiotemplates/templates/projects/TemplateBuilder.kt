package com.github.takajimesser.androidstudiotemplates.templates.projects

import com.android.tools.idea.wizard.template.*
import com.github.takajimesser.androidstudiotemplates.models.recipes.RecipeInfo
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateInfo
import java.io.File

class TemplateBuilder {
    companion object {
        private const val MIN_API = 16

        fun build(templateInfo: TemplateInfo) = template {
            name = templateInfo.name
            description = templateInfo.description
            minApi = MIN_API
            category = templateInfo.category.toCategory()
            formFactor = FormFactor.Mobile
            thumb {
                // TODO - Add dummy thumbnail image
                File("template_base_fragment.png")
            }
            screens = templateInfo.category.toContexts()

            val parameters = templateInfo.category.toStringParameters()
            widgets = parameters.map { parameter ->
                if (parameter.constraints.contains(Constraint.PACKAGE)) {
                    PackageNameWidget(parameter)
                } else {
                    TextFieldWidget(parameter)
                }
            }

            recipe = { data: TemplateData ->
                val moduleData = data as ModuleTemplateData
                val recipeInfo = RecipeInfo(
                    this,
                    moduleData,
                    templateInfo,
                    parameters.associateBy(
                        { it.name },
                        { it }
                    )
                )
                RecipeBuilder.build(recipeInfo)
            }
        }
    }
}

