package com.example.roomdb_alifaa_04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.roomdb_alifaa_04.room.Constant
import com.example.roomdb_alifaa_04.room.Reminder
import com.example.roomdb_alifaa_04.room.ReminderDB
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    val db by lazy {ReminderDB(this)}
    private var reminderId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
    }

    fun setupView() {
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getReminder()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getReminder()
            }
        }
    }

    private fun setupListener() {
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ReminderDao().addReminder(
                    Reminder(
                        0,
                        et_title.text.toString(),
                        et_desc.text.toString()
                    )
                )
                finish()
            }
        }
        btn_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.ReminderDao().updateReminder(
                    Reminder(
                        reminderId,
                        et_title.text.toString(),
                        et_desc.text.toString()
                    )
                )
                finish()
            }
        }
    }

    fun getReminder() {
        reminderId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val reminders = db.ReminderDao().getReminder( reminderId )[0]
            et_title.setText(reminders.title)
            et_desc.setText(reminders.desc)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}