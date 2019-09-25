package com.staticviewmodelstore

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import java.lang.IllegalStateException

open class StaticViewMOdelLifecycleActivity : AppCompatActivity() , StaticViewModelStore.StaticViewModelStoreCaller {

    private val staticViewProviderKey = ArrayList<String>()


    /**
     * 获取得一个全局ViewModel
     * 此ViewModel可以跨越Activity, Fragment以及service
     * 将被StaticViewModelStore持有
     * 此方法应该在onCreate的时候和结束之后再使用
     *
     * @param viewModelStoreKey 区分ViewModelStore的key,同一个key下的ViewModel將能收到同一个时间
     * @param cls               ViewModel实现类的class
     * @param <T>               ViewModel实现的类型
     * @return     请求的ViewModel
     */
    override fun <T : ViewModel> getStaticViewModel(
        viewModelStoreKey: String,
        cls: Class<out ViewModel>
    ): T? {
        if(application == null) throw IllegalStateException("不要在类成员声明时直接调用getStaticViewModel")
        staticViewProviderKey.add(viewModelStoreKey)
        return StaticViewModelStore.getViewModelProvider(viewModelStoreKey , application).get(cls) as T
    }

    override fun onDestroy() {
        super.onDestroy()
        for(viewProviderKey in staticViewProviderKey){
            StaticViewModelStore.giveUpViewModelStore(viewProviderKey , isChangingConfigurations)
        }
        staticViewProviderKey.clear()
    }

}