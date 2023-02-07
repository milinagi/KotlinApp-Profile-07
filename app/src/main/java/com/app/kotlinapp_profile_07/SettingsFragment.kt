package com.app.kotlinapp_profile_07

import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val deleteUserDataPreference = findPreference<Preference>(getString(R.string.preferences_key_delete_data))
        deleteUserDataPreference?.setOnPreferenceClickListener {
            val sharedPreference = context?.let { it1 ->
                PreferenceManager.getDefaultSharedPreferences(
                    it1
                )
            }
            sharedPreference?.edit {
                putString(getString(R.string.key_image), null)
                putString(getString(R.string.key_name), null)
                putString(getString(R.string.key_email), null)
                putString(getString(R.string.key_website), null)
                putString(getString(R.string.key_phone), null)
                putString(getString(R.string.key_latitude), null)
                putString(getString(R.string.key_longitude), null)
                apply()
            }
            true
        }

        val switchPreferenceCompat = findPreference<SwitchPreferenceCompat>(getString(R.string.preferences_key_enable_clicks))
        val listPreference = findPreference<ListPreference>(getString(R.string.preferences_key_ui_img_size))

        val restoreSetting = findPreference<Preference>(getString(R.string.preferences_key_restore_setting))
        restoreSetting?.setOnPreferenceClickListener {
            switchPreferenceCompat?.isChecked = true
            listPreference?.value = getString(R.string.preferences_key_img_size_large)

            true
        }

        val restorePreferenceAndSettings = findPreference<Preference>(getString(R.string.preferences_key_restore_data))
        restorePreferenceAndSettings?.setOnPreferenceClickListener {
            val sharedPreference = context?.let { it1 ->
                PreferenceManager.getDefaultSharedPreferences(
                    it1
                )
            }
            switchPreferenceCompat?.isChecked = true
            listPreference?.value = getString(R.string.preferences_key_img_size_large)

            sharedPreference!!.edit().clear().apply()
            true
        }
    }
}