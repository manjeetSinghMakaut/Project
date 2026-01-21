# Simple Test Runner - No Maven needed
$projectDir = $PSScriptRoot
$mavenRepo = Join-Path $env:USERPROFILE ".m2\repository"
$targetDir = Join-Path $projectDir "target"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Running Tests Directly with Java" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Collecting dependencies..." -ForegroundColor Yellow

$jars = @()

# TestNG
$testngPath = Join-Path $mavenRepo "org\testng\testng\7.10.2\testng-7.10.2.jar"
if (Test-Path $testngPath) { 
    $jars += $testngPath
    Write-Host "  Found TestNG" -ForegroundColor Green 
}

# Selenium - find all selenium jars
$seleniumPath = Join-Path $mavenRepo "org\seleniumhq\selenium"
if (Test-Path $seleniumPath) {
    $seleniumJars = Get-ChildItem $seleniumPath -Recurse -Filter "*.jar" -ErrorAction SilentlyContinue
    foreach ($jar in $seleniumJars) {
        $jars += $jar.FullName
    }
    Write-Host "  Found Selenium ($($seleniumJars.Count) jars)" -ForegroundColor Green
}

# Apache POI
$poiPath = Join-Path $mavenRepo "org\apache\poi"
if (Test-Path $poiPath) {
    $poiJars = Get-ChildItem $poiPath -Recurse -Filter "*.jar" -ErrorAction SilentlyContinue
    foreach ($jar in $poiJars) {
        $jars += $jar.FullName
    }
    Write-Host "  Found Apache POI ($($poiJars.Count) jars)" -ForegroundColor Green
}

# Commons IO
$commonsPath = Join-Path $mavenRepo "commons-io\commons-io\2.15.1\commons-io-2.15.1.jar"
if (Test-Path $commonsPath) {
    $jars += $commonsPath
    Write-Host "  Found Commons IO" -ForegroundColor Green
}

# ExtentReports
$extentPath = Join-Path $mavenRepo "com\aventstack\extentreports"
if (Test-Path $extentPath) {
    $extentJars = Get-ChildItem $extentPath -Recurse -Filter "*.jar" -ErrorAction SilentlyContinue
    foreach ($jar in $extentJars) {
        $jars += $jar.FullName
    }
    Write-Host "  Found ExtentReports ($($extentJars.Count) jars)" -ForegroundColor Green
}

# JCommander (required by TestNG)
$jcommanderPath = Join-Path $mavenRepo "com\beust\jcommander"
if (Test-Path $jcommanderPath) {
    $jcommanderJars = Get-ChildItem $jcommanderPath -Recurse -Filter "*.jar" -ErrorAction SilentlyContinue
    foreach ($jar in $jcommanderJars) {
        $jars += $jar.FullName
    }
    Write-Host "  Found JCommander ($($jcommanderJars.Count) jars)" -ForegroundColor Green
}

# SLF4J (required by TestNG)
$slf4jPath = Join-Path $mavenRepo "org\slf4j"
if (Test-Path $slf4jPath) {
    $slf4jJars = Get-ChildItem $slf4jPath -Recurse -Filter "*.jar" -ErrorAction SilentlyContinue
    foreach ($jar in $slf4jJars) {
        $jars += $jar.FullName
    }
    Write-Host "  Found SLF4J ($($slf4jJars.Count) jars)" -ForegroundColor Green
}

# Find all other dependencies that TestNG might need
$testngDeps = @("org.yaml.snakeyaml", "org.webjars.jquery", "com.google.guava")
foreach ($dep in $testngDeps) {
    $depPath = Join-Path $mavenRepo ($dep.Replace(".", "\"))
    if (Test-Path $depPath) {
        $depJars = Get-ChildItem $depPath -Recurse -Filter "*.jar" -ErrorAction SilentlyContinue
        foreach ($jar in $depJars) {
            $jars += $jar.FullName
        }
    }
}

# Add compiled classes
$classesDir = Join-Path $targetDir "classes"
$testClassesDir = Join-Path $targetDir "test-classes"
$jars += $classesDir
$jars += $testClassesDir

Write-Host ""
Write-Host "Running tests..." -ForegroundColor Cyan
Write-Host ""

# Build classpath
$classpath = $jars -join ";"

# Run TestNG
$testngXml = Join-Path $projectDir "testng.xml"
& java -cp $classpath org.testng.TestNG $testngXml

$exitCode = $LASTEXITCODE
Write-Host ""
if ($exitCode -eq 0) {
    Write-Host "ALL TESTS COMPLETED!" -ForegroundColor Green
} else {
    Write-Host "Some tests failed. Check the output above." -ForegroundColor Red
}

$reportDir = Join-Path $targetDir "surefire-reports"
Write-Host "Test reports: $reportDir" -ForegroundColor Cyan

