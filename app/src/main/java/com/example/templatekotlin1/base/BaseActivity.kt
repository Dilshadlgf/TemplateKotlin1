package com.example.templatekotlin1.base

import android.Manifest
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.AttributeSet
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.example.templatekotlin1.common.LoadingDialog
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint



abstract class BaseActivity<T : ViewBinding>(@LayoutRes val layoutId: Int)  :
    AppCompatActivity(), BaseControlInterface {

    /**
     * activity layout view binding object
     */
    lateinit var binding: T

//    val activityViewModel: ActivityViewModel by viewModels()

//    private lateinit var mFusedLocationClient:FusedLocationProviderClient
    private lateinit var  loadingDialog: AlertDialog

    abstract fun getViewBinding(): T?

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getViewBinding()==null) {
            binding = DataBindingUtil.setContentView(this@BaseActivity, layoutId) as T
            setContentView((binding as ViewBinding).root)
        }else{
            binding=getViewBinding()!!
            setContentView((binding as ViewBinding).root)
        }

//        binding.lifecycleOwner = this

        loadingDialog= LoadingDialog.initLoadingDialog(this)
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        super.onCreate(savedInstanceState)
        addObservers()
        setUpClicks()
        onInitialized()
        checkPermission()

    }
    private fun checkPermission(){
        PermissionX.init(this)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
//                    showToast("All permissions are granted")

                } else {
                    showToast( "These permissions are denied:")
                }
            }
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}