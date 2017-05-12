package cn.zhouwl.sleeptime.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtils {

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014年06月14日16时09分00秒"）返回时间戳

     * @param time
     * *
     * @return
     */
    fun data(time: String): String? {
        val sdr = SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒",
                Locale.CHINA)
        val date: Date
        var times: String? = null
        try {
            date = sdr.parse(time)
            val l = date.time
            val stf = l.toString()
            times = stf.substring(0, 10)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return times
    }

    // 并用分割符把时间分成时间数组
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14-16-09-00"）

     * @param time
     * *
     * @return
     */
    fun timesOne(time: String): String {
        val sdr = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
        val lcc = java.lang.Long.valueOf(time)!!
        val i = Integer.parseInt(time)
        val times = sdr.format(Date(i * 1000L))
        return times

    }

    /**
     * 分割符把时间分成时间数组

     * @param time
     * *
     * @return
     */
    fun division(time: String): Array<String> {

        val fenge = time.split("[年月日时分秒]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        return fenge

    }

    /**
     * 输入日期如（2014年06月14日16时09分00秒）返回（星期数）

     * @param time
     * *
     * @return
     */
    fun week(time: String): String? {
        var date: Date? = null
        val sdr = SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒")
        var mydate = 0
        var week: String? = null
        try {
            date = sdr.parse(time)
            val cd = Calendar.getInstance()
            cd.time = date
            mydate = cd.get(Calendar.DAY_OF_WEEK)
            // 获取指定日期转换成星期几
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        if (mydate == 1) {
            week = "星期日"
        } else if (mydate == 2) {
            week = "星期一"
        } else if (mydate == 3) {
            week = "星期二"
        } else if (mydate == 4) {
            week = "星期三"
        } else if (mydate == 5) {
            week = "星期四"
        } else if (mydate == 6) {
            week = "星期五"
        } else if (mydate == 7) {
            week = "星期六"
        }
        return week
    }

    /**
     * 输入日期如（2014-06-14-16-09-00）返回（星期数）

     * @param time
     * *
     * @return
     */
    fun weekOne(time: String): String? {
        var date: Date? = null
        val sdr = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
        var mydate = 0
        var week: String? = null
        try {
            date = sdr.parse(time)
            val cd = Calendar.getInstance()
            cd.time = date
            mydate = cd.get(Calendar.DAY_OF_WEEK)
            // 获取指定日期转换成星期几
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        if (mydate == 1) {
            week = "星期日"
        } else if (mydate == 2) {
            week = "星期一"
        } else if (mydate == 3) {
            week = "星期二"
        } else if (mydate == 4) {
            week = "星期三"
        } else if (mydate == 5) {
            week = "星期四"
        } else if (mydate == 6) {
            week = "星期五"
        } else if (mydate == 7) {
            week = "星期六"
        }
        return week
    }

    companion object {

        val todayDateTime: String
            get() {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault())
                return format.format(Date())
            }

        val todayDateTimes: String
            get() {
                val format = SimpleDateFormat("MM月dd日",
                        Locale.getDefault())
                return format.format(Date())
            }

        /**
         * 获取当前时间

         * @return
         */
        val currentTime_Today: String
            get() {
                val sdf = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
                return sdf.format(java.util.Date())
            }

        /**
         * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳

         * @param time
         * *
         * @return
         */
        fun dataOne(time: String): String? {
            val sdr = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                    Locale.CHINA)
            val date: Date
            var times: String? = null
            try {
                date = sdr.parse(time)
                val l = date.time
                val stf = l.toString()
                times = stf.substring(0, 10)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return times
        }

        fun getTimestamp(time: String, type: String): String? {
            val sdr = SimpleDateFormat(type, Locale.CHINA)
            val date: Date
            var times: String? = null
            try {
                date = sdr.parse(time)
                val l = date.time
                val stf = l.toString()
                times = stf.substring(0, 10)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return times
        }

        /**
         * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）

         * @param time
         * *
         * @return
         */
        operator fun times(time: String): String {
            val sdr = SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            return times

        }

        fun timestampToDate(time: Long): String {
            val sdr = SimpleDateFormat("yyyy年MM月dd日HH时mm分")
            val times = sdr.format(Date(time))
            return times

        }


        fun getDayFromtimestamp(time: Long): String {
            val sdr = SimpleDateFormat("yyyy年MM月dd日")
            val times = sdr.format(Date(time))
            return times
        }

        fun getTimeFromtimestamp(time: Long): String {
            val sdr = SimpleDateFormat("HH:mm")
            val times = sdr.format(Date(time))
            return times
        }

        /**
         * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014-06-14  16:09:00"）

         * @param time
         * *
         * @return
         */
        fun timedate(time: String): String {
            val sdr = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            return times

        }

        /**
         * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16:09"）

         * @param time
         * *
         * @return
         */
        fun timet(time: String): String {
            val sdr = SimpleDateFormat("yyyy年MM月dd日  HH:mm")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            return times
        }

        /**
         * @param time斜杠分开
         * *
         * @return
         */
        fun timeslashData(time: String): String {
            val sdr = SimpleDateFormat("yyyy/MM/dd")
            val lcc = java.lang.Long.valueOf(time)!!
            //      int i = Integer.parseInt(time);
            val times = sdr.format(Date(lcc * 1000L))
            return times

        }

        /**
         * @param time斜杠分开
         * *
         * @return
         */
        fun timeMinute(time: String): String {
            val sdr = SimpleDateFormat("HH:mm")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            return times

        }

        fun tim(time: String): String {
            val sdr = SimpleDateFormat("yyyyMMdd HH:mm")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            return times
        }

        fun time(time: String): String {
            val sdr = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            return times
        }

        // 调用此方法输入所要转换的时间戳例如（1402733340）输出（"2014年06月14日16时09分00秒"）
        operator fun times(timeStamp: Long): String {
            val sdr = SimpleDateFormat("MM月dd日  #  HH:mm")
            var week = getWeek(timeStamp)
            return sdr.format(Date(timeStamp)).replace("#", week!!, false)

        }

        private fun getWeek(timeStamp: Long): String? {
            var mydate = 0
            var week: String? = null
            val cd = Calendar.getInstance()
            cd.time = Date(timeStamp)
            mydate = cd.get(Calendar.DAY_OF_WEEK)
            // 获取指定日期转换成星期几
            if (mydate == 1) {
                week = "周日"
            } else if (mydate == 2) {
                week = "周一"
            } else if (mydate == 3) {
                week = "周二"
            } else if (mydate == 4) {
                week = "周三"
            } else if (mydate == 5) {
                week = "周四"
            } else if (mydate == 6) {
                week = "周五"
            } else if (mydate == 7) {
                week = "周六"
            }
            return week
        }

        fun timesTwo(time: String): String {
            val sdr = SimpleDateFormat("yyyy-MM-dd")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            return times

        }

        /**
         * 并用分割符把时间分成时间数组

         * @param time
         * *
         * @return
         */
        fun timestamp(time: String): Array<String> {
            val sdr = SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            val fenge = times.split("[年月日时分秒]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return fenge
        }

        /**
         * 根据传递的类型格式化时间

         * @param str
         * *
         * @param type
         * *            例如：yy-MM-dd
         * *
         * @return
         */
        fun getDateTimeByMillisecond(str: String, type: String): String {

            val date = Date(java.lang.Long.valueOf(str)!!)

            val format = SimpleDateFormat(type)

            val time = format.format(date)

            return time
        }

        /**
         * 输入时间戳变星期

         * @param time
         * *
         * @return
         */
        fun changeweek(time: String): String? {
            val sdr = SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            var date: Date? = null
            var mydate = 0
            var week: String? = null
            try {
                date = sdr.parse(times)
                val cd = Calendar.getInstance()
                cd.time = date
                mydate = cd.get(Calendar.DAY_OF_WEEK)
                // 获取指定日期转换成星期几
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            if (mydate == 1) {
                week = "星期日"
            } else if (mydate == 2) {
                week = "星期一"
            } else if (mydate == 3) {
                week = "星期二"
            } else if (mydate == 4) {
                week = "星期三"
            } else if (mydate == 5) {
                week = "星期四"
            } else if (mydate == 6) {
                week = "星期五"
            } else if (mydate == 7) {
                week = "星期六"
            }
            return week

        }

        /**
         * 获取日期和星期　例如：２０１４－１１－１３　１１:００　星期一

         * @param time
         * *
         * @param type
         * *
         * @return
         */
        fun getDateAndWeek(time: String, type: String): String {
            return getDateTimeByMillisecond(time + "000", type) + "  " + changeweekOne(time)
        }

        /**
         * 输入时间戳变星期

         * @param time
         * *
         * @return
         */
        fun changeweekOne(time: String): String? {
            val sdr = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
            val lcc = java.lang.Long.valueOf(time)!!
            val i = Integer.parseInt(time)
            val times = sdr.format(Date(i * 1000L))
            var date: Date? = null
            var mydate = 0
            var week: String? = null
            try {
                date = sdr.parse(times)
                val cd = Calendar.getInstance()
                cd.time = date
                mydate = cd.get(Calendar.DAY_OF_WEEK)
                // 获取指定日期转换成星期几
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            if (mydate == 1) {
                week = "星期日"
            } else if (mydate == 2) {
                week = "星期一"
            } else if (mydate == 3) {
                week = "星期二"
            } else if (mydate == 4) {
                week = "星期三"
            } else if (mydate == 5) {
                week = "星期四"
            } else if (mydate == 6) {
                week = "星期五"
            } else if (mydate == 7) {
                week = "星期六"
            }
            return week

        }

        /**
         * 获取当前时间

         * @return
         */
        val currentTime: String
            get() {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
                return sdf.format(java.util.Date())
            }
    }
}
