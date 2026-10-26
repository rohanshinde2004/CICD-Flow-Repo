#!/usr/bin/env bash
set -euo pipefail
echo "QA check: verifying required project files..."
for file in \
  pom.xml \
  checkstyle.xml \
  src/main/java/com/edme/demo/PolicyCalculator.java \
  src/test/java/com/edme/demo/PolicyCalculatorTest.java
do
  if [[ ! -f "$file" ]]; then
    echo "QA failed: missing required file $file" >&2
    exit 1
  fi
  echo "OK: $file"
done
echo "QA file checks passed."
