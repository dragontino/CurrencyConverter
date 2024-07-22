package com.currencyconverter.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.domain.model.ConversionQuery
import com.currencyconverter.domain.model.ConversionResponse
import com.currencyconverter.domain.model.Currency
import com.currencyconverter.domain.usecase.ConversionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val useCase: ConversionUseCase
) : ViewModel() {
    var state: ViewModelState by mutableStateOf(ViewModelState.Stable)
        private set

    private val _snackbarFlow = MutableSharedFlow<String>()
    val snackbarFlow = _snackbarFlow.asSharedFlow()

    var fromCurrency by mutableStateOf(Currency.Ruble)
    var amount by mutableStateOf("")
    var toCurrency by mutableStateOf(Currency.Euro)

    var showFromCurrencySelectionDialog by mutableStateOf(false)
    var showToCurrencySelectionDialog by mutableStateOf(false)

    var showResult by mutableStateOf(true)

    var result: String by mutableStateOf("")
        private set


    fun convert() {
        viewModelScope.launch {
            val amount = amount.toDoubleOrNull() ?: return@launch

            state = ViewModelState.Loading
            val conversionResult = convert(fromCurrency, toCurrency, amount)
            conversionResult.onSuccess {
                result = it.resultAmount.toString()
                showResult = true
            }
            conversionResult.onFailure {
                _snackbarFlow.emit(it.message ?: "Error!")
            }
            state = ViewModelState.Stable
        }
    }


    private suspend fun convert(from: Currency, to: Currency, amount: Double): Result<ConversionResponse> {
        val query = ConversionQuery(from, to, amount)
        return useCase.convertCurrency(query)
    }
}