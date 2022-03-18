package masondouglas.com.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import masondouglas.com.todolist.databinding.ActivityMainBinding
import masondouglas.com.todolist.databinding.ActivityTaskCreationBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNewTask.setOnClickListener{
            startActivity(Intent(this,taskCreationActivity::class.java))
        }

        val viewModel : taskViewModel by viewModels()
        viewModel.getTasks().observe(this) { projects ->
            binding.recycler.adapter = TaskAdapter(this, projects )
            // binding.linearLayout.children.
//            for (project in it) {
////                var newProjectTextView = TextView(this)
////                newProjectTextView.text = project.toString()
////                binding.linearLayout.addView(newProjectTextView)
//                //Log.i("DB_Response", "Inside CreateProject, project:$project")
//
//            }

        }


        }
//    }
}