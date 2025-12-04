
| CS-622       | Advanced Programming Techniques |
|--------------|---------------------------------|
| Name         | Javier Esteban de Celis         |
| Date         | 09/30/2025 and 12/02/2025       |
| Course       | Fall                            |
| Homework #   | 3 and extra credit              |


# GitHub Repository Link:
https://github.com/JaviEst/cs-622-hw-3


# Implementation Description Homework 3 (Old Console Functionality)

## Overview

This Spring Boot web application provides a beautiful web interface for the anime search functionality. It allows users to:
- Fetch anime data from the Jikan API
- Search for words using both RegEx and Inverted Index algorithms
- Compare performance between the two search methods
- View search history
- Visualize results in an intuitive UI

## Features

- **Modern Web UI**: Clean, responsive interface with gradient backgrounds
- **Real-time Search**: Search multiple words simultaneously (comma-separated)
- **Performance Visualization**: See detailed performance comparisons between algorithms
- **Search History**: Track how many times each term has been searched
- **Anime Card View**: View pretty anime cards to help users decide what anime to watch next
- **API Data Viewer**: View raw JSON data from the API
- **Live Updates**: Real-time updates of search history after each search


# Implementation Description Homework 3 (Old Console Functionality)

## Overview

This is a small application that fetches data from a public REST API (https://jikan.moe/), stores it locally, lets the user search through the data using regular expressions and the inverted index algorithm, keeps a persistent history of searched terms, and compares the performance of both search algorithms.


## Features

- `api.ApiCall`: Fetches data from the Jikan (MyAnimeList) API and handles exceptions.
- `file.FileHandler`: Saves and reads data to and from files.
- `searcher.RegExSearcher`: Counts case-insensitive regex matches for a list of patterns within a text using Tree Traversal DFS.
- `searcher.InvertedIndexSearcher`: Implements an inverted index search algorithm for efficient word frequency counting.
- `searcher.PerformanceMeasurer`: Utility class for measuring and comparing execution times of different search algorithms.
- `searcher.SearchHistory`: Tracks how many times each term has been searched and persists that to `search-history.txt`.
- `Main`: Orchestrates the flow: load history → fetch API data → save/read file → prompt user for search terms → run both search algorithms → compare performance → update and save history.


## Runtime flow:
1. Load prior search counts from `search-history.txt` if present.
2. Fetch anime data from the Jikan API and write it to `api_data.json`.
3. Read the saved file content.
4. Prompt the user for one or more search terms (regex patterns, case-insensitive).
5. Build an inverted index from the text data.
6. Run both RegEx and Inverted Index search algorithms on the same terms.
7. Measure and compare execution times of both algorithms.
8. Display search results, performance comparison, and timing data for plotting.
9. Save updated search history back to disk.


## Search Algorithm Comparison

### RegEx Search (Tree Traversal DFS)
- **Algorithm**: Uses Java's Pattern and Matcher classes with Tree Traversal DFS
- **Characteristics**: 
  - No preprocessing required
  - Good for complex pattern matching
  - Performance degrades with longer patterns


### Inverted Index Search
- **Algorithm**: Pre-builds a word frequency map
- **Characteristics**:
  - Requires preprocessing (one-time cost)
  - Extremely fast for exact word matches
  - Memory efficient for repeated searches


### Performance Measurement
- Both algorithms are measured using `System.nanoTime()` for high precision
- Multiple iterations (10 runs) are performed to get average execution times
- Results include individual word timing data for manual plotting
- Speedup factors are calculated to show relative performance


## Outputs created:
- `api_data.json`: Latest API response persisted locally.
- `search-history.txt`: Key-value lines in the format `term:count`.


# Run the Application (New Website Functionality)
To run the program you will first need to compile the code by running the following command:
```bash
mvn clean compile
```

Once the above is successfully ran, you can run the program by running the following command:
```bash
mvn spring-boot:run
```

Then you will be able to access the full stack appplication at http://localhost:8070/


# Run the Application (Old Console Functionality)
To run the program you will first need to compile the code by running the following command:
```bash
mvn clean compile
```

Once the above is successfully ran, you can run the program by running the following command:
```bash
java -cp target/classes Main
```

# Maven Commands

We'll use Apache Maven to compile and run this project. You'll need to install Apache Maven (https://maven.apache.org/) on your system. 

Apache Maven is a build automation tool and a project management tool for Java-based projects. Maven provides a standardized way to build, package, and deploy Java applications.

Maven uses a Project Object Model (POM) file to manage the build process and its dependencies. The POM file contains information about the project, such as its dependencies, the build configuration, and the plugins used for building and packaging the project.

Maven provides a centralized repository for storing and accessing dependencies, which makes it easier to manage the dependencies of a project. It also provides a standardized way to build and deploy projects, which helps to ensure that builds are consistent and repeatable.

Maven also integrates with other development tools, such as IDEs and continuous integration systems, making it easier to use as part of a development workflow.

Maven provides a large number of plugins for various tasks, such as compiling code, running tests, generating reports, and creating JAR files. This makes it a versatile tool that can be used for many different types of Java projects.

## Compile
Type on the command line: 

```bash
mvn clean compile
```



## JUnit Tests
JUnit is a popular testing framework for Java. JUnit tests are automated tests that are written to verify that the behavior of a piece of code is as expected.

In JUnit, tests are written as methods within a test class. Each test method tests a specific aspect of the code and is annotated with the @Test annotation. JUnit provides a range of assertions that can be used to verify the behavior of the code being tested.

JUnit tests are executed automatically and the results of the tests are reported. This allows developers to quickly and easily check if their code is working as expected, and make any necessary changes to fix any issues that are found.

The use of JUnit tests is an important part of Test-Driven Development (TDD), where tests are written before the code they are testing is written. This helps to ensure that the code is written in a way that is easily testable and that all required functionality is covered by tests.

JUnit tests can be run as part of a continuous integration pipeline, where tests are automatically run every time changes are made to the code. This helps to catch any issues as soon as they are introduced, reducing the need for manual testing and making it easier to ensure that the code is always in a releasable state.

To run, use the following command:
```bash
mvn clean test
```


## Spotbugs 

SpotBugs is a static code analysis tool for Java that detects potential bugs in your code. It is an open-source tool that can be used as a standalone application or integrated into development tools such as Eclipse, IntelliJ, and Gradle.

SpotBugs performs an analysis of the bytecode generated from your Java source code and reports on any potential problems or issues that it finds. This includes things like null pointer exceptions, resource leaks, misused collections, and other common bugs.

The tool uses data flow analysis to examine the behavior of the code and detect issues that might not be immediately obvious from just reading the source code. SpotBugs is able to identify a wide range of issues and can be customized to meet the needs of your specific project.

Using SpotBugs can help to improve the quality and reliability of your code by catching potential bugs early in the development process. This can save time and effort in the long run by reducing the need for debugging and fixing issues later in the development cycle. SpotBugs can also help to ensure that your code is secure by identifying potential security vulnerabilities.

Use the following command:

```bash
mvn spotbugs:gui 
```

For more info see 
https://spotbugs.readthedocs.io/en/latest/maven.html

SpotBugs https://spotbugs.github.io/ is the spiritual successor of FindBugs.


## Checkstyle 

Checkstyle is a development tool for checking Java source code against a set of coding standards. It is an open-source tool that can be integrated into various integrated development environments (IDEs), such as Eclipse and IntelliJ, as well as build tools like Maven and Gradle.

Checkstyle performs static code analysis, which means it examines the source code without executing it, and reports on any issues or violations of the coding standards defined in its configuration. This includes issues like code style, code indentation, naming conventions, code structure, and many others.

By using Checkstyle, developers can ensure that their code adheres to a consistent style and follows best practices, making it easier for other developers to read and maintain. It can also help to identify potential issues before the code is actually run, reducing the risk of runtime errors or unexpected behavior.

Checkstyle is highly configurable and can be customized to fit the needs of your team or organization. It supports a wide range of coding standards and can be integrated with other tools, such as code coverage and automated testing tools, to create a comprehensive and automated software development process.

The following command will generate a report in HTML format that you can open in a web browser. 

```bash
mvn checkstyle:checkstyle
```

The HTML page will be found at the following location:
`target/site/checkstyle.html`
