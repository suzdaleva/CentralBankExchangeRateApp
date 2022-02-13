package com.manicpixie.cfttest.presentation.currencies_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.manicpixie.cfttest.common.Resource

import com.manicpixie.cfttest.domain.use_case.GetCurrenciesUseCase
import com.manicpixie.cfttest.domain.util.CurrenciesOrder

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {
    private var job: Job? = null

    private val _searchQuery = mutableStateOf(
        TextFieldState(
            hint = "Введите название валюты"
        )
    )

    val searchQuery: State<TextFieldState> = _searchQuery
    private val _contentState = mutableStateOf(CurrenciesListState())
    val contentState: State<CurrenciesListState> = _contentState

    private fun getCurrencies(currenciesOrder: CurrenciesOrder) {
        cancelJob()
        job = getCurrenciesUseCase(currenciesOrder).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _contentState.value = CurrenciesListState(
                        currencies = when (currenciesOrder) {
                            is CurrenciesOrder.Initial -> {
                                result.data ?: emptyList()
                            }
                            is CurrenciesOrder.ByValue -> {
                                result.data!!.sortedBy { it.value/it.nominal } ?: emptyList()
                            }
                        },
                        currenciesOrder = currenciesOrder
                    )
                }
                is Resource.Error -> {
                    viewModelScope.launch {
                        _contentState.value =
                            CurrenciesListState(error = result.message ?: "Unknown error")
                    }
                }
                is Resource.Loading -> {
                    _contentState.value = CurrenciesListState(isLoading = true)
                }
            }
        }.cancellable().launchIn(viewModelScope)
    }


    init {
        getCurrencies(CurrenciesOrder.Initial)
    }


    fun searchCurrencies(query: String, currenciesOrder: CurrenciesOrder) {
        cancelJob()
        job = getCurrenciesUseCase(currenciesOrder).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _contentState.value = CurrenciesListState(
                        currencies =
                                result.data!!.filter {
                                    it.name.lowercase().contains(query)  || it.charCode.lowercase().contains(query)
                                            || it.name.contains(query) || it.charCode.contains(query)
                            },
                        currenciesOrder = currenciesOrder
                    )
                }
                is Resource.Error -> {
                    _contentState.value =
                        CurrenciesListState(error = result.message ?: "Unknown error")
                }
                is Resource.Loading -> {
                    _contentState.value = CurrenciesListState(isLoading = true)
                }

            }

        }.cancellable().launchIn(viewModelScope)
    }


    fun onEvent(event: CurrenciesListEvent) {
        when (event) {
            is CurrenciesListEvent.EnteredQuery -> {
                _searchQuery.value = _searchQuery.value.copy(
                    query = event.value
                )
                searchCurrencies(searchQuery.value.query, event.currenciesOrder)
            }
            is CurrenciesListEvent.ChangeQueryFocus -> {
                _searchQuery.value = _searchQuery.value.copy(
                    isHintVisible = !event.focusState.isFocused && _searchQuery.value.query.isBlank(),
                )
            }
            is CurrenciesListEvent.Order -> {
                if (contentState.value.currenciesOrder::class == event.currenciesOrder::class) return
                _searchQuery.value = _searchQuery.value.copy(
                    query = ""
                )
                getCurrencies(event.currenciesOrder)
            }
        }
    }

    private fun cancelJob(){
        job?.cancel()
    }

}