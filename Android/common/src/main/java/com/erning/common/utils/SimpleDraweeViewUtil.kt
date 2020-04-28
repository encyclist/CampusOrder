package com.erning.common.utils

import android.net.Uri
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

fun SimpleDraweeView.setImageSupportGit(url: String){
    setImageURI(url)
    val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
        .setResizeOptions(ResizeOptions(200, 200))
        .setLocalThumbnailPreviewsEnabled(true)
        .build()
    val draweeController = Fresco.newDraweeControllerBuilder()
        .setOldController(controller)
        .setImageRequest(imageRequest)
        .setTapToRetryEnabled(true)
        .setAutoPlayAnimations(true)
        .build()
    controller = draweeController
}