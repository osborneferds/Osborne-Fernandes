package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

data class PortfolioProject(
    val id: String,
    val title: String,
    val category: String,
    @DrawableRes val imageRes: Int,
    val shortDescription: String,
    val techStack: List<String>,
    val keyMetrics: String,
    val projectUrl: String = "https://osborneferds.github.io/Freelancer/"
)

fun defaultPortfolioProjects(): List<PortfolioProject> = listOf(
    PortfolioProject(
        id = "ai_agent_studio",
        title = "OmniAI Workflow Assistant",
        category = "AI & Chatbots",
        imageRes = R.drawable.img_portfolio_ai_assistant,
        shortDescription = "Enterprise conversational AI chatbot with automated tool-calling, custom RAG document indexing, and multi-tenant telemetry.",
        techStack = listOf("GenAI", "Python", "VectorDB", "FastAPI"),
        keyMetrics = "400k+ Queries / Mo",
        projectUrl = "https://osborneferds.github.io/Freelancer/"
    ),
    PortfolioProject(
        id = "iot_telemetry",
        title = "Industrial IoT Edge Gateway",
        category = "IoT & Hardware",
        imageRes = R.drawable.img_portfolio_iot_hardware,
        shortDescription = "Real-time edge hardware monitoring platform with sensor ingestion, microcontroller firmware, and sub-second MQTT telemetry.",
        techStack = listOf("ESP32", "MQTT", "Embedded C++", "Grafana"),
        keyMetrics = "99.98% Edge Uptime",
        projectUrl = "https://osborneferds.github.io/Freelancer/"
    ),
    PortfolioProject(
        id = "cloud_saas_platform",
        title = "Cloud Microservices Suite",
        category = "Cloud & Web",
        imageRes = R.drawable.img_portfolio_cloud_platform,
        shortDescription = "High-throughput microservices architecture with automated Kubernetes scaling, resilient caching, and real-time observability.",
        techStack = listOf("Docker", "K8s", "Go", "PostgreSQL"),
        keyMetrics = "< 45ms P99 Latency",
        projectUrl = "https://osborneferds.github.io/Freelancer/"
    ),
    PortfolioProject(
        id = "mobile_fintech_card",
        title = "FinPulse Jetpack App",
        category = "Mobile App",
        imageRes = R.drawable.img_portfolio_mobile_app,
        shortDescription = "Native Android application built with Jetpack Compose, biometric security, dynamic M3 theming, and offline-first Room persistence.",
        techStack = listOf("Jetpack Compose", "Kotlin", "Coroutines", "Room"),
        keyMetrics = "60 FPS Fluid UI",
        projectUrl = "https://osborneferds.github.io/Freelancer/"
    )
)
