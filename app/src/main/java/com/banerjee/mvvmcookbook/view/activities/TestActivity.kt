package com.banerjee.mvvmcookbook.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.banerjee.mvvmcookbook.R
import com.banerjee.mvvmcookbook.databinding.ActivityTestBinding
import com.banerjee.mvvmcookbook.model.TestModel
import com.banerjee.mvvmcookbook.repository.TestRepository
import com.banerjee.mvvmcookbook.util.Constants
import com.banerjee.mvvmcookbook.viewmodel.TestViewModel
import com.banerjee.mvvmcookbook.viewmodel.TestViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class TestActivity : AppCompatActivity() {

    private lateinit var mTestBinding: ActivityTestBinding
    private val testRepository: TestRepository = TestRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTestBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(mTestBinding.root)

        val testViewModelFactory: TestViewModelFactory by lazy {
            TestViewModelFactory(testRepository)
        }
        val testViewModel: TestViewModel by viewModels {
            testViewModelFactory
        }

        mTestBinding.saveBtn.setOnClickListener {
            val name = mTestBinding.nameTxt.text.toString()
            val email = mTestBinding.emailTxt.text.toString()

            val testModel = TestModel(name, email)
            testViewModel.createTestData(testModel)
            testViewModel.createTestLiveData?.observe(this) {
                Log.d(Constants.TAG, "onCreate: ${testModel.name}")
            }

        }

        testViewModel.getData()
        testViewModel.getAllData?.observe(this) {
            Log.d(Constants.TAG, "onCreate: ${it.size}")
        }

    }

    private fun saveFireStore(name: String, email: String) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()

        user["name"] = name
        user["email"] = email

        db.collection("cookbook_users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@TestActivity, "Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@TestActivity, "Failure", Toast.LENGTH_SHORT).show()
            }
    }

    private fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        val arrayList: MutableList<Any?> = ArrayList<Any?>()

        db.collection("cookbook_users")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        arrayList.add(document.data["name"])
                    }
                    Log.d(Constants.TAG, "readFireStoreData: $arrayList")
                }
            }
            .addOnFailureListener {
                Toast.makeText(this@TestActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
    }
}