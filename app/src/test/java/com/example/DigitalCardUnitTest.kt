package com.example

import com.example.data.model.DigitalCardProfile
import com.example.data.model.defaultPackages
import com.example.data.model.defaultServices
import com.example.util.AndroidIntents
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DigitalCardUnitTest {

    @Test
    fun testOsborneProfileDataIntegrity() {
        val profile = DigitalCardProfile()
        assertEquals("Osborne Fernandes", profile.fullName)
        assertTrue(profile.jobTitle.contains("Senior Developer"))
        assertEquals("+63 9975849230", profile.phone)
        assertEquals("+971 505280169", profile.whatsappPhone)
        assertEquals("osbornefernds@gmail.com", profile.email)
        assertEquals("https://osborneferds.github.io/Freelancer/", profile.websiteUrl)
        assertEquals("https://osborneferds.github.io/Digital-Card/", profile.cardUrl)
        assertTrue(profile.location.contains("Angat, Bulacan"))
        assertTrue(profile.isVerified)
    }

    @Test
    fun testServicesList() {
        val services = defaultServices()
        assertEquals(6, services.size)
        val names = services.map { it.name }
        assertTrue(names.contains("AI Chatbot"))
        assertTrue(names.contains("Mobile App"))
        assertTrue(names.contains("Software Dev"))
        assertTrue(names.contains("Web App"))
        assertTrue(names.contains("AI Development"))
        assertTrue(names.contains("Digital Design"))
    }

    @Test
    fun testPackagesPricing() {
        val packages = defaultPackages()
        assertEquals(3, packages.size)
        val starter = packages.first { it.id == "starter" }
        assertEquals("$137", starter.price)

        val pro = packages.first { it.id == "professional" }
        assertEquals("$275", pro.price)
        assertTrue(pro.isPopular)

        val enterprise = packages.first { it.id == "enterprise" }
        assertEquals("Custom Pricing", enterprise.price)
    }

    @Test
    fun testVCardGeneration() {
        val profile = DigitalCardProfile()
        val vCard = AndroidIntents.buildVCard(profile)
        assertNotNull(vCard)
        assertTrue(vCard.startsWith("BEGIN:VCARD"))
        assertTrue(vCard.contains("FN:Osborne Fernandes"))
        assertTrue(vCard.contains("TEL;TYPE=CELL:+63 9975849230"))
        assertTrue(vCard.contains("TEL;TYPE=WA:+971 505280169"))
        assertTrue(vCard.contains("EMAIL:osbornefernds@gmail.com"))
        assertTrue(vCard.endsWith("END:VCARD"))
    }

    @Test
    fun testPortfolioProjects() {
        val projects = com.example.data.model.defaultPortfolioProjects()
        assertEquals(4, projects.size)
        val categories = projects.map { it.category }.distinct()
        assertTrue(categories.contains("AI & Chatbots"))
        assertTrue(categories.contains("IoT & Hardware"))
        assertTrue(categories.contains("Cloud & Web"))
        assertTrue(categories.contains("Mobile App"))

        projects.forEach { proj ->
            assertTrue(proj.title.isNotBlank())
            assertTrue(proj.techStack.isNotEmpty())
            assertTrue(proj.keyMetrics.isNotBlank())
            assertTrue(proj.imageRes != 0)
        }
    }
}
