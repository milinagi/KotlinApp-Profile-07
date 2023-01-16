package com.app.kotlinapp_profile_07

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.app.kotlinapp_profile_07.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            intent.extras?.let {

                etName.setText(it.getString(getString(R.string.key_name)))
                etEmail.setText(it.getString(getString(R.string.key_email)))
                etWebsite.setText(it.getString(getString(R.string.key_website)))
                etPhone.setText(it.getString(getString(R.string.key_phone)))
                etLat.setText(it.getDouble(getString(R.string.key_latitude)).toString())
                etLong.setText(it.getDouble(getString(R.string.key_longitude)).toString())
            }

            etName.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) { etName.text?.let { etName.setSelection(it.length) } }
            }
            etEmail.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) { etEmail.text?.let { etEmail.setSelection(it.length) } }
            }
            etWebsite.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) { etWebsite.text?.let { etWebsite.setSelection(it.length) } }
            }
            etPhone.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) { etPhone.text?.let { etPhone.setSelection(it.length) } }
            }
            etLat.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) { etLat.text?.let { etLat.setSelection(it.length) } }
            }
            etLong.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) { etLong.text?.let { etLong.setSelection(it.length) } }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_save -> sendData()
        }
//        if (item.itemId == R.id.action_save) {
//            sendData()
//        } else if (item.itemId == android.R.id.home) {
//            onBackPressed()
//        }
        return super.onOptionsItemSelected(item)
    }

    fun sendData() {
        val intent = Intent()

        with(binding) {

            intent.apply {
                putExtra(getString(R.string.key_name), etName.text.toString())
                putExtra(getString(R.string.key_email), etEmail.text.toString())
                putExtra(getString(R.string.key_website), etWebsite.text.toString())
                putExtra(getString(R.string.key_phone), etPhone.text.toString())
                putExtra(getString(R.string.key_latitude), etLat.text.toString().toDouble())
                putExtra(getString(R.string.key_longitude), etLong.text.toString().toDouble())
            }
        }

//        intent.putExtra(getString(R.string.key_name), binding.etName.text.toString())


        setResult(RESULT_OK, intent)
        finish()
    }
}