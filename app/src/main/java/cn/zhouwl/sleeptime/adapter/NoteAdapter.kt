package cn.zhouwl.sleeptime.adapter

import android.support.v4.util.TimeUtils
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import cn.zhouwl.sleeptime.R
import cn.zhouwl.sleeptime.entity.NoteResult
import cn.zhouwl.sleeptime.entity.Sleep
import cn.zhouwl.sleeptime.util.DateUtils

class NoteAdapter(private val noteList: List<NoteResult.Note>?) : BaseAdapter() {

    public override fun getCount(): Int {
        return if (noteList == null) 0 else noteList!!.size
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
            view = View.inflate(viewGroup.getContext(), R.layout.note_list_item, null)
            holder = ViewHolder()
            holder.noteTextView = view!!.findViewById(R.id.note) as TextView
            holder.timeTextView = view!!.findViewById(R.id.time) as TextView
            view!!.setTag(holder)
        } else {
            holder = view!!.getTag() as ViewHolder
        }
        holder.noteTextView.text = noteList!!.get(i).content
        holder.timeTextView.text = (DateUtils.timestampToDate(noteList!!.get(i).time!!))

        return view
    }


    inner class ViewHolder {
        lateinit var noteTextView: TextView
        lateinit var timeTextView: TextView
    }
}
