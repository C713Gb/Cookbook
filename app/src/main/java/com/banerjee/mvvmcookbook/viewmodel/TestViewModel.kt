package com.banerjee.mvvmcookbook.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.banerjee.mvvmcookbook.model.TestModel
import com.banerjee.mvvmcookbook.repository.TestRepository
import com.banerjee.mvvmcookbook.util.Constants

class TestViewModel(private val testRepository: TestRepository) : ViewModel() {

    var createTestLiveData: LiveData<TestModel>? = null
    var getAllData: LiveData<MutableList<TestModel>>? = null

    fun createTestData(testModel: TestModel){
        createTestLiveData = testRepository.addTestData(testModel)
    }

    fun getData() {
        Log.d(Constants.TAG, "readFireStoreData: Coming1")
        getAllData = testRepository.getAllUsersData()
        Log.d(Constants.TAG, "readFireStoreData: Coming2")
    }

}

@Suppress("UNCHECKED_CAST")
class TestViewModelFactory(private val testRepository: TestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)){
            return TestViewModel(testRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}