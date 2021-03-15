package com.mburakcakir.taketicket.ui.entry.login

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.databinding.FragmentLoginBinding
import com.mburakcakir.taketicket.ui.entry.CustomTextWatcher
import com.mburakcakir.taketicket.util.LoginState
import com.mburakcakir.taketicket.util.extToast
import com.mburakcakir.taketicket.util.navigate

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            this.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            loginViewModel.login(username, password)
        }

        binding.edtUsername.afterTextChanged {
            loginViewModel.isDataChanged(
                LoginState.USERNAME,
                binding.edtPassword.text.toString()
            )
        }

        binding.edtPassword.afterTextChanged {
            loginViewModel.isDataChanged(
                LoginState.PASSWORD,
                binding.edtPassword.text.toString()
            )
        }

        loginViewModel.entryFormState.observe(requireActivity(), {
            binding.btnLogin.isEnabled = it.isDataValid
            if (!it.passwordError.isNullOrEmpty())
                binding.edtPassword.error = it.passwordError
            if (!it.usernameError.isNullOrEmpty())
                binding.edtUsername.error = it.usernameError
        })

        loginViewModel.result.observe(requireActivity(), {
            when {
                !it.success.isNullOrEmpty() -> {
                    this.navigate(LoginFragmentDirections.actionLoginFragmentToEventFragment())
                    it.success
                }
                !it.loading.isNullOrEmpty() -> it.loading
                !it.warning.isNullOrEmpty() -> it.warning
                else -> it.error
            }?.let { message ->
                requireContext() extToast message
            }
        })
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}