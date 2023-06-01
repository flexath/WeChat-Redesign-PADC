package com.flexath.moments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.R
import com.flexath.moments.activities.NewContactActivity
import com.flexath.moments.activities.NewGroupActivity
import com.flexath.moments.adapters.GroupAdapter
import com.flexath.moments.databinding.FragmentContactsBinding
import com.flexath.moments.delegates.GroupItemActionDelegate

class ContactsFragment : Fragment(),GroupItemActionDelegate {

    private lateinit var binding:FragmentContactsBinding
    private lateinit var mAdapter:GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpListeners()
    }

    private fun setUpRecyclerView () {
        mAdapter = GroupAdapter(this)
        binding.rvGroupContacts.adapter = mAdapter
        binding.rvGroupContacts.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpListeners() {
        binding.btnAddNewContact.setOnClickListener {
            startActivity(NewContactActivity.newIntent(requireActivity()))
        }
    }

    override fun onTapGroupItem() {
        startActivity(NewGroupActivity.newIntent(requireActivity()))
    }
}