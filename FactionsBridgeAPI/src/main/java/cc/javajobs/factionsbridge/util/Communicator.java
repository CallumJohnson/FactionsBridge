package cc.javajobs.factionsbridge.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * The Communicator interface provides functionality to a class to contact Console.
 * <p>
 * Using {@link Bukkit#getConsoleSender()} and {@link ChatColor#translateAlternateColorCodes(char, String)}
 * in unison creates muliple levels of communication to provide Console with Messages of varying types.
 * <br>Types: Log, Warn, Error, Exception
 * </p>
 *
 * @author Callum Johnson
 * @since 23/02/2021 - 09:27
 */
public interface Communicator {

    /**
     * Method to quickly log a message with the {@link ChatColor#WHITE} colorcode.
     * @param message to log
     */
    default void log(String message) {
        messageConsole("LOG", message, ChatColor.WHITE);
    }

    /**
     * Method to quickly log a warning message with the {@link ChatColor#YELLOW} colorcode.
     * @param message to log as a warning
     */
    default void warn(String message) {
        messageConsole("WARN", message, ChatColor.YELLOW);
    }

    /**
     * Method to quickly log an error message with the {@link ChatColor#RED} colorcode.
     * @param message to log as an error
     */
    default void error(String message) {
        messageConsole("ERROR", message, ChatColor.RED);
    }

    /**
     * Method to print a spacer or '=-=' line with the specified colorcode.
     * @param s being the colorcode.
     */
    default void spacer(ChatColor s) {
        messageConsole("INFO", "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=", s);
    }

    /**
     * Method to print an exception to the console in a fancy way.
     * @param ex to print
     * @param message or cause of the exception.
     */
    default void exception(Exception ex, String message) {
        String cls = ex.getClass().getSimpleName();
        String msg = ex.getMessage() == null ? "No message." : ex.getMessage();
        String stack = ex.getStackTrace()[0].toString();
        messageConsole("EXCEPTION", cls, ChatColor.DARK_RED);
        messageConsole("EXCEPTION", msg, ChatColor.DARK_RED);
        messageConsole("EXCEPTION", stack, ChatColor.DARK_RED);
        messageConsole("EXCEPTION", message, ChatColor.DARK_RED);
    }

    /**
     * Method to message console with a given set of variables.
     * @param level of the message
     * @param message to send
     * @param c (color) of the message
     */
    default void messageConsole(String level, String message, ChatColor c) {
        message = ("&f[FactionsBridge][" + level + "] " + c + message);
        Bukkit.getConsoleSender().sendMessage(translate(message));
    }

    /**
     * Method to translate a given string using {@link ChatColor#translateAlternateColorCodes(char, String)}.
     * @param s being the string
     * @return translated string.
     */
    default String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
