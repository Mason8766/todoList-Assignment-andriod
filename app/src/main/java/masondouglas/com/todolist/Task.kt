package masondouglas.com.todolist

import java.time.LocalDate


//the class that holds the task object
data class Task(
    var taskName: String? = null,
    var description: String? = null,
    var dueDate: String? = null,
    var priority: Int? = null,
    var id: String? = null,
    var userId: String? = null,
    var isSubTask: Boolean = false,
    var parent: String? = null


)
{
    override
    fun toString() : String{
        taskName.let {return taskName!!}
        return "not defined"
    }
}