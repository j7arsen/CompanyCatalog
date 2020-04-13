package com.j7arsen.companycatalog.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.j7arsen.companycatalog.ui.detail.CompanyDetailFragment
import com.j7arsen.companycatalog.ui.list.CompanyListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen


object Screens {

    class CompanyListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return CompanyListFragment.newInstance()
        }
    }

    class CompanyDetailScreen(private val companyId: String) : SupportAppScreen() {
        override fun getFragment(): Fragment? {
            return CompanyDetailFragment.newInstance(companyId)
        }
    }

    class GeoScreen(private val lat: Double, private val lon: Double) : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent? {
            val gmmIntentUri: Uri =
                Uri.parse("geo:${lat},${lon}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            return if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                mapIntent
            } else {
                null
            }
        }
    }

}