package ar.edu.unicen.seminarioentregable.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import ar.edu.unicen.seminarioentregable.R
import com.bumptech.glide.Glide

object BindingAdapters {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(view)
        }
    }
}