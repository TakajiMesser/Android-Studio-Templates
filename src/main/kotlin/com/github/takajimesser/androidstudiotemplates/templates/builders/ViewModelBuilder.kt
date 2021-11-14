package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.github.takajimesser.androidstudiotemplates.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.templates.TemplateFileType

class ViewModelBuilder {
    companion object {
        fun getLoadStateFile(packageName: String) = TemplateFile("LoadState", "viewmodels", TemplateFileType.KOTLIN, """
            package $packageName.viewmodels
    
            import androidx.lifecycle.LiveData
            import androidx.lifecycle.MediatorLiveData
            import androidx.lifecycle.Observer
            
            enum class LoadState {
                NONE,
                LOADING,
                ERROR,
                SUCCESS
            }
            
            class CombinedLiveData(source1: LiveData<LoadState>, source2: LiveData<LoadState>) : MediatorLiveData<LoadState>() {
            
                private var data1: LoadState = LoadState.NONE
                private var data2: LoadState =  LoadState.NONE
            
                init {
                    super.addSource(source1) {
                        data1 = it
                        value = if (data1 == LoadState.NONE && data2 == LoadState.NONE) {
                            LoadState.NONE
                        } else if (data1 == LoadState.LOADING || data2 == LoadState.LOADING) {
                            LoadState.LOADING
                        } else if (data1 == LoadState.ERROR || data2 == LoadState.ERROR) {
                            LoadState.ERROR
                        } else if (data1 == LoadState.SUCCESS || data2 == LoadState.SUCCESS) {
                            LoadState.SUCCESS
                        } else {
                            LoadState.NONE
                        }
                    }
                    super.addSource(source2) {
                        data2 = it
                    }
                }
            
                override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
                    super.addSource(source, onChanged)
                }
            
                override fun <T : Any?> removeSource(toRemove: LiveData<T>) {
                    throw UnsupportedOperationException()
                }
            }
        
        """.trimIndent())
    }
}
