# History

Here I try to document the history of that project...

## 2020-12-22
I started the Level Loader, for getting the level into the game.
I decided for a first data structure to represent the level and got a first render.
Unfortunately it seems that during export from Blender the coordinates got mixed up.

## 2020-12-02
Extend the level editor/transformer.

## 2020-12-01
I thought about a level editor. 
First idea was a swing/awt kind of application, but this will probably take too long.
Next idea was using .png images and using colors to encode level object.
I wrote a small transformer to transform a .png file to a .json file.

## 2020-11-21
Cleanup the DistanceSensor stuff.
Sometimes when game is starting the player falls thru the ground, at the moment I'm not sure why.
I'm also a bit unhappy with all this timer stuff, but for the moment I think it's good to go.

Video: https://www.youtube.com/watch?v=G9iqJod47rA

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

##2020-11-03
Created a simple animation from .obj files.
For that multiple files are read and played in a loop.

Video: https://www.youtube.com/watch?v=jk6NiqBE6i0

## 2020-10-30
Start thinking about 3d animation.

## 2020-10-06
Today initial stuff was created for drawing a textured cube.

Video: https://www.youtube.com/watch?v=ko3-z1awMAE

## 2020-10-02
An idea is born...

[comment]: <> (collection of links sorted alphabetically ascending)
[game-loop]: https://dewitters.com/dewitters-gameloop/
[game-loop-twitter]: https://twitter.com/_deWiTTERS_

