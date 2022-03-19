package masondouglas.com.todolist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Tasks

public class subTaskAdapter(val context: Context,
                            val tasks: List<Task>
                            ) : RecyclerView.Adapter<subTaskAdapter.subTaskViewHolder>(){

    inner class subTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val subTaskTextView = itemView.findViewById<TextView>(R.id.lblTask)
        val subPriorityTextView = itemView.findViewById<TextView>(R.id.lblPriority)
        //val dateTextView = itemView.findViewById<TextView>(R.id.lblDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): subTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_task, parent, false)
        return subTaskViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: subTaskViewHolder, position: Int) {
        val subtask = tasks[position]
        with(viewHolder){
            subTaskTextView.text = subtask.taskName
            subPriorityTextView.text = subtask.priority
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}
