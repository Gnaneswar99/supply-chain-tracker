# 🏭 Supply Chain Tracker

A full-stack Supply Chain Management System built for Infrastructure Team.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-green)
![React](https://img.shields.io/badge/React-18-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Overview

Supply Chain Tracker is a comprehensive web application that enables real-time tracking and management of:
- **Vendors** - Supplier management
- **Warehouses** - Storage facility tracking
- **Inventory** - Stock level monitoring
- **Shipments** - Delivery tracking

## 🚀 Features

### Backend (Spring Boot)
- ✅ RESTful API with 49+ endpoints
- ✅ JPA/Hibernate with H2 and Postgres Database
- ✅ Global Exception Handling
- ✅ Data Validation
- ✅ DTO Pattern Implementation

### Frontend (React)
- ✅ Modern Dashboard with Charts
- ✅ Real-time Data Updates
- ✅ CRUD Operations for all entities
- ✅ Toast Notifications
- ✅ Responsive Design
- ✅ Search and Filter functionality

## 🛠️ Tech Stack

### Backend
| Technology | Purpose |
|------------|---------|
| Java 17 | Programming Language |
| Spring Boot 4.0.2 | Application Framework |
| Spring Data JPA | Database Access |
| H2 Database | In-memory Database |
| Lombok | Reduce Boilerplate |
| Maven | Build Tool |

### Frontend
| Technology | Purpose |
|------------|---------|
| React 18 | UI Framework |
| React Router | Navigation |
| Axios | API Calls |
| Recharts | Data Visualization |
| Lucide React | Icons |
| React Toastify | Notifications |

## 📁 Project Structure
```
Supply Chain Tracker/
├── Supply-Chain-Tracker/          # Backend (Spring Boot)
│   ├── src/main/java/
│   │   └── com/google/supplychain/
│   │       ├── controller/        # REST Controllers
│   │       ├── service/           # Business Logic
│   │       ├── repository/        # Data Access
│   │       ├── model/             # Entity Classes
│   │       ├── dto/               # Data Transfer Objects
│   │       └── exception/         # Exception Handling
│   └── src/main/resources/
│       └── application.properties
│
└── frontend/                       # Frontend (React)
    ├── src/
    │   ├── components/            # React Components
    │   ├── services/              # API Services
    │   └── App.js
    └── package.json
```

## 🏃‍♂️ Running the Application

### Prerequisites
- Java 17+
- Node.js 16+
- Maven 3.8+

### Backend
```bash
cd Supply-Chain-Tracker
mvn spring-boot:run
```
Backend runs on: `http://localhost:8081`

### Frontend
```bash
cd frontend
npm install
npm start
```
Frontend runs on: `http://localhost:3000`

## 📊 API Endpoints

### Vendors
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/vendors | Get all vendors |
| GET | /api/vendors/{id} | Get vendor by ID |
| POST | /api/vendors | Create vendor |
| PUT | /api/vendors/{id} | Update vendor |
| DELETE | /api/vendors/{id} | Delete vendor |

### Warehouses
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/warehouses | Get all warehouses |
| GET | /api/warehouses/nearly-full | Get warehouses >90% capacity |
| POST | /api/warehouses | Create warehouse |
| PUT | /api/warehouses/{id} | Update warehouse |
| DELETE | /api/warehouses/{id} | Delete warehouse |

### Inventory
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/inventory | Get all items |
| GET | /api/inventory/low-stock | Get low stock items |
| POST | /api/inventory | Create item |
| PATCH | /api/inventory/{id}/add-stock | Add stock |
| DELETE | /api/inventory/{id} | Delete item |

### Shipments
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/shipments | Get all shipments |
| GET | /api/shipments/active | Get active shipments |
| GET | /api/shipments/delayed | Get delayed shipments |
| POST | /api/shipments | Create shipment |
| PATCH | /api/shipments/{id}/status | Update status |

### Dashboard
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/dashboard | Get dashboard summary |

## 📸 Screenshots

### Dashboard
- KPI Cards showing real-time metrics
- Interactive charts for inventory and shipments
- Alert notifications for low stock and delays

### Vendors Management
- Card-based vendor display
- Add/Edit/Delete functionality
- Search and filter options

## 👨‍💻 Author

**Gnaneswar Markat**
- Application Engineer project

## 📄 License

This project is licensed under the MIT License.
