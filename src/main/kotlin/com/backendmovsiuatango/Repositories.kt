package com.backendmovsiuatango

import net.bytebuddy.build.Plugin.Factory.UsingReflection.Priority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.scheduling.config.Task
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PriorityRepository: JpaRepository<Priority, Long>

@Repository
interface TaskRepository:JpaRepository< Task,Long>{

    fun findAllByDueDateIs(dueDate:Date) : List<Task>?
}

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