package com.etc.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.etc.R
import com.etc.bean.AgentOperationBean

/**
 * @Description: (用一句话描述)
 * @author Xia燚
 * @date 2022/11/15 11:13
 */
class AgentAdapter (var context: Context, var data: List<AgentOperationBean>) : BaseAdapter() {


    override fun getCount(): Int= data.size

    override fun getItem(position: Int) = data[position].name

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val holder: TestViewHolder
        val view: View

        if(convertView==null){
            view = View.inflate(context, R.layout.item_spinner,null)
            holder = TestViewHolder(view)
            view.tag = holder
        }else {
            view = convertView
            holder = view.tag as TestViewHolder
        }

        holder.tv.text = data[position].name
        holder.tv.textSize =20f
        return view


    }


    class TestViewHolder(var view: View){
        var tv: TextView = view.findViewById(R.id.item_pc_tv)
    }

}