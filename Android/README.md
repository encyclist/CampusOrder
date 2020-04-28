# 校园点餐系统

## 用到的技术
* 网络请求 [retrofit2](https://blog.csdn.net/carson_ho/article/details/73732076)
* 网络请求 [okhttp3](https://www.jianshu.com/p/16ab28d40737)
* JSON解析 [fastjson](https://www.cnblogs.com/felixzh/p/8551796.html)
* 下拉刷新 [SmartRefreshLayout](https://www.jianshu.com/p/29e315ff44a6)
* 控件绑定[butterknife](https://www.jianshu.com/p/5dead31a84f6)
* [qmui](https://qmuiteam.com/android/documents/)

## 关键代码
### MVP的封装
代码请看`FragmentPresenter.java`、`ActivityPresenter.java`、`PresenterActivity.java`、`PresenterFragment.java`，内有注释
* presenter是mvp设计模式中的p，负责逻辑的处理
* FragmentPresenter、ActivityPresenter是封装的Presenter的基类(父类)，管理presenter的生命周期、方便在presenter中调用一些基本功能(获取view、获取网络的封装)。
* 页面创建后会先在PresenterActivity中加载presenter然后使用ButterKnife进行控件绑定，然后开始执行activity中的代码，并且通知presenter开始初始化，这时presenter可以执行自己的代码
* 页面关闭时PresenterActivity中的onDestroy被触发，会对调用BasePresenter的destroy方法来放弃对activity的引用，从而防止内存泄漏
* 安卓中的Activity和Fragment在这里都当作mvp中的v(view)，主要负责更新界面

### 网络请求的封装
代码请看common下的`com.erning.common.net`，其中：
* `Network.java` 是网络请求用的，里面有个`ROOT`是后台的地址，剩下的看注释，里面都写了
* `RemoteService.java`是接口(api)地址的封装，接口请求方式是POST
* `FastJsonConverterFactory.java`是用来将json数据转换成实体类的

## 功能页面（较复杂的页面均有注释，下面会标出）
（待续）

## 技术要求
使用AndroidStudio进行开发，基于Java，兼容到安卓5.0

## 老师可能提的问题
* 数据分页加载相关：加载数据（参数传page和limit），每页加载20条数据，最后一条显示出来时候就去加载第二页
* 欢迎页面动画相关：一个第三方控件，是它自带的动画
* 首页怎么实现的这种效果：ViewPager是可以左右滑动的，里有3个Fragment。当BottomNavigationView(下面的三个按钮)被点击时就让ViewPager滑动到指定的地方
* 最上面的`用到的技术`要多看看