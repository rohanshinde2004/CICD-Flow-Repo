#!/usr/bin/env bash
set -euo pipefail
mvn -B clean package
echo "Build artifact:"
ls -lh target/jenkins-upstream-downstream-demo-1.0.0-SNAPSHOT.jar
