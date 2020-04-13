package com.j7arsen.companycatalog.ui.list

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.j7arsen.companycatalog.R
import com.j7arsen.companycatalog.base.BaseActivity
import com.j7arsen.companycatalog.base.BaseFragment
import com.j7arsen.companycatalog.di.ComponentManager
import com.j7arsen.companycatalog.di.app.qualifier.Global
import com.j7arsen.companycatalog.utils.IBackButtonListener
import com.j7arsen.companycatalog.utils.ResourceProvider
import com.j7arsen.companycatalog.utils.Screens
import com.j7arsen.companycatalog.utils.error.ErrorData
import com.j7arsen.companycatalog.view.ProgressView
import com.j7arsen.domain.model.CompanyModel
import kotlinx.android.synthetic.main.fragment_company_list.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class CompanyListFragment : BaseFragment(), CompanyListAdapter.OnItemClickListener, IBackButtonListener {

    @Inject
    @Global
    lateinit var router: Router

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val viewModel by lazy { ViewModelProvider(this).get(CompanyListViewModel::class.java) }

    private val companyListAdapter = CompanyListAdapter()

    companion object {

        fun newInstance() = CompanyListFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ComponentManager.getCompanyComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initAdapter()
        initListener()
        initObserver()
    }

    private fun initToolbar() {
        val baseActivity = activity as BaseActivity
        val currentToolbar = toolbar as Toolbar
        currentToolbar.title = ""
        baseActivity.setSupportActionBar(currentToolbar)
        if (baseActivity.supportActionBar != null) {
            baseActivity.supportActionBar!!.setDisplayShowTitleEnabled(true)
            baseActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            baseActivity.supportActionBar!!.setDisplayShowHomeEnabled(false)
        }
        currentToolbar.title = resourceProvider.getString(R.string.company_list_screen_title)
    }

    private fun initAdapter(){
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_company_list.layoutManager = LinearLayoutManager(activity)
        } else {
            rv_company_list.layoutManager = GridLayoutManager(activity, 3)
        }
        rv_company_list.itemAnimator = DefaultItemAnimator()
        rv_company_list.adapter = companyListAdapter
    }

    private fun initListener() {
        companyListAdapter.setOnItemClickListener(this)
        pv_company_list.setOnRetryListener(object : ProgressView.OnRetryListener {
            override fun onRetry() {
                viewModel.loadCompanyList()
            }
        })
    }

    override fun onItemClick(companyModel: CompanyModel) {
        router.navigateTo(Screens.CompanyDetailScreen(companyModel.id))
    }

    private fun initObserver(){
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is CompanyListViewModel.CompanyListViewModelState.ShowLoading -> showLoading()
                is CompanyListViewModel.CompanyListViewModelState.CompleteLoading -> completeLoading()
                is CompanyListViewModel.CompanyListViewModelState.ErrorLoading -> errorLoading(it.errorData)
                is CompanyListViewModel.CompanyListViewModelState.ShowEmptyList -> showEmptyList()
                is CompanyListViewModel.CompanyListViewModelState.HideEmptyList -> hideEmptyList()
            }
        })
        viewModel.companyModelList.observe(viewLifecycleOwner, Observer {
            companyListAdapter.setData(it)
        })
    }

    private fun showLoading() {
        cl_company_list_container.visibility = View.GONE
        pv_company_list.startLoading()
    }

    private fun completeLoading() {
        cl_company_list_container.visibility = View.VISIBLE
        pv_company_list.completeLoading()
    }

    private fun errorLoading(errorData: ErrorData) {
        cl_company_list_container.visibility = View.GONE
        pv_company_list.errorLoading(errorMessage = errorData.errorMessage)
    }

    private fun showEmptyList(){
        rv_company_list.visibility = View.GONE
        sv_company_list.visibility = View.VISIBLE
    }

    private fun hideEmptyList(){
        sv_company_list.visibility = View.GONE
        rv_company_list.visibility = View.VISIBLE
    }

    override val layoutId: Int
        get() = R.layout.fragment_company_list

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}