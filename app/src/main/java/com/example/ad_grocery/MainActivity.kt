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
import com.example.ad_grocery.objects.Recipe
import com.example.ad_grocery.objects.User
import com.example.ad_grocery.ui.productList.ProductListFragment
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
            arrayListOf(
                Produce("1", 100, Date(System.currentTimeMillis() + 86400000L * 100), 2.5f, 3, 1, 0.1f, "apa1"),
                Produce("6", 203, Date(System.currentTimeMillis() + 86400000L * 40), 20.0f, 5, 2, 0.15f, "baton3"),
                Produce("9", 401, Date(System.currentTimeMillis() + 86400000L * 20), 20.0f, 1, 4, 0.2f, "lapte1"),
                Produce("8", 303, Date(System.currentTimeMillis() + 86400000L * 18), 8.5f, 6, 3, 0.1f, "caiet3")

            ),
            25.8f
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.nav_product_list)
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

//        Initialize Recipe
        var recipe = Recipe(
            ingredients = listOf(
                ProductDB.getProducts("Littles")?.get(1),
                ProductDB.getProducts("Water")?.get(0),
                ProductDB.getProducts("Bread")?.get(0)
            ),
            name = "Littles",
            recipeDiscount = 0.8f
        )
        recipe.calcPrices()
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
                Produce("1", 100, Date(System.currentTimeMillis() + 86400000L * 100), 2.5f, 50, 1, 0.1f, "apa1"),
                Produce("2", 101, Date(System.currentTimeMillis() + 86400000L * 70), 3.0f, 30, 1, 0.05f, "apa2"),
                Produce("3", 102, Date(System.currentTimeMillis() + 86400000L * 150), 4.0f, 20, 1, 0.15f, "apa3")
            )
        )

        ProductDB.addProductType(
            "Chocolate_Bar",
            listOf(
                Produce("4", 201, Date(System.currentTimeMillis() + 86400000L * 20), 10.5f, 100, 2, 0.0f, "baton1"),
                Produce("5", 202, Date(System.currentTimeMillis() + 86400000L * 30), 15.0f, 80, 2, 0.1f, "baton2"),
                Produce("6", 203, Date(System.currentTimeMillis() + 86400000L * 40), 20.0f, 85, 2, 0.15f, "baton3")
            )
        )

        ProductDB.addProductType(
            "Notebook",
            listOf(
                Produce("7", 301, Date(System.currentTimeMillis() + 86400000L * 20), 5.0f, 60, 3, 0.2f, "caiet1"),
                Produce("8", 302, Date(System.currentTimeMillis() + 86400000L * 18), 4.5f, 40, 3, 0.3f, "caiet2"),
                Produce("8", 303, Date(System.currentTimeMillis() + 86400000L * 18), 8.5f, 70, 3, 0.1f, "caiet3")
            )
        )
        ProductDB.addProductType(
            "Milk",
            listOf(
                Produce("9", 401, Date(System.currentTimeMillis() + 86400000L * 20), 20.0f, 60, 4, 0.2f, "lapte1"),
                Produce("10", 402, Date(System.currentTimeMillis() + 86400000L * 18), 15.5f, 40, 4, 0.3f, "lapte2"),
                Produce("11", 403, Date(System.currentTimeMillis() + 86400000L * 18), 18.5f, 70, 4, 0.1f, "lapte3")
            )
        )
        ProductDB.addProductType(
            "Littels",
            listOf(
                Produce("12", 501, Date(System.currentTimeMillis() + 86400000L * 20), 100.0f, 100, 5, 0.3f, "mic1"),
                Produce("13", 502, Date(System.currentTimeMillis() + 86400000L * 18), 85.5f, 80, 5, 0.2f, "mic2"),
                Produce("14", 503, Date(System.currentTimeMillis() + 86400000L * 18), 98.5f, 70, 5, 0.1f, "mic3")
            )
        )

        ProductDB.addProductType(
            "Bread",
            listOf(
                Produce("15", 601, Date(System.currentTimeMillis() + 86400000L * 20), 5.0f, 60, 6, 0.0f, "paine1"),
                Produce("16", 602, Date(System.currentTimeMillis() + 86400000L * 18), 4.5f, 40, 6, 0.3f, "paine2"),
                Produce("17", 603, Date(System.currentTimeMillis() + 86400000L * 18), 8.5f, 70, 6, 0.5f, "paine3")
            )
        )
        ProductDB.addProductType(
            "Pen",
            listOf(
                Produce("18", 701, Date(System.currentTimeMillis() + 86400000L * 20), 5.0f, 60, 7, 0.0f, "pix1"),
                Produce("19", 702, Date(System.currentTimeMillis() + 86400000L * 18), 4.5f, 40, 7, 0.3f, "pix2"),
                Produce("20", 703, Date(System.currentTimeMillis() + 86400000L * 18), 8.5f, 70, 7, 0.5f, "pix3")
            )
        )

        ProductDB.addProductType(
            "tissues",
            listOf(
                Produce("21", 801, Date(System.currentTimeMillis() + 86400000L * 20), 20.0f, 60, 8, 0.0f, "servetele1"),
                Produce("22", 802, Date(System.currentTimeMillis() + 86400000L * 18), 10.5f, 40, 8, 0.3f, "servetele2"),
                Produce("23", 803, Date(System.currentTimeMillis() + 86400000L * 18), 18.5f, 70, 8, 0.5f, "servetele3")
            )
        )
    }

    fun getBudget(): Float {
        return user.currBudget
    }
}
