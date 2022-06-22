Note: For all the examples, you need your API variable, you can see how to get this in the index readme.

```
Player player = Bukkit.getPlayer("Authorises");
FPlayer factionPlayer = api.getFPlayer(player);
Faction faction = factionPlayer.getFaction();
Bukkit.getLogger().info(faction.getName());
```

In this example, you grab the player from their username, then get their f player. The f player is all the factions info about the player. Then you get their faction, if they are in wilderness, this will return `api.getWilderness()`.
