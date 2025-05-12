package com.neotelemetrixgdscunand.kamekapp.presentation.mapper

import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.CocoaDisease
import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCocoa

object CocoaDiseaseMapper {

    val mapToNameResId = mapOf(
        CocoaDisease.NONE to R.string.name_none,
        CocoaDisease.HELOPELTIS to R.string.name_helo,
        CocoaDisease.BLACKPOD to R.string.name_blackpod,
        CocoaDisease.POD_BORER to R.string.penggerek_buah_kakao
    )

    val mapToCauseResId = mapOf(
        CocoaDisease.NONE to R.string.strip,
        CocoaDisease.HELOPELTIS to R.string.cause_helopelthis,
        CocoaDisease.BLACKPOD to R.string.cause_blackpod,
        CocoaDisease.POD_BORER to R.string.cause_pod_borer
    )

    val mapToSymptomResId = mapOf(
        CocoaDisease.NONE to R.string.symptom_none,
        CocoaDisease.HELOPELTIS to R.string.symptom_helopelthis,
        CocoaDisease.BLACKPOD to R.string.symptom_blackpod,
        CocoaDisease.POD_BORER to R.string.symptom_pod_borer
    )

    val mapToSeedConditionResId = mapOf(
        CocoaDisease.NONE to R.string.seedcond_none,
        CocoaDisease.HELOPELTIS to R.string.seedcond_helo,
        CocoaDisease.BLACKPOD to R.string.seedcond_blackpod,
        CocoaDisease.POD_BORER to R.string.seedcond_pod_borer
    )

    val mapToSolutionResId = mapOf(
        CocoaDisease.NONE to R.string.strip,
        CocoaDisease.HELOPELTIS to R.string.disease_solution_temp,
        CocoaDisease.BLACKPOD to R.string.disease_solution_temp,
        CocoaDisease.POD_BORER to R.string.disease_solution_temp
    )

    val mapToListControlResId = mapOf(
        CocoaDisease.NONE to listOf(
            R.string.control_none1,
            R.string.control_none2,
            R.string.control_none3,
            R.string.control_none4
        ),
        CocoaDisease.HELOPELTIS to listOf(
            R.string.control_helo1,
            R.string.control_helo2,
            R.string.control_helo3
        ),
        CocoaDisease.BLACKPOD to listOf(
            R.string.control_blackpod1,
            R.string.control_blackpod2,
            R.string.control_blackpod3
        ),
        CocoaDisease.POD_BORER to listOf(
            R.string.control_pod_borer
        )
    )

    fun getDefaultSolutionResIdOfInfectedDiseases(detectedCocoas: List<DetectedCocoa>): Int {
        val doesContainBlackpod = detectedCocoas.any { it.disease == CocoaDisease.BLACKPOD }
        val doesContainPodBorer = detectedCocoas.any { it.disease == CocoaDisease.POD_BORER }
        val doesContainHelopeltis = detectedCocoas.any { it.disease == CocoaDisease.HELOPELTIS }

        return when {
            doesContainBlackpod && doesContainPodBorer && doesContainHelopeltis -> {
                R.string.default_solution_all_disease
            }

            doesContainBlackpod && doesContainPodBorer -> {
                R.string.blackpod_podborer_default_solution_disease
            }

            doesContainBlackpod && doesContainHelopeltis -> {
                R.string.blackpod_helopeltis_default_solution_disease
            }

            doesContainPodBorer && doesContainHelopeltis -> {
                R.string.podborer_helopeltis_default_solution_disease
            }

            doesContainBlackpod -> {
                R.string.blackpod_default_solution_disease
            }

            doesContainPodBorer -> {
                R.string.podborer_default_solution_disease
            }

            doesContainHelopeltis -> {
                R.string.helopeltis_default_solution_disease
            }

            else -> R.string.strip
        }
    }

    fun getDefaultPreventionsResIdOfInfectedDiseases(detectedCocoas: List<DetectedCocoa>): Int {
        val doesContainBlackpod = detectedCocoas.any { it.disease == CocoaDisease.BLACKPOD }
        val doesContainPodBorer = detectedCocoas.any { it.disease == CocoaDisease.POD_BORER }
        val doesContainHelopeltis = detectedCocoas.any { it.disease == CocoaDisease.HELOPELTIS }

        return when {
            doesContainBlackpod && doesContainPodBorer && doesContainHelopeltis -> {
                R.string.default_prevention_all_disease
            }

            doesContainBlackpod && doesContainPodBorer -> {
                R.string.blackpod_podborer_default_prevention_disease
            }

            doesContainBlackpod && doesContainHelopeltis -> {
                R.string.blackpod_helopeltis_default_prevention_disease
            }

            doesContainPodBorer && doesContainHelopeltis -> {
                R.string.podborer_helopeltis_default_prevention_disease
            }

            doesContainBlackpod -> {
                R.string.blackpod_default_prevention_disease
            }

            doesContainPodBorer -> {
                R.string.podborer_default_prevention_disease
            }

            doesContainHelopeltis -> {
                R.string.helopeltis_default_prevention_disease
            }

            else -> R.string.control_none
        }
    }
}