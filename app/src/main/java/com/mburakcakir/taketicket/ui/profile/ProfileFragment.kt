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
import com.mburakcakir.taketicket.utils.extToast
import com.mburakcakir.taketicket.utils.navigate

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
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
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        (requireActivity() as MainActivity).changeToolbarVisibility(View.GONE)
//        viewModel.loadImage(viewModel.sessionManager.getUsername()!!,){
//            Glide.with(this).load(it).into(binding.imgProfilePicture)
//        }
        Glide.with(this).load(Uri.parse(viewModel.sessionManager.getImageUri()))
            .into(binding.imgProfilePicture)
        binding.viewExit.setOnClickListener {
            endSession()
        }
    }

    private fun endSession() {
        requireContext() extToast getString(R.string.login_again)
        viewModel.endSession()
        (requireActivity() as MainActivity).changeToolbarVisibility(View.GONE)
        this.navigate(R.id.action_profileFragment_to_loginFragment)
    }

}