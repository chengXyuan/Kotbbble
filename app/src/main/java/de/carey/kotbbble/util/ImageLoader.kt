package de.carey.kotbbble.util

import android.net.Uri
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import de.carey.kotbbble.R
import de.carey.kotbbble.application.App


object ImageLoader {

    fun loadCircle(view: SimpleDraweeView, url: String) = view.setImageURI(url)

    fun loadImage(view: SimpleDraweeView, urlNormal: String, urlLow: String?, autoPlay: Boolean = false) {
        view.hierarchy = GenericDraweeHierarchyBuilder(App.instance.resources)
                .setPlaceholderImage(R.drawable.img_place_holder)
                .setFailureImage(R.drawable.img_load_failed)
                .setRetryImage(R.drawable.img_load_failed)
                .build()

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlNormal))
                .setProgressiveRenderingEnabled(true)
                .build()

        view.controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(autoPlay)
                .setUri(Uri.parse(urlNormal))
                .setLowResImageRequest(ImageRequest.fromUri(urlLow))
                .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(view.controller)
                .build()
    }
}