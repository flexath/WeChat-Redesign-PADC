package com.flexath.moments.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flexath.moments.R
import com.flexath.moments.activities.LoginActivity
import com.flexath.moments.databinding.FragmentSettingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingFragment : Fragment() {

    private lateinit var binding:FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogOut.setOnClickListener {
            val dialog =
                MaterialAlertDialogBuilder(requireActivity(), R.style.RoundedAlertDialog)
                    .setTitle("Log Out ?")
                    .setMessage("Are you sure to log out ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { logoutDialog, _ ->
                        logoutDialog?.dismiss()
                        startActivity(LoginActivity.newIntent(requireContext()))
                        activity?.finish()
                    }
                    .setNegativeButton("Cancel") { logoutDialog, _ ->
                        logoutDialog?.dismiss()
                    }
                    .create()
            dialog.show()
        }
    }
}