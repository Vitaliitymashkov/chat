# Chat
Develop a web-based chat application.
Time: 1 week

It should be a client-server application (or two independent applications: server and index.html file with some scripting) written in any language and hosted somewhere (localhost is OK too).

User should be able to choose his name, change it anytime, and post any text message to the chat.

Do not store messages on the server.
Use web sockets to connect to the server and get and send messages to other users.

When you finish the task, please:
- send a link to your GitHub repository;
- send a link to a working chat (is optional);
- write how much time you spent on the writing of the chat.

## Solution

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

