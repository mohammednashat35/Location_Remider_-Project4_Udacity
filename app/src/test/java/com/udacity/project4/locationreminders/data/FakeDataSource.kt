package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {

    var listOfReminder = mutableListOf<ReminderDTO>()

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getReminders(): Result<List<ReminderDTO>>{
        return try {
            if(shouldReturnError) {
                throw Exception("reminders not found")
            }
            Result.Success(ArrayList(listOfReminder))
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO>  {
        return try {
            val reminder = listOfReminder.find { it.id == id }
            if (shouldReturnError || reminder == null) {
                throw Exception("Not found $id")
            } else {
                Result.Success(reminder)
            }
        } catch (e:Exception) {
            Result.Error(e.localizedMessage)
        }
    }
    override suspend fun saveReminder(reminder: ReminderDTO) {
        listOfReminder.add(reminder)
    }

    override suspend fun deleteAllReminders() {
        listOfReminder.clear()
    }


}