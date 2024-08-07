# Setup Eclipse Workspace
- set JDK 21 as Default
- import existing eclipse project
- switch Text File Encoding to UTF-8
- run the `antlr-gen` (Ant) launch configuration (run with JDK 11)
- [overridden by Oomph settings in a bison eclipse:] Java -> Compiler -> Errors/Warnings -> Incomplete 'switch' cases on enum: Warning (instead of Error)
- [overridden by Oomph settings in a bison eclipse:] Java -> Code Style -> Formatter: import `bison/formatting/Jython-like.xml`
- [overridden by Oomph settings in a bison eclipse:] Java -> Editor -> Save Actions: format edited lines


# Hint how to run unit tests from within Eclipse
- add `exposed-2.7.x.jar` topmost to the run configuration of those unit tests which need initialization
- at the moment this is the full `jython-2.7.x.jar`
  
# Differences to upstream
- `jakarta` migration
- improvement of `string.py` for `java.lang.String` arguments
- consistent calling/overwriting of protected final superclass methods
- build with JDK 17
- artifact name is `jython` (instead of `jython.slim`)
- add an automatic module name `org.python.jython.bison`
- newer gradle version
- newer external libraries (except: guava is older because of gradle variants)
- remove jline import from Py.java (to allow jline being excluded from ear)
- replace `AccessControlException`
- remove `getSecurityManager()`
- treat `PyString` and `PyUnicode` more like Java `String`
 
# Artifactory publishing of a SNAPSHOT
- `./gradlew clean publish`
- copy `build2/stagingRepo/org/python/jython/2.7.x-SNAPSHOT/jython-2.7.x-yyymmdd.hhmmss-1.pom` to `build2/stagingRepo/org/python/jython/2.7.x-SNAPSHOT/jython-2.7.x-SNAPSHOT.pom`
- deploy `jython-2.7.x-SNAPSHOT.pom`
- deploy `/build2/libs/jython-2.7.x-SNAPSHOT.jar` (adjust the `Group ID` to `org.python`)
- deploy `/build2/libs/jython-2.7.x-SNAPSHOT-sources.jar` (adjust the `Group ID` to `org.python`, set `Classifier` to `sources`)
- deploy `/build2/libs/jython-2.7.x-SNAPSHOT-javadoc.jar` (adjust the `Group ID` to `org.python`, set `Classifier` to `javadoc`)

# Artifactory publishing of a final version
- make sure your local copy is clean (`git status` should display `nothing to commit, working tree clean`)
- `git tag -a v2.7.x -m "Jython version 2.7.x"`
- `git push origin v2.7.x`
- `./gradlew clean publish` (this creates all files correctly named in `build2/stagingRepo/org/python/jython/2.7.x`)
- deploy `jython-2.7.x.pom`
- deploy `jython-2.7.x.jar` (adjust the `Group ID` to `org.python`)
- deploy `jython-2.7.x-sources.jar` (adjust the `Group ID` to `org.python`, set `Classifier` to `sources`)
- deploy `jython-2.7.x-javadoc.jar` (adjust the `Group ID` to `org.python`, set `Classifier` to `javadoc`)
- prepare for the next version by increasing the patch version in `build.gradle`

# TODO
- MANIFEST.MF does not contain -SNAPHSOT if it is a SNAPSHOT
- test jline on Ubuntu
- replace `finalize()`