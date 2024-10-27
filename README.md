Todo Application
A full-stack Todo application built with Java Spring Boot (backend) and React (frontend) that allows users to perform basic CRUD (Create, Read, Update, Delete) operations. This project demonstrates a simple example of how to structure and integrate a Java backend with a modern JavaScript frontend.

Features
Create new todos
Read all todos or a specific todo by ID
Update existing todos
Delete todos
Basic RESTful API design
Persistent storage using MySQL (or other relational database)
Frontend built with React for a responsive UI
Cross-Origin Resource Sharing (CORS) enabled for smooth backend-frontend communication
Tech Stack
Backend: Java, Spring Boot, Hibernate, MySQL, HikariCP
Frontend: React
Database: MySQL
Prerequisites
Java 17 or higher
Node.js and npm (for running the React frontend)
MySQL (or other relational database)

API Endpoints
Todo Endpoints
Method	Endpoint	Description
GET	/api/list-of-tasks	Retrieve all todos
POST	/api/create-task	Create a new todo
PUT	/api/edit/{title}	Update a todo by Title
DELETE	/api//delete/{title}	Delete a todo by Title

Usage
Create Todo: Use the form on the frontend to create a new todo item.
View Todos: All existing todos are displayed on the main page.
Update Todo: Click on a todo item to edit its details.
Delete Todo: Remove a todo by clicking the delete button.

The application is also dockerized you can download and run the compose file to run the project 
