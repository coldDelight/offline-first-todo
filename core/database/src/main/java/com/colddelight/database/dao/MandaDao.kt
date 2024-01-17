package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.colddelight.database.model.MandaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun initManda(manda: List<MandaEntity>)

    @Query("SELECT * FROM manda")
    fun getAllManda(): Flow<List<MandaEntity>>

    @Update
    suspend fun updateManda(mandaEntity: MandaEntity)

    @Query("DELETE FROM manda")
    suspend fun deleteAllManda()


}