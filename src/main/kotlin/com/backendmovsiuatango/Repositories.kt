package com.backendmovsiuatango

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PriorityRepository: JpaRepository<Priority, Long>

@Repository
interface TaskRepository : JpaRepository<Task, Long>

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName (@Param("name") name : String) : Optional<Role>
}

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(@Param("email") email : String) : Optional<User>
}

@Repository
interface StatusRepository : JpaRepository<Status, Long> {
    fun findByLabel(@Param("label") label : String) : Optional<Status>
}

@Repository
interface TicketRepository: JpaRepository<Ticket,Long>

@Repository
interface RequestRepository: JpaRepository<Request,Long>

@Repository
interface StateRepository: JpaRepository<RequestState,Long>{
    fun findByName(@Param("name")name : String) : Optional<RequestState>
}

@Repository
interface ReasonRepository: JpaRepository<TicketReason,Long>

@Repository
interface AssetTypeRepository: JpaRepository<AssetType,Long> {
    fun findByName(@Param("name") name: String): Optional<AssetType>
}
    @Repository
interface ClassRepository: JpaRepository<Class, Long>

@Repository
interface ClassroomRepository: JpaRepository<Classroom, Long>
