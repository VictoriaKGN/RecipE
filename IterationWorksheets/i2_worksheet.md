Iteration 2 Worksheet
=====================

Paying off technical debt
-----------------

1. How we handle ingredients and instructions

Initially, we thought it would be fine to collect and manage ingredients and instructions as one big String each. However, we realized that this was bad form for database design since it left us with a multi-valued attribute. We changed how the EditActivity was collecting input from the user, how ingredients and instructions are displayed in ViewActivity, how they are stored within the Recipe object, and how they are laid out in our database. First, we thought we needed Ingredients and Instructions to be [their own objects](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/commit/1eeab86c3493b50f623c734556dd9daa21a995cf#0f3e520891a4e2b3cd996ec12b12837dccef02c0_8_11). However, we eventually decided that this was overkill since these data points didn't need their own methods. We switched to simply using [ArrayLists of Strings](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/commit/c3720b6edcd24581a3b9fd0a8413c802b3ef6bc5#0f3e520891a4e2b3cd996ec12b12837dccef02c0_11_9) for each.

This was inadvertent and prudent technical debt. It was inadvertent, because we didn't consider proper first normal form database design when we choose to collect and store these data points as single, large strings. It was prudent because we thought the simplest way would be quick and efficient.

///////
TD2: - change how we handle Recipe in View and such - passing RecipeManager between views
2. How we handle RecipeManager.java in different Views

In Iteration 1, EditActivity and ViewActivity would pass an instance of RecipeManager back and forth as the Views changed. This was done to ___. However, as we began Iteration 2, we realized that having multiple instances of RecipeManager would not affect how Recipes were added 
///////

2. Pushed part of view feature to Iteration 2

In the rush to meet the Iteration 1 deadline, one of our [user stories](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/issues/15) was overlooked and had to be pushed to Iteration 2. We are paying off this technical debt now by having to implement it along side the rest of our Iteration 2 user stories and features.

This was deliberate and prudent technical debt. It was deliberate because we chose to push this user story to the next iteration. It was prudent because we didn't want it to hold up our deliverable.

- **NEEDS A LINK TO COMPLETED COMMIT**


SOLID
----------------

find violation in group 6. Explain and link.


Retrospective
----------

Our retrospective made us re-evaluate our [branching strategy](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/network/main). In Iteration 1, we had branches for each layer of our architecture. We think this mostly worked well for the initial setup of our app. However, we decided for Iteration 2 to use feature branches instead. This will make it easier to control how and when fully completed features are merged into our dev branch, so that we don't have extraneous code from one layer that we didn't manage to fully implement in all layers.

Our retrospective also helped us plan our timeline better. In Iteration 1, we left the final merge until too late and had many problems getting bugs worked out in time. For Iteration 2, we will begin merging features into dev as soon as possible and well before the deadline to ensure that our iteration release is properly functional. Our [first Iteration 2 feature merged](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/commit/bb52d49b493bc26584632788eac6e5beb66f5c37), `browse`, was merged into our `dev` branch far sooner than any of our merges in Iteration 1.


Design patterns
-----

We have used the [Iterator](https://refactoring.guru/design-patterns/iterator) design pattern to fetch [individual ingredients and instructions](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/commit/c3720b6edcd24581a3b9fd0a8413c802b3ef6bc5#0f3e520891a4e2b3cd996ec12b12837dccef02c0_126_109) from our Recipe object. After checking that there are more ingredients/instructions to fetch with a hasNext method, calling classes can get ingredient/instruction Strings without worrying about how Recipe is storing them. There is also a reset method to start the iterator over.


Iteration 1 feedback fixes
--------------

Our grader suggested that we should move our RecipeValidator.java class into our Recipe object so that Recipes could self-validate. That would also keep all our Recipe code together so that is Recipe ever changed or became a parent class, we would be able to keep all the edits in one class. We did as the grader suggested, and now Recipe objects validate themselves during construction, throwing and error if any pertinent data is missed.

[Link](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/issues/61) to the issue.
[Link](https://code.cs.umanitoba.ca/winter-2022-a02/group-7/digital-cookbook/-/blob/41100f5f5f7c507da0e925bdbb886a5f0901ab3e/app/src/main/java/com/comp3350/recip_e/objects/Recipe.java) to the fix.
