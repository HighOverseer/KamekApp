package com.neotelemetrixgdscunand.kamekapp.domain.data

import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.DiagnosisOutput
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.DiagnosisHistoryItemData
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface Repository {
    fun saveDiagnosis(diagnosisOutput: DiagnosisOutput,
                      sessionName:String,
                      sessionDate: Date,
                      imagePath:String)

    fun getAllDiagnosisHistories(): Flow<List<DiagnosisHistoryItemData>>

    fun getDiagnosisOutput(id:Int):DiagnosisOutput
}