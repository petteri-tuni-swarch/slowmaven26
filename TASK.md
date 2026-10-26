# Introduction

This is a step by step journey to Maven. The idea is to explore the role of Maven for Java development. How far do we get by keeping in the command line and just memorizing?

The purpose is to see how it helps with 
* The basic tasks like compiling, testing, running, deploying Java
* Component based software design

# Step 1 - no Maven

In Step 1 there is just the simple Java program, we compile it manually.
We won't stay in this step too long. But it is possible to write Java without building tools as well.

# Step 2 - Maven as compilation tool

Start using Maven. It means there will be at least pom.xml needed.
Where should we place the Java source file? Can it stay in the root as before.
How to compile the code? And how to run the code? How to create JAR file?

# Step 3a - Adding more source code files

* Make more functionality
* Start using package names

Java files:
* Car - to mode a car
* Garage: to hold cars data
* App: initialize the garage and add some cars there

Use simply "taxicars" for package names to get started.

# Step 3b - Organizing the code to packages

* Add a class to calculate costs for a trip with a car. This will placed to a different package. Now also 
* Align the package names to follow the Maven project naming.

If the Maven project GroupId is fi.arch26 and artifactId slowmaven, the package names could be:
* fi.arch26.taxicars
* fi.arch26.triputils

# Step 4 - Separating the code to two independent Maven projects

From this on, the taxicars and triputils can start living their own lives. But taxicars will be dependent on triputils.

They could be in different Git projects. For this exercise it is enough to separate them to different folders, each having its own pom.xml and Maven project structure.

# Step 5 - Executable JAR with Maven


# Step 6 - Demonstrating independent development of the two projects


# Step 7 - Equipping the project with tests


# END
