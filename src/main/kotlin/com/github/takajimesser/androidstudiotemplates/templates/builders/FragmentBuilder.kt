package com.github.takajimesser.androidstudiotemplates.templates.builders

import com.android.tools.idea.wizard.template.fragmentToLayout
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFile
import com.github.takajimesser.androidstudiotemplates.models.templates.TemplateFileType

class FragmentBuilder {
    companion object {
        fun getFragmentSrcFile(packageName: String, fragmentName: String) = TemplateFile("${fragmentName}Fragment", "ui/fragments", TemplateFileType.KOTLIN, """
            package $packageName.ui.fragments
        
            import android.os.Bundle
            import android.util.Log
            import android.view.LayoutInflater
            import android.view.View
            import android.view.ViewGroup
            import androidx.fragment.app.Fragment
            import androidx.navigation.NavController
            import androidx.navigation.Navigation
            import $packageName.R
            import $packageName.databinding.Fragment${fragmentName}Binding
            import $packageName.viewmodels.${fragmentName}ViewModel
            import org.koin.androidx.viewmodel.ext.android.viewModel
            
            class ${fragmentName}Fragment : Fragment() {
                companion object {
                    private const val TAG = "${fragmentName}Fragment"
                    fun newInstance() = ${fragmentName}Fragment()
                }
            
                private lateinit var binding: Fragment${fragmentName}Binding
                private lateinit var navController: NavController
                private val vm: ${fragmentName}ViewModel by viewModel()
            
                override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
                    binding = Fragment${fragmentName}Binding.inflate(inflater, container, false)
                    return binding.root
                }
            
                override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    super.onViewCreated(view, savedInstanceState)
                    binding = Fragment${fragmentName}Binding.bind(view)
                    navController = Navigation.findNavController(view)
                }
            }
            
        """.trimIndent())

        fun getFragmentViewModelFile(packageName: String, fragmentName: String) = TemplateFile("${fragmentName}ViewModel", "viewmodels", TemplateFileType.KOTLIN, """
            package $packageName.viewmodels
        
            import androidx.lifecycle.*
            import $packageName.networking.ApiErrorResponse
            import $packageName.networking.ApiSuccessResponse
            import kotlinx.coroutines.flow.first
            import kotlinx.coroutines.launch
            
            class ${fragmentName}ViewModel : ViewModel() {
                companion object {
                    private const val TAG = "${fragmentName}ViewModel"
                }
                
                private val loadState = MutableLiveData<LoadState>()
                
                val loadStateLiveData: LiveData<LoadState> = loadState
            
                init {
                    loadState.value = LoadState.NONE
                }
            }
    
        """.trimIndent())

        fun getFragmentLayoutFile(fragmentName: String) = TemplateFile(fragmentToLayout(fragmentName), "layout", TemplateFileType.XML, """
            <?xml version="1.0" encoding="utf-8"?>
            <androidx.constraintlayout.widget.ConstraintLayout
                xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                xmlns:tools="http://schemas.android.com/tools"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                tools:context=".ui.fragments.${fragmentName}Fragment">
                
            </androidx.constraintlayout.widget.ConstraintLayout>
        """.trimIndent())
    }
}
