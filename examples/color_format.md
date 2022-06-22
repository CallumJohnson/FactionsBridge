```
    public static String getFormatted(Player player, Faction faction){
        String formattedName="&cERROR_GETTING_FACTION_NAME";
        Faction playerFaction = api.getFaction(player);
        assert playerFaction != null;
        Relationship relationship = playerFaction.getRelationshipTo(faction);
        if(relationship.equals(Relationship.MEMBER)){
            formattedName="&a"+faction.getName();
        }else if(relationship.equals(Relationship.ALLY)){
            formattedName="&5"+faction.getName();
        }else if(relationship.equals(Relationship.TRUCE)){
            formattedName="&d"+faction.getName();
        }else if(relationship.equals(Relationship.ENEMY)){
            formattedName="&c"+faction.getName();
        }else if(relationship.equals(Relationship.DEFAULT_RELATIONSHIP)){
            formattedName="&b"+faction.getName();
        }
        return ChatColor.translateAlternateColorCodes('&', formattedName);
    }
```

This is the function I use to get the format of a faction for a player, e.g. you want to make it so the faction name is red if enemy, e.t.c.
The first paramater is the Player you want to get the format of, and the second is the faction you are referring to.
