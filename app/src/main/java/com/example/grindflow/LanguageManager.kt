package com.example.grindflow

enum class AppLanguage {
    ENGLISH,
    AFRIKAANS,
    ISIXHOSA
}

object LanguageManager {

    var currentLanguage: AppLanguage = AppLanguage.ENGLISH

    fun text(
        english: String,
        afrikaans: String,
        isiXhosa: String
    ): String {
        return when (currentLanguage) {
            AppLanguage.ENGLISH -> english
            AppLanguage.AFRIKAANS -> afrikaans
            AppLanguage.ISIXHOSA -> isiXhosa
        }
    }
}