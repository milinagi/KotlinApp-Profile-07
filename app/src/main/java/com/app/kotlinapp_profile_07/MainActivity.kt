package com.app.kotlinapp_profile_07

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.app.kotlinapp_profile_07.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lat: Double = 0.0
    private var long: Double = 0.0

    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()
        setupIntents()

//        binding.tvLocation.setOnClickListener {
//            binding.tvLocation.text = "Lat: $lat, Long: $long"
//        }
    }

    private fun setupIntents() {
        binding.tvName.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, binding.tvName.text)
            }
            launchIntent(intent)
        }

        binding.tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
                putExtra(Intent.EXTRA_SUBJECT, "From kotlin basic course")
                putExtra(Intent.EXTRA_TEXT, "Hi! I'm Android developer.")
            }
            launchIntent(intent)
        }

        binding.tvWebsite.setOnClickListener {
            val webPage = Uri.parse(binding.tvWebsite.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, webPage)

            launchIntent(intent)
        }

        binding.tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                val phone = (it as TextView).text
                data = Uri.parse("tel: $phone")
            }
            launchIntent(intent)
        }

        binding.tvLocation.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=$lat,$long(Tenerife)")
                `package` = "com.google.android.apps.maps"
            }
            launchIntent(intent)
        }

        binding.tvSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            launchIntent(intent)
        }
    }

    private fun launchIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.profile_error_no_resolve), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(name: String = "Jorge Ruiz Cabrera",
                         email: String = "jorgercdeveloper@gmail.com",
                         website: String = "https://mnf.red/jorgeruizcabrera/timeline",
                         phone: String = "617909035") {
        binding.imgProfile.setImageURI(imgUri)
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebsite.text = website
        binding.tvPhone.text = phone
//        lat = 28.2542
//        long = -16.6189
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_id) {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(getString(R.string.key_image), imgUri.toString())
            intent.putExtra(getString(R.string.key_name), binding.tvName.text)
            intent.putExtra(getString(R.string.key_email), binding.tvEmail.text.toString())
            intent.putExtra(getString(R.string.key_website), binding.tvWebsite.text.toString())
            intent.putExtra(getString(R.string.key_phone), binding.tvPhone.text)
            intent.putExtra(getString(R.string.key_latitude), lat)
            intent.putExtra(getString(R.string.key_longitude), long)

//            startActivity(intent) <----- solo lanzmaiento
            startActivityForResult(intent, RC_EDIT) // <----- lanzamiento y espera de respuesta
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == RC_EDIT) {
                imgUri = Uri.parse(data?.getStringExtra(getString(R.string.key_image)))
                val name = data?.getStringExtra(getString(R.string.key_name))
                val email = data?.getStringExtra(getString(R.string.key_email))
                val website = data?.getStringExtra(getString(R.string.key_website))
                val phone = data?.getStringExtra(getString(R.string.key_phone))
                lat = data?.getDoubleExtra(getString(R.string.key_latitude), 0.0) ?: 0.0
                long = data?.getDoubleExtra(getString(R.string.key_longitude), 0.0) ?: 0.0

                updateUI(name!!, email!!, website!!, phone!!)
            }
        }
    }

    companion object {
        private const val RC_EDIT = 13
    }
}