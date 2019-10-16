package com.cystatusbar

import com.cystatusbar.algorithm.HeapTree
import org.junit.Test

import org.junit.Assert.*
import kotlinx.coroutines.*
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun test(){

        val datas = (5..22).shuffled().toIntArray()
        datas.forEachIndexed { index, i -> print("$i     ") }
        val heapTree = buildHeapTree(datas)
        println()
        println("----------------------------")
        heapTree.sort()

        heapTree.list.forEach{
            println(it)
        }



    }



    fun buildHeapTree(datas : IntArray):HeapTree{

        val dataIterator = datas.iterator()

        val heapTree = HeapTree(dataIterator.nextInt())

        val trees = ArrayList<HeapTree>()
        trees.add(heapTree)

        var size = 0
        var index = 0

        while(size < datas.size){
            val length = Math.pow(2.toDouble() , index.toDouble()).toInt()
            for(i in 0 until  length){
                val left : HeapTree
                if(dataIterator.hasNext()){
                    left = HeapTree(dataIterator.nextInt())
                    trees.add(left)
                    trees[size+i].left = left
                }
                val right:HeapTree
                if(dataIterator.hasNext()){
                    right = HeapTree(dataIterator.nextInt())
                    trees.add(right)
                    trees[size+i].right = right
                }
            }

            size  += length
            index++
        }
        return heapTree
    }


}
