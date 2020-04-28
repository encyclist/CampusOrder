package com.erning.common.utils

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Message
import android.view.KeyEvent
import android.webkit.*

@Suppress("unused")
class MyWebViewClient: WebViewClient() {
    companion object {
        private const val TAG = "MyWebViewClient"
    }

    /**
     * 页面(url)开始加载
     * @param view 正在启动回调的WebView。
     * @param url 要加载的网址。
     * @param favicon 此页面的favicon（如果它已存在于数据库中）。
     */
    override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        LogUtils.d(TAG, "在开始加载网页时会回调")
        super.onPageStarted(view, url, favicon)
    }

    /**
     * 加载资源时发生了一个SSL错误，应用必需响应(继续请求或取消请求)
     * 处理决策可能被缓存用于后续的请求，默认行为是取消请求
     *
     * @param view 正在启动回调的WebView。
     * @param handler 一个SslErrorHandler将处理用户响应的。
     * @param error SSL错误对象。
     */
    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        LogUtils.d(TAG, "加载资源时发生了一个SSL错误，应用必需响应(继续请求或取消请求)")
        super.onReceivedSslError(view, handler, error)
        handler.cancel()
    }

    /**
     * 页面(url)完成加载
     * @param view 正在启动回调的WebView。
     * @param url 页面的网址。
     */
    override fun onPageFinished(view: WebView, url: String) {
        LogUtils.d(TAG, "在结束加载网页时会回调$url")
        super.onPageFinished(view, url)
        if (onLoadDoneListener != null)
            onLoadDoneListener?.onLoadDone(url)
    }

    interface OnLoadDoneListener {
        fun onLoadDone(url:String)
    }
    private var onLoadDoneListener: OnLoadDoneListener? = null
    fun setOnLoadDoneListener(onLoadDoneListener: OnLoadDoneListener) {
        this.onLoadDoneListener = onLoadDoneListener
    }

    /**
     * 此方法添加于API24，不处理POST请求，可拦截处理子frame的非http请求
     *
     * 当URL即将加载到当前WebView中时，为应用程序提供控制的机会。
     * 如果未提供WebViewClient，则默认情况下WebView将要求活动管理器为URL选择正确的处理程序。
     * 如果提供了WebViewClient，则返回true会导致当前WebView中止加载URL，而返回false会导致WebView像往常一样继续加载URL。
     *
     * 注意：不要使用请求的URL调用WebView＃loadUrl（String），然后返回true。
     * 这会不必要地取消当前负载并使用相同的URL启动新的负载。
     * 继续加载给定URL的正确方法是简单地返回false，而不调用WebView＃loadUrl（String）。
     *
     * 注意：不会为POST请求调用此方法。
     *
     * 注意：可以针对子帧和非HTTP（S）方案调用此方法; 使用这样的URL调用WebView＃loadUrl（String）将失败。
     * @param view 正在启动回调的WebView。
     * @param request 包含请求详细信息的对象。。
     * @return 如果取消当前加载，则返回true，否则返回false。
     */
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        LogUtils.d(TAG, "当URL即将加载到当前WebView中时，为应用程序提供控制的机会。")
        return super.shouldOverrideUrlLoading(view, request)
    }

    /**
     * 将要加载资源(url)将要加载资源(url)
     * @param view 正在启动回调的WebView。
     * @param url WebView将加载的资源的URL。
     */
    override fun onLoadResource(view: WebView, url: String) {
        LogUtils.d(TAG, "在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次$url")
        super.onLoadResource(view, url)
    }

    /**
     * 这个回调添加于API23，仅用于主框架的导航
     * 通知应用导航到之前页面时，其遗留的WebView内容将不再被绘制。
     * 此回调可用于确定使回收WebView可见的安全点 ，确保不显示陈旧内容。
     * 它最早被调用，可以保证WebView#onDraw不再从以前的导航中抽取任何内容。
     * 当HTTP响应的主体开始加载时，此方法被调用，反映在DOM中，并且将在后续绘制中可见
     * 这个回调发生在文档加载的早期，因此它的资源(css,和图像)可能不可用。
     * 如果需要更细粒度的视图更新，查看 postVisualStateCallback(long, WebView.VisualStateCallback).
     * 请注意这上边的所有条件也支持 postVisualStateCallback(long ,WebView.VisualStateCallback)
     * @param view
     * @param url
     */
    override fun onPageCommitVisible(view: WebView, url: String) {
        LogUtils.d(TAG, "通知应用导航到之前页面时，其遗留的WebView内容将不再被绘制。$url")
        super.onPageCommitVisible(view, url)
    }

    /**
     * 拦截资源请求并返回响应数据，返回null时WebView将继续加载资源。否则，将使用返回响应和数据。
     *
     * 此回调被调用各种URL方案（例如http(s):，data:，file:等），不仅那些在网络上发送的请求方案。
     * 这不是针对javascript：URL，blob：URL或通过file：/// android_asset /或file：/// android_res / URLs访问的资产调用的。
     * 对于重定向，仅调用初始资源URL，而不是任何后续重定向URL。
     *
     * 注意：此方法在UI线程以外的线程上调用，因此客户端在访问私有数据或视图系统时应谨慎。
     *
     * 注意：启用安全浏览功能后，这些网址仍会进行安全浏览检查。
     * 如果不希望这样，请使用WebView＃setSafeBrowsingWhitelist将URL列入白名单，
     * 或使用onSafeBrowsingHit（WebView，WebResourceRequest，int，SafeBrowsingResponse）忽略该警告。
     * @param view 请求资源的WebView。
     * @param request 资源的原始网址。
     * @return 包含响应信息的WebResourceResponse;如果WebView应加载资源本身，则为null。
     */
    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
        LogUtils.d(TAG, "拦截资源请求并返回响应数据，返回null时WebView将继续加载资源。否则，将使用返回响应和数据。" + request.url.toString())
        return super.shouldInterceptRequest(view, request)
    }

    /**
     * 此方法添加于API23
     * 加载资源时出错，通常意味着连接不到服务器
     * 由于所有资源加载错误都会调用此方法，所以此方法应尽量逻辑简单
     * @param view 正在启动回调的WebView。
     * @param request 原始请求。
     * @param error 发生错误的信息。
     */
    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
        LogUtils.d(TAG, "加载资源时出错，通常意味着连接不到服务器")
        super.onReceivedError(view, request, error)
    }

    /**
     * 此方法添加于API23
     * 在加载资源(iframe,image,js,css,ajax...)时收到了 HTTP 错误(状态码>=400)
     * 在此回调中执行的逻辑尽量简单
     *
     * @param view 正在启动回调的WebView。
     * @param request 原始请求。
     * @param errorResponse 发生错误的信息。
     */
    override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {
        LogUtils.d(TAG, "在加载资源(iframe,image,js,css,ajax...)时收到了 HTTP 错误(状态码>=400)")
        super.onReceivedHttpError(view, request, errorResponse)
    }

    /**
     * 是否重新提交表单，默认不重发
     * @param view 正在启动回调的WebView。
     * @param dontResend 如果浏览器不应重新发送，则发送消息
     * @param resend 如果浏览器应重新发送数据，则发送消息
     */
    override fun onFormResubmission(view: WebView, dontResend: Message, resend: Message) {
        LogUtils.d(TAG, "是否重新提交表单，默认不重发")
        super.onFormResubmission(view, dontResend, resend)
    }

    /**
     * 通知应用可以将当前的url存储在数据库中，意味着当前的访问url已经生效并被记录在内核当中。
     * 此方法在网页加载过程中只会被调用一次。
     * 此方法在进行加载url时会更新历史记录
     * @param view 正在启动回调的WebView。
     * @param url 正在访问的网址。
     * @param isReload true如果正在重新加载此URL。
     */
    override fun doUpdateVisitedHistory(view: WebView, url: String, isReload: Boolean) {
        LogUtils.d(TAG, "通知应用可以将当前的url存储在数据库中，意味着当前的访问url已经生效并被记录在内核当中。$url")
        super.doUpdateVisitedHistory(view, url, isReload)
    }

    /**
     * 此方法添加于API21，在UI线程被调用
     * 处理SSL客户端证书请求，必要的话可显示一个UI来提供KEY。
     * 有三种响应方式：proceed()/cancel()/ignore()，默认行为是取消请求
     * 如果调用proceed()或cancel()，Webview 将在内存中保存响应结果且对相同的"host:port"不会再次调用 onReceivedClientCertRequest
     * 多数情况下，可通过KeyChain.choosePrivateKeyAlias启动一个Activity供用户选择合适的私钥
     *
     * @param view 正在启动回调的WebView
     * @param request 一个实例 ClientCertRequest
     */
    override fun onReceivedClientCertRequest(view: WebView, request: ClientCertRequest) {
        LogUtils.d(TAG, "处理SSL客户端证书请求，必要的话可显示一个UI来提供KEY。")
        super.onReceivedClientCertRequest(view, request)
    }

    /**
     * 处理HTTP认证请求，默认行为是取消请求
     */
    override fun onReceivedHttpAuthRequest(view: WebView, handler: HttpAuthHandler, host: String, realm: String) {
        LogUtils.d(TAG, "通知主程序：WebView接收HTTP认证请求，主程序可以使用HttpAuthHandler为请求设置WebView响应。默认取消请求。")
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
    }

    interface OnKeyDownListener{
        fun onKeyDown(event: KeyEvent)
    }
    private var onKeyDownListener:OnKeyDownListener? = null
    fun setOnKeyDownListener(onKeyDownListener:OnKeyDownListener){
        this.onKeyDownListener = onKeyDownListener;
    }

    /**
     * 给应用一个机会处理按键事件
     * 例如 菜单快捷键事件需要以这种方式过滤。
     * 如果返回true，则WebView将不处理键事件。 如果返回false，WebView将始终处理键事件，
     * 因此视图链中的super都不会看到键事件。 默认行为返回false。
     * @param view 正在启动回调的WebView。
     * @param event 关键事件。
     * @return 如果主机应用程序想要处理键事件本身，则为true，否则返回false
     */
    override fun shouldOverrideKeyEvent(view: WebView, event: KeyEvent): Boolean {
        LogUtils.d(TAG, "给应用一个机会处理按键事件$event")
        if(onKeyDownListener != null){
            onKeyDownListener?.onKeyDown(event)
        }
        return super.shouldOverrideKeyEvent(view, event)
    }

    /**
     * 处理未被WebView消费的按键事件
     * WebView总是消费按键事件，除非是系统按键或shouldOverrideKeyEvent返回true
     * 此方法在按键事件分派时被异步调用
     *
     * @param view 正在启动回调的WebView。
     * @param event 关键事件。
     */
    override fun onUnhandledKeyEvent(view: WebView, event: KeyEvent) {
        LogUtils.d(TAG, "处理未被WebView消费的按键事件$event")
        super.onUnhandledKeyEvent(view, event)
    }

    /**
     * 通知应用页面缩放系数变化
     * @param view 正在启动回调的WebView。
     * @param oldScale 旧的比例因子
     * @param newScale 新的比例因子
     */
    override fun onScaleChanged(view: WebView, oldScale: Float, newScale: Float) {
        LogUtils.d(TAG, "通知应用页面缩放系数变化")
        super.onScaleChanged(view, oldScale, newScale)
    }

    /**
     * 通知应用有个已授权账号自动登陆了
     * @param view 请求登录的WebView。
     * @param realm 用于查找帐户的帐户域。
     * @param account 可选帐户。如果不是null，则应根据设备上的帐户检查帐户。如果是有效帐户，则应使用该帐户登录用户。这个值可能是null。
     * @param args 用于登录用户的Authenticator特定参数。
     */
    override fun onReceivedLoginRequest(view: WebView, realm: String, account: String?, args: String) {
        LogUtils.d(TAG, "通知应用有个已授权账号自动登陆了")
        super.onReceivedLoginRequest(view, realm, account, args)
    }

    /**
     * 给定WebView的渲染过程已退出。
     * 多个WebView实例可以与单个渲染过程相关联; 将为受影响的每个WebView调用onRenderProcessGone。
     * 应用程序的此回调实现应该只尝试清理作为参数提供的特定WebView，并且不应该假设其他WebView实例受到影响。
     * 给定的WebView不能使用，应该从视图层次结构中删除，应该清除对它的所有引用，例如Activity中的任何引用或使用View.findViewById(int)类似调用保存的其他类等。
     * 为了查找渲染崩溃原因以达到测试目的，我们可以调用loadUrl("chrome://crash")在WebView上。
     * 请注意，如果多个WebView实例共享渲染过程，则可能会受到影响，而不仅仅是加载了chrome：// crash的特定WebView。
     *
     * @param view 需要清理的WebView。
     * @param detail 退出的原因。。
     * @return true 如果主机应用程序处理了进程退出的情况，否则，如果渲染进程崩溃，应用程序将崩溃，或者如果渲染进程被系统杀死，则应用程序将被终止。
     */
    override fun onRenderProcessGone(view: WebView, detail: RenderProcessGoneDetail): Boolean {
        LogUtils.d(TAG, "给定WebView的渲染过程已退出。")
        return super.onRenderProcessGone(view, detail)
    }

    /**
     * 通过安全浏览通知主机应用程序已加载URL。
     * 应用程序必须调用回调以指示首选响应。
     * 默认行为是向用户显示插页式广告，并显示报告复选框。
     * 如果应用程序需要显示自己的自定义插页式UI，
     * 则可以使用SafeBrowsingResponse#backToSafety或异步调用回调SafeBrowsingResponse#proceed，具体取决于用户响应。
     *
     * SAFE_BROWSING_THREAT_*对应的值：
     * SAFE_BROWSING_THREAT_UNKNOWN ：资源因未知原因被阻止。常数值：0（0x00000000）
     * SAFE_BROWSING_THREAT_UNWANTED_SOFTWARE ：资源被阻止，因为它包含不需要的软件。常数值：3（0x00000003）
     * SAFE_BROWSING_THREAT_PHISHING ：资源被阻止，因为它包含欺骗性内容。常数值：2（0x00000002）
     * SAFE_BROWSING_THREAT_MALWARE ：资源被阻止，因为它包含恶意软件。常数值：1（0x00000001）
     *
     * @param view 攻击恶意资源的WebView。
     * @param request 包含请求详细信息的对象。
     * @param threatType 安全浏览捕获资源的原因，对应于 SAFE_BROWSING_THREAT_*值。
     * @param callback 应用程序必须调用其中一个回调方法。
     */
    override fun onSafeBrowsingHit(view: WebView, request: WebResourceRequest, threatType: Int, callback: SafeBrowsingResponse) {
        LogUtils.d(TAG, "通过安全浏览通知主机应用程序已加载URL。")
        super.onSafeBrowsingHit(view, request, threatType, callback)
    }
}