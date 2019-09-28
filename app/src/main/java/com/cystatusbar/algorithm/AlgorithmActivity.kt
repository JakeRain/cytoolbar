package com.cystatusbar.algorithm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cystatusbar.R
import com.cystatusbar.extensions.show
import kotlinx.android.synthetic.main.activity_algorithm.*
import java.lang.StringBuilder
import java.util.*
import java.util.Collections.swap


/**
 * 十个经典的排序算法
 * 学习资料https://www.cnblogs.com/guoyaohua/p/8600214.html
 */


@Suppress("DEPRECATION")
class AlgorithmActivity : AppCompatActivity() {
    val myliveData = MutableLiveData<String>()


    val colors = arrayListOf(
        "#00FFFF",
        "#FFFF00",
        "#FFFF33",
        "#66CCFF",
        "#3399FF",
        "#9999FF",
        "#FF6666"
    )


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algorithm)
        myliveData.observe(this, Observer {
            show show it
        })
    }


    /**
     * 睡眠排序
     * 每条数据单独一个线程
     * 每个线程根据数据sleep长短
     * 防止cpu限制  每个数据扩大100倍
     */
    fun sleep(view: View) {
        val datas = (5..19).shuffled().toIntArray()
        if (!show.text.isNullOrEmpty()) show.text = ""
        val result = StringBuilder("睡眠排序<br/>")

        datas.forEach {

            Thread(Runnable {
                Thread.sleep((it.toLong()) * 100)
                result.append(it).append("&nbsp;&nbsp;")
                myliveData.postValue(result.toString())
            }).start()
        }

    }

    /**
     * 冒泡排序
     * 从第一个数据往后两两比较
     * 小的放前面大的放后面
     */
    @SuppressLint("SetTextI18n")
    fun dichotomy(view: View) {
        val datas = (5..19).shuffled().toIntArray()


        Thread(Runnable {
            val result = StringBuilder("冒泡排序<br/>")
            for (i in 0 until datas.size) {
                for (j in 0 until datas.size - i - 1) {
                    if (datas[j] > datas[j + 1]) {
                        var temp = datas[j]
                        datas[j] = datas[j + 1]
                        datas[j + 1] = temp


                        datas.forEachIndexed { index, value ->
                            val colorString = if (index == j + 1) {
                                colors[0]
                            } else {
                                "#FFFFFF"
                            }

                            result.append("&nbsp;&nbsp;<font color='").append(colorString)
                                .append("'>").append(value).append("</font>")
                        }
                        result.append("<br/>---------------------------------<br/>")
                        myliveData.postValue(result.toString())
                    }

                }
            }
        }).start()


    }

    /**
     * 选择排序
     * 从当前位置 往后循环查找到最小的数字
     * 將最小的数字提到当前的位置
     *
     *
     */
    fun selected(view: View) {
        val datas = (5..19).shuffled().toIntArray()
        Thread(Runnable {
            val result = StringBuilder("选择排序<br/>")
            for (i in 0 until datas.size) {
                var minIndex = i
                for (j in i until datas.size) {
                    if (datas[j] < datas[minIndex]) minIndex = j
                }
                var temp = datas[minIndex]
                datas[minIndex] = datas[i]
                datas[i] = temp
                datas.forEachIndexed { index, value ->
                    val colorString = if (index == i || index == minIndex) {
                        colors[0]
                    } else {
                        "#FFFFFF"
                    }
                    result.append("&nbsp;&nbsp;<font color='${colorString}'>$value </font>")
                }
                result.append("<br/>---------------------------------<br/>")
                myliveData.postValue(result.toString())
            }
        }).start()


    }

    /**
     * 插入排序
     * 从前往后    找到较之前的数字小的,將他插入到他应该在的地方
     * 比如1237895
     * 循环到5发现前面有比它大的数字,將此数字提出放到7前面
     *
     */
    fun insert(view: View) {
        val datas = (5..19).shuffled().toIntArray()

        Thread(Runnable {
            val result = StringBuilder("插入排序<br/>")
            myliveData.postValue(result.toString())

            for (i in 0 until datas.size - 1) {
                var current = datas[i + 1]
                var preIndex = i
                while (preIndex >= 0 && current < datas[preIndex]) {
                    datas[preIndex + 1] = datas[preIndex]
                    preIndex--
                }
                datas[preIndex + 1] = current
                datas.forEachIndexed { index, value ->
                    val colorString = if (index > preIndex && index <= i + 1) {
                        colors[0]
                    } else {
                        "#FFFFFF"
                    }
                    result.append("&nbsp;&nbsp;<font color='${colorString}'>$value </font>")
                    myliveData.postValue(result.toString())
                }
                result.append("<br/>---------------------------------<br/>")
                myliveData.postValue(result.toString())
            }
        }).start()


    }

    /**
     * 希尔排序
     * 1,len/2  2,len/2+1  3,len/2+2.....第一遍len/2这样两两比对
     * 1,len/4  2,len/4+1  3,len/4+2.....第二遍len/2/2
     *                                  .   len/2/2/2
     *                                  .   len/2/2/2/2
     *                                  .
     *                                  直到n除以的2等于0为止
     */
    fun shellSort(view: View) {
        val datas = (5..19).shuffled().toIntArray()


        Thread(Runnable {
            val result = StringBuilder()
            result.append("希尔排序<br/>")
            myliveData.postValue(result.toString())

            val len = datas.size
            var temp: Int
            var gap = len / 2
            while (gap > 0) {
                for (i in gap until len) {
                    temp = datas[i]
                    var preIndex = i - gap
                    while (preIndex >= 0 && datas[preIndex] > temp) {
                        datas[preIndex + gap] = datas[preIndex]
                        preIndex -= gap
                    }
                    datas[preIndex + gap] = temp
                    datas.forEachIndexed { index, value ->
                        val colorString = if (index == (i - gap) || index == i) {
                            colors[0]
                        } else {
                            "#FFFFFF"
                        }
                        result.append("&nbsp;&nbsp;<font color='${colorString}'>$value </font>")
                        myliveData.postValue(result.toString())
                    }
                    result.append("<br/>---------------------------------<br/>")
                    myliveData.postValue(result.toString())
                }
                gap /= 2
            }
        }).start()

    }

    /**
     * 归并排序的要点
     * array.size/2 分成两个数组  left   right
     * 再將left和right   分别除以2   再分成四个数组
     *
     * 如此往下直到不能再拆分
     *
     * 再从最小长度的两两归并按照从小到大的顺序
     *
     *
     */
    //region  归并排序

    val mergeStringBuilder = StringBuilder()

    fun mergeSort(view: View) {
        var datas = (5..19).shuffled().toIntArray()
        mergeStringBuilder.append("归并排序").append("<br/>")
        mergeStringBuilder.append("原数组").append("<br/>")
        datas.forEachIndexed { index, i ->
            mergeStringBuilder.append(i).append("&nbsp;&nbsp;")
        }
        mergeStringBuilder.append("<br/>")
        myliveData.value = mergeStringBuilder.toString()

        Thread(Runnable {
            datas = myMergeSort(datas)
        }).start()

    }

    fun myMergeSort(datas: IntArray): IntArray {
        if (datas.size < 2) return datas
        val mid = datas.size / 2
        val left = Arrays.copyOfRange(datas, 0, mid)
        val right = Arrays.copyOfRange(datas, mid, datas.size)

        return merge(myMergeSort(left), myMergeSort(right))
    }


    fun merge(left: IntArray, right: IntArray): IntArray {
        mergeStringBuilder.append("左边").append("<br/>")

        left.forEachIndexed { index, i ->
            mergeStringBuilder.append(i).append("&nbsp;&nbsp;")
        }
        mergeStringBuilder.append("<br/>")
        mergeStringBuilder.append("右边").append("<br/>")
        right.forEachIndexed { index, i ->
            mergeStringBuilder.append(i).append("&nbsp;&nbsp;")
        }
        mergeStringBuilder.append("<br/>")
        myliveData.postValue(mergeStringBuilder.toString())


        val result = IntArray(left.size + right.size)

        var l = 0
        var r = 0
        for (index in 0 until result.size) {
            //如果l超过左边最大长度取右边
            if (l >= left.size)
                result[index] = right[r++]
            //如果r超过右边最大长度取左边
            else if (r >= right.size)
                result[index] = left[l++]
            //如果左边的大于右边的取右边的
            else if (left[l] > right[r])
                result[index] = right[r++]
            //默认的取右数组
            else
                result[index] = left[l++]
        }

        mergeStringBuilder.append("归并").append("<br/>")
        result.forEachIndexed { index, i ->
            mergeStringBuilder.append(i).append("&nbsp;&nbsp;")
        }
        mergeStringBuilder.append("<br/>")
        myliveData.postValue(mergeStringBuilder.toString())
        return result
    }


