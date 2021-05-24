package me.silverwolfg11.pl3xmaptowny.objects;

import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import net.pl3x.map.api.Point;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

// Entry for a town that is going to be rendered
// Stores all data needed for async rendering
public class TownRenderEntry {

    private final UUID townUUID;
    private final String townName;
    private final boolean capital;

    private final Color nationColor;

    private final String clickText;
    private final String hoverText;

    private final String homeBlockWorld;
    private final Point homeBlockPoint;

    private Map<String, ? extends Collection<StaticTB>> worldBlocks;

    public TownRenderEntry(Town town, Color nationColor, String clickText, String hoverText) {
        this.townUUID = town.getUUID();
        this.townName = town.getName();
        this.capital = town.isCapital();

        this.clickText = clickText;
        this.hoverText = hoverText;

        this.nationColor = nationColor;

        this.homeBlockWorld = town.getHomeblockWorld().getName();
        this.homeBlockPoint = getHomeblockPoint(town).orElse(null);

        worldBlocks = townblockByWorlds(town);
    }

    @NotNull
    public UUID getTownUUID() {
        return townUUID;
    }

    @NotNull
    public String getTownName() {
        return townName;
    }

    public boolean isCapital() {
        return capital;
    }

    @NotNull
    public Optional<Color> getNationColor() {
        return nationColor == null ? Optional.empty() : Optional.of(nationColor);
    }

    @NotNull
    public String getClickText() {
        return clickText;
    }

    @NotNull
    public String getHoverText() {
        return hoverText;
    }

    @NotNull
    public String getHomeBlockWorld() {
        return homeBlockWorld;
    }

    @NotNull
    public Optional<Point> getHomeBlockPoint() {
        return Optional.of(homeBlockPoint);
    }

    public boolean hasWorldBlocks() {
        return worldBlocks.isEmpty();
    }

    @NotNull
    public Map<String, ? extends Collection<StaticTB>> getWorldBlocks() {
        return Collections.unmodifiableMap(worldBlocks);
    }

    @NotNull
    private Optional<Point> getHomeblockPoint(Town town) {
        TownBlock homeblock;

        try {
            homeblock = town.getHomeBlock();
        } catch (TownyException e) {
            return Optional.empty();
        }

        int townblockSize = TownySettings.getTownBlockSize();

        int centerOffset = townblockSize / 2;
        int lowerX = homeblock.getX() * townblockSize;
        int lowerZ = homeblock.getZ() * townblockSize;

        return Optional.of(Point.of(lowerX + centerOffset, lowerZ + centerOffset));
    }

    private final Function<String, List<StaticTB>> mappingFunc = s -> new ArrayList<>();
    @NotNull
    private Map<String, ? extends Collection<StaticTB>> townblockByWorlds(Town town) {
        Map<String, List<StaticTB>> worlds = new HashMap<>();

        List<StaticTB> worldBlocks = null;
        String lastWorld = null;
        for (TownBlock townBlock : town.getTownBlocks()) {
            String townblockWorld = townBlock.getWorld().getName();

            if (lastWorld == null || !lastWorld.equals(townblockWorld)) {
                lastWorld = townblockWorld;
                worldBlocks = worlds.computeIfAbsent(lastWorld, mappingFunc);
            }

            worldBlocks.add(StaticTB.from(townBlock.getX(), townBlock.getZ()));
        }

        return worlds;
    }



}
