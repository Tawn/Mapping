Thanh Kha (Tkha@u.rochester.edu)
Student ID: 29444328 
NetID: Tkha
Partner: None

Street Mapping

FILE INCLUDES
- src which contains all the source codes and data files
- README.txt


RUN AND COMPILE
1. Unzip Project3.zip
2. Change directory to Project3>src (where StreetMap.java is located).
3. Compile javac StreeMap.java
4. Trying the following tests (replacing ankle brackets with test):
- run java StreeMap <file>.txt --show
- run java StreeMap <file>.txt --directions <loc1> <loc2>
- run java StreeMap <file>.txt --show --directions <loc1> <loc2> 
Note: Any route that involves i11-i13 for monroe.txt won't work, because of no paths. 

DESCRIPTION
My program creates a Graph with (nodes, edges) that also maintains the adjacency list and edge weights. These objects are used for graph searching the shortest path between two intersections. The Djikstra's algorithm is a powerful method to finding the shortest path between two points, excluding negative cycles. The implementation of Djikstras initializes all the nodes and starts at the starting location. Utilizing a priority queue as a heap, allows me to use the greedy method of going to the shortest distance (out-neighbor of current node). Then reassigns current node as the smallest node value in the priority queue (head). After all paths been found, it draws the paths and marks the start and end locations, based on the user's input. The shortest path will be printed out onto the console as STDOUT along with the distance travelled (miles).


