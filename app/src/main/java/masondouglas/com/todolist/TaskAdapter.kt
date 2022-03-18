package masondouglas.com.todolist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class TaskAdapter(val context: Context,
                  val tasks : List<Task>
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

        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


}

