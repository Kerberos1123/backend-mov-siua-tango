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
    @ManyToMany                       //MANY TO MANY
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roleUser: Set<Role>,

    @OneToMany(mappedBy = "users")
    var tickets: List<Ticket>,

    @OneToMany(mappedBy = "users")
    var requests: List<Request>,

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
        return "User(id=$id, firstName='$firstName', lastName='$lastName', password='$password', email='$email', createDate=$createDate, enabled=$enabled, roleUser=$roleUser)"
    }

}

@Entity
@Table(name = "privilege")
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String,
    // Entity Relationship
    @ManyToMany(mappedBy = "roleList", fetch = FetchType.LAZY)
    var userList: Set<User>,
    @ManyToMany(mappedBy = "privilegeList", fetch = FetchType.LAZY)
    var roleList: Set<Role>
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Privilege) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Privilege(id=$id, name='$name', userList=$userList, roleList=$roleList)"
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
    @ManyToMany(mappedBy = "roleUser")
    var roles: Set<User>,
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
    var userId: Long? = null,

    @Column(name = "asset_type_id")
    var assetTypeId: Long? = null,

    @Column(name = "ticket_reason_id")
    var ticketReasonId: Long? = null,

    @Column(name = "detail")
    var detail: String? = null,

    //Entity relationships
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    var ticket: Ticket,

    @OneToOne
    @JoinColumn(name = "asset_type_id", referencedColumnName = "id")
    var assetType: AssetType,

    @OneToOne
    @JoinColumn(name = "ticket_reason_id", referencedColumnName = "id")
    var ticketReason: TicketReason,
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
        return "User(id=$id, user_id=$userId, asset_type_id=$assetTypeId, ticket_reason_id:$ticketReasonId, detail=$detail)"
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
    @OneToOne(mappedBy = "ticket_reason")
    var ticket: Ticket,
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
    @OneToOne(mappedBy = "asset_type")
    var ticket: Ticket,
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
    var assetId: Long? = null,

    @Column(name = "user_id")
    var userId: Long? = null,

    @Column(name = "date_hour")
    var dateHour: Date? = null,

    @Column(name = "state_id")
    var stateId: Long? = null,

    //Entity relationships
   /* @OneToOne
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    var assets: Asset,*///crear entity Asset

  /*  @OneToOne(mappedBy = "asset")
    var request: Request,*/ //cortar y pegar esto en Asset

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    var ticket: Ticket,

    /*OneToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    var state: State,*/ //crear entity State

   /* @OneToOne(mappedBy = "state")
    var request: Request,*/ //cortar y pegar esto en State

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
        return "User(id=$id, asset_id=$assetId, user_id=$userId, date_hour=$dateHour, state_id=$stateId)"
    }
}