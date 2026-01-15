# sishuo-java-2048-Game
By sishuo
Created using Java swing with object-oriented design in 2024. I was playing 2048 a lot at the time, and I thought I could create this game in java by myself.
I created all images (.png) by Windows' 3D Painter BY HAND, it was tough to get all of them right.

Packages:
/codes -> all .java files
/images -> all .png files

One can start the game and use arrow keys to move, just like regular 2048. The system will track the score in the background and, if the game ends, a JFrame that contains one's final score will pop out.
The biggest power of 2 that this game has is 2^17 = 131072, because that is the theoretical limit that classic 2048 (only 2s and 4s will spawn) can have (stuck 131072, 65526, 32768 ... all the way to 8, 4 that spans the 16 tiles.

App.java containes public static void main(String[] args).
GameFrame.java extends JFrame and is the Graphical User Interface.
Tile.java is a javaBean (Object-Oriented Design).
