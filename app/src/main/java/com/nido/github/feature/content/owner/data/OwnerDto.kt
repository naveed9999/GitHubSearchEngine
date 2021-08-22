package com.nido.github.feature.content.owner.data

import com.nido.github.feature.content.owner.api.OwnerResponse

object OwnerDto {
    fun fromResponse(ownerResponse: OwnerResponse) = Owner(
        ownerResponse.id,
        ownerResponse.name,
        ownerResponse.avatar,
        ownerResponse.htmlUrl,
        ownerResponse.type,
        ownerResponse.isSiteAdmin,
        ownerResponse.company,
        ownerResponse.location,
        ownerResponse.email,
        ownerResponse.bio,
        ownerResponse.createdAt
    )
}