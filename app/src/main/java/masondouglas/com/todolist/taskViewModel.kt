package masondouglas.com.todolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class taskViewModel() : ViewModel() {


    private val tasks = MutableLiveData<List<Task>>()
    private val subTasks = MutableLiveData<List<Task>>()

    init {
        val userId = Firebase.auth.currentUser?.uid

        val db = FirebaseFirestore.getInstance().collection("task")
            .whereEqualTo("userId", userId)

            .orderBy("priority")
            .addSnapshotListener{documents, exception ->
                if (exception != null) {
                    Log.w("DB_Response", "Listen Failed ${exception.code}")
                    return@addSnapshotListener
                }

                documents?.let {
                    val taskList = ArrayList<Task>()
                    val subTaskList = ArrayList<Task>()
                    for(document in documents){
                        Log.i("DB_Response", "${document.data}")
                        val task = document.toObject(Task::class.java)
                        if(task.isSubTask == false)//checks if this is a subtask, assings task to appropriate array
                            taskList.add(task)
                        else{
                                subTaskList.add(task)}
                    }
                    tasks.value = taskList
                    subTasks.value = subTaskList

                }


            }
    }
    fun getTasks() : LiveData<List<Task>> {
        return tasks
    }
    fun getSubTasks(taskId : String?) : LiveData<List<Task>> {

        return subTasks
    }
}




