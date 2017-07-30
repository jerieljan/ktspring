package com.jerieljan.ktspring.config

import com.jerieljan.ktspring.models.MongoAuthentication
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired lateinit var mongoAuthentication: MongoAuthentication

    private val logger = LoggerFactory.getLogger("com.jerieljan.ktspring.config.WebSecurityConfig")

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
                ?.antMatchers("/static/**", "/webjars/**", "/js/**", "/css/**", "/forms/**")
                ?.permitAll()
                ?.anyRequest()?.authenticated()
                ?.and()
                ?.formLogin()
                ?.loginPage("/login")?.permitAll()
                ?.and()
                ?.logout()?.permitAll()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        // Builds the authentication process by using MongoAuthentication to fetch users
        // and using BCrypt as its password encoder.
        if (mongoAuthentication.isAvailable()) {
            logger.info("Authenticating with MongoDB, using BCrypt algorithm.")
            auth.userDetailsService(mongoAuthentication).passwordEncoder(passwordEncoder())
        } else {
            logger.warn("Cannot authenticate using MongoDB -- falling back to in-memory auth.")
            auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER")
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return mongoAuthentication
    }



}