//endregion



//region堆排序
    var len:Int = 0
    fun heapSort(view: View) {
        Thread(Runnable {
            var datas = (5..19).shuffled().toMutableList()
            val stringBuilder = StringBuilder("堆排序").append("<br/>")
            stringBuilder.append("原数据").append("<br/>")
            datas.forEach{
                stringBuilder.append(it).append("&nbsp;&nbsp;")
            }
            stringBuilder.append("<br/>").append("新数据").append("<br/>")
            val results = myHeapSort(datas)

            results.forEach {
                stringBuilder.append(it).append("&nbsp;&nbsp;")
            }
            myliveData.postValue(stringBuilder.toString())
        }).start()

    }



    fun myHeapSort(array: MutableList<Int>):MutableList<Int>{
        len = array.size
        if(len < 1)return array
        //1.构建一个最大堆
        buildMaxHeap(array)
        //2循环將堆首位(最大值)与末尾交换,然后再重新调整最大堆
        while (len > 0){
            swap(array , 0  , len-1)
            len--
            adjustHeap(array , 0)
        }
        return array
    }

    fun buildMaxHeap(array:MutableList<Int>){
        for(i in (len/2-1) downTo   0){
            adjustHeap(array , i)
        }
    }


    /**
     * https://www.cnblogs.com/guoyaohua/p/8600214.html
     * 原文堆排序
     * 这个地方是错的(错了错了一位)
     */
    fun adjustHeap(array: MutableList<Int> , i : Int){
        var maxIndex = i
        if(i * 2+1 < len && array[i*2+1 ] > array[maxIndex])
            maxIndex = i*2 + 1
        if(i*2 + 2 < len && array[i * 2 + 2] > array[maxIndex])
            maxIndex = i * 2 + 2
        if(maxIndex != i){
            swap(array , maxIndex , i)
            adjustHeap(array,maxIndex)
        }
    }


    //堆排序
}