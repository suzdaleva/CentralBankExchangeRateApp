package com.manicpixie.cfttest.presentation.converter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manicpixie.cfttest.domain.model.Currency
import com.manicpixie.cfttest.domain.use_case.GetCurrencyByNameUseCase

import com.manicpixie.cfttest.presentation.currencies_list.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job


import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val getCurrencyByNameUseCase: GetCurrencyByNameUseCase
): ViewModel(){

    private var job: Job? = null
    private val _currentCurrency =
        mutableStateOf(Currency(charCode = "USD",
        nominal = 1,
        name = "Доллар США",
        value = 75.548,
        previous = 0.0)
        )
    val currentCurrency : State<Currency> = _currentCurrency

    private val _currentResult = mutableStateOf(0.0)
    val currentResult : State<Double> = _currentResult

    private val _inputValue = mutableStateOf(
        TextFieldState(
            hint = "0.0"
        )
    )
    val inputValue: State<TextFieldState> = _inputValue

    fun filterCurrencies(name: String) {
        cancelJob()
        job = viewModelScope.launch {
            _currentCurrency.value = getCurrencyByNameUseCase(name)
        }
    }


    fun onEvent(event: ConverterEvent) {
        when(event) {
            is ConverterEvent.ChangeTextFocus -> {
                _inputValue.value = _inputValue.value.copy(
                    isHintVisible = !event.focusState.isFocused && _inputValue.value.query.isBlank()
                )
            }
            is ConverterEvent.EnteredText -> {

                when {
                    event.value.contains(" ") -> {
                        _inputValue.value = _inputValue.value.copy(query = event.value.replace(" ", "")) }
                    event.value.contains("-") -> {
                        _inputValue.value = _inputValue.value.copy(query = event.value.replace("-", "")) }
                    event.value.contains(",") -> {
                        if (!event.value.contains(".")) {
                        _inputValue.value = _inputValue.value.copy(query = event.value.replace(",", "."))}
                        else _inputValue.value = _inputValue.value.copy(query = event.value.replace(",", ""))
                    }
                    event.value.filter { it == '.' }.count() > 1 -> {
                        val index = event.value.lastIndexOf('.')
                        _inputValue.value = _inputValue.value.copy(query = event.value.removeRange(index, index+1))
                    }
                    else -> _inputValue.value = _inputValue.value.copy(query = event.value)
                }
                if(_inputValue.value.query.isBlank()) _currentResult.value = 0.0
            }
            is ConverterEvent.Filter -> {
                filterCurrencies(event.currency)
                _currentResult.value = 0.0
            }
            is ConverterEvent.Calculate -> {
                _currentResult.value = _inputValue.value.query.toDouble()/currentCurrency.value.value
            }
        }
    }

    private fun cancelJob(){
        job?.cancel()
    }
}