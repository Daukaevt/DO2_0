package com.wixsite.mupbam1.resume.do12.account_helper

import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.wixsite.mupbam1.resume.do12.MainActivity
import com.wixsite.mupbam1.resume.do12.R
import com.wixsite.mupbam1.resume.do12.databinding.ActivityMainBinding

class AccountHelper(act:MainActivity) {
    private val act=act
        fun signUpWithEmail(email:String, password:String){
        if (email.isNotEmpty()&&password.isNotEmpty()){
            act.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                if (task.isSuccessful){
                    sendEmailVerification(task.result?.user!!)
                    act.uiUpdate(task.result?.user)

                }else{
                    Toast.makeText(act,act.resources.getString(R.string.signUpError), Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
    fun signInWithEmail(email:String, password:String){
        if (email.isNotEmpty()&&password.isNotEmpty()){
            act.mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
                if (task.isSuccessful){
                    act.uiUpdate(task.result?.user)

                }else{
                    Toast.makeText(act,act.resources.getString(R.string.signUpError), Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
    private fun sendEmailVerification(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(act,
                    act.resources.getString(R.string.sendVerithicationEmailDone), Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(act,
                    act.resources.getString(R.string.sendVerithicationEmailError),Toast.LENGTH_SHORT).show()

            }
        }

    }
}