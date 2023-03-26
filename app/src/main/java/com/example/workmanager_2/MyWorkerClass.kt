package com.example.workmanager_2

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkerClass(ctx: Context, params: WorkerParameters): Worker(ctx,params) {


    override fun doWork(): Result = try {
        val count = inputData.getInt(MainActivity.KEY_VALUE,0)
        for (i in 0 until count){
            Log.d("TAG", "doWork: Uploading $i")
        }
        Result.success()
    } catch (e: Exception){
        Result.failure()

    }
}