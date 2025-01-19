package com.kotlinSpring.exception

import org.apache.logging.log4j.message.Message

class CourseNotFoundException(message: String) : RuntimeException(message)
