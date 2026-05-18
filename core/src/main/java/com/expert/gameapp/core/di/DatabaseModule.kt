package com.expert.gameapp.core.di

import android.content.Context
import androidx.room.Room
import com.expert.gameapp.core.data.source.local.dao.GameDao
import com.expert.gameapp.core.data.source.local.room.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
        val passphrase = SQLiteDatabase.getBytes("expert_gameapp_secret_key".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            "GameAppExpert.db"
        ).fallbackToDestructiveMigration()
         .openHelperFactory(factory)
         .build()
    }

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()
}
