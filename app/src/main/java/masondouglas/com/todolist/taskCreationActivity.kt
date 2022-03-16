package masondouglas.com.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import masondouglas.com.todolist.databinding.ActivityTaskCreationBinding

class taskCreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateTask.setOnClickListener {
            var taskName = binding.txtTaskName.text.toString().trim()
            var description = binding.txtDescription.text.toString().trim()
            var dueDate = binding.txtDueDate.text.toString().trim()
            var priority = binding.txtPriority.text.toString().trim()

            if (taskName.isNotEmpty()){

                val db = FirebaseFirestore.getInstance().collection("task")

                val id = db.document().getId()
                var task = Task(taskName,description,dueDate, priority,id)
                db.document(id).set(task)
                    .addOnSuccessListener { Toast.makeText(this,"Task added", Toast.LENGTH_LONG).show() }
                    .addOnFailureListener{ Log.w("DB_Fail", it.localizedMessage)}
            }
            else{
                Toast.makeText(this, "Task name not entered", Toast.LENGTH_LONG).show()
            }
        }
    }
}