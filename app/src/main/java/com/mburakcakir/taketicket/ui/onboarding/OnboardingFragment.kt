package com.mburakcakir.taketicket.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mburakcakir.taketicket.databinding.FragmentOnboardingBinding
import com.mburakcakir.taketicket.util.SessionManager
import com.mburakcakir.taketicket.util.navigate

class OnboardingFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var onboardingViewModel: OnboardingViewModel
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
        onboardingViewModel = ViewModelProvider(this).get(OnboardingViewModel::class.java)
        sessionManager = SessionManager(requireContext())

        this.navigate(
            sessionManager.ifUserLoggedIn().run {
                if (this)
                    OnboardingFragmentDirections.actionOnboardingFragmentToEventFragment()
                else
                    OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
            })
    }
}

