# async4j
This is a small, lightweight, and zero dependency library that failitate working with async operation in Java. This java library provides similar opeation like async await in nodejs. 

# Prereq
This library runs off of annotation. So, there are some prerequisite the method needs to pass if we are planning to annotate as **Async**. Here are those prerequisites.
* You can only annotate methods in Interface as Async 
* You can only annotate non static methods as Async 
* You can only annotate public methods as Async 
* The Async annotated method name should starts with "async"
* The Async annotated method should have at least one parameter which is reserved parameter. The reserved first parameter needs to be of type boolean which determines is method call going to be blocking(await) or non-blocking.

Don't worry, you don't have to remember all of this. Library comes with annotation processor that runs in compile time to check if **Async** annotation in your project is correct. This also provides you with any helpful message in compile time if there is any issue with **Async** annotation. An example project [async4j-test](async4j-test) which has some examples of compile time checks the library does. 
If you are looking for an example of how to use the library, please look at [test files](async4j/tree/master/async4j/src/test/java/asyncawait/test). 
