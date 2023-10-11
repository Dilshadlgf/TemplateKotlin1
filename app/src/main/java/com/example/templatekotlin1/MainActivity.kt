package com.example.templatekotlin1

import android.R.attr.fragment
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.templatekotlin1.base.BaseActivity
import com.example.templatekotlin1.common.db.dao.UserDao
import com.example.templatekotlin1.databinding.ActivityMainBinding
import com.example.templatekotlin1.ui.activity.login.LoginActivity
import com.example.templatekotlin1.ui.gallery.GalleryFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var userDao: UserDao
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onInitialized() {
        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_logout,R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun setUpClicks() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_logout) {
            showDialog("Do you want to logout?",1,true
            ) { _, _ ->
                logout()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }
    private fun logout(){
        userDao.clearTable()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}