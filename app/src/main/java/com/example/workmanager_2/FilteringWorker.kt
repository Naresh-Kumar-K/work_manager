package com.example.workmanager_2

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class FilteringWorker(ctx: Context, params: WorkerParameters): Worker(ctx,params) {

    override fun doWork(): Result = try {
        for (i in 0 until 600){
            Log.d("TAG", "doWork: Filtering $i")
        }
        Result.success()
    } catch (e: Exception){
        Result.failure()

    }
}