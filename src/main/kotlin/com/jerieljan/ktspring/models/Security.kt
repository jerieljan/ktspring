package com.jerieljan.ktspring.models

import com.jerieljan.ktspring.repositories.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Document(collection = "user")
data class User(@Id val userName: String, val password: String, val roles: List<String>) {
    fun getAuthorities(): List<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }.toList()
    }
}

@Component
class MongoAuthentication : UserDetailsService {

    //Typical admin:admin user.
    val DEFAULT_ADMIN_USERNAME = "admin"
    val DEFAULT_ADMIN_PASSWORD = "$2a$10$1R3fMiznVI5TWZn4ls5UKOLxonrgJciDF3KfBD3oy967yzWCFqv5O"
    val DEFAULT_ADMIN_ROLES = listOf("USER", "ADMIN")

    private val logger = LoggerFactory.getLogger("com.jerieljan.ktspring.models.security.MongoAuthentication")

    @Qualifier("userRepository") @Autowired lateinit var userRepository: UserRepository

    /**
     * Checks the availability of this service.
     * Returns false if the user repository is unusable and if lookups don't work.
     * @return
     */
    fun isAvailable(): Boolean {
        return userRepository.findAll() != null
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails? {
        val user = userRepository.findOne(s)
        if (user != null) {
            logger.info("Logging in as: " + user.userName)
            return org.springframework.security.core.userdetails.User(user.userName, user.password, user.getAuthorities())
        }
        return null
    }

    /**
     * Upon component initialization, create a default administrator
     * if necessary.
     */
    @Autowired
    fun MongoAuthentication() {
        createDefaultAdministrator()
    }


    /**
     * Creates an administrator for the system if there are no administrators present.
     * See the constants for the default admin credentials.
     */
    private fun createDefaultAdministrator() {
        if (isAvailable() && userRepository.findOne(DEFAULT_ADMIN_USERNAME) == null) {
            logger.info("Administrator not found -- creating default administrator.")
            val admin = User(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD, DEFAULT_ADMIN_ROLES)
            logger.info("Administrator " + admin.userName + " created.")
            userRepository.insert(admin)
        }
    }


}