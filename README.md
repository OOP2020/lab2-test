## JUnit Tests for Lab 2

This repo contains some JUnit tests for Stopwatch.
If I have time, I will add tests for ArrayMath.

```
stopwatch/StopwatchTest.java  - JUnit tests for Stopwatch
```

JUnit is the standard library for testing Java code.
The Python `unittest` module is based on JUnit and quite similar.

**BlueJ**, Eclipse, and IntelliJ include JUnit,
so all you need to do is to copy the source code
for the tests into the correct directory in your project,
and run them as tests (*aka* test suites).

### In BlueJ

* Copy the test file(s) to same folder as your source code
* BlueJ shows test files as green file icons
* Right-click on a Test file and choose "Test All", or click the "Run Tests" button
* A pop-up window shows the results. Passing tests get a green check mark.

### VS Code

VS Code requires some configuration before you can use JUnit.
It seems to require these extensions: 

* Java Extension Pack by Microsoft
* Language Support for Java by Red Hat
* Java Test Runner by Microsoft

as if that isn't enough work, you also have to download
the JUnit libraries (JAR files) yourself. 
For JUnit 4 you need 2 files:
```
    junit-4.12.jar or junit-4.13.jar
    hamcrest-core-1.3.jar
```

Download them from: [https://junit.org/junit4/](https://junit.org/junit4/) which
has a [Download and Install](https://github.com/junit-team/junit4/wiki/Download-and-Install) link.
Download just the JAR files, don't use Maven or Gradle.

After you download `junit-4.12.jar` and `hamcrest-core-1.3.jar` 
you need to add them do your VS Code project "classpath".

There are 2 solutions:

1. Easy but inefficient solution:
    * inside your project create directory named `lib` (actually any name is OK)
    * copy junit-4.12.jar and hamcrest-1.3.jar to `lib`
    * close and reopen the project to update the project dependencies
2. Better solution but more work:
    * create a library directory for JUnit. For example, `c:\Program Files\Java\junit4` is OK. Mine directory is /opt/lib/junit.
    * copy junit-4.12.jar and hamcrest-core-1.3.jar to your junit4 directory.
    * In your VSCode project, edit the file `.vscode/settings.json` and add a line to the variable `java.project.referencedLibraries` like this:
    ```
    "java.project.referencedLibraries": [
          "/home/your_workspace/lab2/** /*.jar",
          "/opt/lib/junit4/*.jar"          <-- use the path to your junit jar files
     ]
    * Close the project and open it again is VS Code

After performing either of these, you should be able to run the tests.
If you open a Test Suite such as `StopwatchTest.java` in an editor window,
a "Run Test" item appears before the class name.

If it doesn't work, try using BlueJ instead.
