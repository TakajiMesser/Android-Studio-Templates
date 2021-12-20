package com.github.takajimesser.androidstudiotemplates.models.templates

import com.android.tools.idea.wizard.template.*

enum class TemplateCategory {
    APP,
    ACTIVITY,
    FRAGMENT,
    DAO,
    REPOSITORY,
    ADAPTER;

    fun toCategory() = when (this) {
        APP -> Category.Application
        ACTIVITY -> Category.Activity
        FRAGMENT -> Category.Fragment
        else -> Category.Other
    }

    fun toContexts() = when (this) {
        APP -> listOf(WizardUiContext.NewProject, WizardUiContext.NewModule, WizardUiContext.MenuEntry)
        ACTIVITY -> listOf(WizardUiContext.ActivityGallery, WizardUiContext.MenuEntry)
        FRAGMENT -> listOf(WizardUiContext.FragmentGallery, WizardUiContext.MenuEntry)
        else -> listOf(WizardUiContext.MenuEntry)
    }

    fun toStringParameters(): List<StringParameter> = when (this) {
        APP -> listOf(
            stringParameter {
                name = "Package name"
                visible = { !isNewModule }
                default = "com.mycompany.myapp"
                constraints = listOf(Constraint.PACKAGE)
                suggest = { packageName }
            }
        )
        ACTIVITY -> listOf(
            stringParameter {
                name = "Fragment Name"
                default = "Home"
                help = "The name of the fragment"
                constraints = listOf(Constraint.NONEMPTY)
                //suggest = { "${activityToLayout(entityName.value.toLowerCase())}s" }
            }
        )
        FRAGMENT -> listOf(
            stringParameter {
                name = "Fragment Name"
                default = "Home"
                help = "The name of the fragment"
                constraints = listOf(Constraint.NONEMPTY)
                //suggest = { "${activityToLayout(entityName.value.toLowerCase())}s" }
            }
        )
        else -> emptyList()
    }
}
