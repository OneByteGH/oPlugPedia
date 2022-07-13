package io.github.onebytegh.oplugpedia;

import io.github.fastily.jwiki.core.Wiki;
import io.github.onebytegh.oplugpedia.commands.WikipediaCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class OPlugPedia extends JavaPlugin {
    private Wiki wiki;

    @Override
    public void onEnable() {
        // Plugin startup logic
        wiki = new Wiki.Builder().build();
        this.getCommand("wikipedia").setExecutor(new WikipediaCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Wiki getWiki() {
        return wiki;
    }
}
