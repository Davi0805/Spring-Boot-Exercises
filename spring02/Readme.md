# Simplified Real-Time Chat Application

## Description
A lightweight Spring Boot application implementing a single chat room with real-time messaging using WebSocket.

## Features
- Real-time chat in a single room
- Display a list of active users
- Persist messages in memory for session duration

## Domain Models
- **ChatMessage:** Represents a message with content, sender, and timestamp.
- **User:** Tracks connected users by username.

## Endpoints
### WebSocket
- `/ws/chat`: Handles sending and receiving messages in real-time.

### REST
- `/api/chat/history`: Returns the chat history (in-memory storage).

## Getting Started

1. Create your models

2. Setup the configurations of Events
