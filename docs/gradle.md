# Building Yapper with Gradle

Install JDK 25 and set `JAVA_HOME` to its installation directory (or put its
`bin` directory on `PATH`). In IntelliJ, also select JDK 25 as the Gradle JVM.
The build's Java toolchain selects JDK 25 for compilation, tests, and `run`.

Use the checked-in wrapper; a separate Gradle installation is unnecessary:

| Task | Windows PowerShell | macOS / Linux |
| --- | --- | --- |
| Check Gradle and JVM | `.\gradlew.bat --version` | `sh gradlew --version` |
| Clean and build | `.\gradlew.bat clean build` | `sh gradlew clean build` |
| Run the chatbot | `.\gradlew.bat --console=plain run` | `sh gradlew --console=plain run` |
| Run tests | `.\gradlew.bat test` | `sh gradlew test` |

The first invocation downloads Gradle; resolving test dependencies also requires
internet access. Generated files go in `build/`; local Gradle state goes in
`.gradle/`. Both directories are already ignored. Test results, once tests exist,
are available at `build/reports/tests/test/index.html`.

Production sources belong under `src/main/java/yapper` with package `yapper`.
The application entry point is `yapper.Yapper`. JUnit Jupiter tests belong under
`src/test/java/yapper`; the build supplies Jupiter 5.13.4 and its platform launcher.
The `run` task forwards standard input so interactive commands work.

## Wrapper provenance

Gradle 9.1.0 is the first release with official Java 25 runtime and toolchain
support according to the [Gradle compatibility matrix](https://docs.gradle.org/current/userguide/compatibility.html).
The scripts and wrapper JAR come from the official
[Gradle v9.1.0 repository](https://github.com/gradle/gradle/tree/v9.1.0).
The downloaded JAR was checked against Gradle's
[published wrapper SHA-256](https://services.gradle.org/distributions/gradle-9.1.0-wrapper.jar.sha256):

```text
76805e32c009c0cf0dd5d206bddc9fb22ea42e84db904b764f3047de095493f3
```

`gradle-wrapper.properties` pins the distribution URL and its
[published SHA-256](https://services.gradle.org/distributions/gradle-9.1.0-bin.zip.sha256),
which the wrapper checks when downloading the distribution.

## Increment integration

This build targets the A-Packages result. Until that increment is integrated,
`clean build` can compile the existing default-package sources, but normal `run`
requires the package change. The Gradle increment was smoke-tested using an
ignored init script that temporarily set the `run` task's `mainClass` to `Yapper`
and its working directory to `.gradle/smoke-run`. Production sources were not moved
or edited. Repeat ordinary `run` after integrating A-Packages.

No tests exist in this increment: `test NO-SOURCE` does not establish test coverage.
Integrate A-JUnit and rerun `test` to validate those tests. Executable JAR
configuration belongs to the separate A-Jar increment.

The [course A-Gradle instructions](https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-w3.html)
also specify merging the `add-gradle-support` branch. No merge, commit, or tag was
performed because this implementation assignment explicitly defers those actions.
The eventual milestone is the lightweight tag `A-Gradle`.
