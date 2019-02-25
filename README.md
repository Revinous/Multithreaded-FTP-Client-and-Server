# distributed-system-2
Assignment 2

### Remaning

1) Mutual Exclusion
2) Implementation of clearing garbage (Clearing only files and removing locls)
3) Check afer 1000B if command terminated

### Completed
1) Created Server.java which spawns ServerTPort and ServerNPort
2) ServerNPort accepts commands and execute them
3) created AmperHandler thread that runs in background in client when get& and put& 
4) on terminate command, client relaying with ServerTPort
5) get& put&
6) Create table for termniate (Create table in Server)

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
