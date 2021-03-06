package cn.zhouwl.sleeptime

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.Toast
import cn.zhouwl.sleeptime.adapter.NoteAdapter

import java.util.Calendar
import java.util.Date

import cn.zhouwl.sleeptime.api.RxService
import cn.zhouwl.sleeptime.api.SleepApi
import cn.zhouwl.sleeptime.entity.NoteResult
import cn.zhouwl.sleeptime.entity.Result
import cn.zhouwl.sleeptime.entity.Sleep
import cn.zhouwl.sleeptime.util.DateUtils
import cn.zhouwl.sleeptime.view.EventCellView
import com.p_v.flexiblecalendar.FlexibleCalendarView
import com.p_v.flexiblecalendar.view.BaseCellView
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.properties.Delegates
import android.widget.EditText



class MainActivity : AppCompatActivity() {

    lateinit var mListView: ListView
    lateinit var mSleepApi: SleepApi
    lateinit var calendarView: FlexibleCalendarView
    var sleepList: List<Sleep>? = null
    var noteList: List<NoteResult.Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mListView = findViewById(R.id.sleepListView) as ListView
        calendarView = findViewById(R.id.calendar_view) as FlexibleCalendarView
        calendarView.setMonthViewHorizontalSpacing(0)
        calendarView.setMonthViewVerticalSpacing(0)

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
                val sleepDate = Date(it.sleep_time - 3 * 3600 * 1000)
                val calendar = Calendar.getInstance()
                calendar.setTime(sleepDate)
                if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                    var sleepText = "睡觉:${DateUtils.getTimeFromtimestamp(it.sleep_time)}"
                    var okiText = it.okiTime.let {
                        if (it > 0)
                            "起床:${DateUtils.getTimeFromtimestamp(it)}"
                        else
                            null
                    }
                    var sleepLengthText: String? = null

                    if (it.okiTime > 0) {
                        val sleepLength = it.okiTime - it.sleep_time
                        val hour = sleepLength / 3600000
                        val minute = sleepLength % 3600000 / 60000
                        sleepLengthText = ""
                        if (hour > 0) {
                            sleepLengthText += hour.toString() + "小时"
                        }
                        sleepLengthText += minute.toString() + "分"
                    }
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                    cal.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                    cal.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
                    cal.set(Calendar.HOUR_OF_DAY, 24)
                    cal.set(Calendar.SECOND, 0)
                    cal.set(Calendar.MINUTE, 0)
                    cal.set(Calendar.MILLISECOND, 0)
                    return@setEventDataProvider listOf(EventCellView.SleepEvent(sleepText, okiText, sleepLengthText, it.sleep_time > cal.timeInMillis ))
                }
            }
            null
        }
        supportActionBar?.title = "${calendarView.currentYear}年${calendarView.currentMonth + 1}月"
        calendarView.setOnMonthChangeListener { year, month, direction ->
            supportActionBar?.title = "${year}年${month + 1}月"
        }
        mSleepApi = RxService.createApi(SleepApi::class.java)
        getSleepData()
        getNoteData()
        //postNote("zhouwl", "周文亮哈哈");
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
                        calendarView.refresh()
                        calendarView.invalidate()
                    }
                })
    }

    fun getNoteData() {
        mSleepApi.getNote("zhouwl")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ noteResult ->
                    if (noteResult.code == 1) {
                        noteList = noteResult.data
                        val noteAdapter = NoteAdapter(noteList)
                        mListView.adapter = noteAdapter
                    }
                }, { exception -> exception.printStackTrace() })
    }

    fun postNote(username: String, content: String) {
        mSleepApi.postNote(username, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result -> getNoteData()}, {e-> e.printStackTrace()})
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sleep -> mSleepApi.sleep("zhouwl", System.currentTimeMillis())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Result> {
                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onNext(result: Result) {
                            if (result.code == 1) {
                                getSleepData()
                            } else {
                                Toast.makeText(this@MainActivity, result.errorMessage, 2000).show()
                            }
                        }
                    })
            R.id.oki -> mSleepApi.oki("zhouwl", System.currentTimeMillis())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<Result> {
                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }

                        override fun onNext(result: Result) {
                            if (result.code == 1) {
                                getSleepData()
                            } else {
                                Toast.makeText(this@MainActivity, result.errorMessage, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
            R.id.post_note -> {
                var builder = AlertDialog.Builder(this)
                builder.setTitle("记录");
                val editText = EditText(this)
                editText.id = 0xFF4011
                builder.setView(editText, 20, 20, 20, 20)
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定") {
                    dialog, which ->
                        postNote("zhouwl", editText.text.toString())
                }
                builder.show();
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
