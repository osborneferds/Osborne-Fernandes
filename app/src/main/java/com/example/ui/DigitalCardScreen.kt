package com.example.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BookingSection
import com.example.ui.components.ContactInfoSection
import com.example.ui.components.HeaderSection
import com.example.ui.components.PortfolioSection
import com.example.ui.components.PricingSection
import com.example.ui.components.QuickActionButtons
import com.example.ui.components.ReviewsSection
import com.example.ui.components.ServicesSection
import com.example.ui.components.SocialLinksSection
import com.example.ui.dialogs.CardCustomizerDialog
import com.example.ui.dialogs.ExchangeContactDialog
import com.example.ui.dialogs.ProjectDetailDialog
import com.example.ui.dialogs.QrCodeDialog
import com.example.ui.dialogs.SavedContactsDialog
import com.example.ui.dialogs.ServiceDetailDialog
import com.example.ui.theme.VioletPrimary
import com.example.util.AndroidIntents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalCardScreen(
    viewModel: DigitalCardViewModel = viewModel()
) {
    val context = LocalContext.current
    val cardProfile by viewModel.cardProfile.collectAsStateWithLifecycle()
    val exchangedContacts by viewModel.exchangedContacts.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.userFeedbackMessage) {
        uiState.userFeedbackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearFeedbackMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Digital Card",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                actions = {
                    // QR Code Modal Button
                    IconButton(
                        onClick = { viewModel.openQrDialog() },
                        modifier = Modifier.testTag("top_bar_qr_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.QrCode,
                            contentDescription = "Show QR Code",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Saved Leads / Contacts Button with Badge Count
                    IconButton(
                        onClick = { viewModel.openSavedContactsDialog() },
                        modifier = Modifier.testTag("top_bar_contacts_btn")
                    ) {
                        BadgedBox(
                            badge = {
                                if (exchangedContacts.isNotEmpty()) {
                                    Badge(
                                        containerColor = VioletPrimary,
                                        contentColor = Color.White
                                    ) {
                                        Text("${exchangedContacts.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.People,
                                contentDescription = "Saved Leads",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Customize Card Profile Button
                    IconButton(
                        onClick = { viewModel.openCustomizerDialog() },
                        modifier = Modifier.testTag("top_bar_edit_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Customize Card",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.openExchangeDialog() },
                containerColor = VioletPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.PersonAdd,
                        contentDescription = "Exchange Contact"
                    )
                },
                text = {
                    Text(
                        text = "Exchange Info",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.testTag("fab_exchange_contact")
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 600.dp)
                    .testTag("digital_card_scrollable_column")
            ) {
                // Header (Hero Gradient, Avatar, Name, Title, Bio)
                item {
                    HeaderSection(profile = cardProfile)
                }

                // Quick Action Buttons (Website, Share, WhatsApp, Add to Contacts)
                item {
                    QuickActionButtons(
                        onWebsiteClick = {
                            AndroidIntents.openWebUrl(context, cardProfile.websiteUrl)
                        },
                        onShareClick = {
                            AndroidIntents.shareCard(context, cardProfile)
                        },
                        onWhatsAppClick = {
                            AndroidIntents.openWhatsApp(context, cardProfile.whatsappPhone)
                        },
                        onAddContactClick = {
                            AndroidIntents.addToContacts(context, cardProfile)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Contact Details Card
                item {
                    ContactInfoSection(
                        profile = cardProfile,
                        onCallClick = { phone ->
                            AndroidIntents.dialPhoneNumber(context, phone)
                        },
                        onWhatsAppClick = { phone ->
                            AndroidIntents.openWhatsApp(context, phone)
                        },
                        onEmailClick = { email ->
                            AndroidIntents.sendEmail(context, email)
                        },
                        onWebsiteClick = { url ->
                            AndroidIntents.openWebUrl(context, url)
                        },
                        onLocationClick = { loc ->
                            AndroidIntents.openLocation(context, loc)
                        },
                        onCopyClick = { text, label ->
                            AndroidIntents.copyToClipboard(context, text, label)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Our Services Section
                item {
                    ServicesSection(
                        services = cardProfile.services,
                        onServiceClick = { service ->
                            viewModel.openServiceDetail(service)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Portfolio Showcase Section with Responsive Grid
                item {
                    PortfolioSection(
                        portfolioUrl = cardProfile.portfolioUrl,
                        onProjectClick = { project ->
                            viewModel.openProjectDetail(project)
                        },
                        onViewOnlineClick = {
                            AndroidIntents.openWebUrl(context, cardProfile.portfolioUrl)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Service Packages & Pricing Section
                item {
                    PricingSection(
                        packages = cardProfile.packages,
                        onPackageInquireClick = { pkg ->
                            val msg = "Hello Osborne! I am interested in your ${pkg.name} (${pkg.price}) and would like to discuss next steps."
                            AndroidIntents.openWhatsApp(context, cardProfile.whatsappPhone, msg)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Book a Meeting Section
                item {
                    BookingSection(
                        calendarUrl = cardProfile.calendarBookingUrl,
                        onScheduleClick = {
                            AndroidIntents.openWebUrl(context, cardProfile.calendarBookingUrl)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Google Reviews Section
                item {
                    ReviewsSection(
                        reviewsUrl = cardProfile.googleReviewUrl,
                        onViewReviewsClick = {
                            AndroidIntents.openWebUrl(context, cardProfile.googleReviewUrl)
                        },
                        onWriteReviewClick = {
                            AndroidIntents.openWebUrl(context, cardProfile.googleReviewUrl)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }

                // Social Links & Verification Footer
                item {
                    SocialLinksSection(
                        twitterUrl = cardProfile.twitterUrl,
                        linkedinUrl = cardProfile.linkedinUrl,
                        githubUrl = cardProfile.githubUrl,
                        email = cardProfile.email,
                        onLinkClick = { url ->
                            AndroidIntents.openWebUrl(context, url)
                        }
                    )
                }

                // Generous bottom padding for FAB clearance
                item { Spacer(modifier = Modifier.height(96.dp)) }
            }
        }
    }

    // QR Code Dialog
    if (uiState.showQrDialog) {
        QrCodeDialog(
            profile = cardProfile,
            onDismiss = { viewModel.closeQrDialog() },
            onShareClick = {
                AndroidIntents.shareCard(context, cardProfile)
            },
            onCopyLinkClick = { link ->
                AndroidIntents.copyToClipboard(context, link, "Card URL")
            }
        )
    }

    // Exchange Contact Dialog
    if (uiState.showExchangeDialog) {
        ExchangeContactDialog(
            onDismiss = { viewModel.closeExchangeDialog() },
            onSaveContact = { contact ->
                viewModel.saveExchangedContact(contact)
            }
        )
    }

    // Saved Contacts / Leads Dialog
    if (uiState.showSavedContactsDialog) {
        SavedContactsDialog(
            contacts = exchangedContacts,
            onDismiss = { viewModel.closeSavedContactsDialog() },
            onCallClick = { phone ->
                AndroidIntents.dialPhoneNumber(context, phone)
            },
            onWhatsAppClick = { phone ->
                AndroidIntents.openWhatsApp(context, phone)
            },
            onEmailClick = { email ->
                AndroidIntents.sendEmail(context, email)
            },
            onDeleteContact = { contact ->
                viewModel.deleteContact(contact)
            }
        )
    }

    // Service Detail Modal
    uiState.selectedService?.let { service ->
        ServiceDetailDialog(
            service = service,
            onDismiss = { viewModel.closeServiceDetail() },
            onInquireWhatsApp = { customMsg ->
                viewModel.closeServiceDetail()
                AndroidIntents.openWhatsApp(context, cardProfile.whatsappPhone, customMsg)
            }
        )
    }

    // Project Detail Modal for Grid Portfolio Showcase
    uiState.selectedProject?.let { project ->
        ProjectDetailDialog(
            project = project,
            onDismiss = { viewModel.closeProjectDetail() },
            onOpenUrl = { url ->
                AndroidIntents.openWebUrl(context, url)
            },
            onInquireWhatsApp = { p ->
                val msg = "Hello Osborne! I saw your ${p.title} project in your digital card portfolio and would like to discuss a similar project."
                viewModel.closeProjectDetail()
                AndroidIntents.openWhatsApp(context, cardProfile.whatsappPhone, msg)
            }
        )
    }

    // Card Customizer Dialog
    if (uiState.showCustomizerDialog) {
        CardCustomizerDialog(
            currentProfile = cardProfile,
            onDismiss = { viewModel.closeCustomizerDialog() },
            onSaveProfile = { updatedProfile ->
                viewModel.updateProfile(updatedProfile)
            },
            onResetToDefault = {
                viewModel.resetToDefaultProfile()
            }
        )
    }
}
