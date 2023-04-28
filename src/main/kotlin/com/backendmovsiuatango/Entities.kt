package com.backendmovsiuatango

import com.fasterxml.jackson.annotation.JsonFormat
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

    @OneToMany
    @JoinColumn(name= "user_id")
    var userTickets: List<User>,

    @OneToMany
    @JoinColumn(name = "user_id")
    var userRequests: List<User>,

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
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var label: String? = null,
    // Entity Relationship
    @OneToMany(mappedBy = "status")
    var taskList: List<Task>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Status) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Status(id=$id, label='$label', taskList=$taskList)"
    }

}

@Entity
@Table(name = "task")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var title: String,
    var notes: String,
    @Temporal(TemporalType.DATE)
    var createDate: Date,
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    var dueDate: Date,

    // Entity Relationship
    @ManyToOne
    @JoinColumn(name = "priority_id", nullable = false, referencedColumnName = "id")
    var priority: Priority,
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false, referencedColumnName = "id")
    var status: Status? = null,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    var user: User? = null,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Task) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Task(id=$id, title='$title', notes='$notes', createDate=$createDate, dueDate=$dueDate, priority=$priority, status=$status, user=$user)"
    }

}

@Entity
@Table(name = "priority")
data class Priority(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var label: String? = null,
    // Entity Relationship
    @OneToMany(mappedBy = "priority")
    var taskList: List<Task>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Priority) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Priority(label='$label', taskList=$taskList, id=$id)"
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
    @JoinColumn(name = "user_id")
    var users:User,

    @OneToOne
    @JoinColumn(name = "asset_type_id")
    var assetType: AssetType,

    @OneToOne
    @JoinColumn(name = "ticket_reason_id")
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
    @OneToOne
    @JoinColumn(name = "ticket_reason_id")
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
@Table(name = "asset")
data class Asset(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var assetName: String? = null,

    @Column(name = "asset_type_id")
    var assetTypeId: Long? = null,

    @Column(name = "available")
    var available: Boolean,

    //Entity relationships
    @OneToOne
    @JoinColumn(name = "asset_id")
    var request: Request,

    @OneToOne
    @JoinColumn(name = "asset_type_id")
    var assetType: AssetType,
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
        return "User(id=$id, name=$assetName, asset_type_id=$assetTypeId, available=$available)"
    }
}

@Entity
@Table(name = "asset_type")
data class AssetType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var assetTypeName: String? = null,

    //Entity relationships
    @OneToOne
    @JoinColumn(name = "asset_type_id")
    var tickets: Ticket,

    @OneToOne
    @JoinColumn(name = "asset_type_id")
    var asset: Asset,
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
        return "User(id=$id, name=$assetTypeName)"
    }
}

@Entity
@Table(name = "state")
data class State(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var stateName: String? = null,

    //Entity relationships
    @OneToOne
    @JoinColumn(name = "state_id")
    var request: Request,
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
        return "User(id=$id, name=$stateName)"
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

    @Column(name = "classroom_id")
    var classroomId: Long? = null,

    @Column(name = "user_id")
    var userId: Long? = null,

    @Column(name = "date_hour")
    var dateHour: Date? = null,

    @Column(name = "state_id")
    var stateId: Long? = null,

    //Entity relationships
    @OneToOne
    @JoinColumn(name = "asset_id")
    var assets: Asset,

    @ManyToOne
    @JoinColumn(name="user_id")
    var users: User,

    @OneToOne
    @JoinColumn(name = "state_id")
    var state: State,
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

@Entity
@Table(name="class")
data class Class(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "name")
        var className: String? = null,

        @Column(name = "id_classroom")
        var idClassroom: String? = null,

        @Column(name = "id_teacher")
        var idTeacher: Int? = null,

){

   /* override fun toString(): String {
        return "Class(id='$id', name='$className', classroom='$classClassroom', teacher= '$classTeacher')"
    }*/
}

@Entity
@Table(name="classroom")
data class Classroom(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "name")
        var classroomName: String? = null,

        @Column(name = "status")
        var classroomStatus: Int? = null
){

    override fun toString(): String {
        return "Classroom(id='$id', name='$classroomName', status= '$classroomStatus')"
    }
}

@Entity
@Table(name="class_day")
data class ClassDay(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "day") // 1: Lunes , 7: Domingo
        var day: Int? = null,

        @Column(name = "id_class")
        var idClass: Int? = null,

        @Column(name = "class_time")
        var classTime: Int? = null,

){

    override fun toString(): String {
        return "ClassDay(id='$id', day='$day')"
    }
}

@Entity
@Table(name="inscription")
data class Inscription(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "id_user")
        var idUser: Int? = null,

        @Column(name = "id_class")
        var idClass: Int? = null
){

}

