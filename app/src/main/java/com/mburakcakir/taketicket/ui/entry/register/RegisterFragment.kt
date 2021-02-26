package com.mburakcakir.taketicket.ui.entry.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.data.db.entity.UserModel
import com.mburakcakir.taketicket.databinding.FragmentRegisterBinding
import com.mburakcakir.taketicket.utils.extToast
import com.mburakcakir.taketicket.utils.navigate

class RegisterFragment : Fragment() {
    lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var filePath: Uri? = null

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

//    fun getImageUri(): String {
//        var value = ""
//        registerViewModel.loadImage("foto"){
//            value = it.toString()
//            Log.v("image", value)
//        }
//        return value
//    }
//
//    fun getUri(): String {
//        val storage = FirebaseStorage.getInstance()
//        val storageReference = storage.reference
//        var value = ""
//        storageReference.child("foto").downloadUrl.addOnSuccessListener {
//            value = it.toString()
//            Log.v("a", it.toString())
//        }.addOnFailureListener {
//            Log.v("a", "ErrorImage")
//        }
//
//    }

    fun uploadAndSaveFile(username: String) {
        registerViewModel.uploadFile(binding.edtUsername.text.toString(), filePath)
        registerViewModel.loadImage(username)
    }

    fun init() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            uploadAndSaveFile(binding.edtUsername.text.toString())
//            val imageUri = getUri()
            val userModel = UserModel(
                binding.edtName.text.toString(),
                binding.edtUsername.text.toString(),
                binding.edtMail.text.toString(),
                binding.edtPassword.text.toString(),
                "imageUri"
            )

            registerViewModel.insertUser(userModel)

        }

        registerViewModel.entryFormState.observe(requireActivity(), {
            binding.btnRegister.isEnabled = it.isDataValid

            if (it.passwordError != null)
                binding.edtPassword.error = getString(it.passwordError)
            if (it.usernameError != null)
                binding.edtUsername.error = getString(it.usernameError)
        })

        registerViewModel.result.observe(requireActivity(), {
            if (it.error != null)
                requireContext() extToast it.error
            if (it.success != null) {
                requireContext() extToast it.success
                this.navigate(R.id.action_registerFragment_to_loginFragment)
            }
        })

        binding.edtUsername.afterTextChanged {
            dataChanged()
        }

        binding.edtPassword.afterTextChanged {
            dataChanged()
        }

        binding.imgProfilePicture.setOnClickListener {
            selectImage()
        }

    }

    private fun dataChanged() {
        registerViewModel.loginDataChanged(
            binding.edtUsername.text.toString(),
            binding.edtPassword.text.toString()
        )
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
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}