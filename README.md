
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


