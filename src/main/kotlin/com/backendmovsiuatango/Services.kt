package com.backendmovsiuatango
//ESTO ES DE SECURITY
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
interface PriorityService {

    fun findAll(): List<PriorityDetails>?

    fun findById(id: Long): PriorityDetails?
}

@Service
class AbstractPriorityService(
    @Autowired
    val priorityRepository: PriorityRepository,

    @Autowired
    val priorityMapper: PriorityMapper,

    ) : PriorityService {

    override fun findAll(): List<PriorityDetails>? {
        return priorityMapper.priorityListToPriorityDetailsList(
            priorityRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): PriorityDetails? {
        val priority: Priority = priorityRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Priority with the id: %s not found!", id))
        return priorityMapper.priorityToPriorityDetails(priority)
    }
}

interface TaskService {

    fun findAll(): List<TaskResult>?

    fun findById(id: Long): TaskResult?

    fun create(taskInput: TaskInput): TaskResult?

    fun update(taskInput: TaskInput): TaskResult?

    fun deleteById(id: Long)
}

@Service
class AbstractTaskService  (
    @Autowired
    val taskRepository: TaskRepository,
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val statusRepository: StatusRepository,
    @Autowired
    val taskMapper: TaskMapper,
) : TaskService {

    override fun findAll(): List<TaskResult>? {
        return taskMapper.taskListToTaskListResult(
            taskRepository.findAll()
        )
    }


    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): TaskResult? {
        val task: Task = taskRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Task with the id: %s not found!", id))
        return taskMapper.taskToTaskResult(task)
    }


    override fun create(taskInput: TaskInput): TaskResult? {
        val task: Task = taskMapper.taskInputToTask(taskInput)
        if (task.user == null){
            val user = userRepository.findByEmail(LoggedUser.get()).orElse(null) //LoggedUser viene de Security.kt
            task.user = user
        }
        if (task.status == null) {
            val status = statusRepository.findByLabel("Pending").orElse(null)
            task.status = status
        }
        return taskMapper.taskToTaskResult(
            taskRepository.save(task)
        )
    }


    @Throws(NoSuchElementException::class)
    override fun update(taskInput: TaskInput): TaskResult? {
        val task: Task = taskRepository.findById(taskInput.id!!).orElse(null)
            ?: throw NoSuchElementException(String.format("The Task with the id: %s not found!", taskInput.id))
        var taskUpdated: Task = task
        taskUpdated.priority = Priority()
        taskMapper.taskInputToTask(taskInput, taskUpdated)
        return taskMapper.taskToTaskResult(taskRepository.save(taskUpdated))
    }


    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        taskRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Task with the id: %s not found!", id))

        taskRepository.deleteById(id)
    }

}

interface UserService{
    fun findAll(): List<UserResult>?

    fun findById(id: Long): UserResult?

    fun create(userInput: UserInput): UserResult?

    fun update(userInput: UserInput): UserResult?

    fun deleteById(id: Long)
}


