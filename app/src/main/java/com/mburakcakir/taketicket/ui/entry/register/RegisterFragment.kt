package com.mburakcakir.taketicket.ui.entry.register

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.databinding.FragmentRegisterBinding
import com.mburakcakir.taketicket.ui.entry.CustomTextWatcher
import com.mburakcakir.taketicket.util.enums.EntryState
import com.mburakcakir.taketicket.util.enums.EntryType
import com.mburakcakir.taketicket.util.navigate
import com.mburakcakir.taketicket.util.toast

class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var filePath: Uri? = null
    private lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun init() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        registerViewModel.setEntryType(EntryType.REGISTER)

        initAlertDialog()

        binding.btnRegister.setOnClickListener {
            if (filePath != null)
                uploadAndSaveFile(binding.edtUsername.text.toString())
            else {
                insertUser(null)
            }
        }

        registerViewModel.stateImageLoading.observe(viewLifecycleOwner) {
            if (it) {
                alertDialog.dismiss()
                insertUser(registerViewModel.imageUri.toString())
            } else
                requireContext() toast "Bir hata oluştu."
        }

        registerViewModel.entryFormState.observe(viewLifecycleOwner, {
            binding.btnRegister.isEnabled = it.isDataValid

            if (!it.passwordError.isNullOrEmpty())
                binding.edtPassword.error = it.passwordError
            if (!it.usernameError.isNullOrEmpty())
                binding.edtUsername.error = it.usernameError
            if (!it.emailError.isNullOrEmpty())
                binding.edtMail.error = it.emailError
        })

        registerViewModel.result.observe(viewLifecycleOwner, {
            it.error?.let { error ->
                requireContext() toast error

            }
            it.success?.let { success ->
                requireContext() toast success
                this.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            }
        })

        binding.edtUsername.afterTextChanged {
            registerViewModel.isDataChanged(
                EntryState.USERNAME,
                binding.edtUsername.text.toString()
            )
        }

        binding.edtPassword.afterTextChanged {
            registerViewModel.isDataChanged(
                EntryState.PASSWORD,
                binding.edtPassword.text.toString()
            )
        }

        binding.edtMail.afterTextChanged {
            registerViewModel.isDataChanged(
                EntryState.EMAIL,
                binding.edtMail.text.toString()
            )
        }

        binding.imgProfilePicture.setOnClickListener {
            selectImage()
        }

    }

    private fun initAlertDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setView(layoutInflater.inflate(R.layout.dialog_image, null))
        dialog.setCancelable(false)

        alertDialog = dialog.create()
    }

    private fun uploadAndSaveFile(username: String) {
        alertDialog.show()
        registerViewModel.uploadFile(username, filePath)
    }

    private fun insertUser(profileImageUri: String?) {
        val userModel = UserModel(
            binding.edtName.text.toString(),
            binding.edtUsername.text.toString(),
            binding.edtMail.text.toString(),
            binding.edtPassword.text.toString(),
            profileImageUri
        )
        registerViewModel.insertUser(userModel)
    }

    private fun selectImage() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "FOTOĞRAF SEÇ"), 1234)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            binding.imgProfilePicture.setImageURI(filePath)
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}