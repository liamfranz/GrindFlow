
package com.example.grindflow.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query(
        "SELECT * FROM schedules " +
                "ORDER BY date ASC, startTime ASC, id DESC"
    )
    fun getAllSchedules(): Flow<List<ScheduleEntity>>

    @Insert
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Update
    suspend fun updateSchedule(schedule: ScheduleEntity)

    @Delete
    suspend fun deleteSchedule(schedule: ScheduleEntity)
}
