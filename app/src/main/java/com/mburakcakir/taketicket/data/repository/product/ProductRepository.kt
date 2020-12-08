package com.mburakcakir.taketicket.data.repository.product

import androidx.lifecycle.LiveData
import com.mburakcakir.taketicket.data.db.entity.ProductModel

interface ProductRepository {
    fun getAllProducts() : LiveData<List<ProductModel>>
    suspend fun insertProduct(productModel: ProductModel)
}