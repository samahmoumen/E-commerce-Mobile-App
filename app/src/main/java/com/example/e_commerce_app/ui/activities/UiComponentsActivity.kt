package com.example.e_commerce_app.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_commerce_app.R
import kotlinx.android.synthetic.main.progress_bar.*

//This activity is for gathering common features that app uses in different activities.
//For now I created a few lines just for progressBar.

open class UiComponentsActivity : AppCompatActivity() {

    private lateinit var myProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_components)
    }


    fun showProgressBar(text: String) {
        myProgressDialog = Dialog(this)
        myProgressDialog.setContentView(R.layout.dialog_progress)
        myProgressDialog.tv_progress_text.text = text
        myProgressDialog.setCancelable(false)
        myProgressDialog.setCanceledOnTouchOutside(false)
        myProgressDialog.show()
    }

    fun hideProgressBar() {
        myProgressDialog.dismiss()
    }






}