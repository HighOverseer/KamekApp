package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import com.neotelemetrixgdscunand.kamekapp.R

data class WeeklyNewsItem(
    val id:Int,
    val title:String,
    val imageUrl:String,
    val date:String
)

fun getDummyWeeklyNewsItems():List<WeeklyNewsItem> {
    val imageUrlItems = listOf(
        "https://akcdn.detik.net.id/visual/2023/10/13/tanaman-kakao_169.jpeg?w=715&q=90",
        "https://s3-alpha-sig.figma.com/img/16ae/e26d/55ac26f9cd5838adc3205e27bcb444ea?Expires=1732492800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=bquea0N6lEEFRzntyYuu-Unf5Qyi~Ftr7m8bzSxcfGv28fnNo-hpxpJN1z0rTy~g6hNRxRwCzEU8KNnjetY9oLEwiLMvBGS10cYRZJRFePnP5vzlgfDojWrAgNwdMpkVrltTAZG4WDLZ-C9FJPdPH0tG4zqBKNL3gfcxzL83aiEvAfDl2~q7qi8ggArsLy2xqqMSM4OA5yHCgRQoFPZWGdUfEOJnaZvUwhqElxhQi4dCDulX0AKSbrCNauK4NPLuFtZhiS70OevTSgt3QZa5cncbDPeXjRt~8RyzfuxIq4Z-2rpUPmJ--EzI9UBCaf5jx6DqNV1l2xwKLOMkhYIOWA__",
        "https://s3-alpha-sig.figma.com/img/c1d0/14a5/ad0f86eb166293df43a487550361baaa?Expires=1732492800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Ngp8VEimDdSSYwjA5MMIjEn9ObRD-fgPsePamDAOkMraVNSfehDqhpiaLsVmvpWqZGQ6gtfMoTsfbfWxNETV~EqBqcoaNrxKMDHwPpg0QeH2P~9MHSzOZq2D~vcvH-5AlwcknY~4DnE7~WTCvb5Tp3pSy4KrBPgXZkcg08sJmU~qx7ZDGcvnAJoAMpo7iwvCoXXNgTu2ELRtTbBfkltVoXSL1nUGg39hOjolgH6lizp81sgrXVvzcd6BttZAAU-z-AbtDwjrY3Mb981UngbNaxkMhjD116DfuGZ8t~C3f61xyVx4IiFrPbQYLAtSxcFSp518N1qJ4YOQszutIuv7Rg__",
        "https://s3-alpha-sig.figma.com/img/e104/ecdc/52b267c5bdb537f2101d93fa3f14bf3e?Expires=1732492800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=ElSJ6pR3jd-fdkyYQg6vci8T-MOX~12UCA~H84xTMrGKGdzZqhR8xfB1NVYmn~40kzazdAUVC3vZGaB7168p~nnB6Kvq-sp9-bQcyoI35uiFdX5VP7Dvth11YsUlfx~Zf8b3IB-kQs4ZXh3XFD0JPWQNr3j~ZH81lu1RdfR015QxC3REef1EmhE1hoU72F4I3GnAMgnpF7TtManqZkBTymWQoiO3AixGyqB4ey3B8CR6aXLoFrpNPe1TR9-v-ze2q4ikHjCEkQEcLY0UiE-82FvSYb5Q5UkdNPln6b9-DVY2STEDzjbZ0lhkYwD-Wxp0ImPw1xP26rzAnGRCRn0q8w__",
        "https://s3-alpha-sig.figma.com/img/b17b/fb9b/f779c699c2950e2f6d44167a8d393389?Expires=1732492800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=hE6Onf8E3Bgp2-kSNnYmWv2FFhhmzBRa8Lq1Jn1radPrWJZ-7d7zn4DvDXhUPVbQU1LctcPa1cpXVFFGgpzCHm5aNuDhfsgyA1vW2x21zlti3V-Mo5ylN5RCd4iHhY3zqZFpLYQ9oMGrTwkfiprY3yXaiSrhK2XEqudRzrV3Im-yeQTzIPQjZyNU--xrYRUBEkkvHBdBVdEMqsFc9jCHNeZLyS~zVJO7jRndhJekZsMYpD3bEDkeFWThNpx9fQFO2CTQCh5963zShiFQV6SP08co~9jpuvSlJ92KEFmLny-1zeFoElB6xg5Vw0nEA5l9T8CXD2aSa4KkvxZ3gYer2w__"

    )

    return listOf(
        WeeklyNewsItem(
            id = 0,
            title = "Coklat Terancam langka, Harga Kakao Terbang 98% dalam 3 Bulan",
            imageUrl = imageUrlItems[0],
            date = "22 Maret 2024",
        ),
        WeeklyNewsItem(
            id = 1,
            title = "Pengertian, Gejala, dan Jenis-Jenis Penyakit pada Pertumbuhan Tanaman",
            imageUrl = imageUrlItems[1],
            date = "21 September 2024",
        ),
        WeeklyNewsItem(
            id = 2,
            title = "Pengertian, Gejala, dan Jenis-Jenis Penyakit pada Pertumbuhan Tanaman",
            imageUrl = imageUrlItems[2],
            date = "21 September 2024",
        ),
        WeeklyNewsItem(
            id = 3,
            title = "Pengertian, Gejala, dan Jenis-Jenis Penyakit pada Pertumbuhan Tanaman",
            imageUrl = imageUrlItems[3],
            date = "21 September 2024",
        ),
        WeeklyNewsItem(
            id = 4,
            title = "Pengertian, Gejala, dan Jenis-Jenis Penyakit pada Pertumbuhan Tanaman",
            imageUrl = imageUrlItems[4],
            date = "21 September 2024",
        ),
    )

}
