package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class DigitalCardProfile(
    val id: String = "osborne_default",
    val fullName: String = "Osborne Fernandes",
    val jobTitle: String = "Senior Developer | AI & IT Infrastructure",
    val headline: String = "20+ Years Full-Stack & Hardware Experience",
    val phone: String = "+63 9975849230",
    val whatsappPhone: String = "+971 505280169",
    val email: String = "osbornefernds@gmail.com",
    val websiteUrl: String = "https://osborneferds.github.io/Freelancer/",
    val cardUrl: String = "https://osborneferds.github.io/Digital-Card/",
    val location: String = "Angat, Bulacan - Philippines",
    val portfolioUrl: String = "https://drive.google.com/file/d/1dtaRhKo9eQMBgtAAGQc9XVpugkTS8VkP/view?usp=drive_link",
    val calendarBookingUrl: String = "https://calendar.app.google/qCop8E4643j7VxVT9",
    val googleReviewUrl: String = "https://share.google/E7vM02AaJmbq260Ms",
    val twitterUrl: String = "https://twitter.com/osborneferds",
    val linkedinUrl: String = "https://www.linkedin.com/in/osborneferds/",
    val githubUrl: String = "https://github.com/osborneferds",
    val isVerified: Boolean = true,
    val services: List<CardService> = defaultServices(),
    val packages: List<PricingPackage> = defaultPackages()
)

data class CardService(
    val id: String,
    val name: String,
    val iconKey: String,
    val description: String
)

data class PricingPackage(
    val id: String,
    val name: String,
    val price: String,
    val duration: String = "One-time payment",
    val isPopular: Boolean = false,
    val features: List<String>
)

@Entity(tableName = "exchanged_contacts")
data class ExchangedContact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val phone: String,
    val email: String,
    val company: String = "",
    val note: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

fun defaultServices(): List<CardService> = listOf(
    CardService(
        id = "ai_chatbot",
        name = "AI Chatbot",
        iconKey = "robot",
        description = "Intelligent conversational agents, LLM integration, automated workflows, and customer support bots."
    ),
    CardService(
        id = "mobile_app",
        name = "Mobile App",
        iconKey = "mobile",
        description = "Native Android and cross-platform apps built with modern Jetpack Compose and high performance."
    ),
    CardService(
        id = "software_dev",
        name = "Software Dev",
        iconKey = "code",
        description = "Robust full-stack development, server architectures, scalable databases, and custom tooling."
    ),
    CardService(
        id = "web_app",
        name = "Web App",
        iconKey = "globe",
        description = "Modern responsive web applications, progressive web apps (PWA), and cloud-optimized systems."
    ),
    CardService(
        id = "ai_development",
        name = "AI Development",
        iconKey = "brain",
        description = "Custom machine learning models, computer vision, data pipelines, and generative AI features."
    ),
    CardService(
        id = "digital_design",
        name = "Digital Design",
        iconKey = "palette",
        description = "Intuitive UI/UX design, interactive prototypes, design systems, and brand asset development."
    )
)

fun defaultPackages(): List<PricingPackage> = listOf(
    PricingPackage(
        id = "starter",
        name = "Starter Package",
        price = "$137",
        duration = "One-time payment",
        isPopular = false,
        features = listOf(
            "Your Service Setup",
            "Your Portfolio Showcase",
            "Basic Support Included"
        )
    ),
    PricingPackage(
        id = "professional",
        name = "Professional Package",
        price = "$275",
        duration = "One-time payment",
        isPopular = true,
        features = listOf(
            "Your Service Setup",
            "Your Portfolio Showcase",
            "Service Pricing & Packages",
            "Google Map Location Integration",
            "Book A Meeting Feature",
            "Analytics Integration",
            "One Custom Feature of Choice"
        )
    ),
    PricingPackage(
        id = "enterprise",
        name = "Enterprise Package",
        price = "Custom Pricing",
        duration = "One-time payment",
        isPopular = false,
        features = listOf(
            "Your Service Setup",
            "Your Portfolio Showcase",
            "Service Pricing & Packages",
            "Shopping Cart Functionality",
            "Google Maps Location",
            "Book A Meeting Scheduler",
            "Performance Monitoring",
            "Analytics Integration",
            "Google Review Integration",
            "Design Portfolio Gallery",
            "Progressive Web App (PWA)",
            "Custom Tailored Features"
        )
    )
)
