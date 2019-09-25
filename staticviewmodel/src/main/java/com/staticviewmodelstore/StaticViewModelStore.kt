package com.staticviewmodelstore

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import java.util.*
import kotlin.collections.HashMap

/**
 * 单例ViewModelStore
 * 用于实现不同Activity和Fragment之间的数据通信
 * 甚至service和Activity之间的通信(暂未验证)
 *
 * 使用方法: 不应该直接调用此类,应该在Activity和Fragment以下方法调用(todo service待做)
 *
 * @author attect
 * @date 2019年5月6日
 */

object  StaticViewModelStore {

    private val TAG = "SVMS"
    private val viewModelStoreMap = HashMap<String , ViewModelStore>()
    private val viewModelProviderCounter = HashMap<String , Int>()


    /**
     * 从给定的全局ViewModelStore中创建一个
     * ViewModelProvider以用于获取Viewmodel实例
     *
     * @param key ViewModelProvider的指定key
     * @param application Android Application ,用于创建ViewModel的Factory
     * @return  可创建指定key的ViewModelStore中持有的ViewModel的ViewModelProvider
     */
    fun getViewModelProvider(key: String , application: Application ):ViewModelProvider{
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        var viewModelStore = viewModelStoreMap[key]
        if(viewModelStore == null){
            viewModelStore = ViewModelStore()
            viewModelStoreMap[key] = viewModelStore
        }
        //更新计数
        var count = viewModelProviderCounter[key]
        if(count != null){
            count ++
        }else{
            count = 1
        }
        viewModelProviderCounter[key] = count
        Log.d(TAG , "new visitor to store:$key now customer(s):$count")
        return ViewModelProvider(viewModelStore , factory)
    }


    /**
     * ViewModel持有者放弃相关key的ViewModel
     * 当持有数量为0的时候,如果Activity再reconfig,则不做其他操作
     * 否则將全局viewModelStoreMap中彻底移除相关ViewModelStore
     *
     * @param key     持有viewModel的ViewModelStore
     * @param isChangingConfigurations Activty是否正在变更配置,传入Activity.isChangingConfiguration()方法结果,若为Fragment或service,则使用为false
     */
    fun giveUpViewModelStore(key : String , isChangingConfigurations : Boolean){
        var count = viewModelProviderCounter[key]
        if(count != null){
            count--
            viewModelProviderCounter[key] = count
            Log.d(TAG  , "A customer leave store ${key} , left $count customer(s)")
            if(count == 0  && !isChangingConfigurations ){
                viewModelProviderCounter.remove(key)
                val viewModelStore = viewModelStoreMap[key]
                if(viewModelStore != null){
                    viewModelStore.clear()
                    viewModelStoreMap.remove(key)
                    Log.i(TAG , "remove viewModelStore:$key")
                }
            }else if(!isChangingConfigurations){
                Log.i(TAG , "keey viewModelStore:$key because activity is changing configuration")
            }
        }
    }
interface StaticViewModelStoreCaller{
    fun <T: ViewModel> getStaticViewModel(viewModelStoreKey: String , cls: Class<out ViewModel>):T?
}



}