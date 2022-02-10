package com.example.getlocation105

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE = 101
    }

    private val button: Button by lazy { findViewById(R.id.btn) }
    private val tvFirst: TextView by lazy { findViewById(R.id.tvFirst) }
    private val tvSecond: TextView by lazy { findViewById(R.id.tvSecond) }

    private val locationsPermissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        button.setOnClickListener{
            checkLocationPermission()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun checkLocationPermission() {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        when {
            ActivityCompat.checkSelfPermission(
                this,
                locationsPermissions[0]
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                locationsPermissions[1]
            ) != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(this, locationsPermissions, REQUEST_CODE)
                return
            }
            ActivityCompat.checkSelfPermission(
                this,
                locationsPermissions[0]
            ) != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(this,
                    arrayOf(locationsPermissions[0]), REQUEST_CODE)
                return
            }
            ActivityCompat.checkSelfPermission(
                this,
                locationsPermissions[1]
            ) != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(this,
                    arrayOf(locationsPermissions[1]), REQUEST_CODE)
                return
            }
        }
        task.addOnSuccessListener {
            if (it != null){
                tvFirst.text = "${it.latitude}\n${it.longitude}"
                tvSecond.text ="😢😢😢😢😁😁😁😁😶‍🌫️\n ${it}"
            }else{
                tvSecond.text ="😢😢😢😢😁😁😁😁😶‍🌫️\n ${it}"
            }
        }


    }
}