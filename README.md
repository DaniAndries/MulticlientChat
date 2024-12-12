# ✨ Chat Multicliente en Java ✨

<img src="https://media.giphy.com/media/26xBwdIuRJiAIqHwA/giphy.gif" alt="Chat Banner" width="500"/>

Este proyecto es una implementación de un chat multicliente en Java que permite la comunicación en tiempo real entre un servidor y varios clientes conectados a él.

## 🚀 Características

- **🖥️ Servidor centralizado:** Administra las conexiones y retransmite los mensajes a todos los clientes.
- **👥 Clientes múltiples:** Los usuarios pueden conectarse al servidor desde diferentes instancias del cliente.
- **⚡ Mensajes en tiempo real:** Los mensajes se transmiten instantáneamente entre los usuarios conectados.
- **⌨️ Interfaz de consola:** Tanto el servidor como los clientes operan desde la línea de comandos.

## 🛠️ Requisitos

- **Java Development Kit (JDK):** Versión 8 o superior.
- **IDE o Editor de texto:** Recomendado IntelliJ IDEA, Eclipse, VS Code, o cualquier editor con soporte para Java.

## 🗂️ Estructura del proyecto

```
chat-multicliente-java/
├── src/
│   ├── server/
│   │   └── ChatServer.java
│   ├── client/
│   │   └── ChatClient.java
├── README.md
├── .gitignore
└── build/
```

- `ChatServer.java`: Código fuente del servidor.
- `ChatClient.java`: Código fuente del cliente.
- `README.md`: Documentación del proyecto.

## ⚙️ Configuración y Ejecución

### 🖥️ Servidor

1. Navega al directorio del servidor:

   ```bash
   cd src/server
   ```

2. Compila el código:

   ```bash
   javac ChatServer.java
   ```

3. Ejecuta el servidor:

   ```bash
   java ChatServer <puerto>
   ```

   Reemplaza `<puerto>` por el número de puerto que deseas usar.

### 💻 Cliente

1. Navega al directorio del cliente:

   ```bash
   cd src/client
   ```

2. Compila el código:

   ```bash
   javac ChatClient.java
   ```

3. Ejecuta el cliente:

   ```bash
   java ChatClient <host> <puerto>
   ```

   - `<host>`: Dirección IP o nombre de host del servidor.
   - `<puerto>`: Número de puerto en el que está escuchando el servidor.

## 📝 Uso

1. Inicia el servidor con el comando mencionado anteriormente.
2. Conecta varios clientes al servidor utilizando la dirección IP y el puerto configurados.
3. Los clientes pueden enviar mensajes que se retransmitirán a todos los usuarios conectados.

## 📋 Ejemplo

### Salida del Servidor:

```
Servidor iniciado en el puerto 12345...
Cliente conectado: 192.168.1.2
Cliente conectado: 192.168.1.3
Mensaje de 192.168.1.2: Hola a todos!
Mensaje de 192.168.1.3: Hola!
```

### Salida de un Cliente:

```
Conectado al servidor en localhost:12345
Ingrese su mensaje: Hola a todos!
Mensaje recibido: Hola!
```

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! Por favor, abre un *issue* o envía un *pull request* para sugerir mejoras o corregir errores.

## 📜 Licencia

Este proyecto está licenciado bajo la [MIT License](LICENSE).

---

¡Gracias por usar este proyecto! Si te ha sido útil, no olvides darle una ⭐ al repositorio en GitHub. 🚀
