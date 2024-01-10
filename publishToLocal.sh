#!/bin/bash
VERSION="local-"$(git log -n1 --format="%h")
echo "Publishing version ${VERSION} to Maven local repo"
./gradlew clean jar publishToMavenLocal -Pversion=$VERSION