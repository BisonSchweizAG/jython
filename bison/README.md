# Setup Eclipse Workspace
- set JDK 8 as Default
- add JDK 11 as well
- import existing eclipse project
- switch Text File Encoding to UTF-8
- run the `antlr-gen` (Ant) launch configuration (run with JDK 11)
- [overridden by Oomph settings in a bison eclipse:] Java -> Compiler -> Errors/Warnings -> Incomplete 'switch' cases on enum: Warning (instead of Error)
- [overridden by Oomph settings in a bison eclipse:] Java -> Code Style -> Formatter: import `bison/formatting/Jython-like.xml`

# Differences to upstream
- `jakarta` migration