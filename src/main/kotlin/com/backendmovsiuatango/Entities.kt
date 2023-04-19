package com.backendmovsiuatango

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "rol")
    var rol: String,

    //Entity relationship
)