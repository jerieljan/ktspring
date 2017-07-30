package com.jerieljan.ktspring.repositories

import com.jerieljan.ktspring.models.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String>
