Iteration 1 Worksheet
=====================

Adding a feature
-----------------

### Our Feature: View a recipe

We decided being able to view a recipe was the top priority, since it would be hard to implement adding or deleting one if we couldn't see the results. Tasks were divided into their architectural layers for each team to work on:

- User Interface
  - Create the app Views that allow the user to view each piece of a recipe
- Logic
  - Provide the UI layer with access to the database layer and do data validation
- Database
  - Provide persistent storage of Recipes for the UI layer to display

Applicable links:
- [The feature](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/issues/1)
- User Stories:
  - ["As a user, I want to see a pre-existing recipe because I don’t have my own or need a place to start."](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/issues/13)
  - ["As a user, I want to view a picture of the recipe, if it is available"](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/issues/14)


Exceptional code
----------------

An InvalidRecipeException is thrown when the User Interface attempts to create a new Recipe object but some of the expected fields are in an incompatible state. For example, we don't want users to feel pressured to use a non-zero number for cook time, since they simply may not want to use this field. However, cook time should not be a negative number. Since Logic handles data verification, RecipeManager calls RecipeValidator and then passes the exception to User Interface to inform the user to fix the bad input.

[A link to exceptional code testing](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/blob/cc65e4cba27eac0e9bb9c06808eea625938ae8c1/app/src/test/java/comp3350/recip_e/tests/logic/RecipeManagerTest.java)


Branching
----------

We originally decided to use the Git flow model of branching, consisting of a release-ready ```main``` branch, an in-development ```dev``` branch, and individual branches for each feature. However, we discovered early on that a compromise between Git flow and GitHub flow would suit our Iteration 1 development better. We still have our ```main``` and ```dev``` branches, but we also have a named branch for each tier of our architecture. An additional named branch, ```layout```, was needed early on to accommodate some file structure changes as we were beginning our project.

![Group 7 branching model](/images/branching.png)


SOLID
-----

At the time of writing, Group 8 did not have a lot of material to look at for making a fair SOLID evaluation. However, as was indicated by Jon on [Piazza](https://piazza.com/class/kymiwbgj51b4r8?cid=41), we decided to create an issue about their folder organization. There is a folder where a persistence layer object and a domain specific object are within the same folder instead of separated into their own architectural folders. These are permalinks to the [specific Recipe object](https://code.cs.umanitoba.ca/winter-2022-a02/group-8/recipe-app/-/blob/41f84eace8ff5461a7ad42837a0dc4d492e3b096/Database/src/Recipe.java) and specific [database object](https://code.cs.umanitoba.ca/winter-2022-a02/group-8/recipe-app/-/blob/41f84eace8ff5461a7ad42837a0dc4d492e3b096/Database/src/Database.java) that are currently being packaged together.

[Link to the issue we created](https://code.cs.umanitoba.ca/winter-2022-a02/group-8/recipe-app/-/issues/19)


Agile Planning
--------------

We ended up pushing [one user story](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/issues/15) to Iteration 2. We also waited until the last day to merge all our material and ran into more issues than expected. We will definitely be pushing to a shared branch earlier for Iteration 2.

There was discussion about whether "Viewing a recipe" should allow a user to switch between two recipes after completing "Add a recipe," however, it was decided that this would be considered scope creep on the planned Iteration 2 feature of "Browse recipes." Thus, the user can view a recipe when the app is first launched, but after adding their own recipe, they can only view the newly added one.
