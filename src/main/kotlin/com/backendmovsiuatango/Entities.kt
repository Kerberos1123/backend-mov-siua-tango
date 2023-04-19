package com.backendmovsiuatango

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "firstName")
    var firstName:  String? = null,

    @Column(name = "lastName")
    var lastName: String? = null,

    @Column(name = "password")
    var password:  String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "rol")
    var rol:  String? = null,

    @Column(name = "createDate")
    var createDate: Date? = null,

    @Column(name = "enabled")
    var enabled: Boolean,

    //Entity relationship
    @ManyToOne                       //MANY TO MANY
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roleList: List<Role>  // falta crear Role

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', password='$password', email='$email', createDate=$createDate, enabled=$enabled, roleList=$roleList)"
    }

}

@Entity
@Table(name="role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var roleName: String? = null,

    //Entity relationship
    @OneToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    var userList: List<User>,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Role) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, name='$roleName)"
    }
}

@Entity
@Table(name = "ticket")
data class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "user_id")
    var user_id: Long? = null,

    @Column(name = "asset_type_id")
    var asset_type_id: Long? = null,

    @Column(name = "ticket_reason_id")
    var ticket_reason_id: Long? = null,

    @Column(name = "detail")
    var detail: String? = null,

    //Entity relationships
    @ManyToMany
    var ticketUser: Set<User>,

   @ManyToOne
    var ticketReason: List<TicketReason>,

    @ManyToOne
    var ticketAsset: List<AssetType>,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ticket) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, user_id=$user_id, asset_type_id=$asset_type_id, ticket_reason_id:$ticket_reason_id, detail=$detail)"
    }
}

@Entity
@Table(name = "ticket_reason")
data class TicketReason(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var reasonName: String? = null,

    //Entity relationships
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TicketReason) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, name=$reasonName)"
    }
}

@Entity
@Table(name = "asset_type")
data class AssetType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var assetName: String? = null,

    //Entity relationships
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AssetType) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, name=$assetName)"
    }
}

@Entity
@Table(name = "request")
data class Request(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "asset_id")
    var asset_id: Long? = null,

    @Column(name = "user_id")
    var user_id: Long? = null,

    @Column(name = "date_hour")
    var date_hour: Date? = null,

    @Column(name = "state_id")
    var state_id: Long? = null,

    //Entity relationships
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Request) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, asset_id=$asset_id, user_id=$user_id, date_hour=$date_hour, state_id=$state_id)"
    }
}