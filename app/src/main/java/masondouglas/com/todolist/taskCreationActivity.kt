package masondouglas.com.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import masondouglas.com.todolist.databinding.ActivityTaskCreationBinding



class taskCreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskCreationBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.btnCreateTask.setOnClickListener {
            var taskName = binding.txtTaskName.text.toString().trim()
            var description = binding.txtDescription.text.toString().trim()
            var dueDate = binding.txtDueDate.text.toString().trim()
            var priority = binding.txtPriority.text.toString().trim()

            if (taskName.isNotEmpty()){

                val db = FirebaseFirestore.getInstance().collection("task")

                val id = db.document().getId()
                var task = Task(taskName,description,dueDate, priority,id, auth.currentUser!!.uid)
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

