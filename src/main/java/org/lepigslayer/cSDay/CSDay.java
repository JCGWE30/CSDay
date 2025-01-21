package org.lepigslayer.cSDay;

import org.bukkit.plugin.java.JavaPlugin;
import org.lepigslayer.cSDay.Wand.Wand;
import org.lepigslayer.cSDay.Wand.WandEventHandler;
import org.lepigslayer.cSDay.Wand.WandGiver;

public final class CSDay extends JavaPlugin {
    public static FileCompiler compiler;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new WandEventHandler(),this);
        WandGiver giver = new WandGiver(this);
        getCommand("give-wand").setExecutor(giver);
        getCommand("give-wand").setTabCompleter(giver);
        getLogger().info("Running recompile");
        compiler = new FileCompiler(this);
        compiler.recompile("BasicWand");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
