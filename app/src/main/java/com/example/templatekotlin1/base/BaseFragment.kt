package com.example.templatekotlin1.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.location.Location
import android.location.LocationManager
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
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.templatekotlin1.common.LoadingDialog
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX

/**
 * Base class for fragments that using databind feature to bind the view
 * also Implements [BaseControlInterface] interface
 * @param T A class that extends [ViewDataBinding] that will be used by the fragment layout binding view.
 * @param layoutId the resource layout view going to bind with the [binding] variable
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment(),
    BaseControlInterface {
    lateinit var binding: T


    lateinit var loadingDialog: AlertDialog

    val baseViewModel: BaseViewModel by viewModels()

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    private var locationPermission =false
    private lateinit var onLocationChangeListener: LocationListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        loadingDialog = LoadingDialog.initLoadingDialog(requireContext())

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitialized()
        addObservers()
        setUpClicks()

    }


    fun showLoading() {
        if (!loadingDialog.isShowing)
            loadingDialog.show()
    }

    fun stopLoading() {
        if (loadingDialog.isShowing)
            loadingDialog.dismiss()
    }

    fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    open fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }



    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (isLocationEnabled(requireContext())) {
            mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                val location: Location? = task.result
                if (location != null) {
                    if(onLocationChangeListener!=null){
//                        onLocationChangeListener.onLocation(location)
                        onLocationChangeListener.onLocationChanged(location)
                    }
//                        val geocoder = Geocoder(this, Locale.getDefault())
//                        val list: List<Address> =
//                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                        mainBinding.apply {
//                            tvLatitude.text = "Latitude\n${list[0].latitude}"
//                            tvLongitude.text = "Longitude\n${list[0].longitude}"
//                            tvCountryName.text = "Country Name\n${list[0].countryName}"
//                            tvLocality.text = "Locality\n${list[0].locality}"
//                            tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
                }
            }
        } else {
            requestDeviceLocationSettings()
            showToast("Please turn on location")
        }
    }
    fun getCurrentLocation(onLocationChangeListener: LocationListener){
        this.onLocationChangeListener=onLocationChangeListener
        checkPermissions()

    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions() {
        PermissionX.init(activity)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    locationPermission=true
                    getLocation()
//                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    showToast( "These permissions are denied: $deniedList")
                }
            }
    }

    fun requestDeviceLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            val state = locationSettingsResponse.locationSettingsStates
            getLocation()
//            val label =
//                "GPS >> (Present: ${state.isGpsPresent}  | Usable: ${state.isGpsUsable} ) \n\n" +
//                        "Network >> ( Present: ${state.isNetworkLocationPresent} | Usable: ${state.isNetworkLocationUsable} ) \n\n" +
//                        "Location >> ( Present: ${state.isLocationPresent} | Usable: ${state.isLocationUsable} )"
//
//            showToast(label)

        }

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
        val dialog=SweetAlertDialog(requireContext(), alertType)
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
    fun showSnackbar(view: View,message: String) : Snackbar {
        val snackbar= Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
        return snackbar
    }


}