package cn.zhouwl.sleeptime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.Toast

import com.prolificinteractive.materialcalendarview.MaterialCalendarView

import java.util.Calendar
import java.util.Date

import cn.zhouwl.sleeptime.adapter.SleepAdapter
import cn.zhouwl.sleeptime.api.RxService
import cn.zhouwl.sleeptime.api.SleepApi
import cn.zhouwl.sleeptime.entity.Sleep
import cn.zhouwl.sleeptime.util.DateUtils
import cn.zhouwl.sleeptime.view.EventCellView
import com.p_v.flexiblecalendar.FlexibleCalendarView
import com.p_v.flexiblecalendar.view.BaseCellView
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var mListView: ListView
    lateinit var mSleepApi: SleepApi
    lateinit var calendarView: FlexibleCalendarView
    var sleepList: List<Sleep>? = null
    //MaterialCalendarView materialCalendarView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mListView = findViewById(R.id.sleepListView) as ListView
        //        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        //        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        //        materialCalendarView.setSelectionColor(0xFF0000FF);
        //        materialCalendarView.setDateSelected(new Date(), true);
        //        materialCalendarView.setDateSelected(new Date(2017, 5, 5), true);
        calendarView = findViewById(R.id.calendar_view) as FlexibleCalendarView
        calendarView.setMonthViewHorizontalSpacing(1)
        calendarView.setMonthViewVerticalSpacing(1)

        calendarView.setCalendarView(object : FlexibleCalendarView.CalendarView {
            override fun getCellView(position: Int, convertView: View?, parent: ViewGroup, cellType: Int): BaseCellView {
                var cellView: BaseCellView? = convertView as? BaseCellView
                if (cellView == null) {
                    val inflater = LayoutInflater.from(this@MainActivity)
                    cellView = inflater.inflate(R.layout.calendar3_date_cell_view, null) as BaseCellView
                }
                return cellView!!
            }

            override fun getWeekdayCellView(position: Int, convertView: View?, parent: ViewGroup): BaseCellView {
                var cellView: BaseCellView? = convertView as? BaseCellView
                if (cellView == null) {
                    val inflater = LayoutInflater.from(this@MainActivity)
                    cellView = inflater.inflate(R.layout.calendar3_week_cell_view, null) as BaseCellView
                }
                return cellView!!
            }

            override fun getDayOfWeekDisplayValue(dayOfWeek: Int, defaultValue: String): String? {
                return null
            }
        })

        calendarView.setEventDataProvider { year, month, day ->
            sleepList?.forEach  {
                val sleepDate = Date(it.sleep_time)
                val calendar = Calendar.getInstance()
                calendar.setTime(sleepDate)
                if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                    var sleepText = "睡觉:" + DateUtils.getTimeFromtimestamp(it.sleep_time)
                    var okiText = it.okiTime.let {
                        if (it > 0)
                            "起床:" + DateUtils.getTimeFromtimestamp(it)
                        else
                            null
                    }
                    return@setEventDataProvider listOf(EventCellView.SleepEvent(sleepText, okiText))
                }
            }
            null
        }
        //calendarView.setEventDataProvider { year, month, day -> getEvents(year, month, day) }
        mSleepApi = RxService.createApi(SleepApi::class.java)
        getSleepData()
    }

    fun getSleepData() {
        mSleepApi.getSleep("zhouwl")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Sleep>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onNext(sleeps: List<Sleep>) {
                        sleepList = sleeps
                        val sleepAdapter = SleepAdapter(sleeps)
                        mListView.adapter = sleepAdapter
                        calendarView.refresh()
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        /*
         * 将actionBar的HomeButtonEnabled设为ture，
         *
         * 将会执行此case
         */
            R.id.sleep -> mSleepApi.sleep("zhouwl", System.currentTimeMillis())
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Void> {
                        override fun onCompleted() {
                            getSleepData()
                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onNext(aVoid: Void) {

                        }
                    })
            R.id.oki -> mSleepApi.oki("zhouwl", System.currentTimeMillis())
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Void> {
                        override fun onCompleted() {
                            getSleepData()
                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onNext(aVoid: Void) {

                        }
                    })
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
