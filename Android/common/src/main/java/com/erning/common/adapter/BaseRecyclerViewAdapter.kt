package com.erning.common.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T>(activity: Activity,var data:ArrayList<T>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = data.size

    fun getItem(position:Int) = data[position]
    fun addData(item:T) {
        data.add(item)
        notifyItemInserted(data.lastIndex)
    }
    fun addData(index:Int,item:T) {
        data.add(index,item)
        notifyItemInserted(index)
    }
    fun addData(items:List<T>) {
        if(items.isEmpty())
            return
        data.addAll(items)
        notifyItemRangeInserted(data.size-items.size,items.size)
    }
    fun setData(items:List<T>) {
        data = items as ArrayList<T>
        notifyDataSetChanged()
    }
    fun removeAll(){
        data.clear()
        notifyDataSetChanged()
    }
    fun remove(index:Int) {
        if (data.size >= index){
            data.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


}