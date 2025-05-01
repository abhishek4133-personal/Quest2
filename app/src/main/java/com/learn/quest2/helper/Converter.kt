package com.learn.quest2.helper

import com.learn.quest2.data.entity.ProductEntity
import com.learn.quest2.data.model.Product

object Converter {

    private fun mapProductToEntity(product: Product): ProductEntity {
        return ProductEntity(
            images = product.images,
            thumbnail = product.thumbnail,
            minimumOrderQuantity = product.minimumOrderQuantity,
            rating = product.rating,
            returnPolicy = product.returnPolicy,
            description = product.description,
            weight = product.weight,
            warrantyInformation = product.warrantyInformation,
            title = product.title,
            tags = product.tags,
            discountPercentage = product.discountPercentage,
            reviews = product.reviews,
            price = product.price,
            meta = product.meta,
            shippingInformation = product.shippingInformation,
            id = product.id,
            availabilityStatus = product.availabilityStatus,
            category = product.category,
            stock = product.stock,
            sku = product.sku,
            brand = product.brand,
            dimensions = product.dimensions
        )
    }

    private fun mapEntityToProduct(entity: ProductEntity): Product {
        return Product(
            images = entity.images,
            thumbnail = entity.thumbnail,
            minimumOrderQuantity = entity.minimumOrderQuantity,
            rating = entity.rating,
            returnPolicy = entity.returnPolicy,
            description = entity.description,
            weight = entity.weight,
            warrantyInformation = entity.warrantyInformation,
            title = entity.title,
            tags = entity.tags,
            discountPercentage = entity.discountPercentage,
            reviews = entity.reviews,
            price = entity.price,
            meta = entity.meta,
            shippingInformation = entity.shippingInformation,
            id = entity.id,
            availabilityStatus = entity.availabilityStatus,
            category = entity.category,
            stock = entity.stock,
            sku = entity.sku,
            brand = entity.brand,
            dimensions = entity.dimensions
        )
    }

    fun Product.toProductEntity(): ProductEntity = mapProductToEntity(this)

    fun ProductEntity.toProduct(): Product = mapEntityToProduct(this)

    fun List<Product>.toProductEntityList(): List<ProductEntity> = map { it.toProductEntity() }

    fun List<ProductEntity>.toProductList(): List<Product> = map { it.toProduct() }
}