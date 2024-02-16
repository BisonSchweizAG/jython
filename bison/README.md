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

# TODO
- do we need jnr-*.jar in bison tools?
-