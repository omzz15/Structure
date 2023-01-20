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
1. In your project, make sure you can get libraries from Maven Central(FTC Projects should automaticlly have this)
2. Add the library to the gradle using:
    ```
    implementation 'io.github.omzz15:supplier:1.1.1-RELEASE'
    ``` 
    *Note: the version numbers may not line up with the main, but they are the same version. 
3. Enjoy :)

# How To Use
Check out the example code in src/test/java
