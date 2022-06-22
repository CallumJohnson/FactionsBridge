Note: For all the examples, you need your API variable, you can see how to get this in the index readme.

```
Player p = Bukkit.getPlayer("Authorises");
FPlayer fp = api.getFPlayer(p);
Faction f = fp.getFaction();
Bukkit.getLogger().info(f.getName());
```

In this example, you grab the player from their username, then get their f player. The f player is all the factions info about the player. Then you get their faction, if they are in wilderness, this will return the same thing as `api.getWilderness()`.
