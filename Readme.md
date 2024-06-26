# JLOX

An implementation of the LOX language from the book [Crafting Interpreters](https://craftinginterpreters.com/).

## Build

This **JLOX** implementation is written in Java 17 and uses Maven as a build tool.

In order to build the project, run the following command:

```bash
./mvnw clean package
```

## Usage

LOX has a Prompt mode and a File mode.

### Prompt mode

```bash
java -jar target/jlox-1.0-SNAPSHOT.jar
```

### File mode

```bash
java -jar target/jlox-1.0-SNAPSHOT.jar <file>
```

Of course replace <file> with the path to the file you want to run.

## Use

You can try also the files in the resources folder

### Arithmetic Operations
So far with the interpreter, you can make some arithmetic calculations:

```jlox
314 == "pi"
```

```jlox
420 + 69
```

More complex operations will be implemented further on.

### Declaration and Assignment of variables

Variables can be dynamically declared:

```jlox
var a = "tuvieja";
```

Also, if a variable exists, can have its value replaced:

```jlox
a = "nolongertuvieja";
```

### If conditions

Similarly, as Java you can declare an if like this:

```jlox
    if ( 1 < 2 ) {
        print true;
    } else { 
        print false;
    }
```

### Logical operators

```jlox
print "hi" or 2;

hi
```

```jlox
print nil or "yes";

yes
```

### While loops

```jlox
{ 
    var i = 0; 
    while (i < 10) { 
        print i; i = i + 1; 
    } 
}
```

### For loops

```jlox
for (var b = 1; a < 10000; b = temp + b) { 
    print a; temp = a; a = b; 
}
```

### Functions

You can try functions with this fibonacci example:

```jlox
fun fib(n) { if (n <=1) return n; return fib(n-2) + fib(n - 1);} for (var i = 0; i < 20; i = i+1) { print fib(i);}
```

## Implemented so far

We all have lives and so do I. I have not implemented the whole language yet, but I am working on it.

So far I have implemented the following:
- Scanner
- Parser
- Interpreter
- Statements and State
- Control Flow
- Functions

Coming next:
- Chapter 12: Classes