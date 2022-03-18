package masondouglas.com.todolist

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class TaskAdapter(val context: Context,
                  val tasks : List<Task>,
                         val itemListener : taskItemListener
                  ) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

                      inner class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                          val taskTextView = itemView.findViewById<TextView>(R.id.lblTask)
                          val priorityTextView = itemView.findViewById<TextView>(R.id.lblPriority)
                          val dateTextView = itemView.findViewById<TextView>(R.id.lblDate)
                          //Log.i("DB_Response", "inside CreateTask, IM IN THE SYSTEM2!")
                      }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
        Log.i("DB_Response", "inside CreateTask, IM IN THE SYSTEM3!")
    }

    override fun onBindViewHolder(viewHolder: TaskAdapter.TaskViewHolder, position: Int) {
        val task = tasks[position]
        with(viewHolder){
            taskTextView.text = task.taskName
            priorityTextView.text = task.priority
            dateTextView.text = task.dueDate
            itemView.setOnClickListener {
                itemListener.taskSelected(task)
            }


            when(task.priority) {
                "1" -> priorityTextView.setTextColor(Color.parseColor("#FF0000"))
                "2" -> priorityTextView.setTextColor(Color.parseColor("#FF4700"))
                "3" -> priorityTextView.setTextColor(Color.parseColor("#FF9900"))
                "8" -> priorityTextView.setTextColor(Color.parseColor("#8CFF00"))
                "9" ->priorityTextView.setTextColor(Color.parseColor("#4CFF00"))
                "10" ->priorityTextView.setTextColor(Color.parseColor("#0AC100"))
            }

//            if(task.priority!! == "2")
//                priorityTextView.setTextColor(Color.BLUE)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    interface taskItemListener{
        fun taskSelected(task : Task)
    }


}

