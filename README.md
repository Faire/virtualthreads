# README

This project contains simple examples of deadlocks with Java virtual threads with JDK 21.
Each example can be run with either platform (traditional) or virtual threads.
All examples run to completion with the platform threads, but all of them deadlock with virtual threads.

To run the examples, use the following command:

```java -Djdk.virtualThreadScheduler.parallelism=1 com.faire.example.virtualthreads.Main <exampleNumber> <useVirtualThreads>```

Where `<exampleNumber>` is the number of the example to run (1-3) and `<useVirtualThreads>` is a boolean value
indicating whether to use virtual threads or not. The `maxPoolSize` property is set to 1 to ensure that we have a
single carrier thread executing two virtual threads, to demonstrate a common case where the number of virtual threads
is greater than the number of carrier threads.

For example, to run example 1 with virtual threads, use the following command:

```java -Djdk.virtualThreadScheduler.parallelism=1 com.faire.example.virtualthreads.Main 1 true```

To obtain thread dump use the following command:

```jps -l | grep com.faire.example.virtualthreads.Main | cut -d ' ' -f 1 | xargs -I{} jcmd {} Thread.dump_to_file -format=text <fileName>```

Where `<fileName>` is the name of the file to write the thread dump to.

You can find the output and thread dumps for the examples in the `log` directory.
