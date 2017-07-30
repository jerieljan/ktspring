package com.jerieljan.ktspring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "ktspring")
class AppConfig {

    var author: String = ""

}
