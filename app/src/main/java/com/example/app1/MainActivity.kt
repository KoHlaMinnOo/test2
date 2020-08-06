package com.example.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.app1.databinding.ActivityMainBinding
import com.example.app1.screens.order.OrderFragment
import com.example.app1.screens.signup.SignUpFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("Unusable Variable")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment())
                .commit()
            binding.bottomNavigation.selectedItemId = R.id.home
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.order -> {
                    val fragment = OrderFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.sign_up -> {
                    val fragment = SignUpFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false

        }
}