package org.lepigslayer.cSDay.Wand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.lepigslayer.cSDay.CSDay;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class WandGiver implements CommandExecutor, TabCompleter {
    private CSDay plugin;

    public WandGiver(CSDay plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) return true;

        System.out.println("Starting Recompile");
        String file = "BasicWand";
        if(strings.length > 0) file = strings[0];
        CSDay.compiler.recompile(file);

        Player p = ((Player) sender);

        p.getInventory().clear();

        p.getInventory().addItem(CSDay.compiler.getWrapper().getWand().assemble());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] strings) {
        File file = plugin.getDataFolder();
        return Arrays.stream(file.list())
                .filter(s->s.endsWith(".java"))
                .map(s->s.replace(".java",""))
                .toList();
    }

}
