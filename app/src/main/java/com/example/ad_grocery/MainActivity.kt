package com.example.ad_grocery

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.ad_grocery.databinding.ActivityMainBinding
import com.example.ad_grocery.objects.ProductDB
import com.example.ad_grocery.objects.Produce
import com.example.ad_grocery.objects.User
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object {
        var user: User = User(
            1234,
            "Mariusel",
            250f,
            250f,
            7,
            Date(System.currentTimeMillis()),
            HashMap(),
            ArrayList(),
            ArrayList(),
            25.8f
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_grocery, R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Initialize the hardcoded database
        initializeDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initializeDatabase() {
        // Add products to the database
        ProductDB.addProductType(
            "Water",
            listOf(
                Produce("1", 100, Date(System.currentTimeMillis() + 86400000L * 100), 2.5f, 50, 1, 0.1f, "apa1.jpg"),
                Produce("2", 101, Date(System.currentTimeMillis() + 86400000L * 70), 3.0f, 30, 1, 0.05f, "apa2.jpg"),
                Produce("3", 102, Date(System.currentTimeMillis() + 86400000L * 150), 4.0f, 20, 1, 0.15f, "apa3.jpg")
            )
        )

        ProductDB.addProductType(
            "Chocolate_Bar",
            listOf(
                Produce("4", 201, Date(System.currentTimeMillis() + 86400000L * 20), 10.5f, 100, 2, 0.0f, "baton1.jpg"),
                Produce("5", 202, Date(System.currentTimeMillis() + 86400000L * 30), 15.0f, 80, 2, 0.1f, "baton2.jpg"),
                Produce("6", 203, Date(System.currentTimeMillis() + 86400000L * 40), 20.0f, 85, 2, 0.15f, "baton3.jpg")
            )
        )

        ProductDB.addProductType(
            "Notebook",
            listOf(
                Produce("7", 301, Date(System.currentTimeMillis() + 86400000L * 20), 5.0f, 60, 3, 0.2f, "caiet1.jpg"),
                Produce("8", 302, Date(System.currentTimeMillis() + 86400000L * 18), 4.5f, 40, 3, 0.3f, "caiet2.jpg"),
                Produce("8", 303, Date(System.currentTimeMillis() + 86400000L * 18), 8.5f, 70, 3, 0.1f, "caiet3.jpg")
            )
        )
    }

    fun getBudget(): Float {
        return user.currBudget
    }
}
