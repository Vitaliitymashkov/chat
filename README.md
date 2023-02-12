# Chat

You need to first run the main() method in the SocketServerRunner class.
    Then run the main() method in the SocketClientRunner class. This way you can send messages from the client
    to the server, and respond from the server to the client. The program runs until the client sends a "stop" message.


## Manual

To run several clients it is necessary
1. To build JAR file


       https://www.jetbrains.com/help/idea/compiling-applications.html#build_artifact

2. To start this file with the command (navigate to the proper folder first)

        cd out\artifacts\chat_client
        java -cp chat.jar edu.AnastasiiTkachuk.ClientRunner

