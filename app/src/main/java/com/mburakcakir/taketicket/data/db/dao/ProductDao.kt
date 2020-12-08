package com.mburakcakir.taketicket.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mburakcakir.taketicket.data.db.entity.ProductModel

@Dao
interface ProductDao {
    @Query("SELECT * FROM table_product")
    fun getAllProducts() : LiveData<List<ProductModel>>

    @Insert
    suspend fun insertProduct(productModel : ProductModel)

    @Query("DELETE FROM table_product")
    suspend fun deleteAll()


}