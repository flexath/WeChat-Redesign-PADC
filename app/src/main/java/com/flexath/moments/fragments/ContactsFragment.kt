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
    private var mGroupList: ArrayList<GroupVO> = arrayListOf()
    private var mContactList: List<UserVO> = listOf()


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

        binding.etSearchContacts.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val groupItemCount = mGroupAdapter.itemCount
                val contactItemCount = mViewPod.getItemCount()

                if(groupItemCount == 0 && contactItemCount == 0) {
                    binding.llNotFound.visibility = View.VISIBLE
                    binding.tvSearchName.text = newText

                    binding.tvGroupsLabel.visibility = View.GONE
                    binding.llNewGroup.visibility = View.GONE
                    binding.vpContacts.root.visibility = View.GONE
                }

                if(newText != null) {
                    searchGroups(newText)
                    searchContacts(newText)
                }
                return true
            }

        })
    }

    private fun searchContacts(newText: String) {
        val contactList = arrayListOf<UserVO>()
        for (contact in mContactList) {
            if (contact.userName.contains(newText)) {
                binding.tvGroupsLabel.visibility = View.VISIBLE
                binding.llNewGroup.visibility = View.VISIBLE
                binding.vpContacts.root.visibility = View.VISIBLE
                binding.llNotFound.visibility = View.GONE
                contactList.add(contact)
            }
        }

        mViewPod.setNewData(
            getAlphabetList(getNameFirstLetterList(contactList)),
            contactList,
            false
        )
    }

    private fun searchGroups(newText: String) {
        val groupList = arrayListOf<GroupVO>()
        for (group in mGroupList) {
            if (group.name.contains(newText)) {
                binding.tvGroupsLabel.visibility = View.VISIBLE
                binding.llNewGroup.visibility = View.VISIBLE
                binding.rvGroupContacts.visibility = View.VISIBLE

                binding.llNotFound.visibility = View.GONE
                groupList.add(group)
            }
        }

        binding.tvNumberOfGroup.text = groupList.size.toString()

        mGroupAdapter.setNewData(groupList)
    }

    private fun getAlphabetList(nameList: List<String>): List<Char> {
        val nameMapList = nameList.groupBy { it[0] }
        val alphabetList = arrayListOf<Char>()
        for (key in nameMapList.keys) {
            alphabetList.add(key)
        }
        return alphabetList.sorted()
    }

    override fun navigateToNewContactScreen() {
        startActivity(NewContactActivity.newIntent(requireActivity()))
    }

    override fun showContacts(contactList: List<UserVO>) {
        mContactList = contactList
        mViewPod.setNewData(
            getAlphabetList(getNameFirstLetterList(contactList)),
            contactList,
            false
        )
    }

    private fun getNameFirstLetterList(contactList: List<UserVO>): ArrayList<String> {
        val nameList = arrayListOf<String>()
        for (contact in contactList) {
            nameList.add(0, contact.userName)
        }
        return nameList
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(requireActivity(), userId, ""))
    }

    override fun addUserToGroup(userId: String) {}

    override fun onResume() {
        super.onResume()
        mPresenter.onUIReady(this)
    }

    override fun getGroupList(groupList: List<GroupVO>) {
        val newGroupList: ArrayList<GroupVO> = arrayListOf()
        for (group in groupList) {
            if (mPresenter.getUserId() in group.userIdList) {
                newGroupList.add(group)
            }
        }
        mGroupList = newGroupList
        mGroupAdapter.setNewData(mGroupList)
        binding.tvNumberOfGroup.text = newGroupList.size.toString()
    }

    override fun navigateToChatDetailScreenFromGroupItem(groupId: String) {
        startActivity(ChatDetailActivity.newIntent(requireActivity(), "", groupId))
    }


    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }
}