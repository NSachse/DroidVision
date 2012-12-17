DroidVision
====================
Drag and Drop stream player.<br>
The objective in this project is to get a simple UI where the user could easily select the film from a list of trailers and drag to the player (old tv) where it will start streaming the video. <br>

Project
-------
In this project I used a SurfaceView to draw all the bitmap objects from the film class and a VideoView to obtain the stream.<br>
Since isn't possible to overlay the VideoView over the SurfaceView, the trick is to get the height of the screen dynamically and award 2/3 of that size to the surface and the rest to the VideoView.<br>

Instalation and Tested
----------------------
It's ready to be install and been tested on Samsung Galaxy S3 and Nexus 7<br>

Future
------
Some future implementations:
<ul>
<li>Get API to get list of films/trailers from IMDB or LoveFilm</li>
<li>Add pinch to zoom to have fullscreen of poster</li>
<li>Improve UI</li>
</ul>
<br>
