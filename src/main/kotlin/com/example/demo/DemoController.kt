package com.example.demo

import com.example.demo.DemoTable.foo
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/")
@RestController
class DemoController {

    @Transactional
    @GetMapping("/insert")
    fun demo() {
        DemoTable.insert { it[foo] = "value" }
        DemoTable.insert { it[foo] = "too long value" } // will throw ExposedSqlException
    }

    @Transactional
    @GetMapping
    fun list(): List<String> = DemoTable.selectAll().map { it[foo] }
}

object DemoTable : LongIdTable() {
    val foo = varchar("foo", 100) // length doesn't match database schema on purpose, to cause an error
}
