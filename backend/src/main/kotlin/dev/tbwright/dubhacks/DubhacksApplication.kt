package dev.tbwright.dubhacks

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DubhacksApplication

fun main(args: Array<String>) {
	runApplication<DubhacksApplication>(*args)
}
