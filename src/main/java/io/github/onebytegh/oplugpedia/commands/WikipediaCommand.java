package io.github.onebytegh.oplugpedia.commands;

import io.github.fastily.jwiki.core.Wiki;
import io.github.onebytegh.oplugpedia.OPlugPedia;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class WikipediaCommand implements CommandExecutor {
    private final OPlugPedia plugin;
    private final Wiki wiki;
    public WikipediaCommand(OPlugPedia plugin) {
        this.plugin = plugin;
        this.wiki = plugin.getWiki();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        if(args.length == 0  || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§c/wikipedia <search>");
            return true;
        }
        if(args[0].equalsIgnoreCase("search")) {
            if (args.length == 1) {
                sender.sendMessage("§c/wikipedia search <search>");
                return true;
            }
            String search = String.join(" ", args);
            if (!wiki.exists(search)) {
                sender.sendMessage("§cNo results found for " + search);
                return true;
            }
            String pageAuthor = wiki.getPageCreator(search);
            String extract = wiki.getTextExtract(search);

            sender.sendMessage(ChatColor.YELLOW + " Title: §a" + search);
            sender.sendMessage(ChatColor.YELLOW + "Author: §a" + pageAuthor);
            sender.sendMessage(ChatColor.YELLOW + "  Text: §a" + extract + "...");
            sender.sendMessage(ChatColor.YELLOW + "  Link: §a" + wiki.whatLinksHere(search).get(0));
        } else if(args[0].equalsIgnoreCase("full")) {
            //prepare a book
            String search = String.join(" ", args);
            if (!wiki.exists(search)) {
                sender.sendMessage("§cNo results found for " + search);
                return true;
            }
            String text = wiki.getPageText(search);
            ItemStack book = new ItemStack(Material.BOOK);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();
            bookMeta.setTitle(search);
            bookMeta.setAuthor(wiki.getPageCreator(search));
            bookMeta.setPages(text.split("\n"));
            book.setItemMeta(bookMeta);
            ((Player) sender).getInventory().addItem(book);
        }
        return false;
    }
}
