package com.car_plate.domain.entity


class Car {
    var photoUrl: String? = null
    var isStolen: Boolean? = null
    var operations: List<Operations?>? = null
    var year: Int? = null
    var vendor: String? = null
    var digits: String? = null
    var model: String? = null
    var region: Region? = null
}


//class User(
//    var id: Long? = null,
//    var username: String? = null,
//    var surname: String? = null,
//    var email: String? = null,
//    var password: String? = null,
//    var avatar: String? = null,
//    var role: Int? = null,
//    var positionCompany: String? = null,
//    var socialFacebook: String? = null,
//    var socialInstagram: String? = null,
//    var socialLinkedIn: String? = null,
//    var companyName: String? = null,
//    var location: String? = null,
//    var website: String? = null,
//    var position: String? = null,
//    var bio: String? = null,
//    var avatarUrl: String? = null,
//    var roleDescription: String? = null,
//    var fullName: String? = null,
//    var userPivot: UserPivot? = null
//) : Serializable
//
//class UserPivot(
//    var userEventId: Long? = null,
//    var userId: Long? = null
//) : Serializable
//
//fun User.toParams(): UpdateProfileParams {
//    return UpdateProfileParams(
//        email = email,
//        password = password ?: "",
//        name = username,
//        surname = surname,
//        avatar = avatar,
//        position_company = position, //here must be positionCompany
//        social_facebook = socialFacebook,
//        social_instagram = socialInstagram,
//        social_linked_in = socialLinkedIn,
//        company_name = companyName,
//        location = location,
//        website = website,
//        position = position,
//        bio = bio
//    )
//}