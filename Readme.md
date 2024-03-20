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


## Implemented so far

We all have lives and so do I. I have not implemented the whole language yet, but I am working on it.

So far I have implemented the following:
- Scanner
- Parser

Next up:
- Expressions