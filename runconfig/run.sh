#!/usr/bin/env bash

pushd ..
./gradlew build
popd

classpath=../build/libs/logback-classic-1.2.3.jar\;../build/libs/logback-core-1.2.3.jar\;../build/libs/slf4j-api-1.7.26.jar
classpath=../build/libs/mazeGeneration.jar\;$classpath
classpath=../build/classes/java/test\;$classpath
classpath=.\;$classpath

echo $classpath

java -cp $classpath mikejyg.mazeGeneration.StGenTester 3 3 1 | tee test.out

java -cp $classpath mikejyg.mazeGeneration.StGenTester 3 3 2 | tee -a test.out

java -cp $classpath mikejyg.mazeGeneration.StGenTester 3 3 3 | tee -a test.out

java -cp $classpath mikejyg.mazeGeneration.StGenTester 3 3 4 | tee -a test.out

java -cp $classpath mikejyg.mazeGeneration.StGenTester 10 20 1 | tee -a test.out

echo comparing with golden log...
diff golden/test.out test.out
