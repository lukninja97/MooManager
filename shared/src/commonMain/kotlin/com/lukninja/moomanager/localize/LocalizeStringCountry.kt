package com.lukninja.moomanager.localize

data class LocalizeStringCountry(
    val countryIsoCode: String,
    val localizeStrings: HashMap<String, String>
)