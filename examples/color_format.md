```
    public static String getFormatted(Player p, Faction f){
        String re="&cERROR_GETTING_FACTION_NAME";
        Faction ff = VortechFactions.api.getFaction(p);
        assert ff != null;
        Relationship r = ff.getRelationshipTo(f);
        if(r.equals(Relationship.MEMBER)){
            re="&a"+f.getName();
        }else if(r.equals(Relationship.ALLY)){
            re="&5"+f.getName();
        }else if(r.equals(Relationship.TRUCE)){
            re="&d"+f.getName();
        }else if(r.equals(Relationship.ENEMY)){
            re="&c"+f.getName();
        }else if(r.equals(Relationship.DEFAULT_RELATIONSHIP)){
            re="&b"+f.getName();
        }
        return ChatColor.translateAlternateColorCodes('&', re);
    }
```

This is the function I use to get the format of a faction for a player, e.g. you want to make it so the faction name is red if enemy, e.t.c.
The first paramater is the Player you want to get the format of, and the second is the faction you are referring to.
