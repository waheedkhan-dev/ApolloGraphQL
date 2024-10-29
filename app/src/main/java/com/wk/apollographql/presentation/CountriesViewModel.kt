package com.wk.apollographql.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.apollographql.data.models.DetailedCountry
import com.wk.apollographql.data.models.SimpleCountry
import com.wk.apollographql.data.repository.MainRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "CountriesViewModel"

@HiltViewModel
class CountriesViewModel @Inject constructor(private val mainRepoImpl: MainRepoImpl) : ViewModel() {
    private val _state = MutableStateFlow(CountriesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }
            _state.update { it.copy(
                countries = mainRepoImpl.getCountries().sortedBy { it.name },
                isLoading = false
            ) }
        }
    }

    fun selectCountry(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                selectedCountry = mainRepoImpl.getCountry(code = code)
            ) }
        }
    }

    fun dismissCountryDialog() {
        _state.update { it.copy(
            selectedCountry = null
        ) }
    }

    data class CountriesState(
        val countries: List<SimpleCountry> = emptyList(),
        val isLoading: Boolean = false,
        val selectedCountry: DetailedCountry? = null
    )
}