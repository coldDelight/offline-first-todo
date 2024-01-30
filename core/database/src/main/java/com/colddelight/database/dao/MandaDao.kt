package com.colddelight.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.colddelight.database.model.MandaEntity
import com.colddelight.database.model.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MandaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun initManda(manda: List<MandaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun syncInsertManda(mandaList: List<MandaEntity>): List<Long>

    @Query("SELECT * FROM manda")
    fun getAllManda(): Flow<List<MandaEntity>>

    @Query("SELECT * FROM manda WHERE update_time > :updateTime AND is_sync=0")
    fun getToWriteMandas(updateTime: String): List<MandaEntity>

    @Update
    suspend fun updateManda(mandaEntity: MandaEntity)

    @Query("DELETE FROM manda")
    suspend fun deleteAllManda()

    @Transaction
    fun getMandaIdByOriginIds(originIdList: List<Int>): List<Int?> {
        return originIdList.map { originId ->
            getMandaIdByOriginId(originId)
        }
    }

    @Query("SELECT id FROM manda WHERE origin_id = :originId")
    fun getMandaIdByOriginId(originId: Int): Int?

    @Query("DELETE FROM manda")
    suspend fun deleteAll()

}