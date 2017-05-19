package cn.zhouwl.sleeptime.entity

/**
 * Created by NetEase on 2017/5/19 0019.
 */
class NoteResult: Result() {

    var data: List<Note>? = null

    class Note {
        var id: Int? = 0
        var content: String? = null
        var username: String? = null
        var time: Long? = 0
    }
}