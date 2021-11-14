package com.github.takajimesser.androidstudiotemplates.providers

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import com.github.takajimesser.androidstudiotemplates.templates.projects.customProjectTemplate
import com.github.takajimesser.androidstudiotemplates.templates.projects.customFragmentTemplate

class CustomTemplateProvider : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> = listOf(
        customProjectTemplate,
        customFragmentTemplate
    )
}