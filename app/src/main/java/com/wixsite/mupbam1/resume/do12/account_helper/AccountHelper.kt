package com.wixsite.mupbam1.resume.do12.account_helper

import android.util.Log
import android.util.Log.d
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.wixsite.mupbam1.resume.do12.MainActivity
import com.wixsite.mupbam1.resume.do12.R
import com.wixsite.mupbam1.resume.do12.databinding.ActivityMainBinding
import com.wixsite.mupbam1.resume.do12.dialogHelper.GoogleAcConst
import com.wixsite.mupbam1.resume.do12.dialogHelper.dialogConst


class AccountHelper(act:MainActivity) {
    private val act=act
    private lateinit var signInClient: GoogleSignInClient
    fun signUpWithEmail(email:String, password:String){

        if (email.isNotEmpty()&&password.isNotEmpty()){
            act.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                if (task.isSuccessful){
                    sendEmailVerification(task.result?.user!!)
                    act.uiUpdate(task.result?.user)
                }else{
                    Log.d("MyLog","Exception: ${task.exception}")
                    Toast.makeText(act,act.resources.getString(R.string.signUpError), Toast.LENGTH_SHORT).show()
                    //Log.d("MyLog","Exception: ${exception.errorCode}")
                    if (task.exception is FirebaseAuthUserCollisionException){
                        val exception=task.exception as FirebaseAuthUserCollisionException
                        if (exception.errorCode==GoogleAcConst.ERROR_EMAIL_ALREADY_IN_USE){
                            Toast.makeText(act, GoogleAcConst.ERROR_EMAIL_ALREADY_IN_USE, Toast.LENGTH_LONG).show()
                        }
                    }else if (task.exception is FirebaseAuthInvalidCredentialsException){
                        val exception=task.exception as FirebaseAuthInvalidCredentialsException
                        if (exception.errorCode==GoogleAcConst.ERROR_INVALID_EMAIL){
                        Toast.makeText(act, GoogleAcConst.ERROR_INVALID_EMAIL, Toast.LENGTH_LONG).show()
                        }
                    }
                    if (task.exception is FirebaseAuthWeakPasswordException){
                        val exception=task.exception as FirebaseAuthWeakPasswordException
                        Log.d("MyLog","Exception: ${exception.errorCode}")
                        if (exception.errorCode==GoogleAcConst.ERROR_WEAK_PASSWORD){
                            Toast.makeText(act, "Password should be at least 6 characters", Toast.LENGTH_LONG).show()
                        }
                    }
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
                    Log.d("MyLog","Exception: ${task.exception}")
                    //Toast.makeText(act,act.resources.getString(R.string.signUpError), Toast.LENGTH_SHORT).show()
                    if (task.exception is FirebaseAuthInvalidCredentialsException){
                        val exception=task.exception as FirebaseAuthInvalidCredentialsException
                        //Log.d("MyLog","Exception: ${exception.errorCode}")
                        if (exception.errorCode==GoogleAcConst.ERROR_INVALID_EMAIL){
                            Toast.makeText(act, GoogleAcConst.ERROR_INVALID_EMAIL, Toast.LENGTH_LONG).show()
                        }else if (exception.errorCode==GoogleAcConst.ERROR_WRONG_PASSWORD){
                            Toast.makeText(act, GoogleAcConst.ERROR_WRONG_PASSWORD, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private fun getSignInClient():GoogleSignInClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(act.getString(R.string.default_web_client_id)).requestEmail().build()
        return GoogleSignIn.getClient(act,gso)
    }
    fun signInWithGoogle(){
        signInClient=getSignInClient()
        val intent=signInClient.signInIntent
        act.startActivityForResult(intent,GoogleAcConst.GoogleSignIn_Request_Code)
    }
    fun signInFirebaseWithGoogle(token:String){
        val credential = GoogleAuthProvider.getCredential(token,null)
        act.mAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if (task.isSuccessful){
                Toast.makeText(act, "signIn done", Toast.LENGTH_LONG).show()
                act.uiUpdate(task.result?.user)
            }else{
                Log.d("MyLog","Google SignIn Exception: ${task.exception}")
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