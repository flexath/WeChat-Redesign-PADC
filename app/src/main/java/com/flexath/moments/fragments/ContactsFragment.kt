package com.flexath.moments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.R
import com.flexath.moments.activities.NewContactActivity
import com.flexath.moments.activities.NewGroupActivity
import com.flexath.moments.adapters.GroupAdapter
import com.flexath.moments.databinding.FragmentContactsBinding
import com.flexath.moments.delegates.GroupItemActionDelegate
import com.flexath.moments.mvp.impls.ContactsPresenterImpl
import com.flexath.moments.mvp.interfaces.ContactsPresenter
import com.flexath.moments.mvp.views.ContactsView

class ContactsFragment : Fragment(),ContactsView {

    private lateinit var binding:FragmentContactsBinding

    // Adapters
    private lateinit var mAdapter:GroupAdapter

    // Presenters
    private lateinit var mPresenter:ContactsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()

        setUpRecyclerView()
        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ContactsPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpRecyclerView () {
        mAdapter = GroupAdapter(mPresenter)
        binding.rvGroupContacts.adapter = mAdapter
        binding.rvGroupContacts.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpListeners() {
        binding.btnAddNewContact.setOnClickListener {
            mPresenter.onTapAddNewContactButton()
        }
    }

    override fun navigateToNewGroupScreen() {
        startActivity(NewGroupActivity.newIntent(requireActivity()))
    }

    override fun navigateToNewContactScreen() {
        startActivity(NewContactActivity.newIntent(requireActivity()))
    }

    override fun showError(error: String) {

    }
}