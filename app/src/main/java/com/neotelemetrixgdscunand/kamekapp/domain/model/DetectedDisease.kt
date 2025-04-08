package com.neotelemetrixgdscunand.kamekapp.domain.model


data class DetectedDisease(
    val id:Int,
    val detectedCacaos: List<DetectedCacao>,
    val disease: CacaoDisease
)

fun getDetectedDiseaseDummies():List<DetectedDisease>{
    return listOf(
        DetectedDisease(
            id = 1,
            detectedCacaos = listOf(
                DetectedCacao(
                    name = "Kakao 1",
                    imageUrl = "https://lh3.googleusercontent.com/fife/ALs6j_FBmyzFhcT48JsWTnlztAKHZJVIxq_KWTNbYFRO8aOkqiJtsH0HDNSDjaqO-1SrWvE8AR85sU2QcU93FD_OWlJ_xZQsL4JpmKESvrTSvPr37FzRfsudWwBrs6HPawtN9qXiqM6JRd4htsh1cuv4SUajVBpjwkIuUVFhfdmMgV-qlZsqn7Ui_W2zj3Kf2Z6BuAeQ-rxzhVoMWxsiXxZL1OVHRz4permiV_G8nOHZLLLbJ_hwKLNHpnFPH15efQarYQtdrfiTXwcYSC_F5_sEasYb-IgUrr1o-_T-__wrnZSc6GEF9YUxQcv2urAt5EqcPdGumJ9GWToq9AtYz9XfD5zB0rsBNl2GhHn23p8DIkq3NCJNUM4hGXSVBLIi5GmBxZXnKlox4vKRarRvtzipvfCpd-iCm6i-66uvx31ghCU2LH5DrqjaPB5-hL30W20F7vbzmHHf2j78Hd6wM7xlhptZChs7_AzZxq9fZ_D9dAAkXaZ9uWuKvKddSFiaajWNapI2mcOae_QKEraLv2t41uxjxAhz1kEhgERN_ZQXAuTpaCudlpaqZRa88LkVwdrmEsfcgAcLAWiEnvcwWJNhjD-B7hWMOx4NvxC_Nz3MAhflOI4-i1u4HDkY6GsZ3mBpzaWiKY6Po6tkjJx1UMTozff4YmDK1W59wqd-YZ8ZQkLCjrRJO_FIkVo9RTaQm-kY_4pCz2f4PjWPbVxsUUiHVQJEB-nus4cK2kGS3DMheO11j2i_EcnuEBXH69fazqZFTU9ahQ4_fARXMxn_k30h5Nkl7YiLMW_R3YK4huwaJUCxwhTTv3vWMeQDkSWOTI2lnks1soQyPRRaBCn3tFYY0BlaR9Gdz__3OrXtFECyGcqVfOEuY-tvGtqwQfHBXNG5XqN8e28CM7Pt2f0gFk3ha9K1m6DOJR2dkLy9monvnx_L4UEPfGWIMa2pnSPOORePp9v_nK380f2t15GPjtpznc4ezON3dHidJV3pkwXpsgXQf0gOjSBWdSf8GtIDoHALK0BpPb5nW0nTMkw7bunhDhk9BCB11WjzbsTLi-Tlau17NQAcFaJE6EQlz0ujZsKMK6oshGveVET6gt7480hPoiGo0LCarO8gDXR8Y6QQDY6mearThrt9xquxCwff-SS-sleeJMk9hqahiAHiureTtT_lwKOJIscCZA7PkDy22tx-gCyquIIh0N3layVLsKX-y07p3Bu18y7KFU1Ld9-RA7IfX6aaOyGyqJMDswpiIytQyQXh9cRlCX0Qjn81YNKmQurktSRZ7c99ht17JBo5dGdmXlVk87kHVthwjqygGU9slR6OhgNW0nOHEgDVS-RDv_7Cy3sA55Vs9P82RO8qC1WW7zlUV5gIETD0o_mgy5zBKEgWRhKixYnSoAuXodVNvUNpG4u7Df29GJkq_exP_iOL0OGS3qPMneG_OC6V9Uez4Jd9nVrNpCnySAXksOGNYUiwt_KkOsc98VRAsb5jV5qrd3ad-bziRcIci09WjDNbMjKJ89bO8yZV8DMCJd0uOVRp3DDHRLh8l5vCha8gtLQqLcMV8htomJtJy7hME1v-C8v9iXt6rIGoRRgYOfnnDNQCwBnRhUxA4WSNMbqnw_qQ2F6LEAODfhJfXfR_di9OyPh2q3K9h3mS=w1920-h927",
                    boundingBox = BoundingBox(0.1f, 0.2f, 0.3f, 0.4f, cx = 1f, cy = 1f, h = 1f, w = 1f, cnf = 1f, cls = 1, clsName = "Kakao")
                ),
                DetectedCacao(
                    name = "Kakao 3",
                    imageUrl = "https://lh3.googleusercontent.com/fife/ALs6j_FBmyzFhcT48JsWTnlztAKHZJVIxq_KWTNbYFRO8aOkqiJtsH0HDNSDjaqO-1SrWvE8AR85sU2QcU93FD_OWlJ_xZQsL4JpmKESvrTSvPr37FzRfsudWwBrs6HPawtN9qXiqM6JRd4htsh1cuv4SUajVBpjwkIuUVFhfdmMgV-qlZsqn7Ui_W2zj3Kf2Z6BuAeQ-rxzhVoMWxsiXxZL1OVHRz4permiV_G8nOHZLLLbJ_hwKLNHpnFPH15efQarYQtdrfiTXwcYSC_F5_sEasYb-IgUrr1o-_T-__wrnZSc6GEF9YUxQcv2urAt5EqcPdGumJ9GWToq9AtYz9XfD5zB0rsBNl2GhHn23p8DIkq3NCJNUM4hGXSVBLIi5GmBxZXnKlox4vKRarRvtzipvfCpd-iCm6i-66uvx31ghCU2LH5DrqjaPB5-hL30W20F7vbzmHHf2j78Hd6wM7xlhptZChs7_AzZxq9fZ_D9dAAkXaZ9uWuKvKddSFiaajWNapI2mcOae_QKEraLv2t41uxjxAhz1kEhgERN_ZQXAuTpaCudlpaqZRa88LkVwdrmEsfcgAcLAWiEnvcwWJNhjD-B7hWMOx4NvxC_Nz3MAhflOI4-i1u4HDkY6GsZ3mBpzaWiKY6Po6tkjJx1UMTozff4YmDK1W59wqd-YZ8ZQkLCjrRJO_FIkVo9RTaQm-kY_4pCz2f4PjWPbVxsUUiHVQJEB-nus4cK2kGS3DMheO11j2i_EcnuEBXH69fazqZFTU9ahQ4_fARXMxn_k30h5Nkl7YiLMW_R3YK4huwaJUCxwhTTv3vWMeQDkSWOTI2lnks1soQyPRRaBCn3tFYY0BlaR9Gdz__3OrXtFECyGcqVfOEuY-tvGtqwQfHBXNG5XqN8e28CM7Pt2f0gFk3ha9K1m6DOJR2dkLy9monvnx_L4UEPfGWIMa2pnSPOORePp9v_nK380f2t15GPjtpznc4ezON3dHidJV3pkwXpsgXQf0gOjSBWdSf8GtIDoHALK0BpPb5nW0nTMkw7bunhDhk9BCB11WjzbsTLi-Tlau17NQAcFaJE6EQlz0ujZsKMK6oshGveVET6gt7480hPoiGo0LCarO8gDXR8Y6QQDY6mearThrt9xquxCwff-SS-sleeJMk9hqahiAHiureTtT_lwKOJIscCZA7PkDy22tx-gCyquIIh0N3layVLsKX-y07p3Bu18y7KFU1Ld9-RA7IfX6aaOyGyqJMDswpiIytQyQXh9cRlCX0Qjn81YNKmQurktSRZ7c99ht17JBo5dGdmXlVk87kHVthwjqygGU9slR6OhgNW0nOHEgDVS-RDv_7Cy3sA55Vs9P82RO8qC1WW7zlUV5gIETD0o_mgy5zBKEgWRhKixYnSoAuXodVNvUNpG4u7Df29GJkq_exP_iOL0OGS3qPMneG_OC6V9Uez4Jd9nVrNpCnySAXksOGNYUiwt_KkOsc98VRAsb5jV5qrd3ad-bziRcIci09WjDNbMjKJ89bO8yZV8DMCJd0uOVRp3DDHRLh8l5vCha8gtLQqLcMV8htomJtJy7hME1v-C8v9iXt6rIGoRRgYOfnnDNQCwBnRhUxA4WSNMbqnw_qQ2F6LEAODfhJfXfR_di9OyPh2q3K9h3mS=w1920-h927",
                    boundingBox = BoundingBox(0.1f, 0.2f, 0.3f, 0.4f, cx = 1f, cy = 1f, h = 1f, w = 1f, cnf = 1f, cls = 1, clsName = "Kakao")
                )
            ),
            disease = CacaoDisease.BLACKPOD,
        ),
        DetectedDisease(
            id = 2,
            detectedCacaos = listOf(
                DetectedCacao(
                    name = "Kakao 2",
                    imageUrl = "https://lh3.googleusercontent.com/fife/ALs6j_FBmyzFhcT48JsWTnlztAKHZJVIxq_KWTNbYFRO8aOkqiJtsH0HDNSDjaqO-1SrWvE8AR85sU2QcU93FD_OWlJ_xZQsL4JpmKESvrTSvPr37FzRfsudWwBrs6HPawtN9qXiqM6JRd4htsh1cuv4SUajVBpjwkIuUVFhfdmMgV-qlZsqn7Ui_W2zj3Kf2Z6BuAeQ-rxzhVoMWxsiXxZL1OVHRz4permiV_G8nOHZLLLbJ_hwKLNHpnFPH15efQarYQtdrfiTXwcYSC_F5_sEasYb-IgUrr1o-_T-__wrnZSc6GEF9YUxQcv2urAt5EqcPdGumJ9GWToq9AtYz9XfD5zB0rsBNl2GhHn23p8DIkq3NCJNUM4hGXSVBLIi5GmBxZXnKlox4vKRarRvtzipvfCpd-iCm6i-66uvx31ghCU2LH5DrqjaPB5-hL30W20F7vbzmHHf2j78Hd6wM7xlhptZChs7_AzZxq9fZ_D9dAAkXaZ9uWuKvKddSFiaajWNapI2mcOae_QKEraLv2t41uxjxAhz1kEhgERN_ZQXAuTpaCudlpaqZRa88LkVwdrmEsfcgAcLAWiEnvcwWJNhjD-B7hWMOx4NvxC_Nz3MAhflOI4-i1u4HDkY6GsZ3mBpzaWiKY6Po6tkjJx1UMTozff4YmDK1W59wqd-YZ8ZQkLCjrRJO_FIkVo9RTaQm-kY_4pCz2f4PjWPbVxsUUiHVQJEB-nus4cK2kGS3DMheO11j2i_EcnuEBXH69fazqZFTU9ahQ4_fARXMxn_k30h5Nkl7YiLMW_R3YK4huwaJUCxwhTTv3vWMeQDkSWOTI2lnks1soQyPRRaBCn3tFYY0BlaR9Gdz__3OrXtFECyGcqVfOEuY-tvGtqwQfHBXNG5XqN8e28CM7Pt2f0gFk3ha9K1m6DOJR2dkLy9monvnx_L4UEPfGWIMa2pnSPOORePp9v_nK380f2t15GPjtpznc4ezON3dHidJV3pkwXpsgXQf0gOjSBWdSf8GtIDoHALK0BpPb5nW0nTMkw7bunhDhk9BCB11WjzbsTLi-Tlau17NQAcFaJE6EQlz0ujZsKMK6oshGveVET6gt7480hPoiGo0LCarO8gDXR8Y6QQDY6mearThrt9xquxCwff-SS-sleeJMk9hqahiAHiureTtT_lwKOJIscCZA7PkDy22tx-gCyquIIh0N3layVLsKX-y07p3Bu18y7KFU1Ld9-RA7IfX6aaOyGyqJMDswpiIytQyQXh9cRlCX0Qjn81YNKmQurktSRZ7c99ht17JBo5dGdmXlVk87kHVthwjqygGU9slR6OhgNW0nOHEgDVS-RDv_7Cy3sA55Vs9P82RO8qC1WW7zlUV5gIETD0o_mgy5zBKEgWRhKixYnSoAuXodVNvUNpG4u7Df29GJkq_exP_iOL0OGS3qPMneG_OC6V9Uez4Jd9nVrNpCnySAXksOGNYUiwt_KkOsc98VRAsb5jV5qrd3ad-bziRcIci09WjDNbMjKJ89bO8yZV8DMCJd0uOVRp3DDHRLh8l5vCha8gtLQqLcMV8htomJtJy7hME1v-C8v9iXt6rIGoRRgYOfnnDNQCwBnRhUxA4WSNMbqnw_qQ2F6LEAODfhJfXfR_di9OyPh2q3K9h3mS=w1920-h927",
                    boundingBox = BoundingBox(0.1f, 0.2f, 0.3f, 0.4f, cx = 1f, cy = 1f, h = 1f, w = 1f, cnf = 1f, cls = 1, clsName = "Kakao")
                )
            ),
            disease = CacaoDisease.HELOPELTHIS,
        )
    )
}