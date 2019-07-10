package com.example.rabbittest.accountrepository

import com.example.rabbittest.accountentry.AccountEntry
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository:JpaRepository<AccountEntry,Int>
{
    fun findBySurname(surname:String):Optional<AccountEntry>
}