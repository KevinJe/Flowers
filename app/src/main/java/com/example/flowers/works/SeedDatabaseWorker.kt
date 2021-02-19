package com.example.flowers.works

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.flowers.data.AppDatabase
import com.example.flowers.data.Plant
import com.example.flowers.utilities.PLANT_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            // 打开assets下的json文件，输入流
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                // 将流转换为JsonReader，注意是Gson包下的
                JsonReader(inputStream.reader()).use { jsonReader ->
                    // 拿到TypeToken
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    // 通过Gson解析
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                    // 新建数据库，单例类
                    val database = AppDatabase.getInstance(applicationContext)
                    // 通过数据访问对象，将数据插入表中
                    database.plantDao().insertAll(plantList)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}