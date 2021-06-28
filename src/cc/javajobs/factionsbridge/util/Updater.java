package cc.javajobs.factionsbridge.util;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The updater class calls the Spiget API to determine the Spigot version and compares it to the running version.
 *
 * <a href="https://github.com/InventivetalentDev/Spiget-Update" target="_blank">Spiget Update (GitHub)</a>
 * @author Callum Johnson &amp; InventiveTalentDev
 */
public class Updater {

    /**
     * Static instance of the Updater class.
     */
    private static Updater instance;

    /**
     * The version status modified by {@link Version#compareTo(Version)} and returned using {@link #getStatus()}.
     */
    private VersionStatus status = VersionStatus.UP_TO_DATE;

    /**
     *  A massive thank you to Spiget for hosting this mechanism.
     *  <p>
     *      Make sure to visit <a href="https://spiget.org" target="_blank">Spiget</a>.
     *  </p>
     */
    private final URL url = new URL("https://api.spiget.org/v2/resources/89716/versions/latest?ut=" + System.currentTimeMillis());

    /**
     * Constructor to compare the version given to the listed Spigot version.
     *
     * @param version of the plugin running, used to compare to.
     * @throws Exception if the spigot version cannot be obtained.
     */
    public Updater(String version) throws Exception {
        instance = this;
        Version pluginVersion = new Version(version);
        Version spigotVersion = new Version(getSpigotVersion());
        if (pluginVersion.compareTo(spigotVersion) > 0) status = VersionStatus.BETA;
    }

    /**
     * Method to obtain the Version Status (comparison results of the listed Spigot version and the running version).
     *
     * @return {@link VersionStatus}.
     */
    public VersionStatus getStatus() {
        return status;
    }

    /**
     * Method to obtain the static instance {@link #instance} of {@link Updater}.
     *
     * @return {@link Updater} instance.
     */
    public static Updater getInstance() {
        return instance;
    }

    /**
     * Method to obtain the Spigot Version of FactionsBridge.
     * <a href="https://www.spigotmc.org/resources/factionsbridge.89716/">FactionsBridge - Spigot.</a>
     *
     * @return Version string listed on Spigot.
     * @throws Exception if the page cannot be retrieved or if the version cannot be found.
     */
    private String getSpigotVersion() throws Exception {
        Pattern pattern = Pattern.compile("\"name\":[^,]+?,");
        Matcher matcher = pattern.matcher(getPage());
        if (matcher.find()) return matcher.group().replace("\"name\": \"", "").replace("\",", "");
        throw new Exception("Unable to find Version in String.");
    }

    /**
     * Method to obtain the page at the url {@link #url} in HTML.
     *
     * @return String html.
     * @throws IOException if the connection cannot be opened or if JSoup fails to parse the HTML.
     * @throws URISyntaxException if the url cannot be converted to URI.
     */
    private String getPage() throws IOException, URISyntaxException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return Jsoup.parse(
                connection.getInputStream(),
                "UTF-8",
                connection.getURL().toURI().toString()
        ).getElementsByTag("body").html();
    }

    /**
     * The Version class stands for one Version, when the plugin boots, two Versions are made and compared.
     * <p>
     *     Versions:
     *     <br>- Running Version
     *     <br>- Listed (Spigot) Version.
     * </p>
     */
    private static class Version implements Comparable<Version> {

        /**
         * The version string to convert into Mini, Minor and Major tags.
         */
        private final String versStr;

        /**
         * Constructor to create a Version, this takes a String and is transformed into its specific parts
         *
         * @param version to convert to integers when required.
         * @see #versStr
         */
        public Version(String version) {
            this.versStr = version;
        }

        /**
         * Major version control.
         *
         * @return major version.
         */
        public int getMajor() {
            return Integer.parseInt(versStr.split("\\.")[0]);
        }

        /**
         * Minor version control.
         *
         * @return minor version.
         */
        public int getMinor() {
            return Integer.parseInt(versStr.split("\\.")[1]);
        }

        /**
         * Mini version control.
         *
         * @return mini version.
         */
        public int getMini() {
            return Integer.parseInt(versStr.split("\\.")[2]);
        }

        /**
         * This toString returns the version formatted using {@link #getMini()}, {@link #getMinor()} and
         * {@link #getMajor()}.
         * @return String representation of the Version.
         */
        @Override
        public String toString() {
            return "Version{" + "major=" + getMajor() + ", minor=" + getMinor() + ", mini=" + getMini() + "}";
        }

        /**
         * Method to compare two versions, updating a variable {@link Updater#status} during comparison.
         *
         * @param o to compare to.
         * @return comparison results.
         * @see Comparable
         * @see VersionStatus
         */
        @Override
        public int compareTo(@NotNull Version o) {
            int result = Integer.compare(getMajor(), o.getMajor());
            if (result == 0) {
                result = Integer.compare(getMinor(), o.getMinor());
                if (result == 0) {
                    result = Integer.compare(getMini(), o.getMini());
                    if (result == 0) {
                        Updater.getInstance().status = VersionStatus.UP_TO_DATE;
                    } else {
                        Updater.getInstance().status = VersionStatus.OUT_OF_DATE_MINI;
                    }
                } else {
                    Updater.getInstance().status = VersionStatus.OUT_OF_DATE_MINOR;
                }
            } else {
                Updater.getInstance().status = VersionStatus.OUT_OF_DATE_MAJOR;
            }
            return result;
        }

    }

    /**
     * The VersionStatus of the project fits into 5 major keys, out of date (mini, minor and major), up-to-date and beta.
     */
    public enum VersionStatus {

        /**
         * This is when the plugin running is newer than the standalone plugin released on Spigot.
         */
        BETA,

        /**
         * This is when the plugin running is the same version as the one released on Spigot.
         */
        UP_TO_DATE,

        /**
         * This is when the plugin running is a large amount of out date,
         * for example '1.x.x' where Spigot lists '2.x.x'.
         */
        OUT_OF_DATE_MAJOR,

        /**
         * This is when the plugin is somewhat out of date.
         * For example, 'x.1.x', where Spigot lists 'x.2.x'.
         */
        OUT_OF_DATE_MINOR,

        /**
         * This is when the plugin is out of date but can be used without large amount of panic.
         * For example, 'x.x.1' where Spigot lists 'x.x.2'.
         */
        OUT_OF_DATE_MINI

    }

}
