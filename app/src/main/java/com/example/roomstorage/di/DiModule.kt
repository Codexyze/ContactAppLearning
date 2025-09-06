package com.example.roomstorage.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomstorage.Constants.Constants
import com.example.roomstorage.data.NotesDatabase
import com.example.roomstorage.domain.Repository.NotesRepository
import com.example.roomstorage.data.RepoImpl.NotesRepositoryImp
import com.example.roomstorage.domain.UseCases.DeleteUseCase
import com.example.roomstorage.domain.UseCases.GetAllNotesUseCase
import com.example.roomstorage.domain.UseCases.SaveOrUpdateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DiModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabase{
        return Room.databaseBuilder(
            context = context,
            NotesDatabase::class.java, "note_db"
        ).build()
    }
    @Provides
    fun notesDaoProvider(@ApplicationContext context: Context):NotesDatabase{
        val passkey = SQLiteDatabase.getBytes(Constants.SQLCIPER_KEY.toCharArray())
        val factory = SupportFactory(passkey)
        return Room.databaseBuilder(
            context = context,
            NotesDatabase::class.java, "note_db"
        ).openHelperFactory(factory =factory ).build()
    }
    @Provides
    fun ProvideRepoInstance(database: NotesDatabase): NotesRepository{
        return NotesRepositoryImp(database = database)

    }

    @Provides
    fun DeleteUseCase(repository: NotesRepository): DeleteUseCase{
        return DeleteUseCase(repository = repository)
    }

    @Provides
    fun GetAllNotesUseCaseProvide(database: NotesDatabase): GetAllNotesUseCase{
        return GetAllNotesUseCase(repository = NotesRepositoryImp(database = database))

    }

    @Provides
    fun SaveOrUpdateUseCaseProvide(database: NotesDatabase): SaveOrUpdateUseCase{
        return SaveOrUpdateUseCase(repository = NotesRepositoryImp(database = database))
    }

}