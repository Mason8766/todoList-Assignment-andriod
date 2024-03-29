package masondouglas.com.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import masondouglas.com.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskAdapter.taskItemListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNewTask.setOnClickListener{
            startActivity(Intent(this,taskCreationActivity::class.java))
        }

        binding.btnWeekly.setOnClickListener {
            startActivity(Intent(this,weeklyActivity::class.java))
        }


        val viewModel : taskViewModel by viewModels()
        viewModel.getTasks().observe(this) { tasks ->
            viewModel.getSubTasks().observe(this){subtask ->
                val tasksr = tasks.reversed()
                binding.recycler.adapter = TaskAdapter(this, tasksr,subtask,this )
            }


        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item1){
        startActivity(Intent(this,taskCreationActivity::class.java))
            return true
        }else{
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this,signinActivity::class.java))
                }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun taskSelected(task: Task) {
        var intent = Intent(this, subTaskActivity::class.java)
        intent.putExtra("taskID", task.id)
        startActivity(intent)
    }
//    }
}