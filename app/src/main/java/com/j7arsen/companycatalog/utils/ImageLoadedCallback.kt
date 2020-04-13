package com.j7arsen.companycatalog.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.j7arsen.companycatalog.R
import com.squareup.picasso.Callback

class ImageLoadedCallback(private var imageView: ImageView, private var progressBar: ProgressBar) :
    Callback {

    override fun onSuccess() {
        progressBar.visibility = View.GONE
        imageView.visibility = View.VISIBLE
    }

    override fun onError(e: Exception?) {
        progressBar.visibility = View.GONE
        imageView.setImageResource(android.R.drawable.ic_dialog_info)
        imageView.visibility = View.VISIBLE
    }
}