package cc.javajobs.factionsbridge.bridge.impl.savagefactions;

import cc.javajobs.factionsbridge.bridge.infrastructure.struct.Faction;
import cc.javajobs.factionsbridge.bridge.impl.factionsuuid.FactionsUUIDFPlayer;
import com.massivecraft.factions.FPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SavageFactionsFPlayer extends FactionsUUIDFPlayer {

    /**
     * Constructor to create a SavageFactionsFPlayer.
     * <p>
     * This class will be used to create each implementation of an 'FPlayer'.
     * </p>
     *
     * @param fPlayer object which will be bridged using the FactionsBridge.
     */
    public SavageFactionsFPlayer(@NotNull FPlayer fPlayer) {
        super(fPlayer);
    }

    /**
     * Method to get the {@link Faction} relative to the FPlayer.
     * <p>
     * This method can return null, please use {@link #hasFaction()} to ensure the safest practice.
     * </p>
     *
     * @return {@link Faction} or {@code null}.
     * @see #hasFaction()
     */
    @Nullable
    @Override
    public Faction getFaction() {
        return new SavageFactionsFaction(fPlayer.getFaction());
    }

}
