package com.kotlinSpring.Entity

import jakarta.persistence.*

@Entity
@Table(name="courses")
data class course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id :Int?,
    var name :String,
    var category: String
)
