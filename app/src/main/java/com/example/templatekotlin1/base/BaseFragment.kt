package com.example.templatekotlin1.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.templatekotlin1.common.LoadingDialog
import com.example.templatekotlin1.base.BaseViewModel

/**
 * Base class for fragments that using databind feature to bind the view
 * also Implements [BaseControlInterface] interface
 * @param T A class that extends [ViewDataBinding] that will be used by the fragment layout binding view.
 * @param layoutId the resource layout view going to bind with the [binding] variable
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment(),
    BaseControlInterface {
    lateinit var binding: T

    abstract fun getViewBinding(): T

    lateinit var  loadingDialog: AlertDialog

    val baseViewModel: BaseViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        if(binding==null) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        }
        loadingDialog= LoadingDialog.initLoadingDialog(requireContext())

        return if(binding is ViewBinding){
            (binding as ViewBinding).root
        }else{
            (binding as ViewDataBinding).lifecycleOwner= this
            (binding as ViewDataBinding).root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
        setUpClicks()
        onInitialized()
    }



    fun showLoading() {
        loadingDialog.show()
    }

    fun stopLoading() {
        loadingDialog.dismiss()
    }
    fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }


}