package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.Manda
import com.colddelight.model.util.DateUtil
import com.colddelight.network.model.NetworkManda
import com.colddelight.network.model.NetworkTodo

@Entity(tableName = "manda")
data class MandaEntity(
    @ColumnInfo(name = "cnt") val cnt: Int = 0,
    @ColumnInfo(name = "is_sync") val isSync: Boolean,
    @ColumnInfo(name = "update_time") val updateTime: String,
    @ColumnInfo(name = "origin_id") val originId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun MandaEntity.asModel() = Manda(
    cnt = cnt,
    updateTime = updateTime,
    isSync = isSync,
    originId = originId,
    id = id
)


fun Manda.asEntity() = MandaEntity(
    cnt = cnt,
    updateTime = updateTime,
    isSync = isSync,
    originId = originId,
    id = id
)

fun NetworkManda.asEntity() = MandaEntity(
    cnt = cnt,
    updateTime = update_time,
    isSync = true,
    originId = id,
)

fun MandaEntity.asNetWorkModel() = NetworkManda(
    cnt = cnt,
    update_time = updateTime,
    id = originId
)
