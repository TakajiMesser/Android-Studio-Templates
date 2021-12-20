package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFileType

class DIBuilder {
    fun getModulesFile(packageName: String) = TemplateFile("KoinModules", "", TemplateFileType.KOTLIN, """
        package $packageName.di

        import android.app.Application
        import androidx.room.Room
        import $packageName.database.SomeDatabase
        import $packageName.networking.SomeService
        import $packageName.networking.adapters.ResponseAdapterFactory
        import $packageName.repositories.Entity1Repository
        import $packageName.repositories.SubBreedRepository
        import $packageName.viewmodels.Entity1ViewModel
        import org.koin.androidx.viewmodel.dsl.viewModel
        import org.koin.dsl.module
        import retrofit2.Retrofit

        val viewModelModule = module {
            viewModel { Entity1ViewModel(get()) }
        }

        val databaseModule = module {
            fun provideDatabase(application: Application) = Room.databaseBuilder(application, DogDatabase::class.java, "dogDB")
                .fallbackToDestructiveMigration()
                .build()

            fun provideBreedDao(database: DogDatabase) = database.breedDao()
            fun provideSubBreedDao(database: DogDatabase) = database.subBreedDao()
            fun provideBreedImageUrlDao(database: DogDatabase) = database.breedImageUrlDao()
            fun provideSubBreedImageUrlDao(database: DogDatabase) = database.subBreedImageUrlDao()

            single { provideDatabase(get()) }
            single { provideBreedDao(get()) }
            single { provideSubBreedDao(get()) }
        }

        val repositoryModule = module {
            single { BreedRepository(get(), get()) }
            single { SubBreedRepository(get(), get()) }
        }

        val serviceModule = module {
            fun provideDogService(retrofit: Retrofit) = retrofit.create(DogService::class.java)

            single { provideDogService(get()) }
        }

        val retrofitModule = module {
            fun provideDogRetrofit() = Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addCallAdapterFactory(ResponseAdapterFactory())
                .build()

            single { provideDogRetrofit() }
        }


    """.trimIndent())
}
