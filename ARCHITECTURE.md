# ARCHITECTURE

![image of architectural model](../images/I1architecture.png)

## User Interface Layer
#### ViewActivity
- The UI that allows users to view a recipe

#### EditActivity
- The UI that allows users to add/edit a recipe

## Logic Layer
#### RecipeManager
- The class the UI layer calls to manage Recipe objects


## Database Layer
#### RecipeManager
- The database interface that the logic layer calls to add/extract recipes

#### recipeDB
- The stub database for Iteration 1

## Domain Specific Objects
#### Recipe
- The main object that contains all the information for each recipe field
