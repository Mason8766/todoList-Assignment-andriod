package masondouglas.com.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

        db.whereEqualTo("id", taskId)
            .get()
            .addOnSuccessListener { querySnapShot ->
                for (document in querySnapShot) {
                    task = document.toObject(Task::class.java)
                    binding.lblTaskName.text = task.taskName
                    binding.lblDescription.text= task.description
                }
            }





        val viewModel : taskViewModel by viewModels()
        viewModel.getTasks().observe(this, { tasks ->
            binding.recyclerSubTaskView.adapter = subTaskAdapter(this,tasks)
        })










        binding = ActivitySubTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}