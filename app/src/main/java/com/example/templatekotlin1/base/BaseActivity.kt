package com.example.templatekotlin1.base

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
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
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.templatekotlin1.common.LoadingDialog
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint



abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutId: Int)  :
    AppCompatActivity(), BaseControlInterface {

    /**
     * activity layout view binding object
     */
    lateinit var binding: T

//    val activityViewModel: ActivityViewModel by viewModels()

//    private lateinit var mFusedLocationClient:FusedLocationProviderClient
    private lateinit var  loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this@BaseActivity, layoutId) as T
            setContentView((binding as ViewDataBinding).root)

//        binding.lifecycleOwner = this

        loadingDialog= LoadingDialog.initLoadingDialog(this)
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        onInitialized()
        addObservers()
        setUpClicks()

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

    fun showWarningDialog(msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
        customDialog(SweetAlertDialog.WARNING_TYPE,"Warning",msg,id,showCancelBtn,onDialogInterface)
    }
    fun showSuccessDialog(msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener){
        customDialog(SweetAlertDialog.SUCCESS_TYPE,"Success",msg,id,showCancelBtn,onDialogInterface)
    }
    fun showErrorDialog(msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
        customDialog(SweetAlertDialog.ERROR_TYPE,"Error",msg,id,showCancelBtn,onDialogInterface)
    }

    fun showDialog( msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
        customDialog(SweetAlertDialog.NORMAL_TYPE,"Alert",msg,id,showCancelBtn,onDialogInterface)
    }
    fun showSimpleDialog( msg: String){
        customDialog(SweetAlertDialog.NORMAL_TYPE,"Alert",msg,-1,false,null)
    }
    private fun customDialog(alertType:Int,title:String, msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
        val dialog= SweetAlertDialog(this, alertType)
            .setTitleText(title)
            .setContentText(msg)
            .setConfirmText("OK")
            .setConfirmClickListener {
                    sDialog -> sDialog.dismissWithAnimation()
                if(onDialogInterface!=null)
                    onDialogInterface.onClick(sDialog,id)
            }
        if(showCancelBtn){
            dialog.setCancelButton(
                "Cancel"
            ) { sDialog -> sDialog.dismissWithAnimation() }
        }
        dialog.show()
    }
    fun showSnackbar(view: View, message: String) : Snackbar {
        val snackbar= Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
        return snackbar
    }

}