# Changes (in no particular order)

- Enable more ant targets
- Enable snapshot publication
- Optionally rank matches in overloaded varargs resolution
- Implement safe default configuration for XML parser
- Initial support for JDK 25
- Update Windows launcher
- Improve backward compatibility of oversized function handling
- Reduce contention around `JyAttribute.getAttr` to improve performance
- Adapt tests to run behind a proxy
- Switch to jakarta.servlet-api (from jakartaee-api)
- Use property `stdout.encoding` in place of `sun.stdout.encoding` where available
- Enable codespell
- CI pipelines on latest ubuntu, mac and windows
- Include the installer in the gradle build (without publishing)


# Dependency Updates

| Artifact         | Initial Version | Actual Version |
| ---------------- | --------------  | -------------- |
| ant              | 1.10.15         | 1.10.18        |
| asm              | 9.9             | 9.11.1         |
| bouncycastle     | 1.82            | 1.87           |
| commons-compress | 1.27.1          | 1.28.0         |
| commons-io       | 2.20.0          | 2.22.0         |
| gradle           | 9.2.0           | 9.9.1          |
| guava            | 33.5.0-jre      | 33.8.0-jre     |
| icu4j            | 77.1            | 79.1           |
| informix jdbc    | 15.0.0.2        | 15.0.1.4       |
| jffi             | 1.3.14          | 1.4.7          |
| jline            | 2.14.6          | 4.7.0          |
| jnr-constants    | 0.10.4          | 0.11.4         |
| jnr-ffi          | 2.2.18          | 2.3.7          |
| jnr-posix        | 3.1.21          | 3.2.8          |
| junit            | 4.13.2          | 6.1.13         |
| netty            | 4.2.7.Final     | 4.2.20.Final   |
| ojdbc8           | 23.26.0.0.0     | 23.26.4.0.0    |
