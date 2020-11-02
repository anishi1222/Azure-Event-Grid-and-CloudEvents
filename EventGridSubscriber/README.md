# EventGridSubscriber

This is a sample JAX-RS application which subscribes Azure Event Grid topic and handle events using CloudEvents APIs.<br/>
As this is a Helidon MP based application, operation follows Helidon's way.<br/>

> Project Helidon<br>
> https://helidon.io/

## How to Build

### Ordinally

Use the following command to build the application.

```bash
maven clean package
```

In case of invoking the artifact, use typical command.

```bash
java -jar [Source Code Home]/target/EventGridSubscriber.jar
```

### application built with jlink

Build command is different from previous one.

```bash
maven clean package -Pjlink-image
```

In case of invoking the artifact, use the following command.

```bash
[Source Code Home]/target/EventGridSubscriber-jri/bin/start
```

### Native image built with GraalVM

Build command is different from previous one. Before invoking this command,
- `$GRAALVM_HOME` should be set to path to GraalVM home.
- `$JAVA_HOME` should be set to `$GRAALVM_HOME`.
- `$GRAALVM_HOME/bin` (which is equal to `$JAVA_HOME/bin`) should be add to `$PATH`.

```bash
maven clean package -Pjnative-image
```

In case of invoking the artifact, use the following command.

```bash
[Source Code Home]/target/EventGridSubscriber
```

## How to containerize the application

### Ordinally

Ordinally, use the following command to containerize the application.
```bash
docker build -t {Container Image Name}:{Tag} .
```

### Application built with jlink

The command is different from previous one.
```bash
docker build -t {Container Image Name}:{Tag} . -f Dockerfile.jlink
```

### Native image built with GraalVM

The command is different from previous one.
```bash
docker build -t {Container Image Name}:{Tag} . -f Dockerfile.native
```
