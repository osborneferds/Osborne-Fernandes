package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.ContactDao
import com.example.data.model.DigitalCardProfile
import com.example.data.model.ExchangedContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DigitalCardRepository(context: Context) {
    private val contactDao: ContactDao = AppDatabase.getDatabase(context).contactDao()

    private val _cardProfile = MutableStateFlow(DigitalCardProfile())
    val cardProfile: StateFlow<DigitalCardProfile> = _cardProfile.asStateFlow()

    val exchangedContacts: Flow<List<ExchangedContact>> = contactDao.getAllContacts()

    fun updateProfile(profile: DigitalCardProfile) {
        _cardProfile.value = profile
    }

    fun resetToOsborneProfile() {
        _cardProfile.value = DigitalCardProfile()
    }

    suspend fun saveExchangedContact(contact: ExchangedContact): Long {
        return contactDao.insertContact(contact)
    }

    suspend fun deleteContact(contact: ExchangedContact) {
        contactDao.deleteContact(contact)
    }

    suspend fun deleteContactById(id: Long) {
        contactDao.deleteById(id)
    }
}
