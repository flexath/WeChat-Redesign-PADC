package com.flexath.moments.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.flexath.moments.R
import com.flexath.moments.adapters.GifAdapter
import com.flexath.moments.data.models.GiphyModel
import com.flexath.moments.data.models.GiphyModelImpl
import com.flexath.moments.data.vos.giphy.Data
import com.flexath.moments.databinding.ActivitySearchGifsBinding
import com.flexath.moments.delegates.GifItemActionDelegate
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchGifsActivity : AppCompatActivity(),GifItemActionDelegate {

    private lateinit var binding: ActivitySearchGifsBinding

    // Model
    private var mGiphyModel: GiphyModel = GiphyModelImpl

    // General
    private lateinit var mAdapter: GifAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchGifsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchGifsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        setUpListeners()
    }

    @SuppressLint("CheckResult")
    private fun setUpListeners() {
        binding.etSearchGif.textChanges()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .flatMap {
                mGiphyModel.searchGifs(it.toString())
            }
            .map {data ->
                if(data.isEmpty()) {
                    mGiphyModel.getAllTrendingGifs(
                        onSuccess = {dataList ->
                            mAdapter.setNewData(dataList)
                        },
                        onFailure = {error ->
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                data
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mAdapter.setNewData(it)
            }, {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            })
    }

    private fun setUpRecyclerView() {
        mAdapter = GifAdapter(this)
        binding.rvGif.adapter = mAdapter
        binding.rvGif.layoutManager = GridLayoutManager(this, 2)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.scroll_down)
    }

    override fun onTapGifImage(url: String) {
        val intent = Intent()
        intent.putExtra("data", url)
        val resultCode = RESULT_OK
        setResult(resultCode, intent)
        finish()
        overridePendingTransition(0, R.anim.scroll_down)
    }
}