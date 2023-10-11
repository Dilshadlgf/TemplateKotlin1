package com.example.templatekotlin1.ui.survey

import android.app.Activity
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.esafirm.imagepicker.features.cameraonly.CameraOnlyConfig
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.example.templatekotlin1.R
import com.example.templatekotlin1.base.BaseFragment
import com.example.templatekotlin1.common.LoadingDialog
import com.example.templatekotlin1.databinding.FragmentTempBinding
import com.example.templatekotlin1.databinding.SurveyFragBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SurveyFragment :BaseFragment<SurveyFragBinding>(R.layout.survey_frag) {


     val surveyViewModel: SurveyViewModel  by viewModels()

    var cntStep=0
    override fun onInitialized() {
        handleStep()
    }

    override fun setUpClicks() {
        binding.layOwner.btnTakePic.setOnClickListener {
            launcher.launch(CameraOnlyConfig())
        }
    }

    fun handleStep(){
        binding.btnNext.setOnClickListener {
            if(cntStep<binding.stepView.stepCount) {
                cntStep++;
                binding.stepView.go(cntStep, true)
                updateUi()
            }
        }
        binding.btnPrev.setOnClickListener {
            if(cntStep>0) {
                cntStep--
                binding.stepView.go(cntStep, true)
                updateUi()
            }
        }
    }
    private fun updateUi(){
        when (cntStep) {
            0 -> {
                binding.layProperty.root.visibility= View.VISIBLE
                binding.layOwner.root.visibility= View.GONE
                binding.btnNext.text="Next"
            }

            1 -> {
                binding.layProperty.root.visibility= View.GONE
                binding.layOwner.root.visibility= View.VISIBLE
                binding.btnNext.text="Submit"
            }
        }
    }


    val launcher = registerImagePicker { result: List<Image> ->
        result.forEach { image ->
            println(image)
            binding.layOwner.ivPreview.setImageURI(image.uri)
        }
    }
}