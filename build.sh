#!/bin/sh
# Compile and run:  ./build.sh
mkdir -p out && javac -d out $(find src -name "*.java") && java -cp out railway.Main
