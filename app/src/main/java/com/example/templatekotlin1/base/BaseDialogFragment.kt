package com.example.templatekotlin1.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.templatekotlin1.R
import com.example.templatekotlin1.common.LoadingDialog
import com.example.templatekotlin1.base.BaseViewModel


abstract class BaseDialogFragment<T : Any>( val layoutId: Int) :
    DialogFragment(), BaseControlInterface {


    lateinit var binding: T

    abstract fun getViewBinding(): T

    lateinit var  loadingDialog: AlertDialog

    val baseViewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_Custom);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
//        binding.la = this
        loadingDialog= LoadingDialog.initLoadingDialog(requireContext())
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
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