package com.example.scorpcase.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scorpcase.data.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModel @Inject constructor(private val dataSource: DataSource) : ViewModel() {

    var person: MutableLiveData<Resource<ArrayList<Person>>> = MutableLiveData()
    var oldPersonList = ArrayList<Person>()
    var nextNew = 0
    var personList: ArrayList<Person>? = null

    fun getDataFetch(refresh: Boolean) = viewModelScope.launch(Dispatchers.IO){
        if (refresh) {
            oldPersonList.clear()
            nextNew = 0
        }

        person.postValue(Resource.loading())

        dataSource.fetch(nextNew.toString(), object : FetchCompletionHandler {
            override fun invoke(p1: FetchResponse?, p2: FetchError?) {
                if (p1 != null && p1.people.isNotEmpty()) {
                    nextNew = p1.next?.toInt() ?: 0
                    personList = p1.people as ArrayList<Person>
                    personList?.let { oldPersonList.addAll(it) }
                    person.postValue(Resource.success(personList))
                } else {
                    person.postValue(Resource.error(
                        p2?.errorDescription ?: "Error"))
                }
            }
        })
    }

    fun refreshData() {
        nextNew = 0
        getDataFetch(true)
    }

    fun nextData() {
        getDataFetch(false)
    }
}

