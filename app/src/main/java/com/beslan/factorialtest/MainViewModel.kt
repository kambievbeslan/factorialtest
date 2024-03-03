package com.beslan.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger


class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = State.Progress
        if (value.isNullOrBlank()) {
            _state.value = State.Error
            return
        }
        val number = value.toLong()
        viewModelScope.launch {
            val result = factorial(number)
            _state.value = State.Factorial(
                value = result
            )
        }
    }

    private suspend fun factorial(number: Long): String {
        return withContext(Dispatchers.Default) {
            var result = BigInteger.ONE
            for(i in 1..number) {
                result  = result.multiply(BigInteger.valueOf(i))
            }
            result.toString()
        }
    }
}