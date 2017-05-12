package cn.zhouwl.sleeptime.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import cn.zhouwl.sleeptime.entity.Sleep

import com.p_v.flexiblecalendar.entity.Event
import com.p_v.flexiblecalendar.view.BaseCellView

class EventCellView : BaseCellView {

    private lateinit var mPaint: Paint
    private lateinit var mTextPaint: Paint
    private var mEventCount: Int = 0
    private var eventCircleY: Int = 0
    private var eventCircleX: Int = 0
    private var mTextY: Int = 0

    private var radius: Int = 0
    private var eventTextColor: Int = 0
    private var eventBackground: Int = 0
    private var eventTextSize: Int = 0
    private var sleepText: String? = null
    private var okiText: String? = null
    private var sleepLengthText: String? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, com.p_v.fliexiblecalendar.R.styleable.EventCountCellView)
        try {
            radius = a.getDimension(com.p_v.fliexiblecalendar.R.styleable.EventCountCellView_event_count_radius, 15f).toInt()
            eventBackground = a.getColor(com.p_v.fliexiblecalendar.R.styleable.EventCountCellView_event_background,
                    resources.getColor(android.R.color.black))
            eventTextColor = a.getColor(com.p_v.fliexiblecalendar.R.styleable.EventCountCellView_event_count_text_color,
                    resources.getColor(android.R.color.white))
            eventTextSize = a.getDimension(com.p_v.fliexiblecalendar.R.styleable.EventCountCellView_event_text_size, -1f).toInt()
        } finally {
            a.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mEventCount > 0) {
            val p = Paint()
            p.textSize = textSize

            val rect = Rect()
            p.getTextBounds("31", 0, 1, rect) // measuring using fake text

            eventCircleY = 20
            eventCircleX = 20

            mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mTextPaint.style = Paint.Style.FILL
            mTextPaint.setTextSize(if (eventTextSize == -1) textSize / 2 else eventTextSize.toFloat())
            mTextPaint.color = eventTextColor
            mTextPaint.textAlign = Paint.Align.CENTER

            mTextY = eventCircleY + radius / 2
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!TextUtils.isEmpty(sleepText) && mPaint != null && mTextPaint != null) {
            canvas.drawText(sleepText, 80f, 25f, mTextPaint)
            okiText?.apply {
                canvas.drawText(okiText, 80f, 50f, mTextPaint)
            }
            sleepLengthText?.apply {
                canvas.drawText(sleepLengthText, 80f, 120f, mTextPaint)
            }
        }
    }

    override fun setEvents(eventList: List<Event>?) {
        if (eventList != null && !eventList.isEmpty()) {
            mEventCount = eventList.size
            for (item in eventList) {
                val sleepEvent = item as SleepEvent
                sleepText = sleepEvent.sleepText
                okiText = sleepEvent.okiText
                sleepLengthText = sleepEvent.sleepLength
            }
            mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mPaint.style = Paint.Style.FILL
            mPaint.color = eventBackground
            invalidate()
            requestLayout()
        }
    }

    class SleepEvent(val sleepText: String, val okiText: String? = null, val sleepLength: String? = null) : Event {
        var eventCorlor: Int = 0xffffff
        override fun getColor(): Int {
            return eventCorlor
        }

    }
}
