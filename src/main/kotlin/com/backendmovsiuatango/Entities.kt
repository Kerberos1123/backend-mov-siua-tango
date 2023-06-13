package com.backendmovsiuatango

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "reminder")
data class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(name = "reminder_date")
    var reminderDate: Date,
    // Entity Relationship
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Share the same primary key between 2 tables
    var task: Task,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Reminder) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Reminder(id=$id, reminderDate=$reminderDate, task=$task)"
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

    @ManyToOne(cascade = [CascadeType.MERGE])
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
@Table(name = "role")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var name: String,

    // Entity Relationship

    @ManyToMany
    @JoinTable(
        name = "role_privilege",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    var privilegeList: Set<Privilege>? = null,

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Role) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Role(id=$id, name='$name', privilegeList=$privilegeList)"
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
    var roleList: Set<Role>,

    ) {
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
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    var email: String? = null,
    @Temporal(TemporalType.DATE)
    var createDate: Date? = null,
    var enabled: Boolean,
    var tokenExpired: Boolean? = null,

    // Entity Relationship

    @OneToMany(mappedBy = "user")
    var userTickets: List<Ticket>? = null,

    @OneToMany(mappedBy = "user")
    var userRequests: List<Request>? = null,

    @OneToMany(mappedBy = "user")
    var taskList: List<Task>? = null,

    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roleList: Set<Role>? = null,

    ) {
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
        return "User(id=$id, firstName='$firstName', lastName='$lastName', password='$password', email='$email', createDate=$createDate, enabled=$enabled, tokenExpired=$tokenExpired, taskList=$taskList, roleList=$roleList)"
    }

}

//-------------------------------------------------------------------- Entities nacho


@Entity
@Table(name = "ticket")
data class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "user_id", insertable = false, updatable = false)
    var userId: Long? = null,

    @Column(name = "asset_type_id", insertable = false, updatable = false)
    var assetTypeId: Long? = null,

    @Column(name = "ticket_reason_id", insertable = false, updatable = false)
    var ticketReasonId: Long? = null,

    var detail: String? = null,

    //Entity relationships

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false, referencedColumnName = "id")
    var user: User? = null,

    @ManyToOne
    @JoinColumn(name = "asset_type_id",nullable = false, referencedColumnName = "id")
    var assetType: AssetType? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "ticket_reason_id",nullable = false, referencedColumnName = "id")
    var ticketReason: TicketReason? = null,


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
        return "Ticket(id=$id, user_id=$userId, asset_type_id=$assetTypeId, ticket_reason_id:$ticketReasonId, detail=$detail)"
    }
}

@Entity
@Table(name = "ticket_reason")
data class TicketReason(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(name = "name", insertable = false, updatable = false)
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
        return "TicketReason(id=$id, name=$reasonName)"
    }
}

@Entity
@Table(name = "asset")
data class Asset(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(name = "name", insertable = false, updatable = false)
    var assetName: String? = null,
    @Column(name = "asset_type_id", insertable = false, updatable = false)
    var assetTypeId: Long? = null,
    @Column(name = "available", insertable = false, updatable = false)
    var available: Boolean,

    //Entity relationships

    //ASSETTYPE DONE
    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "asset_type_id")
    var assetType: AssetType? = null,


){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Asset) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "Asset(id=$id, name=$assetName,  available=$available)"
    }
}

@Entity
@Table(name = "asset_type")
data class AssetType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(name = "name", insertable = false, updatable = false)
    var assetTypeName: String? = null,

    //Entity relationships

    /*
    @OneToMany(mappedBy = "assetType", cascade = [CascadeType.ALL])
    var tickets: List<Ticket>? = null
     */

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
        return "AssetType(id=$id, name=$assetTypeName)"
    }
}

@Entity
@Table(name = "requeststate")
data class RequestState(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(name = "name", insertable = false, updatable = false)
    var name: String? = null,

    //Entity relationships

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RequestState) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return "RequestState(id=$id, name=$name)"
    }
}

@Entity
@Table(name = "request")
data class Request(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "asset_id", insertable = false, updatable = false)
    var assetId: Long? = null,

    @Column(name = "classroom_id", insertable = false, updatable = false)
    var classroomId: Long? = null,

    @Column(name = "user_id",  insertable = false, updatable = false)
    var userId: Long? = null,

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    var dateHour: Date? = null,

    @Column(name = "state_id", insertable = false, updatable = false)
    var stateId: Long? = null,

    //Entity relationships

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false, referencedColumnName = "id")
    var user: User? = null,

    // ASSET
    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "asset_id")
    var assets: Asset,

    //REQUESTSTATE
    @OneToOne(cascade = [CascadeType.MERGE])
    @JoinColumn(name = "state_id")
    var state: RequestState,


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
        return "Request(id=$id, asset_id=$assetId, dateHour=$dateHour, state_id=$stateId)"
    }
}


//----------------------------------------Entities Jonathan
@Entity
@Table(name="class")
data class Class(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "name")
        var className: String? = null,

        @Column(name = "id_classroom", insertable = false, updatable = false)
        var idClassroom: Long? = null,

        @Column(name = "id_teacher", insertable = false, updatable = false)
        var idTeacher: Long? = null,

        @Temporal(TemporalType.DATE)
        var createDate: Date? = null,

        // Entity Relationships
        @OneToOne(cascade = [CascadeType.MERGE])
        @JoinColumn(name = "id_classroom")
        var classClassroom: Classroom? = null,

        @OneToOne(cascade = [CascadeType.MERGE])
        @JoinColumn(name = "id_teacher")
        var classTeacher: User? = null,

        @ManyToMany
        @JoinTable(
                name = "class_inscriptions",
                joinColumns = [JoinColumn(name = "class_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
        )
        var userList: Set<User>? = null,

        @ManyToMany
        @JoinTable(
                name = "class_days",
                joinColumns = [JoinColumn(name = "class_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "day_id", referencedColumnName = "id")]
        )
        var daysList: Set<Day>? = null

){


    override fun toString(): String {
        return "Class(id='$id', name='$className', classroom='$idClassroom', teacher= '$idTeacher')"
    }
}

@Entity
@Table(name="classroom")
data class Classroom(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "name")
        var classroomName: String? = null,

        @Column(name = "state_id", insertable = false, updatable = false)
        var idState: Long? = null,

        @Column(name = "create_date")
        var createDate: Date? = null,

        // Entity Relationships
        @OneToOne(cascade = [CascadeType.MERGE])
        @JoinColumn(name = "state_id", referencedColumnName = "id")
        var classroomState: ClassroomState? = null,

        ){

    override fun toString(): String {
        return "Classroom(id='$id', name='$classroomName', state_id= '$idState')"
    }
}

@Entity
@Table(name="classroom_state")
data class ClassroomState(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "name", insertable = false, updatable = false)
        var stateName: String? = null,

        @Column(name = "create_date")
        var createDate: Date? = null,
){
    // Entity Relationships

}

@Entity
@Table(name="day")
data class Day(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(name = "day") // 1: Lunes , 7: Domingo
        var day: Int? = null,

        @Column(name = "start_time")
        var startTime: String? = null,

        @Column(name = "finish_time")
        var finishTime: String? = null,

        @Column(name = "create_date")
        var createDate: Date? = null


){
}



