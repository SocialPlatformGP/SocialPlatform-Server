# SocialPlatform-Server
ServerSide  using ktor framework and mongo for database

## Authentication API Endpoints

### 1. Sign Up

- **Endpoint**: `POST /signup`
- **Description**: This endpoint is used to create a new user account.
  
#### Request
- **Method**: `POST`
- **Headers**: None
- **Body**:
  ```json
  {
    "username": "example",
    "email": "example@example.com",
    "password": "password123"
  }
  ```
  - `username`: User's username (required)
  - `email`: User's email address (required)
  - `password`: User's password (required)

#### Response
- **Status Code**: 
  - `200 OK`: User account created successfully.
  - `400 Bad Request`: Missing or invalid request body.
  - `409 Conflict`: 
    - If any field is blank in the request body.
    - If the password is too short (less than 6 characters).
    - If an error occurs while creating the user account.

### 2. Sign In

- **Endpoint**: `POST /signin`
- **Description**: This endpoint is used to authenticate and sign in a user.
  
#### Request
- **Method**: `POST`
- **Headers**: None
- **Body**:
  ```json
  {
    "email": "example@example.com",
    "password": "password123"
  }
  ```
  - `email`: User's email address (required)
  - `password`: User's password (required)

#### Response
- **Status Code**: 
  - `200 OK`: User authenticated successfully. Returns a JWT token.
  - `400 Bad Request`: Missing or invalid request body.
  - `409 Conflict`: 
    - If any field is blank in the request body.
    - If the provided email doesn't exist or password is incorrect.

### 3. Authenticate

- **Endpoint**: `GET /authenticate`
- **Description**: This endpoint is used to verify if a user is authenticated.
  
#### Request
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <JWT Token>` (required)

#### Response
- **Status Code**: 
  - `200 OK`: User is authenticated.

### 4. Home

- **Endpoint**: `GET /home`
- **Description**: This endpoint is used to retrieve the user's information after authentication.
  
#### Request
- **Method**: `GET`
- **Headers**: 
  - `Authorization: Bearer <JWT Token>` (required)

#### Response
- **Status Code**: 
  - `200 OK`: Returns the user's ID and email address.
  - `401 Unauthorized`: User is not authenticated.

---

