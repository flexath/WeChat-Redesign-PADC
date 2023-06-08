package com.flexath.moments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.activities.NewContactActivity
import com.flexath.moments.activities.NewGroupActivity
import com.flexath.moments.adapters.AlphabetAdapter
import com.flexath.moments.adapters.ContactsAlphabetGroupAdapter
import com.flexath.moments.adapters.GroupAdapter
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.FragmentContactsBinding
import com.flexath.moments.mvp.impls.ContactsPresenterImpl
import com.flexath.moments.mvp.interfaces.ContactsPresenter
import com.flexath.moments.mvp.views.ContactsView
import com.flexath.moments.utils.GeneralListData

class ContactsFragment : Fragment(),ContactsView {

    private lateinit var binding:FragmentContactsBinding

    // Adapters
    private lateinit var mGroupAdapter:GroupAdapter
    private lateinit var mAlphabetAdapter: AlphabetAdapter
    private lateinit var mContactsAlphabetGroupAdapter: ContactsAlphabetGroupAdapter

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

        setUpGroupRecyclerView()
        setUpAlphabetAdapter()
        setUpContactsAlphabetGroupAdapter()
        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ContactsPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpGroupRecyclerView () {
        mGroupAdapter = GroupAdapter(mPresenter)
        binding.rvGroupContacts.adapter = mGroupAdapter
        binding.rvGroupContacts.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpAlphabetAdapter() {
        mAlphabetAdapter = AlphabetAdapter(GeneralListData.getAlphabetList())
        binding.rvAlphabet.adapter = mAlphabetAdapter
        binding.rvAlphabet.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setUpContactsAlphabetGroupAdapter() {
        mContactsAlphabetGroupAdapter = ContactsAlphabetGroupAdapter()
        binding.rvContactsAlphabetGroup.adapter = mContactsAlphabetGroupAdapter
        binding.rvContactsAlphabetGroup.layoutManager = LinearLayoutManager(requireActivity())

        mPresenter.getContacts(mPresenter.getUserId())
    }

    private fun setUpListeners() {
        binding.btnAddNewContact.setOnClickListener {
            mPresenter.onTapAddNewContactButton()
        }
    }

    private fun getAlphabetList(nameList:List<String>) : List<Char> {
        val nameMapList = nameList.groupBy { it[0] }
        val alphabetList = arrayListOf<Char>()
        for(key in nameMapList.keys) {
            alphabetList.add(key)
        }
        return alphabetList
    }

    override fun navigateToNewGroupScreen() {
        startActivity(NewGroupActivity.newIntent(requireActivity()))
    }

    override fun navigateToNewContactScreen() {
        startActivity(NewContactActivity.newIntent(requireActivity()))
    }

    override fun showContacts(contactList: List<UserVO>) {
        val nameList = arrayListOf<String>()
        for(contact in contactList) {
            nameList.add(0,contact.userName)
        }

        mContactsAlphabetGroupAdapter.setNewData(getAlphabetList(nameList),contactList)
    }

    override fun showError(error: String) {

    }
}