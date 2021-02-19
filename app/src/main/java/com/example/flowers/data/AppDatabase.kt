package com.example.flowers.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.flowers.utilities.DATABASE_NAME
import com.example.flowers.works.SeedDatabaseWorker

/**
 * The Room database for this app
 */
@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gardenPlatingDao(): GardenPlantingDao
    abstract fun plantDao(): PlantDao

    companion object {
        // 单例模式
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val worker = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        // 通过WorkManger在子线程解析json和创建数据库并出入数据
                        WorkManager.getInstance(context).enqueue(worker)
                    }
                }).build()
        }

    }
}