package com.example.workmanager_2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadingWorker(ctx: Context, params: WorkerParameters): Worker(ctx,params) {


    override fun doWork(): Result = try {

            val count = inputData.getString(MainActivity.STRING_VALUE)
        for (i in 0 until 600) {
            Log.d("TAG", "doWork: Downloading $i")
        }
        Log.d("pwr"," Clicked $count")
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }
}