# History

Here I try to document the history of that project...

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

