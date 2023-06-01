package com.flexath.moments.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.flexath.moments.R
import com.flexath.moments.databinding.DialogEditProfileBinding
import com.flexath.moments.databinding.FragmentProfileBinding
import com.flexath.moments.dialogs.EditProfileDialog
import java.util.Calendar

class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnEditProfile.setOnClickListener {
            val dialogBinding = DialogEditProfileBinding.inflate(layoutInflater)
            val dialog = EditProfileDialog(requireActivity())

            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(true)

            val adapter = ArrayAdapter(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getYearsForYearSpinner())
            dialogBinding.yearSpinnerEditProfile.adapter = adapter

            dialog.show()
        }
    }

    private fun getYearsForYearSpinner() : ArrayList<String> {
        val years = arrayListOf("Year")
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (year in 1900..thisYear) {
            years.add(year.toString())
        }
        return years
    }
}