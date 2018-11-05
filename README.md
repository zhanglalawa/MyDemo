这是一个读完《第一行代码》之后练手做的App
用到的主要是书上的内容，以及从视频、文档里面学的一些知识，主要用到的知识总结如下：

1.闪屏用的Handler延迟发送消息2s进入登陆界面

2.对一些常用的比如SharedPreferences，okhttp，log等等进行了简单封装

3.RecyclerView做的聊天界面，RecyclerView做的知乎日报的界面

4.解析各种网络数据(json，xml)

5.ViewPager做的第一次打开APP的引导界面

6.ViewPager + Tablayout + Fragment做的主界面

7.SharedPreferences做了判断是否是第一次打开app，以及记住密码功能

8.WebView显示知乎日报点击进去的界面（但是里面一些图片的大小不合适...后面应该会学到相关知识）

9.尝试了《第一行代码的》的Material Design风格（一个悬浮按钮和滑动缩回Toolbar）

![](https://upload-images.jianshu.io/upload_images/13852523-1cd5b8c0c1f4875f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/13852523-fa87e383e5db4269.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/13852523-8dc2e7b1bcd02562.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

10.用户登陆的后端用的Bmob



下面是各个界面的展示：
## 闪屏页面SplashActivity
![](https://upload-images.jianshu.io/upload_images/13852523-a0ddeb59fab77f32.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 引导页面Activity
![](https://upload-images.jianshu.io/upload_images/13852523-344d2509ebc905e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/13852523-0db17fc541b371a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这块是用`ViewPager`实现的。

## 登陆界面
![](https://upload-images.jianshu.io/upload_images/13852523-33b85a273dc32d6e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 主界面
- `ViewPager` + `Fragment` + `TabLayout`

###### 机器人聊天
![](https://upload-images.jianshu.io/upload_images/13852523-89c311227be1d270.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


###### 查询工具（快递和号码归属地）
![](https://upload-images.jianshu.io/upload_images/13852523-8e842577aa3a9ed0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/13852523-f076d358655b2541.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/13852523-5f9fc1a40fd4be21.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 号码归属地

###### 知乎日报
![](https://upload-images.jianshu.io/upload_images/13852523-c297cc05bec96d7f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###### 个人中心
![](https://upload-images.jianshu.io/upload_images/13852523-dbce6044d3cdd9c2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![](https://upload-images.jianshu.io/upload_images/13852523-ff6f509089fc130e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
