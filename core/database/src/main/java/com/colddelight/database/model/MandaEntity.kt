package com.colddelight.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.colddelight.model.Manda

@Entity(tableName = "manda")
data class MandaEntity(
    @ColumnInfo(name = "cnt") val cnt: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun MandaEntity.asModel() = Manda(
    cnt = cnt,
    id = id
)


fun Manda.asEntity() = MandaEntity(
    cnt = cnt,
    id = id
)