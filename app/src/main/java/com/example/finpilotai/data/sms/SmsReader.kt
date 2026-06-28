package com.example.finpilotai.data.sms

import android.content.Context
import android.provider.Telephony
import com.example.finpilotai.domain.model.SmsMessage

class SmsReader(
    private val context: Context
) {

    fun getAllMessages(): List<SmsMessage> {

        val messages = mutableListOf<SmsMessage>()

        val cursor = context.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            null,
            null,
            null,
            "${Telephony.Sms.DATE} DESC"
        )

        cursor?.use {

            val idIndex = it.getColumnIndexOrThrow(Telephony.Sms._ID)
            val addressIndex = it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
            val bodyIndex = it.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val dateIndex = it.getColumnIndexOrThrow(Telephony.Sms.DATE)

            while (it.moveToNext()) {

                messages.add(
                    SmsMessage(
                        id = it.getLong(idIndex),
                        address = it.getString(addressIndex),
                        body = it.getString(bodyIndex),
                        date = it.getLong(dateIndex)
                    )
                )

            }

        }

        return messages

    }

}