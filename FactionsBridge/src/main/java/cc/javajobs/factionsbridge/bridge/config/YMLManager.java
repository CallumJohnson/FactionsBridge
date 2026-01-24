package cc.javajobs.factionsbridge.bridge.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * Simple YML manager for Spigot/Paper plugins
 * - Read YML files
 * - Create YML from defaults with comments
 * - Get values at path
 */
public class YMLManager {

    private final JavaPlugin plugin;
    private final File file;
    private FileConfiguration config;

    public YMLManager(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                plugin.getLogger().severe("Could not create directory " + file.getParentFile().getAbsolutePath());
                return;
            }
        }
        reload();
    }

    /**
     * Reload configuration from disk
     */
    public void reload() {
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            config = new YamlConfiguration();
        }
    }

    /**
     * Get a value at the specified path
     *
     * @param path The configuration path (e.g., "database.host")
     * @return The value, or null if not found
     */
    @Nullable
    public String get(String path) {
        return config.getString(path);
    }

    /**
     * Create YML file from defaults with comments and header
     * Only creates if file doesn't exist
     *
     * @param defaults Map of path -> ConfigEntry (value + comment)
     * @param headerComments Optional header comments for the file
     */
    public void createFromDefaults(Map<String, ConfigEntry> defaults, List<String> headerComments) {
        if (file.exists()) {
            return; // Don't overwrite existing file
        }

        try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {

            // Write header comments
            if (headerComments != null && !headerComments.isEmpty()) {
                for (String comment : headerComments) {
                    writer.write(formatComment(comment));
                    writer.newLine();
                }
                writer.newLine();
            }

            // Write configuration entries
            for (Map.Entry<String, ConfigEntry> entry : defaults.entrySet()) {
                String path = entry.getKey();
                ConfigEntry configEntry = entry.getValue();

                // Write comments for this entry
                if (configEntry.comments != null) {
                    for (String comment : configEntry.comments) {
                        writer.write(formatComment(comment));
                        writer.newLine();
                    }
                }

                // Write the actual configuration line
                writer.write(formatConfigLine(path, configEntry.value));
                writer.newLine();
                writer.newLine(); // Empty line between entries
            }

            // Reload after creation
            reload();

        } catch (IOException e) {
            plugin.getLogger().severe("Could not create config file: " + file.getName());
        }
    }

    /**
     * Format a comment line
     */
    private String formatComment(String comment) {
        if (comment.trim().startsWith("#")) {
            return comment;
        }
        return "# " + comment;
    }

    /**
     * Format a configuration line
     */
    private String formatConfigLine(String path, Object value) {
        if (value instanceof String) {
            return path + ": \"" + value + "\"";
        } else if (value instanceof List) {
            StringBuilder sb = new StringBuilder();
            sb.append(path).append(":\n");
            for (Object item : (List<?>) value) {
                sb.append("  - ");
                if (item instanceof String) {
                    sb.append("\"").append(item).append("\"");
                } else {
                    sb.append(item);
                }
                sb.append("\n");
            }
            return sb.toString().trim();
        } else {
            return path + ": " + value;
        }
    }

    /**
     * Helper class to hold a config value and its comments
     */
    public static class ConfigEntry {
        private final Object value;
        private final List<String> comments;

        public ConfigEntry(Object value, List<String> comments) {
            this.value = value;
            this.comments = comments;
        }
    }
}