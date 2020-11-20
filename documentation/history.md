# History

Here I try to document the history of that project...

## 2020-11-20
I refactor the whole movement mechanics.
I introduce rays for distance measurements for falling and jumping.
Now it works like it should, but two little things are bothering me:
* Sometimes the player breaks through a bounding box (especially at the start)
* jumping is a tiny bit imprecise.
It feels like jumping is not on point (hitting the button vs seeing the effect) / too slow.

## 2020-11-19
I played around with some BoundingBoxes to limit the player movement.
Well at the moment it doesn't work like expected.

## 2020-11-18
I refactored the jump mechanics.
Now a jumpTimer is used and now it works slightly better than before.

## 2020-11-17
I refactor some stuff and added the simplest jumping mechanic.
It's not nice, because hammering the jump button resets the timer (looks awkward).
I also think about using default metrics (like meter/second, meter, second) for configuration values and for calculating the physics.

## 2020-11-14
I did a small research on "Game Loops" and found an interesting [page][game-loop].
Thanks to [deWiTTERS][game-loop-twitter] for this description.
I implemented the idea of _Constant Game Speed independent of Variable FPS_, but in a recursive way.
In the end it's not really recursive, because of `@tailrec` the compiler will make a loop out of it.
The, lets call it 'advantage' of this procedure is, I can have immutable objects (for e.g. GameState).

I also cleaned up the project a little.
I extract stuff ordered it nicely, so hopefully the code is 'better' now. ;)
In this process I introduced the first simple:
* RenderPipeline
* PhysicPipeline

I also refactored the whole player movement system. 
The movement system got split up into the `PlayerState` and `Modifiers` which can change the `PlayerState` (e.g. position, rotation).
Also a simple physics system was introduced for the movement (s = v * t + s0)


## 2020-11-12
Implement controls for moving a textured cube.

## 2020-11-08
Thinking about data structure for a level.
Reorganize the project for a module approach.

## 2020-10-30
Start thinking about 3d animation.

## 2020-10-06
Today initial stuff was created for drawing a textured cube.

## 2020-10-02
An idea is born...

[comment]: <> (collection of links sorted alphabetically ascending)
[game-loop]: https://dewitters.com/dewitters-gameloop/
[game-loop-twitter]: https://twitter.com/_deWiTTERS_

