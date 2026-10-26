# Jenkins Upstream / Downstream Demo

A small Java + Maven project for learning how separate Jenkins jobs run in order.

## What the application does

`PolicyCalculator` adds a base premium and risk loading, rejects negative values, and returns a health message. JUnit tests cover those behaviors.

## Requirements

- Git (optional if you want to push this to GitHub)
- JDK 17 or later
- Maven 3.8+
- Jenkins with the Maven command available on the agent

The POM compiles with Java release 17, so JDK 17 or 21 is suitable.

## Test locally first

From this directory, run these commands one at a time:

```bash
mvn -B clean
mvn -B -DskipTests compile
mvn -B test
mvn -B checkstyle:check
mvn -B package
```

Expected package output:

`target/jenkins-upstream-downstream-demo-1.0.0-SNAPSHOT.jar`

To inspect the test report, open `target/surefire-reports/`.

## Jenkins job flow

Create four **Freestyle projects**. Configure each job with the same Git repository URL and branch (for example, `*/main`). If you are not using Git yet, place the project in a workspace and configure each job to use that same checked-out project. For a realistic setup, use Source Code Management > Git in every job.

### Job 1: `01-Compile`

Build step: **Invoke top-level Maven targets** (if the Maven Integration plugin is installed), or **Execute shell** / **Execute Windows batch command**.

Linux agent command:
```bash
mvn -B -DskipTests clean compile
```

Windows agent command:
```bat
mvn -B -DskipTests clean compile
```

This checks that production Java source compiles. It skips unit tests intentionally.

### Job 2: `02-Test`

In **Build Triggers**, select **Build after other projects are built** and enter:

`01-Compile`

Set the trigger to run only when the upstream build is stable/successful.

Build command:
```bash
mvn -B test
```

Windows agents can use the same Maven command in a batch step.

This compiles test code and runs JUnit tests. Check `target/surefire-reports/` or the Jenkins console output.

### Job 3: `03-QA`

In **Build Triggers**, select **Build after other projects are built** and enter:

`02-Test`

Require the upstream build to be stable/successful.

Linux build steps (two steps, in this order):
```bash
mvn -B checkstyle:check
bash scripts/qa-check.sh
```

Windows build steps (two separate batch/PowerShell steps):
```bat
mvn -B checkstyle:check
```
Then use a PowerShell build step:
```powershell
./scripts/qa-check.ps1
```

The Checkstyle step checks Java source for selected code-quality issues. The script verifies that important project files exist. Any failed command should fail the job.

### Job 4: `04-Package`

In **Build Triggers**, select **Build after other projects are built** and enter:

`03-QA`

Require the upstream build to be stable/successful.

Build command:
```bash
mvn -B package
```

Maven's `package` phase compiles and tests again before creating the JAR. That repeat is expected in this simple learning setup. Later, you can optimize the workflow by passing a built artifact between jobs or using a Pipeline.

## Visual flow

```text
01-Compile  --success-->  02-Test  --success-->  03-QA  --success-->  04-Package
```

If a job fails, the downstream job configured to run only after a stable upstream build should not start.

## Important Jenkins notes

1. Configure the same repository and branch in all four jobs so each job checks out the source.
2. Ensure the Jenkins agent has a compatible JDK and Maven installed, and that `mvn -version` works as the Jenkins service user.
3. Use **Build Now** on `01-Compile` to start the chain.
4. Open each job's **Console Output** to see the command and result.
5. If your Jenkins agent is Linux, use shell build steps. If it is Windows, use Windows batch or PowerShell steps.
6. This setup demonstrates upstream/downstream triggers, but each job performs a fresh checkout and Maven may repeat work. In a production pipeline, a Jenkins Pipeline and archived artifacts are often easier to maintain.
