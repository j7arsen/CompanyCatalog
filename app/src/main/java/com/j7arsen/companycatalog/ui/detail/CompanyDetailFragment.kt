package com.j7arsen.companycatalog.ui.detail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.j7arsen.companycatalog.R
import com.j7arsen.companycatalog.base.BaseActivity
import com.j7arsen.companycatalog.base.BaseFragment
import com.j7arsen.companycatalog.di.ComponentManager
import com.j7arsen.companycatalog.di.app.qualifier.Global
import com.j7arsen.companycatalog.ui.list.CompanyListAdapter
import com.j7arsen.companycatalog.utils.*
import com.j7arsen.companycatalog.utils.error.ErrorData
import com.j7arsen.companycatalog.view.ProgressView
import com.j7arsen.data.api.BASE_URL
import com.j7arsen.domain.model.CompanyDetailModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_company_detail.*
import kotlinx.android.synthetic.main.fragment_company_list.toolbar
import kotlinx.android.synthetic.main.include_progress_layout.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class CompanyDetailFragment : BaseFragment(), IBackButtonListener {

    @Inject
    @Global
    lateinit var router: Router

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            CompanyDetailViewModelFactory(arguments?.let { it.getString(COMPANY_ID) })
        ).get(CompanyDetailViewModel::class.java)
    }

    private val companyListAdapter = CompanyListAdapter()

    companion object {

        const val COMPANY_ID = "DetailCompanyFragment.COMPANY_ID"

        fun newInstance(companyId: String) = CompanyDetailFragment().apply {
            arguments = Bundle().apply {
                putString(COMPANY_ID, companyId)
            }
        }

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
            baseActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            baseActivity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        currentToolbar.title =
            resourceProvider.getString(R.string.company_detail_screen_title)
        currentToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initListener() {
        pv_company_detail.setOnRetryListener(object : ProgressView.OnRetryListener {
            override fun onRetry() {
                viewModel.loadCompanyDetail()
            }
        })
    }

    fun initObserver() {
        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is CompanyDetailViewModel.DetailCompanyViewModelState.ShowLoading -> showLoading()
                is CompanyDetailViewModel.DetailCompanyViewModelState.CompleteLoading -> completeLoading()
                is CompanyDetailViewModel.DetailCompanyViewModelState.ErrorLoading -> errorLoading(
                    it.errorData
                )
            }
        })
        viewModel.companyDetail.observe(viewLifecycleOwner, Observer {
            initCompanyDetail(it)
        })
    }

    private fun initCompanyDetail(companyDetailModel: CompanyDetailModel) {
        tv_company_detail_title.text = companyDetailModel.name
        Picasso.get()
            .load(BASE_URL + companyDetailModel.img)
            .into(
                iv_company_detail_img,
                ImageLoadedCallback(
                    iv_company_detail_img,
                    pb_company_detail_img
                )
            )
        tv_company_detail_description.text = companyDetailModel.description
        tv_company_detail_description.movementMethod = ScrollingMovementMethod()
        showProp(companyDetailModel?.www, tv_company_detail_link, ll_company_detail_link)
        showProp(companyDetailModel.phone, tv_company_detail_phone, ll_company_detail_phone)

        if(companyDetailModel.lat == null || companyDetailModel.lon == null){
            btn_progress_error_retry.visibility = View.GONE
            btn_progress_error_retry.setOnClickListener(null)
        } else{
            btn_progress_error_retry.visibility = View.VISIBLE
            btn_progress_error_retry.setOnClickListener {
             router.navigateTo(Screens.GeoScreen(companyDetailModel.lat!!, companyDetailModel.lon!!))
            }
        }
    }

    private fun showProp(
        text: String?,
        showTextView: AppCompatTextView,
        hideContainer: LinearLayout
    ) {
        if (!text.isNullOrEmpty())
            showTextView.text = text
        else
            hideContainer.visibility = View.GONE
    }

    private fun showLoading() {
        cl_company_detail_container.visibility = View.GONE
        pv_company_detail.startLoading()
    }

    private fun completeLoading() {
        cl_company_detail_container.visibility = View.VISIBLE
        pv_company_detail.completeLoading()
    }

    private fun errorLoading(errorData: ErrorData) {
        cl_company_detail_container.visibility = View.GONE
        pv_company_detail.errorLoading(errorMessage = errorData.errorMessage)
    }

    override val layoutId: Int
        get() = R.layout.fragment_company_detail

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}