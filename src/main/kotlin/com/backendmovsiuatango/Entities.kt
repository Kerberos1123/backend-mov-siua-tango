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
    var privilegeList: Set<Privilege>,

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


//-------------------------------------------------------------------- Entities nacho

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
    var createDate: Date? = null,
    var enabled: Boolean,
    var tokenExpired: Boolean? = null,

    // Entity Relationship


    /*
    //TICKETS DONE
    @OneToMany(mappedBy = "user")
    var tickets: List<Ticket>?,

    //REQUESTS DONE
    @OneToMany(mappedBy = "user")
    var requests: List<Request>?,


     */

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


/*
@Entity
@Table(name = "ticket")
data class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "user_id")
    var userId: Long? = null,

    @Column(name = "asset_type_id") // explicitly specify the physical column name
    var assetTypeId: Long? = null,

    @Column(name = "ticket_reason_id")
    var ticketReasonId: Long? = null,

    var detail: String? = null,

    //Entity relationships

    //USER DONE
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    var user: User? = null,

    //ASSETTYPE DONE
    @OneToOne
    @JoinColumn(name = "asset_type_id", referencedColumnName = "id")
    var assetType: AssetType,

    //TICKETREASON DONE
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
        return "Ticket(id=$id, user_id=$userId, asset_type_id=$assetTypeId, ticket_reason_id:$ticketReasonId, detail=$detail)"
    }
}

@Entity
@Table(name = "ticket_reason")
data class TicketReason(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var reasonName: String? = null,

    //Entity relationships

    //TICKET DONE
    @OneToOne(mappedBy = "ticketReason")
    var ticket: Ticket? = null,

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
    var assetName: String? = null,
    //var assetTypeId: Long? = null,
    var available: Boolean,

    //Entity relationships

    //REQUEST DONE
    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    var request: Request? = null,

    //ASSETTYPE DONE
    @OneToOne
    @JoinColumn(name = "asset_type_id", referencedColumnName = "id")
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
    var assetTypeName: String? = null,

    //Entity relationships

    //TICKET DONE
    @OneToOne(mappedBy = "assetType")
    var ticket: Ticket? = null,

    //ASSET DONE
    @OneToOne(mappedBy = "assetType")
    var asset: Asset? = null,

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
    var stateName: String? = null,

    //Entity relationships

    //REQUEST DONE
    @OneToOne(mappedBy = "state")
    var request: Request,
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
        return "RequestState(id=$id, name=$stateName)"
    }
}

@Entity
@Table(name = "request")
data class Request(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var assetId: Long? = null,

    var classroomId: Long? = null,

   // var userId: Long? = null,

    var dateHour: Date? = null,

    var stateId: Long? = null,

    //Entity relationships

    // ASSET
    @OneToOne(mappedBy = "request")
    var assets: Asset? = null,

    //USER
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    var user: User? = null,

    //REQUESTSTATE
    @OneToOne
    @JoinColumn(name = "request_state_id", referencedColumnName = "id")
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


 */

//----------------------------------------Entities Jonathan
@Entity
@Table(name="class")
data class Class(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var className: String? = null,
    var classClassroom: String? = null,
    var classTeacher: Int? = null,
    var studentsGroup: Int? = null,
    var classHorary: Int? = null,
){

    override fun toString(): String {
        return "Class(id='$id', name='$className', classroom='$classClassroom', teacher= '$classTeacher', students= '$studentsGroup', horary= '$classHorary')"
    }
}

@Entity
@Table(name="horary")
data class Horary(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "day")
    var dayId: String? = null

){

    override fun toString(): String {
        return "Horary(id='$id', day='$dayId')"
    }
}

@Entity
@Table(name="day")
data class Day(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "day_number")
    var dayNumber: Int? = null,

    @Column(name = "hours")
    var dayHours: Int? = null
){

}

@Entity
@Table(name="group")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "id_student")
    var idStudent: Int? = null

){

}

@Entity
@Table(name="classroom")
data class Classroom(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name = "name")
    var classroomName: String? = null,

    @Column(name = "units")
    var classroomUnits: String? = null,

    @Column(name = "status")
    var classroomStatus: Int? = null
){

    override fun toString(): String {
        return "Classroom(id='$id', name='$classroomName', status= '$classroomStatus')"
    }
}



