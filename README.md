

# Introduction

This is a step by step journey to Maven. The idea is to explore the role of Maven for Java development.
How far do we get by keeping in the command line and just memorizing?

The purpose is to see how it helps with  
* The basic tasks like compiling, testing, running, deploying Java
* Component based software design

# Step 1 - no Maven

In Step 1 there is just the simple Java program, we compile it manually.
We won't stay in this step too long. But it is possible to write Java without building tools as well.

## Implementation

* Creating file
* Compiling
* Running

````
$ cat App.java 

class App {

public static void main() {

        System.out.println("Step 1: Very basic Java, Maven not used");

        }

}

petteri@hopee7:/inno/slowdemo/step1$ javac App.java
petteri@hopee7:/inno/slowdemo/step1$ java App
Step 1: Very basic Java, Maven not used
````

# Step 2 - Maven as compilation tool

Start using Maven. It means there will be at least pom.xml needed.
Where should we place the Java source file? Can it stay in the root as before.
How to compile the code? And how to run the code? How to create JAR file?

## Research

Hint - if not using the lifecycle goals, ERROR message helps:

````
[ERROR] No goals have been specified for this build. You must specify a valid lifecycle phase or a goal in the format <plugin-prefix>:<goal> or <plugin-group-id>:<plugin-artifact-id>[:<plugin-version>]:<goal>. Available lifecycle phases are: pre-clean, clean, post-clean, validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy, pre-site, site, post-site, site-deploy. -> [Help 1]
````

## Implementation

* Adding pom.xml
* Moving java files to proper folder

Minimalistic pom.xml:

````
$ cat pom.xml 
<project>
<modelVersion>4.0.0</modelVersion>
<groupId>group</groupId>
<artifactId>artifact</artifactId>
<version>1 </version>
</project>
````

File structure for step2:

````
$ tree
.
├── pom.xml
├── src
│   └── main
│       └── java
│           └── App.java
└── target
    ├── artifact-1.jar
    ├── classes
    │   └── App.class

````

How to compile -> will generate the class file(s).
How to build -> will generate the JAR

````
$mvn compile
$mvn package
````

Running the code
````
$ java -cp target/artifact-1.jar App
Step 1: Very basic Java, Maven not used
````

# Step 3a - Adding more source code files

* Make more functionality
* Start using package names

Java files:
* Car - to mode a car
* Garage: to hold cars data
* App: initialize the garage and add some cars there

Use simply "taxicars" for package names to get started.

# Step 3b - Organizing the code to packages

* Add a class to calculate costs for a trip with a car. This will placed to a different package. Now al
so  
* Align the package names to follow the Maven project naming.

If the Maven project GroupId is fi.arch26 and artifactId slowmaven, the package names could be:
* fi.arch26.taxicars
* fi.arch26.triputils

## Implementation


Running the code:

step3b$ java -cp target/taxicars-1.jar fi.arch26.taxicars.App
Step 3a - adding more files for the application
Plate: ABC-123 - Model: Audi A3


# Step 4 - Separating the code to two independent Maven projects

From this on, the taxicars and triputils can start living their own lives. But taxicars will be depende
nt on triputils.

They could be in different Git projects. For this exercise it is enough to separate them to different f
olders, each having its own pom.xml and Maven project structure.

## Implementation

The directory structure will look like this:

````
├── garage
│   ├── pom.xml
│   │       └── java
│   │           ├── App.java
│   │           ├── Car.java
│   │           └── Garage.java
│       └── taxicars-1.jar
└── triputils
    ├── pom.xml
    │       └── java
    │                   └── triputils
    │                       └── TripCost.java
        │           └── triputils
        └── triputils-1.0.0.jar
````

The JAR files will be there after compilation.

Garage (main application) pom.xml:

````
petteri@hopee7:/inno/slowdemo/step4$ cat garage/pom.xml 
<project>
        <modelVersion>4.0.0</modelVersion>
        <groupId>fi.arch26</groupId>
        <artifactId>taxicars</artifactId>
        <version>1 </version>

<dependencies>

<dependency>
        <groupId>fi.arch26</groupId>
        <artifactId>triputils</artifactId>
        <version>1.0.0</version>
</dependency>

</dependencies>

</project>
````

The triputils pom:

````
petteri@hopee7:/inno/slowdemo/step4$ cat triputils/pom.xml 
<project>
<modelVersion> 4.0.0 </modelVersion>

<groupId>fi.arch26</groupId>
<artifactId>triputils</artifactId>
<version>1.0.0</version>

</project>
````

For running the code, both required JAR-files must be included for the -cp (-classpath) parameter:
````
step4/garage$ java -cp "target/taxicars-1.jar:/inno/slowdemo/step4/triputils/target/triputils-1.0.0.jar" fi.arch26.taxicars.App
Step 3a - adding more files for the application
Plate: ABC-123 - Model: Audi A3
Trip with model Audi A3 and distance 100 will cost 7.3
Car not found: ABC-12
````

# Step 5 - Executable JAR with Maven

* Configure the garage/pom.xml to generate proper JAR file that
** Contains all the bytecode needed - including from external dependencies like triputils in this case
** Knows the location of main() - the entry point to start execution. 

## Implementation

Now the "by heart" requirements starts to be challenging. The definitions to add will be quite tough.
The plugin for creating the "FAT-JAR" is __maven-assembly-plugin__
Many times the version for the maven-compiler is also fixed by introducing plugin __maven-compiler-plugin__ explicitly.

The resulting build-section of the pom.xml will be as follows:

````
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.13.0</version>
      </plugin>

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.4.2</version>
        <configuration>
          <archive>
            <manifest>
              <mainClass>fi.arch26.taxicars.App</mainClass>
            </manifest>
          </archive>
          <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
          </descriptorRefs>
        </configuration>
        <executions>
          <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
````

# Step 6 - More decent implementation. Equipping the project with tests

Codex to rescue:

1. Make the entry point portable. In App.java (garage/src/main/java/App.java:5), change main() to main(String[] args). The current form works on Java 25 but will fail on older Java runtimes, despite the build targeting Java 8.

2. Make the two-module build reproducible. Add a root Maven POM containing triputils and garage, preferably also serving as their parent. Currently, garage/pom.xml (garage/pom.xml:8) resolves an installed triputils artifact; building the sibling source does not automatically update that dependency. A reactor build would let mvn clean verify build both together.

3. Declare the Java target and encoding. Set maven.compiler.release and project.build.sourceEncoding centrally, and pin the build plugins used by both modules. The current build emits compiler and encoding warnings. Choose the Java release you actually intend to support.

4. Protect basic data integrity. Make the fields in Car.java (garage/src/main/java/Car.java:4) private final, with getters. A mutable plate can become inconsistent with its map key. Reject null cars, blank model/plate values, and negative distances; explicitly decide whether duplicate plates replace a car or cause an error.

5. Add a small, meaningful test suite. Cover the cost calculation, zero and negative distances, adding/finding cars, unknown plates, and duplicate plates. Include a packaged-JAR launch check—the current successful Maven build does not validate the entry point.

6. Clean up and document. Move garage sources under src/main/java/fi/arch26/taxicars/, remove redundant .orig, pom.bk1, and foo.xml files if obsolete, ignore target/, and add a short README with prerequisites and build/run commands.


# Step 7 - Demonstrating independent development of the two projects


# END

