package com.mburakcakir.taketicket.ui.entry.login

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
import com.mburakcakir.taketicket.databinding.FragmentLoginBinding
import com.mburakcakir.taketicket.utils.extToast
import com.mburakcakir.taketicket.utils.navigate

class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            this.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            loginViewModel.login(username, password)
        }

        binding.edtUsername.afterTextChanged {
            dataChanged()
        }

        binding.edtPassword.afterTextChanged {
            dataChanged()
        }

        loginViewModel.entryFormState.observe(requireActivity(), {
            binding.btnLogin.isEnabled = it.isDataValid
            if (it.passwordError != null)
                binding.edtPassword.error = getString(it.passwordError)
            if (it.usernameError != null)
                binding.edtUsername.error = getString(it.usernameError)
        })

        loginViewModel.result.observe(requireActivity(), {
            if (it.success != null) {
                this.navigate(R.id.action_loginFragment_to_eventFragment)
            }
            if (it.error != null)
                requireContext() extToast it.error
            if (it.warning != null)
                requireContext() extToast it.warning
            if (it.loading != null)
                requireContext() extToast it.loading
        })

    }

    fun dataChanged() {
        loginViewModel.loginDataChanged(
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