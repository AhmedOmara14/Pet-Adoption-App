package com.omaradev.pet_adoption.domain.models

data class Pagination(
    val count_per_page: Int? = null,
    val current_page: Int? = null,
    val total_count: Int? = null,
    val total_pages: Int? = null,
)