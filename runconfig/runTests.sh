#!/usr/bin/env bash

pushd ..
./gradlew build
popd

./run.sh 3 3 1 | tee test.out

./run.sh 3 3 2 | tee -a test.out

./run.sh 3 3 3 | tee -a test.out

./run.sh 3 3 4 | tee -a test.out

./run.sh 20 10 1 | tee -a test.out

echo comparing with golden log...
diff golden/test.out test.out
