package com.mburakcakir.taketicket.ui.entry.register

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    fun init() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            val userModel = UserModel(
                binding.edtName.text.toString(),
                binding.edtUsername.text.toString(),
                binding.edtMail.text.toString(),
                binding.edtPassword.text.toString()
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

    }

    private fun dataChanged() {
        registerViewModel.loginDataChanged(
            binding.edtUsername.text.toString(),
            binding.edtPassword.text.toString()
        )
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