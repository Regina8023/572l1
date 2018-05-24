#!/bin/bash
javac -d "classes" Parser/src/parserdef/*.java
javac -cp "classes:Parser/lib/poi-3.17/*" -d "classes" Parser/src/handler/*.java
java -cp "classes:Parser/lib/poi-3.17/*:Parser/lib/poi-3.17/lib/*:Parser/lib/poi-3.17/ooxml-lib/*" handler.SocketServer &