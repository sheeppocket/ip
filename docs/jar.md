# Build and run Yapper as a JAR

The Gradle build applies `gradle/jar.gradle` to package the `yapper.Yapper` entry point.

The script configures the standard Java plugin's `jar` task with an executable
manifest and a stable filename. Yapper uses only JDK classes at runtime, so it
does not need a fat JAR. JUnit belongs to the test dependencies and is not shipped.
If runtime libraries are added later (for example JavaFX), revisit dependency
packaging; this script does not bundle external libraries.

## Build

Install JDK 25 and point `JAVA_HOME` and `PATH` to it. Check `java -version` and
the Gradle JVM before building. From the project root on Windows PowerShell:

```powershell
java -version
.\gradlew.bat --version
.\gradlew.bat clean jar
```

On macOS/Linux, use `./gradlew --version` and `./gradlew clean jar` instead.
On macOS, `sdk use java 25.0.3.fx-zulu` selects the project's required JDK if
installed through SDKMAN.

The executable is `build/libs/yapper.jar`. Build outputs are already ignored by
Git through `/build/`; do not commit the generated JAR.

## Run

Copy only `build/libs/yapper.jar` into an empty folder. Open a terminal in that
folder, ensure `java -version` reports Java 25, and run:

```text
java -jar "yapper.jar"
```

Try these commands:

```text
todo verify jar packaging
list
bye
```

Run the same JAR again and enter `list`, then `bye`. The task should still be
present. Yapper creates `data/yapper.txt` relative to the terminal's working
directory when a task changes. Keep that folder to preserve your tasks.

## Course delivery

The [A-Jar requirements](https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-w3.html#A-Jar)
call for an executable JAR and distribution through a GitHub release, rather
than committing the binary. After integration and validation, create the
lightweight milestone tag `A-Jar` and attach `build/libs/yapper.jar` to a release
when publishing is authorized. Publishing requires a separately authorized push and release upload.

See also the [SE-EDU JAR guide](https://se-education.org/guides/tutorials/jar.html).
