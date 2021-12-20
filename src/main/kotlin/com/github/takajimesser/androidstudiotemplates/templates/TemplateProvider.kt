package com.github.takajimesser.androidstudiotemplates.templates

import com.github.takajimesser.androidstudiotemplates.models.templates.DependencyType
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateDependency
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateDirectory
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFileType
import com.github.takajimesser.androidstudiotemplates.templates.builders.*

class TemplateProvider(private val packageName: String) : Provider {
    companion object {
        const val LIFECYCLE_VERSION = "2.3.1"
        const val ROOM_VERSION = "2.4.0-alpha04"
        const val KOIN_VERSION = "3.1.2"
        const val NAV_VERSION = "2.4.0-alpha08"
        const val RETROFIT_VERSION = "2.9.0"
        const val RXJAVA_VERSION = "2.2.21"
    }

    override fun getDependencies() = listOf(
        TemplateDependency("com.android.support:multidex", "1.0.3", "", DependencyType.APPLICATION),
        TemplateDependency("org.jetbrains.kotlin:kotlin-stdlib", "1.5.21", "", DependencyType.APPLICATION),
        TemplateDependency("androidx.core:core-ktx", "1.6.0", "", DependencyType.APPLICATION),
        TemplateDependency("androidx.appcompat:appcompat", "1.3.1", "", DependencyType.APPLICATION),
        TemplateDependency("com.google.android.material:material", "1.4.0", "", DependencyType.APPLICATION),
        TemplateDependency("androidx.constraintlayout:constraintlayout", "2.1.0", "", DependencyType.APPLICATION),
        TemplateDependency("androidx.datastore:datastore-preferences", "1.0.0", "", DependencyType.APPLICATION),
        TemplateDependency("androidx.legacy:legacy-support-v4", "1.0.0", "", DependencyType.APPLICATION),
        TemplateDependency("androidx.recyclerview:recyclerview", "1.2.1", "", DependencyType.APPLICATION),
        TemplateDependency("androidx.lifecycle:lifecycle-livedata-ktx", LIFECYCLE_VERSION, "lifecycle_version", DependencyType.APPLICATION),
        TemplateDependency("androidx.lifecycle:lifecycle-viewmodel-ktx", LIFECYCLE_VERSION, "lifecycle_version", DependencyType.APPLICATION),
        TemplateDependency("androidx.lifecycle:lifecycle-process", LIFECYCLE_VERSION, "lifecycle_version", DependencyType.APPLICATION),
        TemplateDependency("androidx.room:room-runtime", ROOM_VERSION, "room_version", DependencyType.APPLICATION),
        TemplateDependency("androidx.room:room-ktx", ROOM_VERSION, "room_version", DependencyType.APPLICATION),
        TemplateDependency("io.insert-koin:koin-core", KOIN_VERSION, "koin_version", DependencyType.APPLICATION),
        TemplateDependency("io.insert-koin:koin-android", KOIN_VERSION, "koin_version", DependencyType.APPLICATION),
        TemplateDependency("io.insert-koin:koin-androidx-compose", KOIN_VERSION, "koin_version", DependencyType.APPLICATION),
        TemplateDependency("androidx.navigation:navigation-fragment-ktx", NAV_VERSION, "nav_version", DependencyType.APPLICATION),
        TemplateDependency("androidx.navigation:navigation-ui-ktx", NAV_VERSION, "nav_version", DependencyType.APPLICATION),
        TemplateDependency("com.squareup.retrofit2:retrofit", RETROFIT_VERSION, "retrofit_version", DependencyType.APPLICATION),
        TemplateDependency("com.squareup.retrofit2:converter-gson", RETROFIT_VERSION, "retrofit_version", DependencyType.APPLICATION),
        TemplateDependency("com.squareup.retrofit2:adapter-rxjava2", RETROFIT_VERSION, "retrofit_version", DependencyType.APPLICATION),
        TemplateDependency("io.reactivex.rxjava2:rxjava", RXJAVA_VERSION, "rxjava_version", DependencyType.APPLICATION)
    )

    /*if (!moduleData.projectTemplateData.androidXSupport) {
        addDependency("com.android.support:support-v4:19.+")
    }*/

    override fun getDirectories() = listOf(
        TemplateDirectory("databases", TemplateFileType.KOTLIN),
        TemplateDirectory("di", TemplateFileType.KOTLIN),
        TemplateDirectory("models/database", TemplateFileType.KOTLIN),
        TemplateDirectory("models/domain", TemplateFileType.KOTLIN),
        TemplateDirectory("models/network/requests", TemplateFileType.KOTLIN),
        TemplateDirectory("models/network/responses", TemplateFileType.KOTLIN),
        TemplateDirectory("networking/adapters", TemplateFileType.KOTLIN),
        TemplateDirectory("networking/converters", TemplateFileType.KOTLIN),
        TemplateDirectory("repositories", TemplateFileType.KOTLIN),
        TemplateDirectory("ui/activities", TemplateFileType.KOTLIN),
        TemplateDirectory("ui/adapters", TemplateFileType.KOTLIN),
        TemplateDirectory("ui/fragments", TemplateFileType.KOTLIN),
        TemplateDirectory("utils", TemplateFileType.KOTLIN),
        TemplateDirectory("viewmodels", TemplateFileType.KOTLIN)
    )

    override fun getFiles() = listOf(
        AppBuilder.getAppFile(packageName),
        AdapterBuilder.getProgressFile(packageName),
        ActivityBuilder.getMainActivitySrcFile(packageName),
        ActivityBuilder.getActivityLayoutFile(),
        DatabaseBuilder.getBaseDaoFile(packageName),
        ModelBuilder.getProgressFile(packageName),
        NetworkingBuilder.getResponseAdapterFactoryFile(packageName),
        NetworkingBuilder.getResponseAdapterFile(packageName),
        NetworkingBuilder.getResponseCallbackFile(packageName),
        NetworkingBuilder.getResponseCallFile(packageName),
        NetworkingBuilder.getApiResponseFile(packageName),
        UtilBuilder.getActivityUtilFile(packageName),
        UtilBuilder.getCastUtilFile(packageName),
        UtilBuilder.getContextUtilFile(packageName),
        UtilBuilder.getLiveDataUtilFile(packageName),
        ViewModelBuilder.getLoadStateFile(packageName)
    )

    /*fun getXmls() = listOf(
        TemplateXML("nav_main", "navigation", navGraphXml(

        ))
    )*/
}
