package com.mburakcakir.taketicket.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.FragmentProfileBinding
import com.mburakcakir.taketicket.ui.MainActivity
import com.mburakcakir.taketicket.util.navigate
import com.mburakcakir.taketicket.util.toast


class ProfileFragment : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.viewmodel = profileViewModel
        profileViewModel.sessionManager.getImageUri()?.let {
            Glide.with(this).load(Uri.parse(profileViewModel.sessionManager.getImageUri()))
                .into(binding.imgProfilePicture)
        }

        binding.viewTickets.setOnClickListener {
            this.navigate(ProfileFragmentDirections.actionProfileFragmentToTicketFragment())
        }

        binding.viewInfo.setOnClickListener {
            this.navigate(ProfileFragmentDirections.actionProfileFragmentToİnfoFragment())
        }

        binding.viewExit.setOnClickListener {
            endSession()
        }
    }

    private fun endSession() {
        this.navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        requireContext() toast getString(R.string.login_again)
        profileViewModel.endSession()
        (requireActivity() as MainActivity).changeToolbarVisibility(View.GONE)
    }

}