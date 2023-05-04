package com.backendmovsiuatango

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.jdbc.Sql
import javax.transaction.Transactional

@Profile("initlocal")
@SpringBootTest
@Sql("/import-database.sql")

@Transactional
class LoadInitData (
    @Autowired
    val taskRepository: TaskRepository,

    @Autowired
    val userRepository: UserRepository

){
    @Test
    fun testEjemplo() {

        val taskList: List<Task> = taskRepository.findAll()

        Assertions.assertTrue(taskList.size == 2)

    }

    @Test   //------------- TEST DANIEL
    fun test1(){

        val userList: List<User> = userRepository.findAll()

        Assertions.assertTrue(userList.size == 3)
        
    }
    @Test   //------------- TEST JONATHAN
    fun test2(){

    }
    @Test   //------------- TEST NACHO
    fun test3(){

    }


}
