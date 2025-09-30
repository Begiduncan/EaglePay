package com.begi.eaglepay.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    var cartItems =  { mutableStateOf(listOf<String>()) }
}