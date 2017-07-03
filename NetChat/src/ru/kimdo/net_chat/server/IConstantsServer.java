package ru.kimdo.net_chat.server;

/**
 * @author Pavel Petrikovskiy
 * @version 03.07.17
 */
interface IConstantsServer {
    String DRIVER_NAME = "org.sqlite.JDBC";
    String SQLITE_DB = "jdbc:sqlite:chat.db";
    int SERVER_PORT = 2048; // servet port
    String SERVER_START = "Server is started...";
    String SERVER_STOP = "Server stopped.";
    String CLIENT_JOINED = " client joined.";
    String CLIENT_DISCONNECTED = " disconnected.";
    String AUTH_SIGN = "auth";
    String AUTH_FAIL = "Authentication failure.";
    String SQL_SELECT = "SELECT * FROM users WHERE login = '?'";
    String PASSWD_COL = "passwd";
    String EXIT_COMMAND = "exit"; // command for exit
}
