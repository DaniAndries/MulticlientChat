# ✨ Multi-client Chat in Java ✨

<div align="center">
  <img src="https://media.giphy.com/media/26xBwdIuRJiAIqHwA/giphy.gif" alt="Chat Banner" width="500"/>
</div>

This project is an implementation of a multi-client chat in Java that enables real-time communication between a server and multiple connected clients.

## 🚀 Features

- **🖥️ Centralized Server:** Manages connections and relays messages to all clients.
- **👥 Multiple Clients:** Users can connect to the server from different client instances.
- **⚡ Real-time Messaging:** Messages are instantly transmitted between connected users.
- **⌨️ Console Interface:** Both the server and clients operate via the command line.

## 🛠️ Requirements

- **Java Development Kit (JDK):** Version 8 or higher.
- **IDE or Text Editor:** Recommended IntelliJ IDEA, Eclipse, VS Code, or any editor with Java support.

## 🗂️ Project Structure

```
chat-multiclient-java/
├── src/
│   ├── server/
│   │   └── ChatServer.java
│   ├── client/
│   │   └── ChatClient.java
├── README.md
├── .gitignore
└── build/
```

- `ChatServer.java`: Server source code.
- `ChatClient.java`: Client source code.
- `README.md`: Project documentation.

## ⚙️ Setup and Execution

### 🖥️ Server

1. Navigate to the server directory:

   ```bash
   cd src/server
   ```

2. Compile the code:

   ```bash
   javac ChatServer.java
   ```

3. Run the server:

   ```bash
   java ChatServer <port>
   ```

   Replace `<port>` with the port number you want to use.

### 💻 Client

1. Navigate to the client directory:

   ```bash
   cd src/client
   ```

2. Compile the code:

   ```bash
   javac ChatClient.java
   ```

3. Run the client:

   ```bash
   java ChatClient <host> <port>
   ```

   - `<host>`: IP address or hostname of the server.
   - `<port>`: Port number where the server is listening.

## 📝 Usage

1. Start the server using the command mentioned above.
2. Connect multiple clients to the server using the configured IP address and port.
3. Clients can send messages that will be broadcast to all connected users.

## 📋 Example

### Server Output:

```
Server started on port 12345...
Client connected: 192.168.1.2
Client connected: 192.168.1.3
Message from 192.168.1.2: Hello everyone!
Message from 192.168.1.3: Hi there!
```

### Client Output:

```
Connected to server at localhost:12345
Enter your message: Hello everyone!
Message received: Hi there!
```

## 🤝 Contributions

Contributions are welcome! Please open an *issue* or submit a *pull request* to suggest improvements or fix bugs.

## 📜 License

This project is licensed under the [MIT License](LICENSE).

---

Thank you for using this project! If you found it helpful, don't forget to give the repository a ⭐ on GitHub. 🚀
