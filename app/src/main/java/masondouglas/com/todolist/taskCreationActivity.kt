package masondouglas.com.todolist

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.time.LocalDate
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import masondouglas.com.todolist.databinding.ActivityTaskCreationBinding
import java.util.*



class taskCreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskCreationBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("NewApi")
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
            var priority = binding.txtPriority.text.toString().trim()

            val check = binding.cbxCalender.isChecked
            var month = binding.txtMonth.text.toString().trim()
            var day = binding.txtDate.text.toString().trim()
            var year = binding.txtYear.text.toString().trim()


            var date = month +"/" + day +"/"+year
            if(date =="//")
                date = ""
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

            var flag = 1;
            //var dateLocal = LocalDate.of(0,0,0)
            //dateLocal = LocalDate.of(2021,1,5)
            try{
               var nummonth = Integer.parseInt(month)

              var  numday = Integer.parseInt(day)

              var  numyear = Integer.parseInt(year)



              //  dateLocal = LocalDate.of(2021,1,5)
                if(nummonth < 1 || nummonth > 12 )
                    flag = 0

            }catch(e:  java.lang.NumberFormatException){

                if(month.isEmpty() == false || day.isEmpty() == false|| year.isEmpty() == false)
                    Toast.makeText(this, "Invalid date, cannot be used for google calander", Toast.LENGTH_LONG).show()
            }

            if (taskName.isNotEmpty() && flag==1){//INPUT TASK into database

                val db = FirebaseFirestore.getInstance().collection("task")

                val id = db.document().getId()
                var task = Task(taskName,description,date, priHolder,id, auth.currentUser!!.uid,false,null,)
                db.document(id).set(task)
                    .addOnSuccessListener { Toast.makeText(this,"Task added", Toast.LENGTH_LONG).show() }
                    .addOnFailureListener{ Log.w("DB_Fail", it.localizedMessage)}

                binding.txtTaskName.setText("")
                binding.txtDescription.setText("")
                binding.txtPriority.setText("")

                if(check) {
                    val calendarEvent = Calendar.getInstance()
                    calendarEvent.set(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day))

                    

                    val i = Intent(Intent.ACTION_EDIT)
                    i.type = "vnd.android.cursor.item/event"
                    i.putExtra("beginTime", calendarEvent.timeInMillis)
                    i.putExtra("allDay", true)

                    i.putExtra("endTime", calendarEvent.timeInMillis)
                    i.putExtra("title", taskName)
                    startActivity(i)
                }
                binding.txtDate.setText("")
                binding.txtMonth.setText("")
                binding.txtYear.setText("")
//                db.document("0B6jUPCfEnCWV6RO5Au0")
//                    .delete()
//                    .addOnSuccessListener { Log.d(TAG, "DB_DELETE COMPLETE") }
//                    .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }



            }
            else if (flag != 1){//task could not be added because it was empty
                Toast.makeText(this, "Invalid Task", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Task name not entered,", Toast.LENGTH_LONG).show()
            }
        }



    }

}

