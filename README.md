Movie Booking Platform – Backend Case Study
Overview

This project is a backend-only Movie Ticket Booking Platform built using Java, Spring Boot, Maven, and REST.
The goal is to demonstrate system design, backend problem-solving, and clean code rather than building a full product.

Instead of implementing all features, the project focuses on one critical real-world scenario: booking movie tickets with seat selection, covering concurrency, transactions, and data consistency.

Implemented Feature


Book Movie Tickets (Seat Selection)

A customer can:

Select a movie show

Choose preferred seats

Complete booking with payment

The system guarantees:

 No double booking of seats

 Transactional consistency

 Safe concurrent access

High-Level Architecture

The platform can be logically divided into:

Catalog Service – browse movies, theatres, and shows (read-heavy)

Inventory Service – manage seat availability per show

Booking Service – create, confirm, or fail bookings

Payment Service – external payment gateway integration (stubbed)

Partner Service (B2B) – theatre onboarding and show management (future)

For this exercise, all services are implemented in a single Spring Boot application, structured for easy decomposition into microservices.

Booking Flow (Core Logic)

Validate show existence

Reserve selected seats (AVAILABLE → RESERVED)

Create booking with status PENDING_PAYMENT

Associate seats with booking

Process payment

On success:

Booking → CONFIRMED

Seats → BOOKED

On failure:

Booking → FAILED

Seats released back to AVAILABLE

All steps run inside transactional boundaries to ensure consistency.

Database Design Highlights

show_seats table tracks seat availability per show

booking_seats table maps seats to bookings

Unique constraint on show_seat_id prevents double booking even under concurrency

Database acts as the final source of truth

Concurrency & Consistency

Seat conflicts are handled using database-level unique constraints

Transactions ensure atomicity

Failed or partial bookings automatically roll back safely

Scale, Availability & Security
Scalability

Read APIs are cache-friendly

Write operations can be partitioned by city, theatre, or show date

Availability

Stateless services enable horizontal scaling

Designed for HA database deployments

Security

JWT-based authentication for customers

OAuth2 / mTLS for theatre partners (B2B)

PII protection and encrypted secrets

Payment & Integrations

Payment gateway abstraction allows easy integration with providers like Razorpay or Stripe

Production-ready design supports asynchronous webhook callbacks and idempotency

AI Opportunities

Personalized movie recommendations

Demand forecasting & dynamic pricing

Fraud detection for abnormal booking behavior

Chatbots for customer support and cancellations

Tech Stack

Language: Java 17

Framework: Spring Boot

Build Tool: Maven

Database: H2 (local), PostgreSQL/MySQL (production)

Architecture: REST, transactional backend design

Running the Application
mvn clean spring-boot:run

Sample API
POST /api/v1/bookings

{
"showId": 101,
"seatIds": [501, 502],
"customerName": "Sebu",
"customerEmail": "sebu@test.com"
}
