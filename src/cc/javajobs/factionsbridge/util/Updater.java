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
 * <a href="https://github.com/InventivetalentDev/Spiget-Update" target="_blank">Spiget Update (GitHub)</a>
 * @author Callum Johnson & InventiveTalentDev
 */
public class Updater {

    private static Updater instance;
    private VersionStatus status = VersionStatus.UP_TO_DATE;

    /**
     *  A massive thank you to Spiget for hosting this mechanism.
     *  <p>
     *      Make sure to visit <a href="https://spiget.org" target="_blank">Spiget</a>.
     *  </p>
     */
    private final URL url = new URL("https://api.spiget.org/v2/resources/89716/versions/latest?ut=" + System.currentTimeMillis());

    public Updater(String version) throws Exception {
        instance = this;
        Version pluginVersion = new Version(version);
        Version spigotVersion = new Version(getSpigotVersion());
        if (pluginVersion.compareTo(spigotVersion) > 0) status = VersionStatus.BETA;
    }

    public VersionStatus getStatus() {
        return status;
    }

    public static Updater getInstance() {
        return instance;
    }

    private String getSpigotVersion() throws Exception {
        Pattern pattern = Pattern.compile("\"name\":[^,]+?,");
        Matcher matcher = pattern.matcher(getPage());
        if (matcher.find()) return matcher.group().replace("\"name\": \"", "").replace("\",", "");
        throw new Exception("Unable to find Version in String.");
    }

    private String getPage() throws IOException, URISyntaxException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return Jsoup.parse(
                connection.getInputStream(),
                "UTF-8",
                connection.getURL().toURI().toString()
        ).getElementsByTag("body").html();
    }

    private static class Version implements Comparable<Version> {

        private final String versStr;

        public Version(String version) {
            this.versStr = version;
        }

        public int getMajor() {
            return Integer.parseInt(versStr.split("\\.")[0]);
        }

        public int getMinor() {
            return Integer.parseInt(versStr.split("\\.")[1]);
        }

        public int getMini() {
            return Integer.parseInt(versStr.split("\\.")[2]);
        }

        @Override
        public String toString() {
            return "Version{" + "major=" + getMajor() + ", minor=" + getMinor() + ", mini=" + getMini() + "}";
        }

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

    public enum VersionStatus {

        BETA,
        UP_TO_DATE,
        OUT_OF_DATE_MAJOR,
        OUT_OF_DATE_MINOR,
        OUT_OF_DATE_MINI

    }

}
