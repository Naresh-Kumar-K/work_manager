package com.example.workmanager_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.workmanager_2.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY_VALUE = "key_count"
        const val STRING_VALUE = "string_count"
    }

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.bOneTime.setOnClickListener {
            oneTimeRequest()
        }

        binding.bPeriodic.setOnClickListener {
            periodicRequest()
        }
    }
    private fun oneTimeRequest(){

        val data: Data = Data.Builder()
            .putInt(KEY_VALUE,600)
            .putString(STRING_VALUE,"Kumar")
            .build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            //.setRequiresDeviceIdle(true)
            //.setRequiredNetworkType(NetworkType.METERED)
            //.setRequiresStorageNotLow(true)
            .build()


        val request = OneTimeWorkRequest.Builder(MyWorkerClass::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        val requestF = OneTimeWorkRequest.Builder(FilteringWorker::class.java)
            .build()

        val requestD = OneTimeWorkRequest.Builder(DownloadingWorker::class.java)
            .build()

        val parallelWork = mutableListOf<OneTimeWorkRequest>()
            parallelWork.add(requestD)
            parallelWork.add(requestF)

        WorkManager.getInstance(applicationContext)
            .beginWith(parallelWork)
            .then(request)
            .enqueue()

        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(request.id).observe(this, Observer {
                Toast.makeText(this, it.state.name,Toast.LENGTH_LONG).show()
            })
    }

    private fun periodicRequest(){

        val data: Data = Data.Builder()
            .putString(STRING_VALUE,"Naresh")
            .build()
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()

            val myRequest = PeriodicWorkRequest.Builder(
                DownloadingWorker::class.java,
                15,
                TimeUnit.MINUTES
            ).setConstraints(constraints)
                .setInputData(data)
                .addTag("my_id")
                .build()

            WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                    "my_id",
                    ExistingPeriodicWorkPolicy.KEEP,
                    myRequest
                )
        WorkManager.getInstance(applicationContext)
            .getWorkInfoByIdLiveData(myRequest.id).observe(this, Observer {
                Toast.makeText(this, it.state.name,Toast.LENGTH_LONG).show()
            })
        }
    }