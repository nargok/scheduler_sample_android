package com.example.myscheduler

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ScheduleAdapter(data: OrderedRealmCollection<Schedule>):
    RealmRecyclerViewAdapter<Schedule, ScheduleAdapter.ViewHolder>(data, true) {

    // 関数型の宣言なので、Unitは省略できない. Unitは戻り値がないことを意味する
    private var listener: ((Long?) -> Unit)? = null

    fun setOnItemClickListener(listener: (Long?)-> Unit ) {
        this.listener = listener
    }

    // 高速表示化対応:データ内の1つの項目を指ししめすために固有のIDを使う
    init {
        setHasStableIds(true)
    }

    // セルに表示するviewを保持するもの
    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val date: TextView = cell.findViewById(android.R.id.text1)
        val title: TextView = cell.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        // LayoutInflaterクラスのインスタンスを取得する
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        val schedule: Schedule? = getItem(position)
        holder.date.text = DateFormat.format("yyyy/MM/dd", schedule?.date)
        holder.title.text = schedule?.title
        holder.itemView.setOnClickListener {
            // invoke -> 関数型の変数を実行する特殊メソッド
            listener?.invoke(schedule?.id)
        }
    }

    // 高速表示対応:更新位置を特定するための固有のIDを返す
    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }
}