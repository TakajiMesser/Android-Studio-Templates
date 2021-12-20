package com.github.takajimesser.androidstudiotemplates.models.recipes

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.StringParameter
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateInfo

data class RecipeInfo(
    val executor: RecipeExecutor,
    val moduleData: ModuleTemplateData,
    val templateInfo: TemplateInfo,
    val parameterByName: Map<String, StringParameter>
)
