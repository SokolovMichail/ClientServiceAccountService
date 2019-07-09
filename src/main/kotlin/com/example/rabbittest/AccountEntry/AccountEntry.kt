package com.example.rabbittest.AccountEntry

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "accounts")
class AccountEntry(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int = 0,
        var surname: String = "Pass",
        var account_number: String = "ERR_NO_ACC_REP") {
}