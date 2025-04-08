package com.neotelemetrixgdscunand.kamekapp.data

import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import com.neotelemetrixgdscunand.kamekapp.domain.model.CacaoDisease
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.DiagnosisOutput
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.DiagnosisHistoryItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor():Repository {
    private val diagnosisHistories = MutableStateFlow(
        mutableListOf(
            DiagnosisHistoryItemData(
                id = 0,
                title = "Sampel Kakao Pak Tono"/*"Sampel Kakao Pak Tono"*/,
                imageUrlOrPath = "https://lh3.googleusercontent.com/fife/ALs6j_FBmyzFhcT48JsWTnlztAKHZJVIxq_KWTNbYFRO8aOkqiJtsH0HDNSDjaqO-1SrWvE8AR85sU2QcU93FD_OWlJ_xZQsL4JpmKESvrTSvPr37FzRfsudWwBrs6HPawtN9qXiqM6JRd4htsh1cuv4SUajVBpjwkIuUVFhfdmMgV-qlZsqn7Ui_W2zj3Kf2Z6BuAeQ-rxzhVoMWxsiXxZL1OVHRz4permiV_G8nOHZLLLbJ_hwKLNHpnFPH15efQarYQtdrfiTXwcYSC_F5_sEasYb-IgUrr1o-_T-__wrnZSc6GEF9YUxQcv2urAt5EqcPdGumJ9GWToq9AtYz9XfD5zB0rsBNl2GhHn23p8DIkq3NCJNUM4hGXSVBLIi5GmBxZXnKlox4vKRarRvtzipvfCpd-iCm6i-66uvx31ghCU2LH5DrqjaPB5-hL30W20F7vbzmHHf2j78Hd6wM7xlhptZChs7_AzZxq9fZ_D9dAAkXaZ9uWuKvKddSFiaajWNapI2mcOae_QKEraLv2t41uxjxAhz1kEhgERN_ZQXAuTpaCudlpaqZRa88LkVwdrmEsfcgAcLAWiEnvcwWJNhjD-B7hWMOx4NvxC_Nz3MAhflOI4-i1u4HDkY6GsZ3mBpzaWiKY6Po6tkjJx1UMTozff4YmDK1W59wqd-YZ8ZQkLCjrRJO_FIkVo9RTaQm-kY_4pCz2f4PjWPbVxsUUiHVQJEB-nus4cK2kGS3DMheO11j2i_EcnuEBXH69fazqZFTU9ahQ4_fARXMxn_k30h5Nkl7YiLMW_R3YK4huwaJUCxwhTTv3vWMeQDkSWOTI2lnks1soQyPRRaBCn3tFYY0BlaR9Gdz__3OrXtFECyGcqVfOEuY-tvGtqwQfHBXNG5XqN8e28CM7Pt2f0gFk3ha9K1m6DOJR2dkLy9monvnx_L4UEPfGWIMa2pnSPOORePp9v_nK380f2t15GPjtpznc4ezON3dHidJV3pkwXpsgXQf0gOjSBWdSf8GtIDoHALK0BpPb5nW0nTMkw7bunhDhk9BCB11WjzbsTLi-Tlau17NQAcFaJE6EQlz0ujZsKMK6oshGveVET6gt7480hPoiGo0LCarO8gDXR8Y6QQDY6mearThrt9xquxCwff-SS-sleeJMk9hqahiAHiureTtT_lwKOJIscCZA7PkDy22tx-gCyquIIh0N3layVLsKX-y07p3Bu18y7KFU1Ld9-RA7IfX6aaOyGyqJMDswpiIytQyQXh9cRlCX0Qjn81YNKmQurktSRZ7c99ht17JBo5dGdmXlVk87kHVthwjqygGU9slR6OhgNW0nOHEgDVS-RDv_7Cy3sA55Vs9P82RO8qC1WW7zlUV5gIETD0o_mgy5zBKEgWRhKixYnSoAuXodVNvUNpG4u7Df29GJkq_exP_iOL0OGS3qPMneG_OC6V9Uez4Jd9nVrNpCnySAXksOGNYUiwt_KkOsc98VRAsb5jV5qrd3ad-bziRcIci09WjDNbMjKJ89bO8yZV8DMCJd0uOVRp3DDHRLh8l5vCha8gtLQqLcMV8htomJtJy7hME1v-C8v9iXt6rIGoRRgYOfnnDNQCwBnRhUxA4WSNMbqnw_qQ2F6LEAODfhJfXfR_di9OyPh2q3K9h3mS=w1920-h927",
                date = "9-11-2024",
                predictedPrice = 700f,
                outputId = 0
            ),
            DiagnosisHistoryItemData(
                id = 1,
                title = "Kakao di Taman Belakang",
                imageUrlOrPath = "https://lh3.googleusercontent.com/fife/ALs6j_HcmYH_j-h4EwEqK155-RPK_wshD94bzoZLx614MwI_R6mey7SEBJGStN3YCt5Kkyz1sJXASOSOIcIvTSd_oixj2UxhzVldFSHcvoC5g0gcVMVquadAmmIwi4srPaISNf-SBp2oaqLgJfmMx9GkJPHyxzzWtPPNvtTUBLwN3E6372_FDlC5SAIK9Etsq1A31Gg5xwrIiUTvwlcDRyZahicehfErGq_ZisBBmZdxLNSqhmLkckbpXj1uWnU5ZGEuMcVKocEC4JNxpOAre4crwRk76Qbo7SC8Jan2kFP4NdFGIw_YUHzNiQALiQRPl4-RcjbTv9yIZnEehAC4VpCnNvagXrObNQa2kfOyJ2HPZCuapTXp1w0uF23v1-53z4YezUkW0xNQJEJWQ8AnRWN-YmhqOfQJ7DfFVSo4fnxrLn_H4JjvkZzrf8ullyTKBlpTMxvxovHgJJt0YmRnEp70YFWLTfm-nyr6YalLXgjzzkZdyga9GW9pPJ5LFaPKi0x2k0x-vD2ifTB8IavXjaO5k5dJa9OatJ_yh4-pRxXgsioCHmPTTfPVmwq1Yg_7GeGWDN3ACqZ9cWaJr1O1rLsgycc_p5NgZzSdl148CP6gZvQxhAhgYmq6eZ2iwfoKhl9WuKI0nPWXVCCuLbk5a-qR-zmrM8ORAosr-ULCqn8QrlDxTdrmEuqG1e_350nXkhSGGkzGpdIPXZtsyXn3W6AffIwg7GV17TP6DkCHHgpFZ6H8t5JPyS_LAkI3UGA7wKL_gm91JCq5tJ_KBMTG4CuYw8a6pMzspJlD67wIpsmCFUfe84A0F2rjE2A1ZoTUHx53wKXg-Rb-S4x7OTr9MTnQlK46LDBxOdG9cV-sdV-PtpF9YUcnZw5X3tv7FAhg5jkOxwxH3aZFyEQ0HQ9NYJNNP7BKyx2JJ7fulXOexu6mewP4c7jm3zqRYBx88n94q2d0GSV8x4zGEcfstPwUGb37ndilwhW-PCrec79YqtA_zERKNeyk0RH_jW5TqceagKYRRHBlKayQMNHFYdpBc_oHPCE3Gpm_WJb4iIPIieiIKKnNaeuOxDL-zG0EFC8Sv1q8sAjBvGx_0wAzHt9dcaSqu_pB6UZBZTtLcqbwLRMuoMQWKPCxXDAYLSY-IGumXt1R8jCw0amplogEr-JpS6wA5DnDsVYheYVT9X5XvEB9WtxZlmE5qdkExNQfqN6_r-5QxrtItQwqMU94RM0geZ0_jRxZTvaGbw1z9JqBSm8M7PqSBhkBDIf6vvwuc5IS31NjCClSCz-igDthtNeEdpzOLqW9O5dZADi7MvdNIb1h7STrbENfsWaV8U8m-PqMPEov73K6OYNp2YAawNKsHOxE4GqHopUk9duOSggDCcMpYTZekjSVe4FI_SNPXFgzvNukWZwiFzOl3KVhCuxAonw63ZhwNowrDKPrjqosBUCtW89mtjPPhtGD-N_sA58t5UN5EUKCLpobSsA28T7l_b86HWXciAyX8JKB7JjQcN8WkPO4hKZISNENqjZETxy0fb8qrsWYkrqr4IJN6kS0zcH8S6kYOcXSrNfbRkz6bULgYEQKPyhZXHLiRq2T3QJ0A2PhD5vhClyAFZ3rs__BmJdX7B2Gnj470VLjRFdpQIZgxGc6u9QcrVDz-bd_KA=w1920-h927",
                date = "12-11-2024",
                predictedPrice = 2000f,
                outputId = 1
            ),
            DiagnosisHistoryItemData(
                id = 2,
                title = "Pohon Kakao 1",
                imageUrlOrPath = "https://lh3.googleusercontent.com/fife/ALs6j_E-Bx0LvSqZ3y622ISOIuaV_aVvbaokqdKwKNfq7QxeNq8MiUxfqj0vzXg0Q38K_QZrZta1AUCGYOTmc-SaKGHiUOsm32zgmbr5V_B6le_SOw7py1tshiXKUPav1p-HbFnYx3ZS5ewKtQygdf5AB5G2Ds26bzRAXrXSE0IVmOiRdBAmnGtckQ0wHel5JbTosyZyxbP496nBS28A6gHiET5oDk-qPE-xbmVrKrvmSt7Oo_ft84wg4mfBpOkl6_qKSWTCv2YHTOIFXQUut98QjQFlFbzavf75AfvhvFn7HndYy80ctR0krnD9IRTHo4UUrTiCWtHyCqzbrTlC05Py0gsJfsXsr1CaQsC4AsZxxOKZYD5MZb9ICEutt9kxvl8LRTG1R34W-AvssyqMjtuEj4pg-o4yOlPXNkiPRj-6ekz87fGNxxbqF_2yF0oIao_9XqN0EiypUIJssOMIMg3JsprRuVJIfNB51aBxr4vqtWZZi3Tq1I0_ZV32V1KwhG0kwfbADKteSDT2za8lDqd157QqOC-MDQpAPRhNYydoXzh0RM5gI195vuK26sJGxGIcTlSc-8Dz_uuOakcmoa_5jjf-E0oqY9vbIcbM5KZLNKR2ZGAzy2cOpBSHb4hP20uAYxlyKt-naows_IvNt2gtdPF5zPh86CjJB5tkVgfJ7NwBSPhpl3ffGWUuORt86IqSRqp_qj8qoBE2EDNiBrV2-s5DSSQ74-1_vk7ul5eIcvZMO9oFQ58o0VHBz6T_u_Fy6ts5bn1CMVrTHFxvYyJ_uHvMgdh9QBn46915cFXF_fAUM_iIM0VmpxxjNRQh2boSYcpZH1mvPoQTVY_JW6_ao5bzbX0TPkEyp7_Wqri2Va3VdgETZhvJCs7zidA4cljIsVyLWxP4srGz1wPf8yZS-GNkSzld97rJ9ItcekZJR3-AqYZo1Ba2bHHZLrrQU52KfqMkdMH94OJ77HGWQ2XIh67EGuCHsTCUuzu534IHQtivPdqk_wiZr9VSobtuluJzNgiVBxFLAP8Xd-vLzyUnhHXs83fp85wlgOq2au39Ays7KcAJ7xCePwHvu2u1SIIviK2WmDrGdCxS2_lc47A0QcH7nd7zy83_BnbDB1FetB3m1yg1MJWsjfqRPTLlSOtm63Hdo9s_-wXo0uokUVgSLdNodEkm3-wpv2OwYew4s30ROC2i1mMXuO-HqCns-0rikf-fU3loBHnt5DLWlkV_tF-lkvxxroPqs0p5zDSai-FEKpuWfzgI5gVdWIb6oIizLZo-wcN1QrhS_OTaQrYSPFplVISMt7jEYS9DdiC3SY3mxfhqZJHg0ODP7uGQ_HgpQcCyonx2BCXuNJUnvQo_gPDFhvpwGD7jhCthlB0bk7RqWu4uSFBCmqbX58WL3ui6gQ85sOzt6Ul8m2XTcKk7qTjj7KVuxlsVqwBVnfA1M-9Ifyw61HVo8aWqrRFeCqrf-G6Sj_FiLocYRrHgNZkK2FIx-OAuvrh-zud8-iw0i7jXBmEPxej5amISKFLXxdm_IkDoxdJVl8HWUzpf-sQKgvDXzDlHDDpyTUs5xFfvHXRwIHRM-ulOoPaYT1mBnaxq7fTeowOobt1arhE8Vp9zeWV1jERtFyRePS6sUnkXLBM0Ns9p_yih2KyxeQ=w1920-h927",
                date = "13-11-2024",
                predictedPrice = 600f,
                outputId = 2
            ),
            DiagnosisHistoryItemData(
                id = 3,
                title = "Pohon Kakao 2",
                imageUrlOrPath = "https://lh3.googleusercontent.com/fife/ALs6j_GZzhKqYXGRy0f7f95N_AZSXzxdPQ12EGsuEFQ7V5aASmV-hV3TwPfjieVaE-qMa1yvq-YAjXXCuYadTGSUOt5OXxVkO1ToYBPmXyRWoOlfEZmCCx-6necSRJYWtw1mAmF_6fBUcbmLGIeGgjdqTYGbfAzJZMpuHJRfsSFBaukk1uQquUOxaxMBIuGHMg2de6RfQSWmqAmq6iVLgsBus9smQHClWrf5EY4ZEPv3WPW6KqUOKb4ggja3oWZAoxZms8M_fte8nOlHy3tBZJUC9RS5LBPgBS635xBTEQp6RnW4wMdBxbnKrdKSqjwD5p1Z3QkP9v_bDjZ4CM4ZacV-cH7T1ftAVI-zSsKir8TWSZQ8v74N5cW8kM1rAnNKLK__9lgES_d9x7_vhO7nI6KyPLuT3NLovulWuDKv-s3IchLZqSsL1xBVgz1oyRWMeOIYPXB-DhNuY2kuBt483ipkh8c22ePOW8lNpSB_2i4c6t6qP_LhIsQBy5aLsm_hPjCMS4BGnLHEDx9cWEVDDYsJvgAiWHIm4J1HDg6U-TSRKM7xy5YpF5QVAVL3k6zMfIwTJQwMXvUMC55C7E9Zd9LnRo-JVFN45tMyCDK-KvVGm1B58i2Txy0KNUDlLaiOLrJFe8sNMxERsUGe1CTOJgMV66Blns_vQjnYsLKg0Fce5W7mcFv6y0LmjaJyjZKBY174eOxCQM7CtiqJGBAR1polKdJu2xfWQgBhcY_Y0WL1RVcw17rq0xP0NsYe-EMfmAv9KofVNuOLeH3YnsJ8p_m-KkglwBSvkP5RAkZ37o3yBzU10ww5a3lEMBaNr0BUtrBfAuqrMpteSRpmWAKP8IFbCXDrVAj4GsKoPj7lrJWNCPT_n0qYFNt0Nr7QFvfMMSuo-bvwHKxlymHvPbO-DOGapv_YBOw80C47wAaaPrsZG_K_q27carTkZhXYc9YnOsJ-GA8MlKJyQumZJA-wGj0ylpn2sKpP91fSGd4qZo6ZWBa5VTgW4EWezMjNTRr-6JqgTmG7pgAmAOTSh23K6y9kCaW14486THqRtWIsnJOpUzU1u4vOQm3pPQe792lS0oqZoA5mh5PkG5NzqLt3rj2z62rrohgQmJuq0hWpn2b4YGb4i8VqaTgWMnvsQwkU6a8Zgv2KLwB1tlwfB1uuOysU87LK14dNBn5JfP2SuYPLdKaxEqkeP1pmjBiKpFqaHAaBhYDmmOSv4IYsw0aD8jRglEnO-DZvHw6vlk4gnCkugX2dUzbZke94xFQb5tnYC4iwf-_8QSiVGCRICIdKwQEepNmIWcWNBbZtoZYI8Wb4wrJrT5sv-9f7-Mnd5Mf1mg1BDiYBTPL_Z4AxxhMzuMDYjydmDLHcTDEAIZp-gSoe7QqBHfchNgJ7Jm0vmTerAtmPtQMdTS7up_EVyE12Zc2aEheojyrvoIdclQ1JTD8Nzf0WNDCu5Io9CZj28faec32FLAl2hwXXUOCkVtN8hfv5pF6rRuACnWMJaULzRafWcwZwroXU_IoAfa85b9RcYiXoT8gHyjzU-KmK8tI-kAPDN9cuR8gcUHag4bfG8WDVkGaznIFUy6IDy3BO9bq4omGI2ZCYMyXvOUeIF1jz_p09DH4aPzQ-eXfgdo6cWAlQKzJZmBHUP6iUL4uDCQ=w1920-h927",
                date = "14-11-2024",
                predictedPrice = 1200f,
                outputId = 3
            )
        )
    )



    private val diagnosisOutputs = MutableStateFlow(
        mutableListOf(
            DiagnosisOutput(
                id = 0,
                disease = CacaoDisease.BLACKPOD,
                damageLevel = 0.9f,
                sellPrice = 200f
            ),
            DiagnosisOutput(
                id = 1,
                disease = CacaoDisease.NONE,
                damageLevel = 0f,
                sellPrice = 2000f
            ),
            DiagnosisOutput(
                id = 2,
                disease = CacaoDisease.BLACKPOD,
                damageLevel = 0.6f,
                sellPrice = 800f
            ),
            DiagnosisOutput(
                id = 3,
                disease = CacaoDisease.HELOPELTHIS,
                damageLevel = 0.4f,
                sellPrice = 1200f
            )
        )
    )

    override fun saveDiagnosis(
        diagnosisOutput: DiagnosisOutput,
        sessionName:String,
        sessionDate:Date,
        imagePath:String
    ) {
        val formatDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = formatDate.format(sessionDate) ?: formatDate.format(Calendar.getInstance().time)

        if(dateString == null) throw Exception("e")

        val newHistory = DiagnosisHistoryItemData(
            title = sessionName,
            imageUrlOrPath = imagePath,
            date = dateString,
            outputId = diagnosisOutput.id,
            predictedPrice = diagnosisOutput.sellPrice
        )

        diagnosisHistories.update {
            it.apply {
                add(newHistory)
            }
        }

        diagnosisOutputs.update {
            it.apply {
                add(diagnosisOutput)
            }
        }
    }

    override fun getAllDiagnosisHistories(): Flow<List<DiagnosisHistoryItemData>> {
        return diagnosisHistories.asStateFlow()
    }

    override fun getDiagnosisOutput(id: Int): DiagnosisOutput {
        val output = diagnosisOutputs.value.find { it.id == id }
        return output ?: diagnosisOutputs.value[0]
    }
}