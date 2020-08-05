package com.example.ecommerce.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerce.common.application.EMarketApplication

object PreferenceHelper {

    private const val PREFERENCE_NAME = "wellsite_preferences"
    private const val PERSISTENT_PREFERENCES = "wellsite_persistent_preferences"

    fun getEMarketPreferences(): SharedPreferences =
        EMarketApplication.get().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getEMarketPersistentPreferences(): SharedPreferences =
        EMarketApplication.get().getSharedPreferences(PERSISTENT_PREFERENCES, Context.MODE_PRIVATE)

    enum class Key(val key: String) {
        ACCESS_TOKEN("key_access_token"),
        USER_ID("key_id"),
        USER_EMAIL("key_email"),
        USER_FIRST_NAME("key_first_name"),
        USER_LAST_NAME("key_last_name"),
        USER_PHONE("key_phone"),
        USER_EMPLOYER("key_employer"),
        USER_ROLES("key_roles"),
        USER_STATES("key_states"),
        USER_MAP_STYLE("key_map_style"),
        USER_LAYER("key_layer"),
        PERSISTENT_FIRST_OPEN("first_open"),
        PERSISTENT_DAILY_COUNTER("daily_counter_date"),
        PERSISTENT_THREE_DAYS_COUNTER("three_days_counter_date"),
        PERSISTENT_WEEKLY_COUNTER("weekly_counter_date"),
        PERSISTENT_WEEKLY_REVIEW_COUNTER("weekly_review_counter_date"),
        PERSISTENT_TWO_WEEKS_REVIEW_COUNTER("two_weeks_review_counter_date"),
        USER_REVIEW_DONE("key_review_done"),
        USER_REVIEW_RATE_DONE("key_review_rate_done"),
        SHOW_INSTALLING_POP_UP("key_show_installing_pop_up"),
        USER_OLD_APP_CREDITS("updates"),
        USER_FEATURES("key_features"),
        USER_CREDITS_USER_ID("key_credits_user_id"),
        USER_TYPE("key_user_type"),
        USER_IS_BUSINESS("key_user_is_business"),
        USER_SHOW_NEARBY_MARKERS("key_user_show_nearby_markers"),
        USER_SHOW_NEARBY_CUSTOM_MARKERS("key_user_show_nearby_custom_markers"),
        USER_SHOW_ONLY_FAVORITES("key_user_show_only_favorites"),
        USER_FOLDER_SHOWING_FAVORITES("key_user_folder_showing_favorites"),
        USER_DARK_MODE("key_dark_mode")
    }

    fun clearSharedPreferences() {
        val sharedPreferences = getEMarketPreferences()
        val credits: Int? = sharedPreferences.get(Key.USER_OLD_APP_CREDITS)
        sharedPreferences.edit().clear().apply()
        if (credits != null && credits > 0) sharedPreferences.set(Key.USER_OLD_APP_CREDITS, credits)
    }
}

/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
fun SharedPreferences.set(key: PreferenceHelper.Key, value: Any?) {
    when (value) {
        is String -> edit { it.putString(key.key, value) }
        is Int -> edit { it.putInt(key.key, value) }
        is Boolean -> edit { it.putBoolean(key.key, value) }
        is Float -> edit { it.putFloat(key.key, value) }
        is Long -> edit { it.putLong(key.key, value) }
        null -> edit { it.remove(key.key) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
inline fun <reified T : Any> SharedPreferences.get(
    key: PreferenceHelper.Key,
    defaultValue: T? = null
): T? {
    return when (T::class) {
        String::class -> getString(key.key, defaultValue as? String) as T?
        Int::class -> getInt(key.key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key.key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key.key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key.key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}