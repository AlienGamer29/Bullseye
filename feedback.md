# Feedback


Congratulations on your game! Your source code is very clean and its noticeable that you've tried to make it scalable, clean and easy to maintain. Your main mishaps happened with following conventions for variables and with the structure of `if-else-if-else` and `switch` statements.

Where to improve? Check below:

#### On the `Arena` class:

* The convention for naming a final property is `BUSH_PADDING` and not `BUSHPADDING`.
`
* The method `displayArena()` has an `if-else`, a bit pointless, since it will always run what is in the `if` clause. Remember to use the parameter provided.

* Be careful, your code has comments in Portuguese and English, choose one or the other for this. This is minor, but is something that catches the attention when working in a project in real life.

#### On the `ArrowTypes` enum:

* Final properties should follow the convention mentioned above.


#### On the `Arrows` class:

* The `switch` case on `setRandomArray()` need a `default` and can be reduced for cases 2, 3, 4, like that: `case 2, 3, 4:`.

#### On the `GameState` class:

* On the method `displayIntro()` on the `if` clause, there is no need for `(show == true)`, just `(show)` will suffice. The value of the variable will change automatically.

#### On the `Game` class:

* Watch out for unused properties. This clutters your source code and turn maintenance into a nightmare.

#### On the `Keyboard` class:

* Take a look again and check for un-used local variables.

* Also check again to have an `else`clause on the `keyPressed()` method.

#### To restart your game:

One of the things we mentioned in class, was the ability to restart a game. For this, you could create a public method on the `Game` class, that restores the game to its original state. Return `score` and `maxArrows` to their the initial value` and ask again to show the intro. Then choose a key of your keyboard, that when pressed invokes this method!

Good luck! And congrats again!






