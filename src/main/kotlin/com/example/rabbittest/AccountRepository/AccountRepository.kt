package com.example.rabbittest.AccountRepository

import com.example.rabbittest.AccountEntry.AccountEntry
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository:JpaRepository<AccountEntry,Int>
{
    fun findBySurname(surname:String):Optional<AccountEntry>
}