# Yapper

Yapper is a chatbot application. Given below are instructions on how to set it up.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/yapper/Yapper.java` file, right-click it, and choose `Run Yapper.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
   __   __                               
   \ \ / /_ _ _ __  _ __   ___ _ __     
    \ V / _` | '_ \| '_ \ / _ \ '__|    
     | | (_| | |_) | |_) |  __/ |       
     |_|\__,_| .__/| .__/ \___|_|       
              |_|   |_|                  
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Running from the command line

From the project root, use JDK 25 to compile and run Yapper:

```text
javac -d out src/main/java/yapper/*.java
java -cp out yapper.Yapper
```

All production classes belong to the `yapper` package under the `src/main/java`
source root. Classes in this package can refer to each other without imports.
Yapper stores tasks in `data/yapper.txt` relative to the directory you run it from.

For automated builds and tests, see [the Gradle instructions](docs/gradle.md).
To build and run a standalone executable, see [the JAR instructions](docs/jar.md).

## Finding tasks

Use `find <keyword>` to search task descriptions. Matching ignores letter case
and accepts a substring or phrase, such as `find book` or `find return book`.
Results keep their original task numbers so `mark`, `unmark`, and `delete`
continue to refer to the same tasks. Searching does not change the saved list.
