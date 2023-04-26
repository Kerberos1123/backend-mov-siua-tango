package com.backendmovsiuatango

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.jdbc.Sql

@Profile("initlocal")
@SpringBootTest
@Sql("/import-database.sql")

class LoadInitData (
    @Autowired
    val taskRepository: TaskRepository,
){
    @Test
    fun testTaskFindAll() {

        val taskList: List<Task> = taskRepository.findAll()
        Assertions.assertTrue(taskList.size == 2)

    }
}
