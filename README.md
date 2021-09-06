# An Introduction

Emmel is a minimalistic programming language based on the premise of rapid development. It is a high level language that utilizes syntax from Java and C; this makes it very easy for any experienced developer to pick it up.

# The Interpreter

This repository hosts the source code for the interpreter that scans, parses, and interprets emmel code. This interpreter is written in Java.

Run these commands to get this project running on your machine

```
git clone https://github.com/sferia003/emmel.git

cd emmel

javac -cp . com/sferia/emmel/*.java
```

Now create a file with a .emmel extension and run it like so.

```
java -cp . com/sferia/emmel/Emmel FileName.emmel
```

# Example

There is an example emmel file in the root directory of this project to help get you acquainted to the language.

# Resources

Robert Nystrom's Crafting Interpreters Book was an invaluable resource in designing this language. This interpreter utilized the tutorial in his implementation of his own language.