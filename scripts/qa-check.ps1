$ErrorActionPreference = "Stop"
Write-Host "QA check: verifying required project files..."
$requiredFiles = @(
    "pom.xml",
    "checkstyle.xml",
    "src/main/java/com/edme/demo/PolicyCalculator.java",
    "src/test/java/com/edme/demo/PolicyCalculatorTest.java"
)
foreach ($file in $requiredFiles) {
    if (-not (Test-Path $file)) {
        throw "QA failed: missing required file $file"
    }
    Write-Host "OK: $file"
}
Write-Host "QA file checks passed."
