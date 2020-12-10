package com.mburakcakir.taketicket.data.repository.product

import androidx.lifecycle.LiveData
import com.mburakcakir.taketicket.data.db.dao.ProductDao
import com.mburakcakir.taketicket.data.db.entity.ProductModel

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    override fun getAllProducts(): LiveData<List<ProductModel>> = productDao.getAllProducts()
    override suspend fun insertProduct(productModel: ProductModel) =
        productDao.insertProduct(productModel)
}