package com.backendmovsiuatango

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${url.priorities}")
class PriorityController(private val priorityService: PriorityService){


    @GetMapping
    @ResponseBody
    fun findAll() = priorityService.findAll()


    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = priorityService.findById(id)
}

@RestController
@RequestMapping("\${url.tasks}")
class TaskController(private val taskService: TaskService) {
    @GetMapping
    @ResponseBody
    fun findAll() = taskService.findAll()

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = taskService.findById(id)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun create(@RequestBody taskInput: TaskInput) : TaskResult? {
        return taskService.create(taskInput)
    }

    @Throws(NoSuchElementException::class)
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun update(@RequestBody taskInput: TaskInput) : TaskResult? {
        return taskService.update(taskInput)
    }

    @Throws(NoSuchElementException::class)
    @DeleteMapping("{id}")
    @ResponseBody
    fun deleteById(@PathVariable id:Long) {
        taskService.deleteById(id)
    }
}

@RestController
@RequestMapping("\${url.classes}")
class ClassController(private val classService: ClassService){

    @GetMapping
    @ResponseBody
    fun findAll() = classService.findAll()

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = classService.findById(id)
}

@RestController
@RequestMapping("\${url.requests}")
class RequestController(private val requestService: RequestService){

    @GetMapping
    @ResponseBody
    fun findAll() = requestService.findAll()

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = requestService.findById(id)
}

@RestController
@RequestMapping("\${url.user}")
class UserController(private val userService: UserService){
    @GetMapping
    @ResponseBody
    fun findAll() = userService.findAll()

    @Throws(NoSuchElementException::class)
    @GetMapping("{id}")
    @ResponseBody
    fun findById(@PathVariable id:Long) = userService.findById(id)
}