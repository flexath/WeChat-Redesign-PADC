package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.adapters.NewMemberGroupAdapter
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityNewGroupBinding
import com.flexath.moments.mvp.impls.NewGroupPresenterImpl
import com.flexath.moments.mvp.interfaces.NewGroupPresenter
import com.flexath.moments.mvp.views.NewGroupView
import com.flexath.moments.utils.GeneralListData.getAlphabetList
import com.flexath.moments.views.viewpods.ContactsViewPod

class NewGroupActivity : AppCompatActivity(), NewGroupView {

    private lateinit var binding: ActivityNewGroupBinding

    // Adapters
    private lateinit var mAdapter: NewMemberGroupAdapter

    // Presenters
    private lateinit var mPresenter: NewGroupPresenter

    // ViewPods
    private lateinit var mViewPod: ContactsViewPod

    // General
    private var mMemberList: ArrayList<UserVO> = arrayListOf()
    private var mUserList: List<UserVO> = listOf()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewGroupActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()
        setUpViewPods()
        setUpRecyclerView()

        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewGroupPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {

        binding.btnCreateNewGroup.setOnClickListener {
            val mUserIdList = arrayListOf<String>()
            val groupName = binding.etGroupNameNewGroup.text.toString()
            for (member in mMemberList) {
                mUserIdList.add(member.userId)
            }
            mUserIdList.add(mPresenter.getUserId())
            if (groupName.isNotEmpty()) {
                mPresenter.onTapCreateButton(
                    System.currentTimeMillis(),
                    groupName,
                    mUserIdList.toList()
                )
                finish()
            }
        }

        binding.etSearchContactsNewGroup.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchContacts(it)
                }
                return true
            }
        })
    }

    private fun searchContacts(newText: String) {
        val contactList = arrayListOf<UserVO>()
        Log.i("UserListSize",mUserList.size.toString())
        Log.i("UserListSizeText",newText)
        for (contact in mUserList) {
            if (contact.userName.contains(newText)) {
                contactList.add(contact)
            }
        }
        mViewPod.setNewData(
            getAlphabetList(getNameFirstLetterList(contactList)),
            contactList,
            true
        )
    }

    private fun setUpViewPods() {
        mViewPod = binding.vpGroup.root
        mViewPod.setDelegate(mPresenter, mPresenter)

        mPresenter.getContacts(mPresenter.getUserId())
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMemberGroupAdapter()
        binding.rvMemberNewGroup.adapter = mAdapter
        binding.rvMemberNewGroup.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getAlphabetList(nameList: List<String>): List<Char> {
        val nameMapList = nameList.groupBy { it[0] }
        val alphabetList = arrayListOf<Char>()
        for (key in nameMapList.keys) {
            alphabetList.add(key)
        }
        return alphabetList.sorted()
    }

    override fun showContacts(userList: List<UserVO>) {
        mUserList = userList
        mViewPod.setNewData(getAlphabetList(getNameFirstLetterList(userList)), userList, true)
    }

    private fun getNameFirstLetterList(contactList: List<UserVO>): ArrayList<String> {
        val nameList = arrayListOf<String>()
        for (contact in contactList) {
            nameList.add(0, contact.userName)
        }
        return nameList
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(this, userId, ""))
    }

    override fun addUserToGroup(userId: String, isCheck: Boolean) {

        if (isCheck) {
            for (user in mUserList) {
                if (userId == user.userId) {
                    mMemberList.add(user)
                }
            }
        } else {
            for (user in mUserList) {
                if (userId == user.userId) {
                    mMemberList.remove(user)
                }
            }
        }
        mAdapter.setNewData(mMemberList)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}