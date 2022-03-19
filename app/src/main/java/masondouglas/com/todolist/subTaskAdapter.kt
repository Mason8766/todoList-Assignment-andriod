package masondouglas.com.todolist

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

public class subTaskAdapter(val context: Context,
                            val tasks: List<Task>
                            ) : RecyclerView.Adapter<subTaskAdapter.subTaskViewHolder>(){

    inner class subTaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val subTaskTextView = itemView.findViewById<TextView>(R.id.lblTask)
        val subPriorityTextView = itemView.findViewById<TextView>(R.id.lblPriority)
        val chxBox = itemView.findViewById<CheckBox>(R.id.cbxComplete)
        //val dateTextView = itemView.findViewById<TextView>(R.id.lblDate)
        val upArrow = itemView.findViewById<ImageView>(R.id.btnUp)
        val downArrow = itemView.findViewById<ImageView>(R.id.btnDown)

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

            var flag = 0
            var priHolder = 0
            val db = FirebaseFirestore.getInstance().collection("task")
            upArrow.setOnClickListener {
                priHolder = Integer.parseInt(subtask.priority)

                priHolder += 1

                if(priHolder > 10 || priHolder < 1)
                    priHolder = 5
                subtask.id?.let { it1 ->
                    db.document(it1)
                        .update("priority", priHolder.toString())
                }


            }

            downArrow.setOnClickListener {
                priHolder = Integer.parseInt(subtask.priority)

                priHolder -= 1

                if(priHolder > 10 || priHolder < 1)
                    priHolder = 5
                subtask.id?.let { it1 ->
                    db.document(it1)
                        .update("priority", priHolder.toString())
                }

            }
            chxBox.setOnClickListener {

                subtask.id?.let { it1 ->
                    db.document(it1)
                        .delete()
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DB_DELETE COMPLETE") }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
                }
                subTaskTextView.setTextColor(Color.parseColor("#FF0000"))

            }
            if (flag == 1)
                chxBox.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}
