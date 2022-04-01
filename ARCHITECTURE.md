# ARCHITECTURE

![image of architectural model](../images/I2architecture.png)

## User Interface Layer
#### LoginActivity
- The UI that the user first sees, allowing the user to either create or log into an existing account

#### ProfileActivity
- The UI that allows a user to view and change their user information

#### ViewActivity
- The UI that allows users to view a recipe

#### EditActivity
- The UI that allows users to add/edit a recipe

#### DrawerBaseActivity
- The UI that allows users to view all of their recipes

## Logic Layer
#### UserManager
- The class that the UI ;ayer calls to manage User objects

#### RecipeManager
- The class the UI layer calls to manage Recipe objects


## Database Layer
#### iUserManager
- The database interface that the logic layer calls to add, verify, and extract users

#### userPersisHsqlDB
- The persistent storage of users

#### iRecipeManager
- The database interface that the logic layer calls to add, extract, and delete recipes

#### recipePersisHsqlDB
- The persistent storage of recipes

## Domain Specific Objects
#### Recipe
- Contains all the information for each recipe field

#### User
- Contains login credentials for each user

## Application
#### Main
- Used to tell the rest of the app where to reach the database

#### Services
- Used to maintain a singleton instance of each HSQL persistence while running the app

#### App
- Used to maintain the state of the logged in user

## Exceptions
#### InvalidRecipeException
- Thrown when an attempt is made to create a Recipe with invalid data

#### hsqlDBException
- Thrown when the SQL/HSQL has a runtime exception, to let the developers have a better idea of what crashed

#### IncorrectPasswordException
- Thrown when a password entered at login does not match the user

#### EmailDoesNotExistException
- Thrown when a user tries to login before creating an account

#### UsernameDoesNotExist
- Thrown when the username does not exist
