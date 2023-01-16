package com.app.kotlinapp_profile_07

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.Menu
import android.view.MenuItem
import com.app.kotlinapp_profile_07.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lat: Double = 0.0
    private var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUI()
        binding.tvLocation.setOnClickListener {
            binding.tvLocation.text = "Lat: $lat, Long: $long"
        }
    }

    private fun updateUI(name: String = "Jorge Ruiz Cabrera",
                         email: String = "jorgercdeveloper@gmail.com",
                         website: String = "https://mnf.red/jorgeruizcabrera/timeline",
                         phone: String = "617909035") {
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