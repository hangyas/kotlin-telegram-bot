package me.ivmg.telegram.dispatcher.handlers

import me.ivmg.telegram.HandleUpdate
import me.ivmg.telegram.dispatcher.filters.Filter
import me.ivmg.telegram.entities.Update

class TextHandler(private val text: String? = null, filter: Filter? = null, handler: HandleUpdate) : Handler(handler, filter) {
    override val groupIdentifier: String = "CommandHandler"

    override fun checkUpdate(update: Update): Boolean {
        if (update.message?.text != null && text == null) return true
        else if (text != null) {
            return (update.message?.text != null && update.message.text.toLowerCase().contains(text.toLowerCase()))
        }
        return false
    }
}