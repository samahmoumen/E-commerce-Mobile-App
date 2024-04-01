package com.example.e_commerce_app.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.e_commerce_app.R
import com.example.e_commerce_app.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.e_commerce_app.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            com.example.e_commerce_app.R.id.bottomNavigation
        )
    bottomNavigationView.visibility = android.view.View.VISIBLE
}