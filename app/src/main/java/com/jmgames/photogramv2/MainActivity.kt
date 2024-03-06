package com.jmgames.photogramv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jmgames.photogramv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    var username: String? = null

    // Declarar una instancia única de DataStore
    lateinit var dataStore: DataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Inicializar la instancia única de DataStore
        dataStore = DataStore(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcwMain) as NavHostFragment
        val navController = navHostFragment.navController
        // Establece la barra con controles
        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)

        // Ocultar el BottomNavigationView cuando se muestre el LoginFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.informacionFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fcwMain)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
