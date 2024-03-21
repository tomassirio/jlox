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

## Implemented so far

We all have lives and so do I. I have not implemented the whole language yet, but I am working on it.

So far I have implemented the following:
- Scanner
- Parser
- Interpreter
- Statements and State

Coming up:
- Chapter 9: Control Flow
