package com.etc.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.etc.databinding.DialogToSettingForPwdBinding
import com.etc.ext.setOnClickListener
import com.etc.ext.showToast

/**
 * @Description: 密码弹窗
 * @author Xia燚
 * @date 2022/11/11 16:05
 */
class PasswordDialogOne(context: Context?) : AlertDialog(context) {

    lateinit var passwordDialogConfirmListener: PasswordDialogConfirmListener
    private lateinit var binding: DialogToSettingForPwdBinding
    private lateinit var edit: EditText

    interface PasswordDialogConfirmListener {
        fun onConfirmListener(pwd: CharSequence?)
    }

    fun setPasswordDialogConfirmListener(passwordDialogConfirmListener: PasswordDialogConfirmListener?): PasswordDialogOne? {
        if (passwordDialogConfirmListener != null) {
            this.passwordDialogConfirmListener = passwordDialogConfirmListener
        }
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogToSettingForPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        val screenWidth = window!!.windowManager.defaultDisplay.width
        val screenHeight = window!!.windowManager.defaultDisplay.height
        window!!.setLayout((screenWidth * 0.7).toInt(), (screenHeight * 0.55).toInt())
        window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
        initView()

    }


    private fun initView() {
        edit = binding.dialogPwdEdit
        setOnClickListener(
            binding.num0, binding.num1, binding.num2, binding.num3,
            binding.num4, binding.num5, binding.num6,
            binding.num7, binding.num8, binding.num9,
            binding.numDel, binding.numUpdate, binding.dialogOk, binding.dialogCancel
        ) {
            when (this) {
                binding.num0 -> {
                    editTextAppend("0")
                }
                binding.num1 -> {
                    editTextAppend("1")
                }
                binding.num2 -> {
                    editTextAppend("2")
                }
                binding.num3 -> {
                    editTextAppend("3")
                }
                binding.num4 -> {
                    editTextAppend("4")
                }
                binding.num5 -> {
                    editTextAppend("5")
                }
                binding.num6 -> {
                    editTextAppend("6")
                }
                binding.num7 -> {
                    editTextAppend("7")
                }
                binding.num8 -> {
                    editTextAppend("8")
                }
                binding.num9 -> {
                    editTextAppend("9")
                }
                binding.numDel -> {
                    if (edit.text.isNotEmpty()) {
                        val index = edit.selectionStart
                        val editable = edit.editableText
                        editable.delete(index - 1, index)
                    }
                }
                binding.dialogOk -> {
                    if (passwordDialogConfirmListener != null) {
                        if (TextUtils.isEmpty(edit.text)) {
                            context.showToast("密码不能为空")

                        } else {
                            passwordDialogConfirmListener.onConfirmListener(
                                edit.text.toString()
                            )
                        }
                        dismiss()
                    }
                }
                binding.dialogCancel -> {
                    if (passwordDialogConfirmListener != null) {
                        passwordDialogConfirmListener.onConfirmListener("")
                    }
                    dismiss()
                }

            }
        }
    }

    private fun editTextAppend(text: String) {
        edit.append(text)
    }

}