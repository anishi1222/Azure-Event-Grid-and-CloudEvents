# EventGridSubscriber

This is a sample JAX-RS application which subscribes Azure Event Grid topic and handle events using CloudEvents APIs.

## How to Build

### Ordinally

Use the following command to build the application.

```bash
maven clean package
```

### In case of using jlink to create custom runtime image

Build command is different from previous one.

```bash
maven clean package -Pjlink-image
```

In case of invoking the artifact, use the following command.

```bash
[Source Code Home]/target/EventGridSubscriber-jri/bin/start
```

## How to containerize the application

### Ordinally

Ordinally, use the following command to containerize the application.
```bash
docker build -t {Container Image Name}:{Tag} .
```

### In case of containerizing custom runtime image created with jlink

The command is different from previous one.
```bash
docker build -t {Container Image Name}:{Tag} . -f Dockerfile.jlink
```
