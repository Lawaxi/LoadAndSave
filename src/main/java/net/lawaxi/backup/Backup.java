package net.lawaxi.backup;

import javafx.scene.shape.Path;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.util.logging.Logger;

public final class Backup extends JavaPlugin {

    Logger logger;
    FileConfiguration config;

    @Override
    public void onEnable() {
        logger=getLogger();
        logger.info("Enabling Backup By Lawaxi");

        saveDefaultConfig();
        config=getConfig();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        logger.info("Disabling Backup By Lawaxi");
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cn =command.getName();
        if(cn.equalsIgnoreCase("saveme"))
        {
            this.method(0,(Player)sender);
            return true;

        }
        else if(cn.equalsIgnoreCase("loadme"))
        {
            this.method(1,(Player)sender);
            return true;
        }
        return super.onCommand(sender, command, label, args);
    }

    private void method(int mode, Player player)
    {
        if(player.getWorld().getName().equals("world"))
        {
            String uuid = player.getUniqueId().toString();
            File world = new File("world"+File.separator+"playerdata"+File.separator + uuid + ".dat");
            File worldsave = new File(getDataFolder()+File.separator+ uuid + ".dat");
            YamlConfiguration.loadConfiguration()
            if(mode==0)
            {
                player.kickPlayer(config.getString("info"));
                FileUtil.copy(world,worldsave);
            }
            else
            {
                if(worldsave.exists())
                {
                    player.kickPlayer(config.getString("info"));
                    FileUtil.copy(worldsave,world);

                    if(!config.contains(player.getName()))
                        config.set(player.getName(),0);
                    config.set(player.getName(),config.getInt(player.getName())+1);
                    saveConfig();
                }
                else
                {
                    player.sendMessage(config.getString("warn2"));
                }
            }
        }
        else
        {
            player.sendMessage(config.getString("warn"));
        }
    }
}
