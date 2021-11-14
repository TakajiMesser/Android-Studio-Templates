package com.github.takajimesser.androidstudiotemplates.templates.projects

import com.android.tools.idea.wizard.template.*

val customProjectTemplate
    get() = template {
        //revision = 1
        name = "Custom Project"
        description = "Creates a custom project."
        minApi = 16
        //minBuildApi = 16
        category = Category.Application // Check other categories
        formFactor = FormFactor.Mobile
        screens = listOf(
            //WizardUiContext.FragmentGallery,
            WizardUiContext.MenuEntry,
            WizardUiContext.NewProject,
            WizardUiContext.NewModule
        )

        val packageNameParam = stringParameter {
            name = "Package name"
            visible = { !isNewModule }
            default = "com.mycompany.myapp"
            constraints = listOf(Constraint.PACKAGE)
            suggest = { packageName }
        }
        /*val entityName = stringParameter {
            name = "Entity Name"
            default = "Wurst"
            help = "The name of the entity class to create and use in Activity"
            constraints = listOf(Constraint.NONEMPTY)
            //suggest = { "${activityToLayout(entityName.value.toLowerCase())}s" }
        }*/

        widgets(
            //TextFieldWidget(entityName),
            PackageNameWidget(packageNameParam)
        )

        recipe = { data: TemplateData ->
            customProjectSetup(data as ModuleTemplateData)
        }
    }

val customFragmentTemplate
    get() = template {
        //revision = 1
        name = "Custom Fragment"
        description = "Creates a custom fragment."
        minApi = 16
        //minBuildApi = 16
        category = Category.Fragment // Check other categories
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.FragmentGallery,
            WizardUiContext.MenuEntry
        )

        val fragmentName = stringParameter {
            name = "Fragment Name"
            default = "Home"
            help = "The name of the fragment"
            constraints = listOf(Constraint.NONEMPTY)
            //suggest = { "${activityToLayout(entityName.value.toLowerCase())}s" }
        }

        widgets(
            TextFieldWidget(fragmentName),
        )

        recipe = { data: TemplateData ->
            customFragmentSetup(data as ModuleTemplateData, fragmentName.value)
        }
    }
