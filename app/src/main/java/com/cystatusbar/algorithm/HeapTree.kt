package com.cystatusbar.algorithm

import java.util.*


/**
 * 二叉树的排序
 * 有坑
 * 不知道在哪里
 * (二叉树   只考虑左右对称情况,但是运行起来不对成的好像也是对的)
 *
 * @date 2019-9-28
 * @author caoyu
 */
class HeapTree(var num: Int, var left: HeapTree? = null, var right: HeapTree? = null) {

    val list = LinkedList<Int>()

    /**
     * 将最大的点取出
     * 再取剩余的最大值放在顶结点
     */
    fun sort() {
        biggest()

        val last = getLastChild()
        exchange(last)
        list.addFirst(last.num)
        last.num = -1
        if(hasChild()){
            sort()
        }else{
            list.addFirst(this.num)
        }

    }


    /**
     * 將最大数字赋值给顶节点
     */
    fun biggest(){
        if(!hasChild())return

        if(right != null){
            if(right?.num != -1){
                right?.let {
                    it.biggest()
                    if(it == compareBigOne(it)){
                        exchange(it)
                    }
                }
            }
        }

        if(left!= null && left?.num != -1){
            left?.let {
                it.biggest()
                if(it == compareBigOne(it)){
                    exchange(it)
                }
            }
        }

    }




    /**
     * 交换数字
     */
    private fun exchange(heapTree: HeapTree?) {
        if (heapTree == null) return
        val temp = this.num
        this.num = heapTree.num
        heapTree.num = temp
    }


    /**
     * 跟一个树节点对比返回数值大的那个
     */

    private fun compareBigOne(heapTree: HeapTree): HeapTree =
        if(num > heapTree.num) this else heapTree


    /**
     * 是否含有子节点
     */
    fun hasChild(): Boolean {
        return hasChildLeft() || hasChildRight()
    }

    /**
     * 是否含有左子节点
     */
    fun hasChildLeft(): Boolean {
        if(left != null && left?.num != -1){
            return true
        }
        return false
    }

    /**
     * 是否含有右子节点
     */
    fun hasChildRight(): Boolean {
        if(right != null && right?.num != -1)
            return true
        return false
    }

    /**
     * 获取最后一个子节点
     */
    fun getLastChild(): HeapTree {
        if(hasChildRight()){
            right?.let { return it.getLastChild() }
        }else if(hasChildLeft()){
            left?.let { return it.getLastChild() }
        }
        return this
    }

}