@Service
class AbstarctUserService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val userMapper: UserMapper
):UserService{
    override fun findAll(): List<UserResult>? {
        return userMapper.userListToUserListResult(
            userRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): UserResult? {
        val user:User = userRepository.findById(id).orElse(null)
            ?:throw NoSuchElementException(String.format("The User with the id: %s not found!",id))
        return userMapper.userToUserResult(user)
    }

    override fun create(userInput: UserInput): UserResult? {
        val user: User = userMapper.userInputToUser(userInput)
        if (user.tokenExpired == null) {
            user.tokenExpired = true
        }
        return userMapper.userToUserResult(
            userRepository.save(user)
        )

    }

    @Throws(NoSuchElementException::class)
    override fun update(userInput: UserInput): UserResult? {
        val user: User = userRepository.findById(userInput.id!!).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the id: %s not found!", userInput.id))
        var userUpdated: User = user
        userMapper.userInputToUser(userInput, userUpdated)
        return userMapper.userToUserResult(userRepository.save(userUpdated))
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        userRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The User with the id: %s not found!", id))

        userRepository.deleteById(id)
    }

}



//------------------------------------------------ ESTO ES DE SECURITY

@Service
@Transactional
class AppUserDetailsService(
    @Autowired
    val userRepository: UserRepository,
    @Autowired
    val roleRepository: RoleRepository,
) : UserDetailsService {


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        //modelo user details, viene de spring
        val userAuth: org.springframework.security.core.userdetails.User

        //usamos uso de repositorio con findbyEmail con el username que le mandamos
        val user: User = userRepository.findByEmail(username).orElse(null)

            //si no existe manda este error
            ?: return org.springframework.security.core.userdetails.User(
                "", "", true, true, true, true,
                getAuthorities(Arrays.asList(
                    roleRepository.findByName("ROLE_USER").get())))

        //si el usuario existe en la BD lo retorno a response con sus datos
        userAuth = org.springframework.security.core.userdetails.User(
            user.email, user.password, user.enabled, true, true,
            true, getAuthorities(user.roleList!!.toMutableList()))

        return userAuth
    }

    private fun getAuthorities(roles: MutableList<Role>, ): Collection<GrantedAuthority?> {
        return getGrantedAuthorities(getPrivileges(roles))
    }

    private fun getPrivileges(roles: MutableList<Role>?): List<String> {
        val privileges: MutableList<String> = ArrayList()
        val collection: MutableList<Privilege> = ArrayList()
        if (roles != null) {
            for (role in roles) {
                collection.addAll(role.privilegeList)
            }
        }
        for (item in collection) {
            privileges.add(item.name)
        }
        return privileges
    }

    private fun getGrantedAuthorities(privileges: List<String>): List<GrantedAuthority?> {
        val authorities: MutableList<GrantedAuthority?> = ArrayList()

        for (privilege in privileges) {
            authorities.add(SimpleGrantedAuthority(privilege))
        }
        return authorities
    }

}


//----------------------------Services nacho-----------

interface RequestService{
    fun findAll():List<RequestResult>?

    fun findById(id: Long): RequestResult?

    fun create(requestInput: RequestInput):RequestResult?

    fun update(taskInput: RequestInput): RequestResult?

    fun deleteById(id:Long)
}

