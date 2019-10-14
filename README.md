# Multithreaded FTP Client and Server

## Introduction
The aim of this project is to design multi-threaded FTP servers. The client will be able to handle multiple commands (from same user) simultaneously and server should be able to handle multiple commands from multiple clients.
The client and server will support the same set of commands as indicated in project 1 (get, put,
delete, ls, cd, mkdir, pwd, quit). In addition, they will support one more command called
“terminate”, which is used for terminating a long-running command (e.g., get and put on large
files) from the same client. 

### Remaning
None

### Current Issues
1) Sometime on quiting getting error after quit command :
```
java.io.EOFException
        at java.io.ObjectInputStream$BlockDataInputStream.peekByte(ObjectInputStream.java:2959)
        at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1539)
        at java.io.ObjectInputStream.readObject(ObjectInputStream.java:430)
        at CommandHandler.run(CommandHandler.java:30)
```
2) get&, put&, get, put not working on different systems: Getting same error:
```
java.io.EOFException
        at java.io.ObjectInputStream$BlockDataInputStream.peekByte(ObjectInputStream.java:2959)
        at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1539)
        at java.io.ObjectInputStream.readObject(ObjectInputStream.java:430)
        at CommandHandler.run(CommandHandler.java:30)
```
### Completed
1) Created Server.java which spawns ServerTPort and ServerNPort
2) ServerNPort accepts commands and execute them
3) created AmperHandler thread that runs in background in client when get& and put& 
4) on terminate command, client relaying with ServerTPort
5) get& put&
6) Create table for termniate (Create table in Server)
7) Implementation of clearing garbage (Clearing only files)
8) Check afer 1000B if command terminated

## Installation

1) Create two projects in eclipse (Server and Client)
2) In Server Project, select Server.java, then goto Run(Run Menu)-> Run Configuration -> Argument Tab -> Auguments type
  9876  
  9877
3) Run client normally (Need to change afterwards to accept port number from command line, for now kept static)

## Project Design

![alt text](/image_6546160.JPG)


## Authors

1) Akshay Kokane
2) Abhilash Dorle
