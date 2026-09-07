package com.example.entregable.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.entregable.model.Product
import com.example.entregable.model.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "inventory_app.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val USER_ID = "id"
        private const val USER_NAME = "username"
        private const val USER_PASSWORD = "password"

        private const val TABLE_PRODUCTS = "products"
        private const val PRODUCT_ID = "id"
        private const val PRODUCT_NAME = "name"
        private const val PRODUCT_PRICE = "price"
        private const val PRODUCT_QUANTITY = "quantity"
        private const val PRODUCT_IMAGE_URI = "imageUri"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = ("CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT UNIQUE, "
                + USER_PASSWORD + " TEXT" + ")")

        val createProductsTable = ("CREATE TABLE " + TABLE_PRODUCTS + "("
                + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_NAME + " TEXT, "
                + PRODUCT_PRICE + " REAL, "
                + PRODUCT_QUANTITY + " INTEGER, "
                + PRODUCT_IMAGE_URI + " TEXT" + ")")

        db.execSQL(createUsersTable)
        db.execSQL(createProductsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun registerUser(user: User): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(USER_NAME, user.username)
            put(USER_PASSWORD, user.password)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun validateUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(USER_ID),
            "$USER_NAME = ? AND $USER_PASSWORD = ?",
            arrayOf(username, password),
            null, null, null
        )
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    fun addProduct(product: Product): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(PRODUCT_NAME, product.name)
            put(PRODUCT_PRICE, product.price)
            put(PRODUCT_QUANTITY, product.quantity)
            put(PRODUCT_IMAGE_URI, product.imageUri)
        }
        val result = db.insert(TABLE_PRODUCTS, null, values)
        db.close()
        return result != -1L
    }

    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCTS", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_NAME))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_PRICE))
                val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_QUANTITY))
                val imageUri = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_IMAGE_URI))

                productList.add(Product(id, name, price, quantity, imageUri))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return productList
    }

    fun updateProduct(id: Int, name: String, price: Double, quantity: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(PRODUCT_NAME, name)
            put(PRODUCT_PRICE, price)
            put(PRODUCT_QUANTITY, quantity)
        }
        val result = db.update(TABLE_PRODUCTS, values, "$PRODUCT_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun deleteProduct(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_PRODUCTS, "$PRODUCT_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
}