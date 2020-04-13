package com.j7arsen.companycatalog.ui.company

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.j7arsen.companycatalog.R
import com.j7arsen.companycatalog.base.BaseContainerActivity
import com.j7arsen.companycatalog.di.ComponentManager
import com.j7arsen.companycatalog.di.app.qualifier.Global
import com.j7arsen.companycatalog.utils.IBackButtonListener
import com.j7arsen.companycatalog.utils.Screens
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


class CompanyActivity : BaseContainerActivity() {

    @Inject
    @Global
    lateinit var router: Router

    @Inject
    @Global
    lateinit var navigatorHolder: NavigatorHolder

    private val viewModel by lazy {ViewModelProvider(this).get(CompanyViewModel::class.java)}

    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.fl_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        ComponentManager.getCompanyComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewModel.openRootScreen.observe(this, Observer { router.newRootChain(Screens.CompanyListScreen()) })
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fl_container)
        if (fragment != null && fragment is IBackButtonListener
            && (fragment as IBackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }

}
