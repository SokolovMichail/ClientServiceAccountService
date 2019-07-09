package com.example.rabbittest.AccountMessageHandler

import com.example.rabbittest.AccountRepository.AccountRepository
import com.google.gson.Gson
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
@RabbitListener(queues = ["requests"])
class AccountServerMessageHandler(
                                  val repo: AccountRepository,
                                  val template: RabbitTemplate) {
    var gson = Gson()

    @RabbitHandler
    fun handleAccountRequest(inp: String) {
        logger.info { "Received input on request queue: ${inp} " }
        val c = repo.findBySurname(inp)
        if (c.isPresent) {
            val tmp = c.get()
            val responseMessage = JSONMessage(inp,tmp.account_number)
            logger.info { "Successfully found a client with surname = ${tmp.surname}" }
            this.template.convertAndSend("replies", gson.toJson(responseMessage))
            logger.info { "Successfully send account number of surname = ${inp}" }
        } else {
            logger.error { "Failed to find a client with surname = ${inp}" }
            //Т.К поведение на таких случаях не специфицировали, то при варианте, что
            //в базе аккаунтов нет такой фамилии, отправляем спец.ключ - ERR_NO_ACC_REP
            //Ну и закидываем сообщение в ошибку
            this.template.convertAndSend("replies",
                    gson.toJson(JSONMessage(inp)))
            this.template.convertAndSend("errors",inp)
        }
    }
}
