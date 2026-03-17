# 🏭 Supply Chain Tracker

A full-stack Supply Chain Management System built to support infrastructure and operations workflows.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![React](https://img.shields.io/badge/React-18-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Overview

Supply Chain Tracker is a full-stack web application designed to help teams manage and monitor core supply chain operations in one centralized system. The application provides visibility into vendors, warehouses, inventory, and shipments, along with dashboard insights to support better operational decision-making.

It was built to reflect real-world business application needs such as:
- supplier and vendor management
- warehouse capacity tracking
- inventory stock monitoring
- shipment status visibility
- operational alerts and dashboard reporting

---

## 🎯 Why I Built This

I built this project to deepen my understanding of how internal business applications support supply chain and operational workflows. As I became more interested in Application Engineer roles focused on enterprise systems, I wanted to create a practical end-to-end solution that demonstrates backend system design, API development, database integration, and a user-friendly frontend experience.

---

## 💼 Business Problems Solved

This application addresses common operational challenges such as:

- Lack of centralized visibility across vendors, warehouses, inventory, and shipments
- Difficulty identifying low-stock inventory before it impacts operations
- Limited tracking of delayed or active shipments
- Poor warehouse capacity monitoring
- Need for dashboard-based insights for faster decisions

---

## 🚀 Features

### Backend (Spring Boot + PostgreSQL)
- ✅ RESTful APIs for core supply chain workflows
- ✅ Layered architecture with Controller, Service, Repository, DTO, and Exception handling
- ✅ PostgreSQL integration for persistent relational data storage
- ✅ Data validation and input handling
- ✅ Global exception handling
- ✅ Modular backend design for maintainability and scalability

### Frontend (React)
- ✅ Interactive dashboard with KPI cards and charts
- ✅ CRUD operations for all major entities
- ✅ Search and filter functionality
- ✅ Toast notifications for user actions
- ✅ Responsive UI for a better user experience
- ✅ Clean navigation across modules

---

## 🛠️ Tech Stack

### Backend
| Technology | Purpose |
|------------|---------|
| Java 17 | Core programming language |
| Spring Boot | Backend application framework |
| Spring Data JPA | ORM and data access |
| PostgreSQL | Relational database |
| Lombok | Reduce boilerplate code |
| Maven | Dependency and build management |

### Frontend
| Technology | Purpose |
|------------|---------|
| React 18 | Frontend UI development |
| React Router | Client-side routing |
| Axios | API communication |
| Recharts | Data visualization |
| Lucide React | Icons |
| React Toastify | Notifications |

---

## 🏗️ Architecture

The application follows a layered architecture:

- **Controller Layer**: Exposes REST API endpoints
- **Service Layer**: Contains business logic
- **Repository Layer**: Handles database interaction using Spring Data JPA
- **DTO Layer**: Manages request/response objects
- **Exception Layer**: Centralized error handling
- **Frontend Layer**: React-based user interface for operations and dashboards

---

## 📦 Core Modules

### 1. Vendors
Manage supplier information and maintain vendor records.

### 2. Warehouses
Track warehouse capacity and monitor storage usage.

### 3. Inventory
Monitor stock levels, identify low-stock items, and manage inventory records.

### 4. Shipments
Track shipment status, active deliveries, and delayed shipments.

### 5. Dashboard
Provide a summarized operational view using metrics, alerts, and charts.

---

## 📁 Project Structure

```text
Supply Chain Tracker/
├── backend/
│   ├── src/main/java/com/google/supplychain/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   └── exception/
│   └── src/main/resources/
│       └── application.properties
│
└── frontend/
    ├── src/
    │   ├── components/
    │   ├── services/
    │   └── App.js
    └── package.json
