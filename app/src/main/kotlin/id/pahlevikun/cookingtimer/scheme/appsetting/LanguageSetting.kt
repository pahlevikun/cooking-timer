package id.pahlevikun.cookingtimer.scheme.appsetting

import android.os.Parcelable
import id.pahlevikun.cookingtimer.scheme.appsetting.constant.CountryName
import id.pahlevikun.cookingtimer.scheme.appsetting.constant.CountryType
import id.pahlevikun.cookingtimer.scheme.appsetting.constant.LanguageType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageSetting(
    val countryName: String,
    val countryCode: String? = null,
    val languageCode: String
) : Parcelable {

    companion object Country {
        object Indonesia {
            fun bahasa() =
                LanguageSetting(
                    CountryName.INDONESIA,
                    CountryType.INDONESIA,
                    LanguageType.INDONESIA
                )

            fun english() =
                LanguageSetting(CountryName.OTHERS, CountryType.ENGLISH, LanguageType.ENGLISH)
        }
    }
}