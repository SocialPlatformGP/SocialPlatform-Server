


## Table of Contents

- [Authentication API Endpoints](#authentication-api-endpoints)
- [Post Management API Endpoints](#post-management-api-endpoints)

---

## Authentication API Endpoints

### 1. Sign Up

| Property      | Value                           |
|---------------|---------------------------------|
| **Endpoint**  | `POST /signup`                  |
| **Method**    | `POST`                          |
| **Description** | Create a new user account.     |
| **Headers**   | None                            |
| **Request Body** | json
                    {
                      "username": "example",
                      "email": "example@example.com",
                      "password": "password123"
                    }
                                               |
| **Response Status Codes** | 
  - `200 OK`: User account created successfully.
  - `400 Bad Request`: Missing or invalid request body.
  - `409 Conflict`: 
    - If any field is blank in the request body.
    - If the password is too short (less than 6 characters).
    - If an error occurs while creating the user account. |


### 2. Sign In

| Property      | Value                           |
|---------------|---------------------------------|
| **Endpoint**  | `POST /signin`                  |
| **Method**    | `POST`                          |
| **Description** | Authenticate and sign in a user. |
| **Headers**   | None                            |
| **Request Body** | json|


                    {
                      "email": "example@example.com",
                      "password": "password123"
                    }

                                            
| **Response Status Codes** | 
  - `200 OK`: User authenticated successfully. Returns a JWT token.
  - `400 Bad Request`: Missing or invalid request body.
  - `409 Conflict`: 
    - If any field is blank in the request body.
    - If the provided email doesn't exist or password is incorrect. |

### 3. Authenticate

| Property      | Value                           |
|---------------|---------------------------------|
| **Endpoint**  | `GET /authenticate`             |
| **Method**    | `GET`                           |
| **Description** | Verify if a user is authenticated. |
| **Headers**   | `Authorization: Bearer <JWT Token>` (required) |
| **Response Status Codes** | 
  - `200 OK`: User is authenticated.       |

### 4. Home

| Property      | Value                           |
|---------------|---------------------------------|
| **Endpoint**  | `GET /home`                     |
| **Method**    | `GET`                           |
| **Description** | Retrieve user's information after authentication. |
| **Headers**   | `Authorization: Bearer <JWT Token>` (required) |
| **Response Status Codes** | 
  - `200 OK`: Returns the user's ID and email address.
  - `401 Unauthorized`: User is not authenticated. |


---

## Post Management API Endpoints

### 1. Create Post

| Property      | Value                           |
|---------------|---------------------------------|
| **Endpoint**  | `POST /createPost`             |
| **Method**    | `POST`                          |
| **Description** | Create a new post.            |
| **Headers**   | None                            |
| **Request Body** | json
                    {
                      "title": "Sample Title",
                      "body": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                      "userId": "user123"
                    }
                                               |
| **Response Status Codes** | 
  - `200 OK`: Post created successfully.
  - `400 Bad Request`: Missing or invalid request body.
  - `409 Conflict`: 
    - If any field is blank in the request body.
    - If an error occurs while creating the post. |

### 2. Get All Posts

| Property      | Value                           |
|---------------|---------------------------------|
| **Endpoint**  | `GET /getAllPosts`             |
| **Method**    | `GET`                          |
| **Description** | Retrieve all posts.           |
| **Headers**   | None                            |
| **Response Status Codes** | 
  - `200 OK`: Returns a list of all posts.
  - `404 Not Found`: No posts found.         |


