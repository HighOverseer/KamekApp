package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiagnosisResultViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    fun saveDiagnosisResult(diagnosisOutput: DiagnosisOutput, imagePath:String, sessionName:String){
        repository.saveDiagnosis(
            diagnosisOutput = diagnosisOutput,
            sessionName = sessionName,
            sessionDate = Calendar.getInstance().time,
            imagePath = imagePath
        )
    }

    fun getDiagnosisResult(id:Int) = repository.getDiagnosisOutput(id)
}