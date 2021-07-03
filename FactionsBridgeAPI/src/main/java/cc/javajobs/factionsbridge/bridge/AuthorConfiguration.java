package cc.javajobs.factionsbridge.bridge;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * An AuthorConfiguration is a Configuration of Contributors towards a project at a given time.
 * <p>
 *     Due to the nature of projects changing their contributors/authors increasing,
 *     I needed a way to check multiple sets of Authors against Providers without creating duplicate implementations.
 * </p>
 * @author Callum Johnson
 * @since 03/07/2021 - 11:50
 * @see Provider#Factions_FactionsUUID
 */
public class AuthorConfiguration {

    /**
     * A Hashset containing the specified authors for this AuthorConfiguration.
     */
    private final HashSet<String> authors;

    /**
     * Version of this AuthorConfiguration.
     */
    private final String version;

    /**
     * Constructor to initialise an AuthorConfiguration
     *
     * @param version of the config.
     * @param authors for this version of the config.
     */
    public AuthorConfiguration(@NotNull String version, @NotNull String... authors) {
        this.version = version;
        this.authors = new HashSet<>(Arrays.asList(authors));
    }

    /**
     * Method to obtain the {@link #authors} as an {@link ArrayList}.
     *
     * @return {@link ArrayList} of authors.
     */
    @NotNull
    public ArrayList<String> getAuthors() {
        return new ArrayList<>(authors);
    }

    /**
     * Method to determine if the given List of Strings matches this configuration.
     *
     * @param authors to test.
     * @return {@code true} if it does.
     */
    public boolean equals(@NotNull List<String> authors) {
        return this.authors.equals(new HashSet<>(authors));
    }

    /**
     * Method to obtain the configuration as String.
     *
     * @return {@link String} representation of this class.
     */
    @Override
    public String toString() {
        return version + " (" + authors.size() + ")";
    }

}
