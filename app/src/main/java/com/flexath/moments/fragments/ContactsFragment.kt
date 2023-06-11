package com.flexath.moments.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.activities.ChatDetailActivity
import com.flexath.moments.activities.NewContactActivity
import com.flexath.moments.activities.NewGroupActivity
import com.flexath.moments.adapters.GroupAdapter
import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.FragmentContactsBinding
import com.flexath.moments.mvp.impls.ContactsPresenterImpl
import com.flexath.moments.mvp.interfaces.ContactsPresenter
import com.flexath.moments.mvp.views.ContactsView
import com.flexath.moments.utils.GeneralListData
import com.flexath.moments.views.viewpods.ContactsViewPod

class ContactsFragment : Fragment(), ContactsView {

    private lateinit var binding: FragmentContactsBinding

    // Adapters
    private lateinit var mGroupAdapter: GroupAdapter

    // Presenters
    private lateinit var mPresenter: ContactsPresenter

    // ViewPods
    private lateinit var mViewPod: ContactsViewPod

    // General


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
        setUpViewPods()
        setUpGroupRecyclerView()
        setUpListeners()

        mPresenter.onUIReady(this)

        Log.i("OnAth", "onCreate")
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ContactsPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpViewPods() {
        mViewPod = binding.vpContacts.root
        mViewPod.setDelegate(mPresenter, mPresenter)

        mPresenter.getContacts(mPresenter.getUserId())
    }

    private fun setUpGroupRecyclerView() {
        mGroupAdapter = GroupAdapter(mPresenter)
        binding.rvGroupContacts.adapter = mGroupAdapter
        binding.rvGroupContacts.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpListeners() {
        binding.btnAddNewContact.setOnClickListener {
            mPresenter.onTapAddNewContactButton()
        }

        binding.btnAddNewGroup.setOnClickListener {
            startActivity(NewGroupActivity.newIntent(requireActivity()))
        }
    }

    private fun getAlphabetList(nameList: List<String>): List<Char> {
        val nameMapList = nameList.groupBy { it[0] }
        val alphabetList = arrayListOf<Char>()
        for (key in nameMapList.keys) {
            alphabetList.add(key)
        }
        return alphabetList.sorted()
    }

    override fun navigateToNewGroupScreen() {
        startActivity(NewGroupActivity.newIntent(requireActivity()))
    }

    override fun navigateToNewContactScreen() {
        startActivity(NewContactActivity.newIntent(requireActivity()))
    }

    override fun showContacts(contactList: List<UserVO>) {
        val nameList = arrayListOf<String>()
        for (contact in contactList) {
            nameList.add(0, contact.userName)
        }
        mViewPod.setNewData(getAlphabetList(nameList), contactList, false)
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(requireActivity(), userId))
    }

    override fun addUserToGroup(userId: String) {

    }

    override fun getGroupList(groupList: List<GroupVO>) {
        Log.i("GroupNumber1", groupList.size.toString())
        val mGroupList = arrayListOf<GroupVO>()
        for (group in groupList) {
            if (mPresenter.getUserId() in group.userIdList) {
                mGroupList.add(group)
            }
        }
        Log.i("GroupNumber2", mGroupList.size.toString())
        mGroupAdapter.setNewData(mGroupList)
        binding.tvNumberOfGroup.text = mGroupList.size.toString()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onUIReady(this)
        Log.i("OnAth", "onResume")
    }


    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }
}