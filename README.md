**本项目只是为了个人学习总结用的**

# 一.设置状态栏

这是我总结的设置状态栏的方法(简单说说实现方式)

1.主题的样式修改,去掉原先的toolbar,

添加自己的toolbar

2.给toolbar设置颜色

3.给toolbar设置透明度4.给界面设置全屏,去掉toolbar先说说怎么设置主题样式这是一个总的样式   

<style name="CYAppTheme" parent="Theme.AppCompat.DayNight.NoActionBar">    
<item name="colorPrimary">@color/colorPrimary</item><!--  主题颜色 -->      
<item name="colorAccent">@color/colorAccent</item><!--  主题颜色 -->       
<item name="colorPrimaryDark">@color/colorPrimaryDark</item><!--  主题颜色 -->        
<item name="android:windowTranslucentStatus">false</item><!--  true为半透明, -->   
<!--      其实是为了能够让ActionMode能够覆盖我们的Toolbar       -->       
<item name="windowActionModeOverlay">true</item> 
<!--      其实是为了能够让ActionMode能够覆盖我们的Toolbar       -->  
<item name="android:windowActionModeOverlay">true</item>     
<item name="actionModeStyle">@style/CYAppTheme.ActionMode</item>        
<!--   返回键的样式         --> 
<item name="actionModeCloseButtonStyle">@style/CYAppTheme.ActionMode.ColseStype</item>      
<item name="actionModeBackground">@color/colorPrimary</item>    
</style>   

//toolbar高度    
<style name="CYAppTheme.ActionMode" parent="Widget.AppCompat.ActionMode">        
<item name="height">@dimen/toolbar_height</item>    
</style>    

//返回键    
<style name="CYAppTheme.ActionMode.ColseStype" parent="Widget.AppCompat.ActionButton.CloseMode">        <item name="android:tint">@android:color/white</item>       
<item name="android:layout_marginStart">2dp</item>    
</style>    

//设置toolbar的theme    
<style name="AppTheme.AppBarOverlay" parent="ThemeOverlay.AppCompat.Dark" >      
<item name="windowActionModeOverlay">true</item>      
<item name="android:windowActionModeOverlay">true</item>       <!--底部无阴影-->       
<item name="elevation">0dp</item>    
</style>

以上就是整个app的主题,以及toolbar的主题点击设置顶部状态栏的颜色,
直接设置就好了,具体方法见Activity.setStatusBarColor(@ColorInt color:Int)
设置状态栏的透明度,具体方法见Activity.transparentStatusBar(lightIconStyle: Boolean = true)
最后一个设置全屏的方法见Activity.fullScreen(isFullScreen: Boolean = true)

# 二    將同事的staticviewmodel加入进来这个是他的主页,https://github.com/Attect

# 三    学习十个景点的排序算法现在只有七个排序算法

1/冒泡排序,2/选择排序,3/插入排序,4/希尔排序,5/归并排序,6/快速排序,7/堆排序
(用两句话根据自己的理解简单的描述)

1. 冒泡排序
从前往后两两比较,小的换到前面,大的到后面循环次数n*n*

2. 选择排序

   从前往后扫描找到最小的,將最小的放到前面
   循环次数n*n

3. 插入排序
   两层循环第一层从前往后第二层循环,从上一层的index倒回到0位置,目的將index数据找到他合适的未知最差的情况  
   循环次数n*n
   
4. 希尔排序
   將数组长度/2=len  index={(0,len) , (1 , len+1)... (len,len+len)}
   如此两两比对,小的到前面大的到后面数组长度/2/2 =len   
   如此从0 开始每隔len个数字为一组,进行插入排序
   循环次数n*log(2)n
   
5. 归并排序(个人认为最有趣的一种, 用到了递归)
   將数组一直除以2除到不能除为止,將数组分成若干个小数组,然后归并(按照从小到大的归并)
   
6. 快速排序

   在数组中随机取一个数字,將比此数字大的放在右边,比它小的放左边

   再分别左右进行快速排序,如此达到按顺序排列

7. .堆排序(原文章https://www.cnblogs.com/guoyaohua/p/8600214.html, adjustHeap()方法有错)
   將数组按照二叉树的顺序进行比对排序,將最大的数字浮到顶节点,然后將他与最后一个节点交换如此往复循环到最后,最小的数字在顶层节点

8. 计数排序

   用到了桶的概念

   (max-min)个桶,將数字按他们的大小放入指定的桶中(这里用ArrayList来做桶)

   然后按从小到大的顺序取出

9. 桶排序

   原理和8有点像,但是更高级了

   固定了桶的个数bucketSize(减少了资源的开支)

   第一步,(max-min)/bucketSize + 1   算出共需要多少个桶架(每个桶架需要摆bucketSize个桶)

   第二步,將数字num放入第(num-min)/bucketSize的桶中

   第三步,將数字按桶的顺序取出就好了

10. 基数排序

   这样的排序可以反转我们的常规思维(非常的有趣).也用到了桶的概念

   0-9个桶

   首先算出最大的数字max有几位(好比百十个)

   每一位创建   0..9个桶,

   第一步,將所有数字按照其个位的数字,放在其对用的0..9个桶内,然后按顺序取出

   第三步,將所有数字按照其十位的数字,放在其对用的0..9个桶内,然后按顺序取出

   第四步,將所有数字按照其千位的数字,放在其对用的0..9个桶内,然后按顺序取出

   最后取出的必然按从小到大的顺序


听同事说有个睡眠排序,自己靠着理解尝试的睡眠排序原理: 每个数据单独的一个线程      根据数据让线程进入睡眠状态      先醒的必然是比较小的数字,將排在前面      由于jvm的线程工作原理(两个相邻的数字容易混乱)    所以將每个数字都扩大了100倍 , 让线程睡100倍的时间