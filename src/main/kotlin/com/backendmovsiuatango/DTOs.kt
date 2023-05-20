package com.backendmovsiuatango

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*


data class StatusDetails(
    var id: Long? = null,
    var label: String? = null,
)

data class PriorityDetails(
    var id: Long? = null,
    var label: String? = null,
)

data class PrivilegeDetails(
    var id: Long? = null,
    var name: String? = null,
)

data class RoleDetails(
    var id: Long? = null,
    var name: String? = null,
    var privileges: List<PrivilegeDetails>? = null,
)

data class TaskInput(
    var id: Long? = null,
    var title: String? = null,
    var notes: String? = null,
    var createDate: Date? = null,
    @JsonFormat(pattern="dd/MM/yyyy")
    var dueDate: Date? = null,
    var priority: PriorityDetails? = null,
    var status: StatusDetails? = null,
    var user: UserInput? = null,
)

data class TaskResult(
    var id: Long,
    var title: String,
    var notes: String,
    var createDate: Date,
    var dueDate: Date,
    var priority: PriorityDetails,
    var status: StatusDetails,
)

data class UserInput(
    var id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
    var enabled: Boolean? = null,
    var createDate: Date? = null,
    var roleList: List<RoleDetails>? = null,
)

data class UserLoginInput(
    var username: String = "",
    var password: String = "",
)

data class UserResult(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var enabled: Boolean?,
    var tokenExpired: Boolean?,
    var createDate: Date?,
    var roleList: List<RoleDetails>?,
)

data class UserSignUpInput(
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null,
)
//-----------------------------------------------------DTOs Nacho
data class TicketInput(
    var id:Long? = null,
    var user: UserResult?=null,
    var assetType:AssetTypeDetails?=null,
    var ticketReasons:TicketReasonDetails?=null,
    var detail:String?=null,
)

data class TicketResult(
    var id:Long,
    var user: UserResult,
    var assetType:AssetTypeDetails,
    var ticketReasons:TicketReasonDetails,
    var detail:String,
)

data class TicketReasonDetails(
    var id:Long?=null,
    var name:String?=null,
)

data class AssetDetails(
    var id: Long? = null,
    var assetName: String? = null,
    var assetType: AssetTypeDetails? = null,
    var available: Boolean?=null,
)

data class AssetTypeDetails(
    var id:Long?=null,
    var name:String?=null
)

data class RequestStateDetails(
    var id:Long?=null,
    var name:String?=null,
)

data class RequestInput(
    var id: Long? = null,
    var assets: AssetDetails? = null,
    //var classroom: ClassroomResult? = null,
    var classroomId: Long? = null,
    var user: UserResult? = null,
    @JsonFormat(pattern="dd/MM/yyyy")
    var dateHour: Date? = null,
    var state:RequestStateDetails?=null,
)

data class RequestResult(
    var id: Long,
    var assets: AssetDetails,
    var classroomId: Long,
    var user: UserResult,
    var dateHour: Date,
    var state:RequestStateDetails,
)

//-----------------------------------------------------DTOs Jonathan
data class ClassInput(
        var id: Long? = null,
        var className: String? = null,
        var classTeacher: UserResult? = null,
        var classClassroom: ClassroomResult? = null,
        var createDate: Date? = null
)

data class ClassResult(
    var id: Long,
    var className: String,
    var classTeacher: UserResult,
    var classClassroom: ClassroomResult,
    var createDate: Date
)

data class ClassroomInput(
        var id: Long? = null,
        var name: String? = null,
        var state: ClassroomStateDetails? = null,
        var createDate: Date
)

data class ClassroomResult(
        var id: Long,
        var classroomName: String,
        var classroomState: ClassroomStateDetails,
        var createDate: Date,
)

data class ClassroomStateDetails(
        var id:Long?=null,
        var name:String?=null,
)

data class DayInput(
        var id: Long? = null,
        var day: Int? = null,
        var startTime: String? = null,
        var finishTime: String? = null,
        var createDate: Date,
)

data class DayResult(
        var id: Long,
        var day: Int,
        var startTime: String,
        var finishTime: String,
        var createDate: Date,
)