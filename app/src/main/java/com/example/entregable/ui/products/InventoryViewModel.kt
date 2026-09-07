package com.example.entregable.ui.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.entregable.data.DatabaseHelper
import com.example.entregable.data.PdfReportGenerator
import com.example.entregable.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = DatabaseHelper(application)
    private val pdfGenerator = PdfReportGenerator(application)

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dbHelper.getAllProducts()
            _products.value = list
        }
    }

    fun addProduct(name: String, price: Double, quantity: Int, imageUri: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(name = name, price = price, quantity = quantity, imageUri = imageUri)
            if (dbHelper.addProduct(product)) {
                loadProducts()
            }
        }
    }

    fun editProduct(id: Int, name: String, price: Double, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (dbHelper.updateProduct(id, name, price, quantity)) {
                loadProducts()
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (dbHelper.deleteProduct(id)) {
                loadProducts()
            }
        }
    }

    fun generatePdfReport() {
        pdfGenerator.generatePdfReport(_products.value)
    }
}