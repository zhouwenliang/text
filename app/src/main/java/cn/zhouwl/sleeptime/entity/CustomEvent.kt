package cn.zhouwl.sleeptime.entity

import com.p_v.flexiblecalendar.entity.Event

/**
 * @author p-v
 */
class CustomEvent(private val color: Int) : Event {

    override fun getColor(): Int {
        return color
    }
}
