
# Introduction

This is a step by step journey to Maven. The idea is to explore the role of Maven for Java development. How far do we get by keeping in the command line and just memorizing?

The purpose is to see how it helps with 
* The basic tasks like compiling, testing, running, deploying Java
* Component based software design

# Step 1 - no Maven

In Step 1 there is just the simple Java program, we compile it manually.
We won't stay in this step too long. But it is possible to write Java without building tools as well.

Source code App.java:

````
public class App
	public static void main () {
 		System.out.println("Step 1 - hello there. This is the simplest possible Java application");
	}
}
````

Important with Java:
* Everything is class - so that is mandatory.
* This is very static - to run the code, entry point method needs to be static.
* Main has certain parameters (String[] args) ... are they needed? No.

Compile the code: 
``` javac App.java ```

... this will produce the "compiled" Java bytecode App.class

Run the code:
``` java App ```

... adding the class happens almost automatically - but that is not allowed!

# Step 2 - Maven as compilation tool

Start using Maven. It means there will be at least pom.xml needed. What else? Where should we place the Java source file? Can it stay in the root with new pom.xml?

How to compile the code? And how to run the code? How to create JAR file?

The minimal pom.xml is like this:

````
<project>
<modelVersion>4.0.0</modelVersion>
    <groupId>fi.arch26</groupId>
    <artifactId>slowmaven</artifactId>
    <version>1.0-SNAPSHOT</version>
</project>
````

Actually not absolute need to memorize, as error messages will tell.

Compilation:
```` mvn compile ````

Other lifecycle commands:
* compile
* package
* install
* clean
* validate
* test

## Where to place the code --------------

This is slightly difficult to deduce. It must be in certain place: 
** CONVENTION OVER CONFIGURATION ** holds.

It needs to be under src/main/java. Test code will be in src/main/test

The tree:

````
step2/
├── pom.xml
├── src
│   └── main
│       └── java
│           └── App.java
└── target
    ├── classes
    │   └── App.class
    └── slowmaven-1.0-SNAPSHOT.jar
````

## Running the code.

Alternative 1 - go to folder where the class file is and as before, use java-command without .class-enging.

Alternative 2:

````
$ mvn package
$ java -cp target/slowmaven-1.0-SNAPSHOT.jar App
````

Using the JAR-file requires telling where the main() resides. Later this can be configured in pom.xml.

# Step 3a - Adding more source code files

* Make more functionality
* Start using package names

Java files:
* Car - to mode a car
* Garage: to hold cars data
* App: initialize the garage and add some cars there

Use simply "taxicars" for package names to get started.

## Implementation

Start from beginning again:

````
$ mkdir -p src/main/java
$ vi src/main/java/App.java
$ cd src/main/java/
$ javac App.java 
$ java App
Step 3a - adding more files for the application
````

So we can compile the code there as well. But we want to use Maven. Let's also start using some package names here, a short "taxicars" for start. A side effect, we need to specify the package when running the code:

````
$ more src/main/java/App.java 
package taxicars;
class App {
...
$ java -cp target/slowmaven-1.0-SNAPSHOT.jar taxicars.App
Step 3a - adding more files for the application
````

Now adding the files and some functionality there. Example Garage.java:

````
package taxicars;
import java.util.HashMap;

class Garage {
        protected HashMap<String,Car> carMap = new HashMap<String,Car>();
        public void addCar(Car car) {
                carMap.put(car.plate, car);
        }

        public void reportCars() {
                for(String plate: carMap.keySet()) {
                        Car car = carMap.get(plate);
                        System.out.println("Plate: " + car.plate + " - Model: " + car.model);
                }
        }
}
````

After adding a couple of new files, it now looks like this:

````
$ tree step3/
step3/
├── pom.xml
└── src
    └── main
        └── java
            ├── App.java
            ├── Car.java
            └── Garage.java
````

And running the code:

````
$ java -cp target/slowmaven-1.0-SNAPSHOT.jar taxicars.App
Step 3a - adding more files for the application
Plate: ABC-123 - Model: Audi A3
````

# Step 3b 

* Add a class to calculate costs for a trip with a car. This will placed to a different package. Now also 
* Align the package names to follow the Maven project naming.

If the Maven project GroupId is fi.arch26 and artifactId slowmaven, the package names could be:
* fi.arch26.taxicars
* fi.arch26.triputils

## Implementation and observations

One small detail was noticed at this point, not having the "public" for the classes. The classes worked fine inside same package, but were unaccessible from outside.

Starting to use proper package names complicated placement of Java files a lot. After adding the TripCost.java as the only file in fi.arch.triputils, package and moving the existing files to fi.arch26.taxicars package, the end result was this:

````
src/main/java/
├── fi
│   └── arch26
│       ├── taxicars
│       │   ├── App.java
│       │   ├── Car.java
│       │   └── Garage.java
│       └── triputils
│           └── TripCost.java
└── TripCost.class
````

Directory hierarchy is much more complex now. And using Maven for compiling starts to be a must ... using manual javac is not fun anymore.

Example of executing code at this point:
````
$ java -cp target/slowmaven-1.0-SNAPSHOT.jar fi.arch26.taxicars.App 
Step 3a - adding more files for the application
Plate: ABC-123 - Model: Audi A3
Trip with model Audi A3 and distance 100 will cost 7.3
Car not found: ABC-12
````


...

# END

