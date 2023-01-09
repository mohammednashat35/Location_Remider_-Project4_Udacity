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


        return if (shouldReturnError){
            Result.Error("reminders not found")
        }else{
            Result.Success(ArrayList(listOfReminder))
        }

    }

    override suspend fun getReminder(id: String): Result<ReminderDTO>  {


        return if(shouldReturnError){
            Result.Error("Error")
        }else{
            val reminder = listOfReminder.find { it.id == id }
            if (reminder != null) {
                Result.Success(reminder)
            } else {
                Result.Error("reminder not found")
            }
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        listOfReminder.add(reminder)
    }

    override suspend fun deleteAllReminders() {
        listOfReminder.clear()
    }


}