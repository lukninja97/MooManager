package com.lukninja.moomanager.localize

import com.lukninja.moomanager.localize.language.br.localizeStringCountryBR
import com.lukninja.moomanager.localize.language.en.localizeStringCountryEN
import com.lukninja.moomanager.localize.language.es.localizeStringCountryES
import com.moomanager.database.LocalizePreference
import com.moomanager.database.MooManagerDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val EN = "en"
const val PT_BR = "pt"
const val ES = "es"


//REGION
const val BRAZIL = "BR"
const val CANADA = "CA"
const val USA = "US"
const val SPANISH = "ES"

const val UNIQUE_INDEX_LOCALIZE_PREFERENCES = 1L

class LocaleResource(countryCode: String? = null, regionCode: String? = null) : KoinComponent {
    private val database: MooManagerDatabase by inject()

    var currentLanguage: CountryCodeEnum
    var currentRegion: RegionCodeEnum

    init {
      
        if (countryCode != null && regionCode != null) {
            database.localizePreferenceQueries.insert(
                LocalizePreference(
                    UNIQUE_INDEX_LOCALIZE_PREFERENCES, countryCode, regionCode
                )
            )
        }

        val (countryCodeToSet, regionCodeToSet) =
            database.localizePreferenceQueries.select().executeAsList().firstOrNull()?.let {
                it.language to it.region
            } ?: run {
                EN to USA
            }

        when (countryCodeToSet) {
            PT_BR -> this.currentLanguage = CountryCodeEnum.PT_BR
            EN -> this.currentLanguage = CountryCodeEnum.EN
            ES -> this.currentLanguage = CountryCodeEnum.ES
            else -> this.currentLanguage = CountryCodeEnum.EN
        }

        when (regionCodeToSet) {
            BRAZIL -> this.currentRegion = RegionCodeEnum.BRAZIL
            CANADA -> this.currentRegion = RegionCodeEnum.CANADA
            SPANISH -> this.currentRegion = RegionCodeEnum.SPANISH
            else -> this.currentRegion = RegionCodeEnum.USA
        }
    }

    fun getLocalizeString(): LocalizeStringCountry {
        return when (currentLanguage) {
            CountryCodeEnum.PT_BR -> localizeStringCountryBR
            CountryCodeEnum.EN -> localizeStringCountryEN
            CountryCodeEnum.ES -> localizeStringCountryES
        }
    }

    fun currentRouteLanguage() = when (currentLanguage) {
        CountryCodeEnum.PT_BR -> "pt_br"
        CountryCodeEnum.EN -> "en_us"
        CountryCodeEnum.ES -> "es"
    }
}

///instance provider
fun getLocaleResources(): LocaleResource = com.lukninja.moomanager.di.getKoinInstance()

fun String.localizeString(): String {
    return getLocaleResources().getLocalizeString().localizeStrings[this] ?: this
}

fun String.replaceValue(value: String): String {

    val matches = Regex(REPLACE_TAG).findAll(this).count()
    return if (matches > 1) {
        "Error, you are trying to use a string with multiple replace TAGS"
    } else {
        this.replace(
            oldValue = REPLACE_TAG,
            newValue = getLocaleResources().getLocalizeString().localizeStrings[value]
                ?: value
        ).trim()
    }
}

fun String.replaceValues(values: List<String>): String {

    val localeResource = getLocaleResources()
    val matches = Regex(REPLACE_TAG).findAll(this).count()

    if (matches == 1) {
        return "Error, you are trying to use a string with one replace TAG"
    } else {

        val splitString = this.split(
            REPLACE_TAG
        ).mapNotNull {
            if (it.isEmpty()) null else it
        }

        return if (splitString.size == values.size) {
            var finalString = ""
            splitString.indices.forEach { index ->
                finalString += splitString[index] +
                        (localeResource
                            .getLocalizeString().localizeStrings[values[index]]
                            ?: values[index])
            }
            return finalString.trim()
        } else {
            "Error, the size of the values list needs to be equal to the replace TAG($REPLACE_TAG)"
        }
    }
}

///Locale Enums
enum class RegionCodeEnum {
    BRAZIL, CANADA, USA, SPANISH
}

enum class CountryCodeEnum {
    EN,
    PT_BR,
    ES
}
