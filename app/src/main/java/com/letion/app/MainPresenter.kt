package com.letion.app

import com.letion.app.pojo.BookBean
import com.weyee.poscore.mvp.BaseModel
import com.weyee.poscore.mvp.BasePresenter
import com.weyee.poscore.mvp.IView
import com.weyee.sdk.api.RxHttpUtils
import com.weyee.sdk.api.observer.ProgressSubscriber
import com.weyee.sdk.api.observer.transformer.Transformer
import com.weyee.sdk.api.observer.transmission.DownloadTransformer
import com.weyee.sdk.api.observer.transmission.UploadTransformer
import com.weyee.sdk.toast.ToastUtils

/**
 * <p>
 * @describe ...
 *
 * @author wuqi
 * @date 2018/12/11 0011
 */
class MainPresenter(val view: MainView) : BasePresenter<BaseModel, IView>() {
    fun getBook() {
        val map: Map<String, Any> = mapOf("page" to 1, "name" to "刘枫")
        RxHttpUtils.createApi(ApiService::class.java).getBook(map).compose(Transformer.switchSchedulers(view.dialog()))
            .subscribe(object : ProgressSubscriber<BookBean>() {

                override fun setTag(): String {
                    return "book"
                }

                /**
                 * 成功回调
                 *
                 * @param t
                 */
                override fun onSuccess(t: BookBean?) {
                    ToastUtils.show(t?.summary)
                }

            })
    }

    fun getBook(b: Boolean) {
        RxHttpUtils.createApi(ApiService::class.java).bookString.compose(Transformer.switchSchedulers(view.dialog()))
            .subscribe(object : ProgressSubscriber<String>() {

                override fun setTag(): String {
                    return "book-string"
                }

                /**
                 * 成功回调
                 *
                 * @param t
                 */
                override fun onSuccess(t: String?) {
                    ToastUtils.show(t)
                }

            })
    }

    fun downloadApk() {
        val url = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk"
        val fileName = "alipay.apk"
        RxHttpUtils.createApi(ApiService::class.java).downloadFile(url)
            .compose(DownloadTransformer.transformerFormParams(
                view.context().getExternalFilesDir(null),
                fileName
            ) { _, _, progress, _, _ ->
                run {
                    view.showProgress(progress)
                }
            })
            .compose(Transformer.switchSchedulers(view.dialog()))
            .subscribe(object : ProgressSubscriber<String>() {
                override fun setTag(): String {
                    return "download-apk"
                }

                /**
                 * 成功回调
                 *
                 * @param t
                 */
                override fun onSuccess(t: String?) {
                    ToastUtils.show(t)
                }

            })
    }

    fun uploadImages(list: List<String>) {
        val url = "http://t.xinhuo.com/index.php/Api/Pic/uploadPic"
        RxHttpUtils.createApi(ApiService::class.java)
            .uploadFiles(url, UploadTransformer.transformerFormParams(null, list))
            .compose(Transformer.switchSchedulers(view.dialog()))
            .subscribe(object : ProgressSubscriber<String>() {
                /**
                 * 成功回调
                 *
                 * @param t
                 */
                override fun onSuccess(t: String?) {
                    ToastUtils.show("上传完毕: $t")
                }

            })
    }

    fun cancelBook() {
        RxHttpUtils.cancel("book")
    }

    fun cancelBook(b: Boolean) {
        RxHttpUtils.cancel("book-string")
    }

    fun cancelApk() {
        RxHttpUtils.cancel("download-apk")
    }
}