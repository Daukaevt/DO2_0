package com.wixsite.mupbam1.resume.do12.dialogHelper

import android.app.AlertDialog
import com.wixsite.mupbam1.resume.do12.MainActivity
import com.wixsite.mupbam1.resume.do12.R
import com.wixsite.mupbam1.resume.do12.account_helper.AccountHelper
import com.wixsite.mupbam1.resume.do12.databinding.SignDialogBinding

class DialogHelper(act:MainActivity) {
    private val act=act
    private val accHelper=AccountHelper(act)

    fun createSignDialog(index:Int){
        val builder = AlertDialog.Builder(act)
        val rootDialogElement=SignDialogBinding.inflate(act.layoutInflater)
        val view=rootDialogElement.root
        builder.setView(view)

        if (index==dialogConst.SIGN_UP_STATE){
            rootDialogElement.tvSignTitle.text=act.getText(R.string.ac_Sign_Up)
            rootDialogElement.btSignUpIn.text=act.getText(R.string.signUpAction)
        }else{
            rootDialogElement.tvSignTitle.text=act.getText(R.string.ac_Sign_In)
            rootDialogElement.btSignUpIn.text=act.getText(R.string.signInAction)
        }
        val dialog=builder.create()
        rootDialogElement.btSignUpIn.setOnClickListener{
            dialog.dismiss()
            if (index==dialogConst.SIGN_UP_STATE){
                accHelper.signUpWithEmail(rootDialogElement.edSignEmail.text.toString(),
                rootDialogElement.edSignPassword.text.toString())

            }else{
                accHelper.signInWithEmail(rootDialogElement.edSignEmail.text.toString(),
                    rootDialogElement.edSignPassword.text.toString())

            }
        }

        dialog.show()
    }
}