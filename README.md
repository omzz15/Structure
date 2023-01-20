# Java Structures

A simple java library that provides a way to manage trees of children and parents. This library contains a lot of boilerplate code that I use quite often, so I decided to make a library for anyone who might need the same functionality.

## What does it do?
- Provides interfaces and classes that can be implemented/extended to manage child/parent relations between objects.
- Automatically attach child to parent or parent to child if they implement the correct interface.
- Provide methods for invoking actions when a child or parent is attached or detached.
- Automatically detach the old parent(and detach the child from the old parent) before adding a new parent.
- Generally allows for a simpler way to implement structures rather than writing boilerplate code.
- Use generics to keep code efficient and restrict what can be a child/parent.

### Future Additions
- System to restrict what can be added to a structure(adding parent/child) based on conditions.
- Community Requests(your input is always important)

# How To Install
Currently, this library is only available on Maven Central, but I might also compile binaries and put them as releases on Git Hub.

To install with Maven:
1. In your project, make sure you can get libraries from Maven Central(maven projects should automatically have this, but you can search how to do this online if it's not there)
2. Add this library as a dependency
  - For Maven, put this in the dependencies section in your pom.xml: 
    ```
    <dependency>
      <groupId>io.github.omzz15</groupId>
      <artifactId>supplier</artifactId>
      <version>1.2.3-RELEASE</version>
    </dependency>
    ```
  - There is also an FTC version (using java 8 instead of 18) you can add to grade using:
    ```
    implementation 'io.github.omzz15:supplier:1.1.1-RELEASE'
    ``` 
    *Note: the version numbers may not line up, but they are the same version. For more information check out the ftc_master branch
  - For other platforms or to use different versions, check out Maven Central: https://central.sonatype.dev/artifact/io.github.omzz15/supplier/1.2.3-RELEASE/versions
3. Enjoy :)

# How To Use
Check out the example code in src/test/java
