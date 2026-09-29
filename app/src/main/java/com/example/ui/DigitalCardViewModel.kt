package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.CardService
import com.example.data.model.DigitalCardProfile
import com.example.data.model.ExchangedContact
import com.example.data.model.PortfolioProject
import com.example.data.model.PricingPackage
import com.example.data.repository.DigitalCardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DigitalCardUiState(
    val showQrDialog: Boolean = false,
    val showExchangeDialog: Boolean = false,
    val showSavedContactsDialog: Boolean = false,
    val showCustomizerDialog: Boolean = false,
    val selectedService: CardService? = null,
    val selectedProject: PortfolioProject? = null,
    val selectedPackage: PricingPackage? = null,
    val userFeedbackMessage: String? = null
)

class DigitalCardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DigitalCardRepository(application.applicationContext)

    val cardProfile: StateFlow<DigitalCardProfile> = repository.cardProfile

    val exchangedContacts: StateFlow<List<ExchangedContact>> = repository.exchangedContacts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow(DigitalCardUiState())
    val uiState: StateFlow<DigitalCardUiState> = _uiState.asStateFlow()

    fun openQrDialog() {
        _uiState.value = _uiState.value.copy(showQrDialog = true)
    }

    fun closeQrDialog() {
        _uiState.value = _uiState.value.copy(showQrDialog = false)
    }

    fun openExchangeDialog() {
        _uiState.value = _uiState.value.copy(showExchangeDialog = true)
    }

    fun closeExchangeDialog() {
        _uiState.value = _uiState.value.copy(showExchangeDialog = false)
    }

    fun openSavedContactsDialog() {
        _uiState.value = _uiState.value.copy(showSavedContactsDialog = true)
    }

    fun closeSavedContactsDialog() {
        _uiState.value = _uiState.value.copy(showSavedContactsDialog = false)
    }

    fun openCustomizerDialog() {
        _uiState.value = _uiState.value.copy(showCustomizerDialog = true)
    }

    fun closeCustomizerDialog() {
        _uiState.value = _uiState.value.copy(showCustomizerDialog = false)
    }

    fun openServiceDetail(service: CardService) {
        _uiState.value = _uiState.value.copy(selectedService = service)
    }

    fun closeServiceDetail() {
        _uiState.value = _uiState.value.copy(selectedService = null)
    }

    fun openProjectDetail(project: PortfolioProject) {
        _uiState.value = _uiState.value.copy(selectedProject = project)
    }

    fun closeProjectDetail() {
        _uiState.value = _uiState.value.copy(selectedProject = null)
    }

    fun saveExchangedContact(contact: ExchangedContact) {
        viewModelScope.launch {
            repository.saveExchangedContact(contact)
            _uiState.value = _uiState.value.copy(
                showExchangeDialog = false,
                userFeedbackMessage = "Contact for ${contact.name} saved successfully!"
            )
        }
    }

    fun deleteContact(contact: ExchangedContact) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun updateProfile(profile: DigitalCardProfile) {
        repository.updateProfile(profile)
        _uiState.value = _uiState.value.copy(
            showCustomizerDialog = false,
            userFeedbackMessage = "Card profile updated!"
        )
    }

    fun resetToDefaultProfile() {
        repository.resetToOsborneProfile()
        _uiState.value = _uiState.value.copy(
            showCustomizerDialog = false,
            userFeedbackMessage = "Reset to Osborne Fernandes profile"
        )
    }

    fun clearFeedbackMessage() {
        _uiState.value = _uiState.value.copy(userFeedbackMessage = null)
    }
}
