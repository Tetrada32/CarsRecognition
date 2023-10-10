package com.gahov.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gahov.domain.entity.cars.Operations
import com.gahov.domain.entity.cars.Region

/**
 * An entity representing weather data, stored in a database.
 *
 */


@Entity(
    tableName = "carData"
)
data class CarDTO(
    var id: Long? = 0,
    var photoUrl: String? = "",
    var isStolen: Boolean = false,
    var year: String = "",
    var brand: String? = "",
    var digits: String? = "",
    var model: String? = "",
    var city: String? = "",
    var lastRegistrationDate: String? = "",
    var searchTime: String? = ""
) {

    /**
     * The primary key auto-generated by the database.
     * That means that the DTO is not unique.
     */

    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
}