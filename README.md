# Rectangle Exercise
An implementation of various geometric algorithms applicable to arbitrary axis-aligned rectangle pairs.
# Available Algorithms
- Intersection
- Containment
- Adjacency

## Intersection
Implemented in the `Geometry.getRectangleIntersections` method; pass in two rectangles and an array of points defining their points of intersection (if any) will be returned. 

In the visualizer these points will be shown as a green circles with their point coordinates, like so:

*Diagram 1:*

![Intersection Example](https://i.imgur.com/zt6FJDW.png)

## Containment
Implemented in the `Geometry.isContained` method; pass in two rectangles and a boolean representing whether one is wholly contained within the other will be returned (true if there is full containment, false otherwise). To calculate a set of points defining the boundaries of the two rectangles area-of-overlap, use `Geometry.getOverlapPoints`

In the visualizer there will be a box drawn to visualize the area of overlap between the two rectangles, red if only partial overlap, and green if there is full containment.

__Partial containment:__ See Diagram 1

__Full containment:__ See Diagram 2

*Diagram 2*

![Partial containment example](https://i.imgur.com/wzpgOIN.png)

## Adjacency
Implemented in `Geometry.getAdjacencyLines` method; pass in two rectangles and an array of `AdjacencyLine` entities will be returned representing all lines shared by the two rectangles (including a flag indicating the exact type of adjacency), if there are any. If simply detecting whether an adjacency relationship exists is all that's required, use `Geometry.isAdjacent` instead.

In the visualizer adjacency lines will be highlighted with a thick magenta stroke and a yellow text label denoting the type of adjacency represented by the highlighted line. All three adjacency types can be seen in the following example:

*Diagram 3*

![Adjacency example](https://i.imgur.com/WU7ed3P.png)

# Building and running
1) Pull or download/extract this repo
2) Navigate to the project root directory (hitherto refered to as `root`) in command line/terminal
3) Run `gradlew build`
4) Navigate to `root\build\libs` in command line/terminal
5) Run `java -jar Rectangle_Exercise.jar`