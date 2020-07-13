package com.kadmiv.random_user_app.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kadmiv.random_user_app.repo.api.models.user.response.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, url: String?) {

    var LOG: Logger = LoggerFactory.getLogger("BindingAdapter")
    LOG.debug("glide is work")

    if (url == null) {
        return
    }

    try {

        Glide.with(imageView.context)
            .load(url)
//            .error(R.drawable.full_cake)
            .apply(
                RequestOptions()
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
            )
            .into(imageView)

    } catch (ex: Exception) {
        LOG.error("", ex)
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindAge")
fun bindAge(textView: TextView, model: User?) {
    if (model == null) {
        return
    }
    textView.text = "${model.age} years old"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindFullName")
fun bindFullName(textView: TextView, model: User?) {
    if (model == null) {
        return
    }
    textView.text = "${model.firstName} ${model.lastName} "
}