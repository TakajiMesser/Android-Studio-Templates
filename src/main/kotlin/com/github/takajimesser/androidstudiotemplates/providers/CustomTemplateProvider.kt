package com.github.takajimesser.androidstudiotemplates.providers

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import com.github.takajimesser.androidstudiotemplates.templates.projects.TemplateBuilder
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateCategory
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateInfo

class CustomTemplateProvider : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> = listOf(
        customProjectTemplate,
        customFragmentTemplate
    )

    private val customProjectTemplate
        get() = TemplateBuilder.build(
            TemplateInfo(
                "Custom Project",
                "Creates a custom project.",
                TemplateCategory.APP
            )
        )

    private val customFragmentTemplate
        get() = TemplateBuilder.build(
            TemplateInfo(
                "Custom Fragment",
                "Creates a custom fragment.",
                TemplateCategory.FRAGMENT
            )
        )
}