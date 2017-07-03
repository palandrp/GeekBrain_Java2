package ru.kimdo.net_chat.client;

/**
 * @author Pavel Petrikovskiy
 * @version 03.07.17
 */
interface IConstantsClient {
    String SERVER_ADDR = "localhost"; // server net name or "127.0.0.1"
    int SERVER_PORT = 2048; // servet port
    String CLIENT_PROMPT = "$ "; // client prompt
    String LOGIN_PROMPT = "Login: ";
    String PASSWD_PROMPT = "Passwd: ";
    String AUTH_SIGN = "auth";
    String AUTH_FAIL = "Authentication failure.";
    String CONNECT_TO_SERVER = "Connection to server established.";
    String CONNECT_CLOSED = "Connection closed.";
    String EXIT_COMMAND = "exit"; // command for exit
    String TITLE_OF_PROGRAM = "Network chat";
    int START_LOCATION = 200;
    int WINDOW_WIDTH = 350;
    int WINDOW_HEIGHT = 450;
    String NAME_LOG_FILE = "log.txt";
    String NO_LOG_FILE = "SYSTEM: Не удалось создать/открыть лог-файл!";
    String TITLE_BTN_ENTER = "Enter";
}
