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

