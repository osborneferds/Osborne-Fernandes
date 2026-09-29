package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import com.example.data.model.DigitalCardProfile

object AndroidIntents {

    fun dialPhoneNumber(context: Context, phone: String) {
        try {
            val cleanPhone = phone.replace(" ", "")
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanPhone"))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open dialer: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, rawPhone: String, customMessage: String? = null) {
        try {
            // Remove '+' or any non-numeric characters for wa.me URL
            val cleanNumber = rawPhone.replace(Regex("[^0-9]"), "")
            val defaultMsg = "Hello Osborne! I found your digital business card and would like to discuss a potential project."
            val msg = customMessage ?: defaultMsg
            val url = "https://wa.me/$cleanNumber?text=${Uri.encode(msg)}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open WhatsApp: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(context: Context, email: String, subject: String = "Inquiry via Digital Card", body: String = "") {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                if (body.isNotEmpty()) {
                    putExtra(Intent.EXTRA_TEXT, body)
                }
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open email app: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWebUrl(context: Context, url: String) {
        try {
            val fullUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else {
                url
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open link: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openLocation(context: Context, address: String) {
        try {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open map: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun copyToClipboard(context: Context, text: String, label: String = "Copied text") {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "$label copied to clipboard!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to copy: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun addToContacts(context: Context, profile: DigitalCardProfile) {
        try {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, profile.fullName)
                putExtra(ContactsContract.Intents.Insert.JOB_TITLE, profile.jobTitle)
                putExtra(ContactsContract.Intents.Insert.COMPANY, "Freelancer")
                putExtra(ContactsContract.Intents.Insert.PHONE, profile.phone)
                putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, profile.whatsappPhone)
                putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
                putExtra(ContactsContract.Intents.Insert.EMAIL, profile.email)
                putExtra(ContactsContract.Intents.Insert.POSTAL, profile.location)
                putExtra(ContactsContract.Intents.Insert.NOTES, "Digital Business Card: ${profile.cardUrl}\nWebsite: ${profile.websiteUrl}")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback: copy contact details to clipboard or share
            copyToClipboard(context, buildVCard(profile), "vCard details")
            Toast.makeText(context, "Contacts manager unavailable. Contact info copied!", Toast.LENGTH_LONG).show()
        }
    }

    fun shareCard(context: Context, profile: DigitalCardProfile) {
        try {
            val shareText = """
                💼 ${profile.fullName}
                ${profile.jobTitle}
                
                🌐 Website: ${profile.websiteUrl}
                📱 Phone / WA: ${profile.phone} / ${profile.whatsappPhone}
                ✉️ Email: ${profile.email}
                📍 Location: ${profile.location}
                
                🔗 View Digital Card: ${profile.cardUrl}
                📅 Book a Meeting: ${profile.calendarBookingUrl}
            """.trimIndent()

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_SUBJECT, "${profile.fullName} - Digital Business Card")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share Digital Business Card")
            context.startActivity(shareIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open share menu: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun buildVCard(profile: DigitalCardProfile): String {
        return """
            BEGIN:VCARD
            VERSION:3.0
            FN:${profile.fullName}
            TITLE:${profile.jobTitle}
            ORG:Freelancer
            TEL;TYPE=CELL:${profile.phone}
            TEL;TYPE=WA:${profile.whatsappPhone}
            EMAIL:${profile.email}
            URL:${profile.websiteUrl}
            ADR:;;${profile.location};;;
            NOTE:Digital Business Card - ${profile.cardUrl}
            END:VCARD
        """.trimIndent()
    }
}
