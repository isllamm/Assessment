package com.technical.assessment.featuremain.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.technical.assessment.R
import com.technical.assessment.core.LoadingDialog
import com.technical.assessment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
        setupNavController()
    }
    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController
    }
    fun showLoading() {
        loadingDialog.showLoading()
    }

    fun hideLoading(){
        loadingDialog.hideLoading()
    }
}