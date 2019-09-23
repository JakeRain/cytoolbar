package cy.statusbar.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.ResultReceiver
import android.view.FrameMetrics.ANIMATION_DURATION
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimatorRes
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import com.cystatusbar.R
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

val Activity.rootView: View
    get() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        return if (viewGroup.childCount == 0) viewGroup else viewGroup.getChildAt(0)
    }


/**
 * 设置状态栏颜色
 * 如果Android LOLLIPOP以下,则没有效果
 * @param color 色彩int值,非资源id
 */
fun Activity.setStatusBarColor(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        //这一步最好要做,因为如果这两个flag没有清除的话下面没有生效
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        getWindow().statusBarColor = color
    }
}

/**
 * 设置状态栏为透明
 * 一些版本的操作系统不支持图标明暗风格变更
 * 使用此方法默认全局都为透明状态栏,不透明效果自己进行伪实现
 * @param lightIconStyle 图标明暗风格,true图标为白色
 */
fun Activity.transparentStatusBar(lightIconStyle: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !lightIconStyle) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    miUISetStatusBarLightMode(this.window, !lightIconStyle)
    flymeSetStatusBarLightMode(this.window, !lightIconStyle)

}


fun Activity.fullScreen(isFullScreen: Boolean = true) {

    if (isFullScreen) {
        exitAnimation()
    } else {
        enterAnimation()
    }
}






private fun Activity.enterAnimation(){
    startAnimation(appbarLayout,R.anim.top_up_to_down_translate_anim);//Toolbar 从上到下动画
//    startAnimation(mBottomViewGroup,R.anim.bottom_down_to_up_translate_anim);//Bottom 从下到上动画
    appbarLayout.postDelayed( Runnable() {//动画结束后再显示状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    },ANIMATION_DURATION.toLong());
}

private fun Activity.exitAnimation(){
    startAnimation(appbarLayout,R.anim.top_down_to_up_translate_anim);//Toolbar 从下到上动画
//    startAnimation(mBottomViewGroup,R.anim.bottom_up_to_down_translate_anim);//Bottom 从上到下动画
    appbarLayout.postDelayed( Runnable() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    },ANIMATION_DURATION.toLong());
}


 fun Activity.startAnimation( view: View,  animationId : Int) {//开始动画
    if(view!=null&&animationId!=0){
        val animation = AnimationUtils.loadAnimation(this,animationId);
        view.startAnimation(animation);
    }
}




/**
 * 安装指定路径的APK
 * 会弹出系统安装App界面
 * 一般情况下还会被安全设置所提醒和拦截
 * 还要确保对应路径有读取权限
 *
 * @param path APK的绝对路径
 */
fun Activity.installApk(path: String) {
    val file = File(path)
    if (!file.exists() || !file.isFile) return
    val intent = Intent(Intent.ACTION_VIEW)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri: Uri
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        uri = FileProvider.getUriForFile(this, application.packageName + ".file.provider", file)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } else {
        uri = Uri.fromFile(file)
    }
    intent.setDataAndType(uri, "application/vnd.android.package-archive")
    startActivity(intent)
}

/**
 * 隐藏软件盘
 * 显示 @see View.showSoftKeyboard
 * @param flag 0/InputMethodManger.HIDE_IMPLICIT_ONLY/InputMethodManger.HIDE_NOT_ALWAYS
 * @param receiver andorid.os.ResultReciever
 */
@JvmOverloads
fun Activity.hideSoftKeyboard(flag: Int = 0, receiver: ResultReceiver? = null) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, flag, receiver)
    }
}

/**
 *向系统表示要分享一段文字
 *
 * @param content 要分享的内容
 * @param titleRes 分享的标题的文字资源
 */
fun Activity.shareTextContent(content: String, @StringRes titleRes: Int = R.string.share_to) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.putExtra(Intent.EXTRA_TEXT, content)
    intent.type = "text/plain"
    startActivity(Intent.createChooser(intent, getString(titleRes)))
}


/**
 * 启用安全显示
 * 启用后不可截图 不可被录屏,橄榄窗口也看不到
 * 当然,对root后的设备不一定有效
 */
fun Activity.secureDisplay(enable: Boolean = true) {
    if (enable) window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
    else window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
}


fun Activity.toHomeScreen() {
    val startMain = Intent(Intent.ACTION_MAIN)
    startMain.addCategory(Intent.CATEGORY_HOME)
    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(startMain)
}