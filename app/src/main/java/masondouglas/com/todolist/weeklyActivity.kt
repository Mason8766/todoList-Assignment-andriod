package masondouglas.com.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import masondouglas.com.todolist.databinding.ActivitySubTaskBinding.bind
import masondouglas.com.todolist.databinding.ActivitySubTaskBinding.inflate
import masondouglas.com.todolist.databinding.ActivityWeeklyBinding
import masondouglas.com.todolist.databinding.ActivityWeeklyBinding.inflate
import java.time.LocalDate


class weeklyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeeklyBinding
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeeklyBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val viewModel : taskViewModel by viewModels()
        viewModel.getTasks().observe(this) { tasks ->
            var x = 0

            //create the list objects
            var list1 = mutableListOf<String>()
            var list2 = mutableListOf<String>()
            var list3 = mutableListOf<String>()
            var list4 = mutableListOf<String>()
            var list5 = mutableListOf<String>()
            var list6 = mutableListOf<String>()
            var list7 =mutableListOf<String>()
            var current = LocalDate.now()

            //set the values of all the labels
            binding.lblMonth1.setText(current.month.toString())
            binding.lblDay1.setText(current.dayOfMonth.toString())

            current = current.plusDays(1)
            binding.lblMonth2.setText(current.month.toString())
            binding.lblDay2.setText(current.dayOfMonth.toString())

            current = current.plusDays(1)
            binding.lblMonth3.setText(current.month.toString())
            binding.lblDay3.setText(current.dayOfMonth.toString())

            current = current.plusDays(1)
            binding.lblMonth4.setText(current.month.toString())
            binding.lblDay4.setText(current.dayOfMonth.toString())

            current = current.plusDays(1)
            binding.lblMonth5.setText(current.month.toString())
            binding.lblDay5.setText(current.dayOfMonth.toString())

            current = current.plusDays(1)
            binding.lblMonth6.setText(current.month.toString())
            binding.lblDay6.setText(current.dayOfMonth.toString())

            current = current.plusDays(1)
            binding.lblMonth7.setText(current.month.toString())
            binding.lblDay7.setText(current.dayOfMonth.toString())
            current = LocalDate.now()
            for(task in tasks) //for each task that had a due date
            {

                if(task.dueDate != "") {
                    try{//make sure the date can be converted
                    var dateList: List<String>
                    dateList = task.dueDate?.split("/")!!
                    var month = Integer.parseInt(dateList[0])
                    var day = Integer.parseInt(dateList[1])
                    var year = Integer.parseInt(dateList[2])


                    val date = LocalDate.of(year,month,day)
                    var compare = date.dayOfYear - current.dayOfYear
                    //val compare = current.compareTo(date)


//                    list1.add((date.dayOfYear.toString()))
//                    list1.add(current.dayOfYear.toString())
//                    list1.add(compare.toString())
                    when(compare) {//determine if the  date is within 7 days of todays date
                        0 -> {
//                           binding.lblMonth1.set = date.month.toString()
//                           binding.lblDay1.text = date.dayOfMonth.toString()
                            list1.add(task.taskName.toString())
                        }
                        1 -> {
                            list2.add(task.taskName.toString())
                        }
                        2 -> {

                            list3.add(task.taskName.toString())
                        }
                        3 -> {

                            list4.add(task.taskName.toString())
                        }
                        4 -> {

                            list5.add(task.taskName.toString())
                        }
                        5 -> {

                            list6.add(task.taskName.toString())
                        }
                        6 -> {

                            list7.add(task.taskName.toString())
                        }
                    }
                    x = 1
                }catch (e:  java.time.DateTimeException){
                        continue
                    }
                }
            }
//update the list
            for(task in list1)
                binding.lblTask1.setText(binding.lblTask1.text.toString() +" " +task.toString())
            for(task in list2)
                binding.lblTask2.setText(binding.lblTask2.text.toString() +" " + task.toString())
            for(task in list3)
                binding.lblTask3.setText(binding.lblTask3.text.toString() +" " + task.toString())
            for(task in list4)
                binding.lblTask4.setText(binding.lblTask4.text.toString() +" " + task.toString())
            for(task in list5)
                binding.lblTask5.setText(binding.lblTask5.text.toString() +" " + task.toString())
            for(task in list6)
                binding.lblTask6.setText(binding.lblTask6.text.toString() +" " + task.toString())
            for(task in list7)
                binding.lblTask7.setText(binding.lblTask7.text.toString() +" " + task.toString())

        }

        binding.btnbackWeek.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}