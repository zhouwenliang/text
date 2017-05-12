package cn.zhouwl.sleeptime.adapter

import android.support.v4.util.TimeUtils
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import cn.zhouwl.sleeptime.R
import cn.zhouwl.sleeptime.entity.Sleep
import cn.zhouwl.sleeptime.util.DateUtils

class SleepAdapter(private val sleepList: List<Sleep>?) : BaseAdapter() {

    public override fun getCount(): Int {
        return if (sleepList == null) 0 else sleepList!!.size
    }

    public override fun getItem(i: Int): Any? {
        return null
    }

    public override fun getItemId(i: Int): Long {
        return 0
    }

    public override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.sleep_list_item, null)
            holder = ViewHolder()
            holder.dateTextView = view!!.findViewById(R.id.date) as TextView
            holder.sleepTimeTextView = view!!.findViewById(R.id.sleepTime) as TextView
            holder.okiTimeTextView = view!!.findViewById(R.id.okiTime) as TextView
            holder.sleepLengthTextView = view!!.findViewById(R.id.sleepLength) as TextView
            holder.reasonTextView = view!!.findViewById(R.id.reason) as TextView
            view!!.setTag(holder)
        } else {
            holder = view!!.getTag() as ViewHolder
        }
        holder.dateTextView.setText(DateUtils.getDayFromtimestamp(sleepList!!.get(i).sleep_time))
        holder.sleepTimeTextView.setText("睡觉时间:" + DateUtils.getTimeFromtimestamp(sleepList!!.get(i).sleep_time))
        if (sleepList!!.get(i).okiTime == 0L) {
            holder.okiTimeTextView.setVisibility(View.GONE)
            holder.sleepLengthTextView.setVisibility(View.GONE)
        } else {
            holder.okiTimeTextView.setVisibility(View.VISIBLE)
            holder.okiTimeTextView.setText("起床时间:" + DateUtils.getTimeFromtimestamp(sleepList!!.get(i).okiTime))
            holder.sleepLengthTextView.setVisibility(View.VISIBLE)
            val sleepLength = sleepList.get(i).okiTime - sleepList.get(i).sleep_time
            val hour = sleepLength / 3600000
            val minute = sleepLength % 3600000 / 60000
            var sleepLengthText = ""
            if (hour > 0) {
                sleepLengthText += hour.toString()  + "小时"
            }
            sleepLengthText += minute.toString() + "分"

            holder.sleepLengthTextView.setText("睡觉时长:" + sleepLengthText)
        }
        if (TextUtils.isEmpty(sleepList.get(i).reason)) {
            holder.reasonTextView.setText("正常睡觉")
        } else {
            holder.reasonTextView.setText(sleepList!!.get(i).reason)
        }

        return view
    }


    inner class ViewHolder {
        lateinit var dateTextView: TextView
        lateinit var sleepTimeTextView: TextView
        lateinit var okiTimeTextView: TextView
        lateinit var sleepLengthTextView: TextView
        lateinit var reasonTextView: TextView
    }
}
