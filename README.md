# Java Structures

A simple java library that provides a way to manage trees of children and parents. This library contains a lot of boilerplate code that I use quite often, so I decided to make a library for anyone who might need the same functionality.

## What does it do?
- Provides interfaces and classes that can be implemented/extended to manage child/parent relations between objects.
- Automatically attach child to parent or parent to child if they implement the correct interface.
- Provide methods for invoking actions when a child or parent is attached or detached.
- Automatically detach the old parent(and detach the child from the old parent) before adding a new parent.
- Generally allows for a simpler way to implement structures rather than writing boilerplate code.
- Use generics to keep code efficient and restrict what can be a child/parent.

## Future Additions
- More examples
- Community Requests(your input is always important)

# How To Install
**Warning: A some older versions have breaking changes so freely switching between versions may not be possible. Any version after 2.0.0 should be cross compatible and I will use a new version number(3.0.0) when there are breaking changes.

There are multiple ways to use this library, but it was primarily made for Maven/Gradle so that will be the most up to date. There will also be releases on GitHub, but I would recommend using Maven.

## To Install with Maven:
1. In your project, make sure you can get libraries from Maven Central(This should be automatically available in maven projects)
2. Add the library to the project(by default it should be in the dependencies section of pom.xml)
   ```
   <dependency>
      <groupId>io.github.omzz15</groupId>
      <artifactId>structure</artifactId>
      <version>2.2.1-FTCRELEASE</version>
   </dependency>
   ```
3. Enjoy :)

## To Install with Gradle
1. Add the mavenCentral() repository to the repositories section(it should be in build.gradle by default)
2. Add the library to the gradle using:
    ```
    implementation 'io.github.omzz15:structure:2.2.1-FTCRELEASE'
    ```
3. Enjoy :)

# How To Use
Check examples [here](./src/test/java/examples)
