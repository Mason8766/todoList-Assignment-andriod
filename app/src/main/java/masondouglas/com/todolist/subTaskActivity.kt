package masondouglas.com.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
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
        //gets the task info, and updates the lables
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



        //gets the subtask item, and puts it in the recylcer

        var holder = ArrayList<Task>()
        val viewModel : taskViewModel by viewModels()
        viewModel.getSubTasks(task.id).observe(this, { subTasks ->
            holder.removeAll(holder)//clears the filtered list
            Log.i("DB_Response", "inside CreateTask, IM IN THE SYSTEM3!")
            subTasks.filter { x -> x.parent == taskId }
            for (temp in subTasks){
                if(temp.parent == taskId)
                    holder.add(temp)//adds the items that are filtered
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

            //if user enters a invalid priholder
            var priHolder = 0

            try{
                priHolder = Integer.parseInt(priority)

                if(priHolder > 10)
                    priHolder = 10
                else if (priHolder < 1)
                    priHolder = 1

                priority = priHolder.toString()
            }catch(e:  java.lang.NumberFormatException){

                priHolder = 5
            }

            //adds a new subtask
            if (taskName.isNotEmpty()){

                val db = FirebaseFirestore.getInstance().collection("task")
                val id = db.document().getId()
                var subtask = Task(taskName,null,null, priHolder,id, auth.currentUser!!.uid,true,taskId)

                db.document(id).set(subtask)
                    .addOnSuccessListener { Toast.makeText(this,"Task added", Toast.LENGTH_LONG).show() }
                    .addOnFailureListener{ Log.w("DB_Fail", it.localizedMessage)}
                binding.txtSubTaskName.setText("")
                binding.txtSubTaskPriority.setText("")
            }
            else{
                Toast.makeText(this, "Task name not entered", Toast.LENGTH_LONG).show()
            }
        }
        //returns to the main menu
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    //menu options
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


}