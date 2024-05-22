package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Address
import com.omaradev.pet_adoption.domain.models.Pagination
import kotlinx.serialization.Serializable

@Serializable
class PaginationNetwork {
    var count_per_page: Int? = null
    var current_page: Int? = null
    var total_count: Int? = null
    var total_pages: Int? = null
}

fun PaginationNetwork.toPagination(): Pagination {
    return Pagination(
        count_per_page,
        current_page,
        total_count,
        total_pages
    )
}
