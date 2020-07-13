package com.kadmiv.random_user_app.app.ui.page_list

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kadmiv.random_user_app.R
import com.kadmiv.random_user_app.app.ui.page_item.ItemActivity
import com.kadmiv.random_user_app.app.ui.page_item.USER_EXTRAS
import com.kadmiv.random_user_app.base.adapters.recycler_adapter.BaseNormalAdapter
import com.kadmiv.random_user_app.databinding.ActivityListBinding
import com.kadmiv.random_user_app.repo.api.models.user.response.User
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.android.ext.android.get
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class ListActivity : AppCompatActivity(), IPageList {

    var LOG: Logger = LoggerFactory.getLogger(ListActivity::class.java)

    val mPresenter: ListActivityPresenter = get()

    lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null)
            mListState = savedInstanceState.getParcelable(LIST_STATE)

        mPresenter.onAttach(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        binding.presenter = mPresenter
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    val LIST_STATE = "LIST_STATE"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE, mListState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }


    lateinit var adapter: BaseNormalAdapter<User, ListActivityPresenter>
    var mListState: Parcelable? = null
    override fun initRecyclerView(dataList: List<User>) {

        LOG.debug("| task size = ${dataList.size}")

        val recyclerView = recycler

        //Manager
        val lManager = LinearLayoutManager(this)

        if (mListState != null)
            lManager.onRestoreInstanceState(mListState)

        recyclerView.layoutManager = lManager

        //Adapter
        adapter = BaseNormalAdapter(mPresenter, R.layout.item_user)
        adapter.onNewData(dataList)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                mListState = recyclerView.layoutManager!!.onSaveInstanceState()
            }
        })

    }

    override fun showItemDetails(item: User) {
        LOG.debug("")

        //Fixme - Хотел сделать красивую анимацию перехода, но че криов работало - в Flutter-е пропроще
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra(USER_EXTRAS, item.firstName + item.lastName)
        startActivity(intent)
    }

    override fun showErrorView(visible: Boolean) {
        if (visible) {
            connection_error.visibility = View.VISIBLE
        } else {
            connection_error.visibility = View.GONE
        }
    }

    override fun setConnectionErrorDescription() {
        setErrorDescription(resources.getString(R.string.connection_error))
    }

    override fun setErrorDescription(description: String) {
        error_description.text = description
    }

    override fun showLoadingView(visible: Boolean) {
        if (visible) {
            loadingView.visibility = View.VISIBLE
        } else {
            loadingView.visibility = View.GONE
        }
    }

    override fun hasConnection(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo
        val answer = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        LOG.debug("$answer")
        return answer
    }
}