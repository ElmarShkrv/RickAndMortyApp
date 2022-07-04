package com.chiore.rickandmortyapp.ui.fragments.detailsfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiore.rickandmortyapp.domain.models.Character
import com.chiore.rickandmortyapp.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
): ViewModel() {

    private val _charachterByLiveData = MutableLiveData<Character?>()
    val charachterByLiveData: LiveData<Character?> = _charachterByLiveData

    fun refreshCharachter(charahterId: Int) {

        viewModelScope.launch{
            val response = repository.getCharachter(charahterId)

            _charachterByLiveData.postValue(response)
        }
    }

}