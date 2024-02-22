# Setup Eclipse Workspace
- set JDK 17 as Default
- import existing eclipse project
- switch Text File Encoding to UTF-8
- run the `antlr-gen` (Ant) launch configuration (run with JDK 11)
- [overridden by Oomph settings in a bison eclipse:] Java -> Compiler -> Errors/Warnings -> Incomplete 'switch' cases on enum: Warning (instead of Error)
- [overridden by Oomph settings in a bison eclipse:] Java -> Code Style -> Formatter: import `bison/formatting/Jython-like.xml`
- [overridden by Oomph settings in a bison eclipse:] Java -> Editor -> Save Actions: format edited lines

# Differences to upstream
- `jakarta` migration
- improvement of `string.py` for `java.lang.String` arguments
- consistent calling/overwriting of protected final superclass methods
- build with JDK 17

# Artifactory publishing
- `./gradlew publish`
- copy `build2/stagingRepo/org/python/jython/2.7.x-SNAPSHOT/jython-2.7.x-yyymmdd.hhmmss-1.pom` to `build2/stagingRepo/org/python/jython/2.7.x-SNAPSHOT/jython-2.7.x-SNAPSHOT.pom`
- deploy `jython-2.7.x-SNAPSHOT.pom`
- deploy `/build2/libs/jython-2.7.x-SNAPSHOT.jar` (adjust the `Group ID` to `org.python`)
- deploy `/build2/libs/jython-2.7.x-SNAPSHOT-sources.jar` (adjust the `Group ID` to `org.python`, set `Classifier` to `sources`)
- deploy `/build2/libs/jython-2.7.x-SNAPSHOT-javadoc.jar` (adjust the `Group ID` to `org.python`, set `Classifier` to `javadoc`)


# TODO
- MANIFEST.MF does not contain -SNAPHSOT if it is a SNAPSHOT
- test jline on Ubuntu
- maketrans UnicodeDecodeError (see also: PyString in 2.5.3.bison)