package com.udacity.project4.locationreminders.data

import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result

//Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeDataSource : ReminderDataSource {

    var listOfReminder = mutableListOf<ReminderDTO>()

    private var errorOrNot = false



    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        return try {

            if (errorOrNot) {
                throw Exception("reminders not found")
            }
            Result.Success(ArrayList(listOfReminder))
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }



    override suspend fun getReminder(id: String): Result<ReminderDTO> {

        return try {
            val reminder = listOfReminder.find { it.id == id }
            if (errorOrNot || reminder == null) {
                throw Exception("there is no this $id")
            } else {
                Result.Success(reminder)
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        listOfReminder.add(reminder)
    }

    override suspend fun deleteAllReminders() {
        listOfReminder.clear()
    }


    fun setError(value: Boolean) {
        errorOrNot = value
    }


}