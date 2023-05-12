package com.backendmovsiuatango

import org.mapstruct.*
import java.time.LocalDateTime

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface PriorityMapper {
    fun priorityToPriorityDetails(
        priority: Priority,
    ): PriorityDetails

    fun priorityListToPriorityDetailsList(
        priorityList: List<Priority>,
    ): List<PriorityDetails>
}

@Mapper(
    imports = [LocalDateTime::class],
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface TaskMapper {
    fun taskToTaskResult(
        task: Task,
    ): TaskResult

    fun taskListToTaskListResult(
        taskList: List<Task>,
    ): List<TaskResult>

    @Mapping(target = "createDate", defaultExpression = "java(new java.util.Date())")
    fun taskInputToTask(
        taskInput: TaskInput,
    ): Task

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun taskInputToTask(dto: TaskInput, @MappingTarget task: Task)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RoleMapper {
    fun roleListToRoleDetailsList(
        roleList: Set<Role>?,
    ): Set<RoleDetails>
}

//------------------------------------mappers nacho
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TicketMapper{
    fun ticketToTicketResult(
        ticket:Ticket,
    ):TicketResult

    fun ticketListToTicketListResult(
        ticketList: List<Ticket>,
    ): List<TicketResult>


   /* fun ticketInputToTicket(
        ticketInput: TicketInput,
    ): Ticket*/

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun ticketInputToTicket(dto: TicketInput, @MappingTarget ticket: Ticket)

}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TicketReasonMapper {
    fun ticketReasonToTicketReasonDetails(
        ticketReason: TicketReason,
    ): TicketReasonDetails

    fun ticketReasonListToTicketReasonDetailsList(
        ticketReasonList: Set<TicketReason>?,
    ): Set<TicketReasonDetails>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AssetMapper {
    fun assetListToAssetDetailsList(
        assetList: Set<Asset>?,
    ): Set<AssetDetails>

    fun assetToAssetDetails(
        asset: Asset,
    ): AssetDetails
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AssetTypeMapper {
    fun assetTypeListToAssetTypeDetailsList(
        assetTypeList: Set<AssetType>?,
    ): Set<AssetTypeDetails>

    fun typeToTypeDetails(
        assetType: AssetType,
    ): PriorityDetails
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RequestStateMapper {
    fun requestStateListToRequestStateDetailsList(
        requestStateList: Set<RequestState>?,
    ): Set<RequestStateDetails>

    fun requestStateToRequestStateDetails(
        requestState: RequestState,
    ): RequestStateDetails
}

@Mapper(
    imports = [LocalDateTime::class],
    componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface RequestMapper{
    fun requestToRequestResult(
        request:Request,
    ):RequestResult

    fun requestListToRequestListResult(
        requestList: List<Request>,
    ): List<RequestResult>

    @Mapping(target = "date_hour", defaultExpression = "java(new java.util.Date())")
    fun requestInputToRequest(
        requestInput: RequestInput,
    ): Request

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun requestInputToRequest(dto: RequestInput, @MappingTarget request: Request)

}


//--------------------------mappers Jonathan
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ClassMapper {
    fun classToClassResults(
            course: Class,
    ): ClassResult

    fun classListToClassListResult(
            classList: List<Class>,
    ): List<ClassResult>

    @Mapping(target = "createDate", defaultExpression = "java(new java.util.Date())")
    fun classInputToClass(
            classInput: ClassInput,
    ): Class

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun classInputToClass(dto: ClassInput, @MappingTarget course: Class)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ClassroomMapper {
    fun classroomToClassroomResults(
            classroom: Classroom,
    ): ClassroomResult

    fun classroomListToClassroomListResult(
            classroomList: List<Classroom>,
    ): List<ClassroomResult>

    @Mapping(target = "createDate", defaultExpression = "java(new java.util.Date())")
    fun classroomInputToClassroom(
            classroomInput: ClassroomInput,
    ): Classroom

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun classroomInputToClassroom(dto: ClassroomInput, @MappingTarget classroom: Classroom)
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ClassroomStateMapper {
    fun clStateListToClassroomStateDetailsList(
            clStateList: Set<ClassroomState>?,
    ): Set<ClassroomStateDetails>
}

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface DayMapper {
    fun dayToDayResults(
            day: Day,
    ): DayResult

    fun dayListToDayListResult(
            dayList: List<Day>,
    ): List<DayResult>

    @Mapping(target = "createDate", defaultExpression = "java(new java.util.Date())")
    fun dayInputToDay(
            dayInput: DayInput,
    ): Day

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun dayInputToDay(dto: DayInput, @MappingTarget day: Day)
}
