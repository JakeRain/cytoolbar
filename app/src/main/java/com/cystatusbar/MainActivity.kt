package com.cystatusbar

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cystatusbar.extensions.*
import com.cystatusbar.viewmodel.WindowInsetsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import cy.statusbar.extensions.fullScreen
import cy.statusbar.extensions.rootView
import cy.statusbar.extensions.setStatusBarColor
import cy.statusbar.extensions.transparentStatusBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var colors: ArrayList<Int>

    var isTransParent = false


    lateinit var windowInsetsViewModel: WindowInsetsViewModel
    val windowInsets: MutableLiveData<WindowInsetsCompat>
        get() = windowInsetsViewModel.windowInsetsCompatMutableLiveData


    private var windowInsetsWatchingView: View? = null



    //region appbarlayout中的布局

    var appbarLayoutParent: ViewGroup? = null
    var appbarLayout: AppBarLayout? = null
    var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    /**
     *  标题  导航控制   菜单等组件的容器
     *
     */
    var toolbar: Toolbar? = null
    var toolbarTitle: AppCompatTextView? = null

    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        windowInsetsViewModel = ViewModelProviders.of(this).get(WindowInsetsViewModel::class.java)

        colors = arrayListOf(
            androidx.core.content.res.ResourcesCompat.getColor(
                resources,
                com.cystatusbar.R.color.colorAccent,
                resources.newTheme()
            ),
            ResourcesCompat.getColor(resources, R.color.colorPrimary, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.red_100, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.red_700, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.red_A700, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.pink_100, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.pink_700, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.pink_A700, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.purple_100, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.purple_700, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.purple_A700, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.deep_purple_100, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.deep_purple_700, resources.newTheme()),
            ResourcesCompat.getColor(resources, R.color.deep_purple_A700, resources.newTheme())
        )


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        transparentStatusBar(true)


        initAppbar()

    }

    override fun onStart() {
        super.onStart()
        watchWindowInsetsChange(rootView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun colorBar(view: View) {
        val color = colors.shuffled().last()
        setStatusBarColor(color)
    }

    fun transparentBar(view: View) {
        isTransParent = !isTransParent
        transparentStatusBar(isTransParent)
    }

    var isFull = false

    fun showFullScreen(view: View) {
        isFull = !isFull
        fullScreen(isFull)
    }


    /**
     * 通过一个View来监听屏幕安全区域的变化
     * 可以判断到状态栏/屏幕缺口/虚拟键盘等区域
     * 对view直接使用ViewCompat.setOnApplyWindowInsetsListener也可获取到类似的效果,但ViewCompat.setOnApplyWindowInsetsListener方法中获取的windowInsets可能不可控的被消耗掉
     * 因此此处新编写观察逻辑来确保有jing获取当前window中一个始终不变的windowInsets来让界面作出相关适应
     *
     * @param view 通过哪个view来观察windowinsets变化,此view应该再布局中尽量顶层
     */
    private fun watchWindowInsetsChange(view:View?){
        if(view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH){
            windowInsetsWatchingView?.let {
                //清空之前的view的观察,防止多次触发
                ViewCompat.setOnApplyWindowInsetsListener(it , null)
            }
            windowInsetsWatchingView = view
            ViewCompat.setOnApplyWindowInsetsListener(view){_,insets->
                windowInsets.value = insets
                insets// 不消费
            }
        }
    }


    private fun initAppbar(){
        appbarLayoutParent = findViewById(R.id.appbarLayoutParent)
        appbarLayout = appbarLayoutParent?.findViewById(R.id.appbarLayout)
        appbarLayoutParent?.let { parent->
            toolbar = appbarLayout?.findViewById(R.id.toolbar)
            collapsingToolbarLayout = appbarLayout?.findViewById(R.id.collaspingToolbarLayout)
            windowInsets.observe(this , Observer {windowInsetsCompat->
                if(isFull)return@Observer
                parent.layoutParams?.let { lp->
                    if(lp is ViewGroup.MarginLayoutParams){
                        lp.leftMargin = windowInsetsCompat.currentSafeLeft
                        lp.rightMargin = windowInsetsCompat.currentSafeRight
                        if(toolbar == null )lp.topMargin = windowInsetsCompat.currentSafeTop
                        lp.bottomMargin = windowInsetsCompat.currentSafeBottom
                    }
                }
            })


            if(toolbar!= null && toolbar?.parent is AppBarLayout){
                toolbar?.setBackArrowColor()
                toolbar?.layoutParams?.height = resources.getDimensionPixelSize(R.dimen.toolbar_height)

                toolbarTitle = toolbar?.findViewById(R.id.toolbarTitle)
                toolbarTitle?.apply {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX , resources.getDimensionPixelSize(R.dimen.toolbar_title_text_size).toFloat())
                    setTextColor(ResourcesCompat.getColor(resources , R.color.appbarTitleColor , theme))
                }

                setSupportActionBar(toolbar)
                //清空android原有标题
                toolbar?.title = ""
                super.setTitle("")

                val appbarTitleColor = ResourcesCompat.getColor(resources , R.color.appbarTitleColor , theme)
                toolbar?.apply {
                    setTitleTextColor(appbarTitleColor)
                    setSubtitleTextColor(appbarTitleColor)
                }

                windowInsets.observe(this , Observer {windowInsetsCompat->
                    if(isFull)return@Observer
                    toolbar?.layoutParams?.let {lp->
                        if(lp is ViewGroup.MarginLayoutParams){
                            lp.topMargin = windowInsetsCompat.currentSafeTop
                        }
                    }
                })
            }


            if(collapsingToolbarLayout != null && collapsingToolbarLayout?.parent is CollapsingToolbarLayout){
                collapsingToolbarLayout?.apply {
                    setContentScrimColor(
                        ResourcesCompat.getColor(
                            resources ,
                            R.color.collapsing_toolbar_layout_content_scrim,
                            theme
                        )
                    )
                    setExpandedTitleColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.collapsing_toolbar_layout_expanded_title,
                            theme
                        )
                    )
                    title = ""
                }
                windowInsets.observe(this , Observer {windowInsetsCompat->
                    if(isFull)return@Observer
                    collapsingToolbarLayout?.layoutParams?.let {lp->
                        if(lp is ViewGroup.MarginLayoutParams){
                            lp.topMargin = windowInsetsCompat.currentSafeTop
                        }
                    }
                })
            }
        }

    }



}
