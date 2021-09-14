package com.wixsite.mupbam1.resume.do12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.RootElement
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.wixsite.mupbam1.resume.do12.databinding.ActivityMainBinding
import com.wixsite.mupbam1.resume.do12.dialogHelper.DialogHelper
import com.wixsite.mupbam1.resume.do12.dialogHelper.dialogConst


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvAccount:TextView
    private lateinit var rootElement:ActivityMainBinding
    private val dialogHelper=DialogHelper(this)
    val mAuth=FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        rootElement= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(
            this, rootElement.drawerLayout, rootElement.mainContent.toolbar, R.string.open, R.string.close
        )
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener(this)
        tvAccount=rootElement.navView.getHeaderView(0).findViewById(R.id.tvAccount)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_myAds -> {
                Toast.makeText(this, "Presed id_myAds", Toast.LENGTH_LONG).show()
            }
            R.id.id_cars -> {
                Toast.makeText(this, "Presed id_cars", Toast.LENGTH_LONG).show()
            }
            R.id.id_pc -> {
                Toast.makeText(this, "Presed id_pc", Toast.LENGTH_LONG).show()
            }
            R.id.id_smart -> {
                Toast.makeText(this, "Presed id_smart", Toast.LENGTH_LONG).show()
            }
            R.id.id_dm -> {
                Toast.makeText(this, "Presed id_dm", Toast.LENGTH_LONG).show()
            }
            R.id.id_signUp -> {
                dialogHelper.createSignDialog(dialogConst.SIGN_UP_STATE)
            }
            R.id.id_signIn -> {
                dialogHelper.createSignDialog(dialogConst.SIGN_IN_STATE)
            }
            R.id.id_signOut -> {
                uiUpdate(null)
                mAuth.signOut()
            }
        }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    fun uiUpdate(user:FirebaseUser?){
        tvAccount.text=if (user==null){
            resources.getString(R.string.not_reg)
        }else{
            user.email
        }
    }
}