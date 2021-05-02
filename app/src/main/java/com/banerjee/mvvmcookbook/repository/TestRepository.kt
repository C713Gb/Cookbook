package com.banerjee.mvvmcookbook.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.banerjee.mvvmcookbook.model.TestModel
import com.banerjee.mvvmcookbook.util.Constants
import com.banerjee.mvvmcookbook.util.Constants.Companion.TAG
import com.google.firebase.firestore.FirebaseFirestore

class TestRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersRef = db.collection(Constants.USERS)

    fun addTestData(testModel: TestModel): MutableLiveData<TestModel> {
        val newTestMutableLiveData = MutableLiveData<TestModel>()
        usersRef.add(testModel)
                .addOnSuccessListener {
                    Log.d(TAG, "addTestData: Success")
                }
                .addOnFailureListener {
                    Log.d(TAG, "addTestData: Failure")
                }
        newTestMutableLiveData.value = testModel
        return newTestMutableLiveData
    }

    fun getAllUsersData(): MutableLiveData<MutableList<TestModel>> {
        val newTestMutableLiveData = MutableLiveData<MutableList<TestModel>>()
        val newMutableList: MutableList<TestModel> = ArrayList<TestModel>()
        Log.d(Constants.TAG, "readFireStoreData: Coming")
        usersRef.get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result!!) {
                            val name = document.data["name"].toString()
                            val email = document.data["email"].toString()
                            val testModel = TestModel(name, email)
                            newMutableList.add(testModel)
                        }
                        Log.d(Constants.TAG, "readFireStoreData: Success")
                    }
                    newTestMutableLiveData.value = newMutableList
                }
                .addOnFailureListener {
                    Log.d(Constants.TAG, "readFireStoreData: Failed")
                }


        return newTestMutableLiveData
    }

}