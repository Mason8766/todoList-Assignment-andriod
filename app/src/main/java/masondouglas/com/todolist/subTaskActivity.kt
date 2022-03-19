package masondouglas.com.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import masondouglas.com.todolist.databinding.ActivitySubTaskBinding


class subTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubTaskBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get the course information and update the header
        val taskId = intent.getStringExtra("taskID")
        val db = FirebaseFirestore.getInstance().collection("task")
        var task = Task()
        auth = Firebase.auth
        db.whereEqualTo("id", taskId)
            .get()
            .addOnSuccessListener { querySnapShot ->
                for (document in querySnapShot) {
                    task = document.toObject(Task::class.java)
                    binding.lblTaskName.text = task.taskName
                    binding.lblDescription.text= task.description
                }
            }




        var holder = ArrayList<Task>()
        val viewModel : taskViewModel by viewModels()
        viewModel.getSubTasks(task.id).observe(this, { subTasks ->
            subTasks.filter { x -> x.parent == taskId }
            for (temp in subTasks){
                if(temp.parent == taskId)
                    holder.add(temp)
            }

            binding.recyclerSubTaskView.adapter = subTaskAdapter(this,holder)
          //  holder.clear()
        })









        binding = ActivitySubTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddSubTask.setOnClickListener {
            holder.clear()
            var taskName = binding.txtSubTaskName.text.toString().trim()
            var priority = binding.txtSubTaskPriority.text.toString().trim()
            var descrpiton = "N/A"
            var dueDate = "N/A"

            if (taskName.isNotEmpty()){

                val db = FirebaseFirestore.getInstance().collection("task")

                val id = db.document().getId()
                var subtask = Task(taskName,descrpiton,dueDate, priority,id, auth.currentUser!!.uid,true,taskId)

                db.document(id).set(subtask)
                    .addOnSuccessListener { Toast.makeText(this,"Task added", Toast.LENGTH_LONG).show() }
                    .addOnFailureListener{ Log.w("DB_Fail", it.localizedMessage)}
            }
            else{
                Toast.makeText(this, "Task name not entered", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))

        }
    }
}