package com.mburakcakir.taketicket.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.FragmentOnboardingBinding
import com.mburakcakir.taketicket.utils.SessionManager
import com.mburakcakir.taketicket.utils.navigate

class OnboardingFragment : Fragment() {
    lateinit var sessionManager: SessionManager
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.navigate(
            sessionManager.ifUserLoggedIn().run {
                if (this)
                    R.id.action_onboardingFragment_to_eventFragment
                else
                    R.id.action_onboardingFragment_to_loginFragment
            })
    }
}