@Service
class AbstractRequestService(
    @Autowired
    val requestRepository: RequestRepository,

    @Autowired
    val userRepository: UserRepository,

    @Autowired
    val stateRepository: StateRepository,

    @Autowired
    val requestMapper: RequestMapper,
):RequestService{
    override fun findAll(): List<RequestResult>? {
        return requestMapper.requestListToRequestListResult(
            requestRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): RequestResult? {
        val request:Request = requestRepository.findById(id).orElse(null)
            ?:throw NoSuchElementException(String.format("The Request with the id: %s not found!",id))
        return requestMapper.requestToRequestResult(request)
    }

    override fun create(requestInput: RequestInput): RequestResult? {
        val request: Request = requestMapper.requestInputToRequest(requestInput)
        if(request.user == null){
            val user = userRepository.findByEmail(LoggedUser.get()).orElse(null)
            request.user = user
        }
        if(request.state == null){
            val state = stateRepository.findByName("Pending").orElse(null)
            request.state = state
        }

        return requestMapper.requestToRequestResult(
            requestRepository.save(request)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun update(requestInput: RequestInput): RequestResult? {
        val request: Request = requestRepository.findById(requestInput.id!!).orElse(null)

            ?: throw NoSuchElementException(String.format("The Request with the id: %s not found!", requestInput.id))

        var requestUpdated: Request = request

        requestMapper.requestInputToRequest(requestInput, requestUpdated)

        return requestMapper.requestToRequestResult(requestRepository.save(requestUpdated))
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        requestRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Request with the id: %s not found!", id))
        requestRepository.deleteById(id)
    }
}

interface TicketService{
    fun findAll():List<TicketResult>?

    fun findById(id: Long): TicketResult?

    fun create(ticketInput: TicketInput):TicketResult?

    fun deleteById(id:Long)
}

@Service
class AbstractTicketService(
    @Autowired
    val ticketRepository: TicketRepository,

    @Autowired
    val userRepository: UserRepository,

    @Autowired
    val ticketMapper: TicketMapper,
):TicketService{
    override fun findAll(): List<TicketResult>? {
        return ticketMapper.ticketListToTicketListResult(
            ticketRepository.findAll()
        )
    }

    override fun create(ticketInput: TicketInput): TicketResult? {
        val ticket:Ticket = ticketMapper.ticketInputToTicket(ticketInput)
        if(ticket.user == null){
            val user = userRepository.findByEmail(LoggedUser.get()).orElse(null)
            ticket.user = user
        }

        return ticketMapper.ticketToTicketResult(
            ticketRepository.save(ticket)
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): TicketResult? {
        val ticket: Ticket = ticketRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Ticket with the id: %s not found!", id))
        return ticketMapper.ticketToTicketResult(ticket)
    }
    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        ticketRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Ticket with the id: %s not found!", id))

        ticketRepository.deleteById(id)
    }
}

//----------------------------Services Jonathan-----------

interface ClassService {

    fun findAll(): List<ClassResult>?

    fun findById(id: Long): ClassResult?

    fun create(taskInput: ClassInput): ClassResult?

    fun update(taskInput: ClassInput): ClassResult?

    fun deleteById(id: Long)
}

@Service
class AbstractClassService(
        @Autowired
        val classRepository: ClassRepository,

        @Autowired
        val userRepository: UserRepository,

        @Autowired
        val classMapper: ClassMapper,

        ) : ClassService {

    override fun findAll(): List<ClassResult>? {
        return classMapper.classListToClassListResult(
                classRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): ClassResult? {
        val course: Class = classRepository.findById(id).orElse(null)
                ?: throw NoSuchElementException(String.format("The Class with the id: %s not found!", id))
        return classMapper.classToClassResults(course)
    }

    override fun create(classInput: ClassInput): ClassResult? {

        val clase : Class = classMapper.classInputToClass(classInput)
        /*if (clase.userList == null){
            val userx = userRepository.findByEmail(LoggedUser.get()).orElse(null) //LoggedUser viene de Security.kt

        }*/
        return classMapper.classToClassResults(
            classRepository.save(clase)
        )

    }

    @Throws(NoSuchElementException::class)
    override fun update(classInput: ClassInput): ClassResult? {
        val clase: Class = classRepository.findById(classInput.id!!).orElse(null)

            ?: throw NoSuchElementException(String.format("The Class with the id: %s not found!", classInput.id))

        var classUpdated: Class = clase

        classMapper.classInputToClass(classInput, classUpdated)
        return classMapper.classToClassResults(classRepository.save(classUpdated))
    }

    @Throws(NoSuchElementException::class)
    override fun deleteById(id: Long) {
        classRepository.findById(id).orElse(null)
            ?: throw NoSuchElementException(String.format("The Class with the id: %s not found!", id))

        classRepository.deleteById(id)
    }
}


interface ClassroomService {

    fun findAll(): List<ClassroomResult>?

    fun findById(id: Long): ClassroomResult?
}

@Service
class AbstractClassroomService(
        @Autowired
        val classroomRepository: ClassroomRepository,

        @Autowired
        val classroomMapper: ClassroomMapper,

        ) : ClassroomService {

    override fun findAll(): List<ClassroomResult>? {
        return classroomMapper.classroomListToClassroomListResult(
                classroomRepository.findAll()
        )
    }

    @Throws(NoSuchElementException::class)
    override fun findById(id: Long): ClassroomResult? {
        val course: Classroom = classroomRepository.findById(id).orElse(null)
                ?: throw NoSuchElementException(String.format("The Classroom with the id: %s not found!", id))
        return classroomMapper.classroomToClassroomResults(course)
    }
}