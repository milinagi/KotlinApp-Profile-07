package com.app.kotlinapp_profile_07

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.app.kotlinapp_profile_07.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityEditBinding

    private var imgUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            intent.extras?.let {

                imgUri = Uri.parse(it.getString(getString(R.string.key_image)))
                updateImage()
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

            btnSelectPhoto.setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/jpeg"
                }
                startActivityForResult(intent, RC_GALLERY)
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
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == RC_GALLERY) {
                imgUri = data?.data

                imgUri?.let {
                    val contentResolver = applicationContext.contentResolver
                    val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    contentResolver.takePersistableUriPermission(it, takeFlags)

                    updateImage()
                }
            }
        }
    }

    private fun updateImage() {
        binding.imgProfile.setImageURI(imgUri)
    }

    private fun sendData() {
        val intent = Intent()

        with(binding) {

            intent.apply {
                putExtra(getString(R.string.key_image), imgUri.toString())
                putExtra(getString(R.string.key_name), etName.text.toString())
                putExtra(getString(R.string.key_email), etEmail.text.toString())
                putExtra(getString(R.string.key_website), etWebsite.text.toString())
                putExtra(getString(R.string.key_phone), etPhone.text.toString())
                putExtra(getString(R.string.key_latitude), etLat.text.toString().toDouble())
                putExtra(getString(R.string.key_longitude), etLong.text.toString().toDouble())
            }
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        private const val RC_GALLERY = 22
    }
}