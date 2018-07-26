package me.ivmg.telegram.dispatcher.handlers

import me.ivmg.telegram.dispatcher.filters.Filter
import me.ivmg.telegram.Bot
import me.ivmg.telegram.CommandHandleUpdate
import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.entities.Update

class CommandHandler(private val command: String, filter: Filter? = null, handler: HandleUpdate) : Handler(handler, filter) {

    constructor(command: String, filter: Filter? = null, handler: CommandHandleUpdate) : this(command, filter, CommandHandleUpdateProxy(handler))

    override val groupIdentifier: String = "CommandHandler"

    override fun checkUpdate(update: Update): Boolean {
        return (update.message?.text != null && update.message.text.startsWith("/") &&
            update.message.text.drop(1).split(" ")[0].split("@")[0] == command)
    }
}

private class CommandHandleUpdateProxy(private val handleUpdate: CommandHandleUpdate) : HandleUpdate {
    override fun invoke(bot: Bot, update: Update) {
        handleUpdate(bot, update, update.message?.text?.split("\\s+".toRegex())?.drop(1) ?: listOf())
    }
}