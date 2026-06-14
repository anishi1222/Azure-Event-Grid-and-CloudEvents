package dev.logicojp.micronaut;

import io.micronaut.configuration.picocli.PicocliRunner;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "CloudEvents2EventGrid", description = "...",
        mixinStandardHelpOptions = true)
public class PostEvents implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(PostEvents.class, args);
    }

    public void run() {

        // CloudEventsを送信するためのコードが含まれるクラスをインスタンス化し、呼び出す
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
