这是我总结的设置状态栏的方法(简单说说实现方式)

1.主题的样式修改,去掉原先的toolbar,添加自己的toolbar
2.给toolbar设置颜色
3.给toolbar设置透明度
4.给界面设置全屏,去掉toolbar



先说说怎么设置主题样式

这是一个总的样式
    <style name="CYAppTheme" parent="Theme.AppCompat.DayNight.NoActionBar">
        <item name="colorPrimary">@color/colorPrimary</item><!--  主题颜色 -->
        <item name="colorAccent">@color/colorAccent</item><!--  主题颜色 -->
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item><!--  主题颜色 -->

        <item name="android:windowTranslucentStatus">false</item><!--  true为半透明, -->
        <item name="windowActionModeOverlay">true</item> <!--      其实是为了能够让ActionMode能够覆盖我们的Toolbar       -->
        <item name="android:windowActionModeOverlay">true</item><!--      其实是为了能够让ActionMode能够覆盖我们的Toolbar       -->

        <item name="actionModeStyle">@style/CYAppTheme.ActionMode</item>
        <item name="actionModeCloseButtonStyle">@style/CYAppTheme.ActionMode.ColseStype</item><!--   返回键的样式         -->
        <item name="actionModeBackground">@color/colorPrimary</item>
    </style>


    //toolbar高度
    <style name="CYAppTheme.ActionMode" parent="Widget.AppCompat.ActionMode">
        <item name="height">@dimen/toolbar_height</item>
    </style>
    //返回键
    <style name="CYAppTheme.ActionMode.ColseStype" parent="Widget.AppCompat.ActionButton.CloseMode">
        <item name="android:tint">@android:color/white</item>
        <item name="android:layout_marginStart">2dp</item>
    </style>


    //设置toolbar的theme
    <style name="AppTheme.AppBarOverlay" parent="ThemeOverlay.AppCompat.Dark" >
       <item name="windowActionModeOverlay">true</item>
       <item name="android:windowActionModeOverlay">true</item>
       <!--底部无阴影-->
       <item name="elevation">0dp</item>
    </style>

以上就是整个app的主题,以及toolbar的主题

点击设置顶部状态栏的颜色,直接设置就好了,具体方法见Activity.setStatusBarColor(@ColorInt color:Int)
设置状态栏的透明度,具体方法见Activity.transparentStatusBar(lightIconStyle: Boolean = true)
最后一个设置全屏的方法见Activity.fullScreen(isFullScreen: Boolean = true)



