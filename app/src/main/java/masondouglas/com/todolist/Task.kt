package masondouglas.com.todolist

import java.util.*

data class Task(
    var taskName  : String? = null,
    var description : String? = null,
    var dueDate : String? = null,
    var priority : String? = null,
    var id : String? = null
   // var uId : String? = null,

)
{
    override
    fun toString() : String{
        taskName.let {return taskName!!}
        return "not defined"
    }
}