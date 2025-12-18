# Voucher Management System

A robust Spring Boot-based RESTful application designed to manage the lifecycle of promotional vouchers. This system allows for creating, updating, validating, and claiming vouchers with features like claim limits and expiry dates.

## 🚀 Features

*   **Voucher Creation & Management**: Add new vouchers or update existing ones.
*   **Validation**: Check if a specific voucher code is active and available for use.
*   **Claim System**: Automatically decrement the claim limit upon use and deactivate the voucher once the limit reaches zero.
*   **Expiry Tracking**: Supports formatted dates for voucher validity.
*   **Full CRUD**: Retrieve all vouchers or delete specific ones by code.
*   **CORS Enabled**: Ready to be consumed by frontend applications.

## 🛠️ Tech Stack

*   **Language**: Java 24
*   **Framework**: Spring Boot (Spring MVC, Spring Data JPA)
*   **API Specification**: Jakarta EE
*   **Database**: MySQL
*   **Utilities**: Lombok
*   **Build Tool**: Maven

## 📂 Project Structure

```text
src/main/java/dev/saivinay/vouchermanagementsystem/
├── controller   # REST Endpoints
├── model        # Entity definitions
├── repo         # Data access layer (Spring Data JPA)
├── service      # Business logic
└── Main.java    # Application entry point
```

## ⚙️ Configuration

Ensure you have a MySQL database named `demo` (or update the URL in `application.properties`).

**File:** `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 🔌 API Endpoints

The base path for all endpoints is `/api/v1`.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/vouchers` | Get a list of all vouchers. |
| **POST** | `/voucher` | Create a new voucher. |
| **PUT** | `/voucher` | Update an existing voucher. |
| **GET** | `/isAvailable/{code}` | Check if a voucher code is valid/active. |
| **GET** | `/claim/{code}` | Claim a voucher (reduces claim limit). |
| **DELETE** | `/voucher/{code}` | Remove a voucher from the system. |

### Sample JSON Request Body
```json
{
  "vcode": "SUMMER2025",
  "claimLimit": 50,
  "discountPercentage": 20,
  "expiry": "2025-08-31",
  "active": true
}
```

## 🏃 Getting Started

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/Voucher-Management-System.git
    ```
2.  **Build the project**:
    ```bash
    ./mvnw clean install
    ```
3.  **Run the application**:
    ```bash
    ./mvnw spring-boot:run
    ```

## 📝 License

Distributed under the MIT License.