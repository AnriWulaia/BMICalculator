package com.example.finaluri

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results)



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        val controller = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.resultFragment,
                R.id.tipsFragment,
            )
        )

        setupActionBarWithNavController(controller,appBarConfiguration)
        bottomNavigationView.setupWithNavController(controller)


        val bmi = intent.getStringExtra("BMI")

        val bundle = Bundle()
        val fragment = ResultFragment()
        bundle.putString("BMI", bmi)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,fragment).commit()

    }
}
