package com.example.phonebook.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [PhoneDbModel::class, ColorDbModel::class, TagDbModel::class],
    version = 6,
    autoMigrations = [
        AutoMigration (from = 5, to = 6, spec = AppDatabase.MyExampleAutoMigration::class)
    ],

    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun phoneDao(): PhoneDao
    abstract fun colorDao(): ColorDao
    abstract fun tagDao(): TagDao

    @RenameTable(fromTableName = "NoteDbModel", toTableName = "PhoneDbModel")
    class MyExampleAutoMigration : AutoMigrationSpec {
        @Override
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            // Invoked once auto migration is done
        }
    }


    companion object {

        val MIGRATION_1_3 = object : Migration(1, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE NoteDbModel ADD COLUMN tag_id INTEGER NOT NULL DEFAULT 0")
                database.execSQL("CREATE TABLE IF NOT EXISTS `TagDbModel` (`name` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)")
            }
        }

        val MIGRATION_3_5 = object : Migration(3, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("UPDATE NoteDbModel SET tag_id = 1 WHERE tag_id = 0")
                database.execSQL("CREATE TABLE IF NOT EXISTS `TagDbModel` (`name` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)")
            }
        }




        private const val DATABASE_NAME = "phone-maker-database"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addMigrations(MIGRATION_3_5).build()

                INSTANCE = instance
            }

            return instance
        }
    }
}