package dev.binekrasik.cropsplus;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CropsPlus extends JavaPlugin {
	public String version = "1.2.1";
	
	public void onEnable() {
		this.saveDefaultConfig();
		getLogger().info("\n-    -    -    -    -\n"
				+ " >>  CropsPlus "+version+" by Binekrasik\n"
				+ " >>  Enabled!\n"
				+ "-    -    -    -    -");
		
		getCommand("crops").setExecutor(new CropsCmd());
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			if (getConfig().getBoolean("growth-particles.enabled")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!getConfig().getStringList("disabled-worlds").contains(p.getLocation().getWorld().getName())) {
						int radius = getConfig().getInt("growth-particles.display-radius");
						Location loc = p.getLocation();
						World world = loc.getWorld();
						for (int x = -radius; x < radius; x++) {
						    for (int y = -radius; y < radius; y++) {
						        for (int z = -radius; z < radius; z++) {
						            Block block = world.getBlockAt(loc.getBlockX()+x, loc.getBlockY()+y, loc.getBlockZ()+z);
						            
						            if (block.getType().equals(Material.BAMBOO) || block.getType().equals(Material.SUGAR_CANE)) return;
						            
						            if (block.getBlockData() != null) {
							            if (block.getBlockData() instanceof Ageable) {
							            	if (((Ageable)block.getBlockData()).getAge() >= ((Ageable)block.getBlockData()).getMaximumAge()) {
							            		String[] rgb = getConfig().getString("growth-particles.grown-rgb").split(", ");
							            		Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])), 1);
								    			block.getLocation().getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().getX() + 0.5, block.getLocation().getY() + 1, block.getLocation().getZ() + 0.5, getConfig().getInt("growth-particles.amount"), 0, 0.15, 0, dustOptions);
							            	} else {
							            		int age = ((Ageable)block.getBlockData()).getAge();
							            		if (block.getType() == Material.BEETROOT) {
							            			switch (age) {
							            			case 0:
							            				String[] rgb1 = getConfig().getString("growth-particles.stage1-rgb").split(", ");
							            				rgbParticles(rgb1, block);
							            				break;
							            			case 1:
							            				String[] rgb2 = getConfig().getString("growth-particles.stage3-rgb").split(", ");
							            				rgbParticles(rgb2, block);
							            				break;
							            			case 2:
							            				String[] rgb3 = getConfig().getString("growth-particles.stage5-rgb").split(", ");
							            				rgbParticles(rgb3, block);
							            				break;
							            			case 3:
							            				String[] rgb4 = getConfig().getString("growth-particles.stage7-rgb").split(", ");
							            				rgbParticles(rgb4, block);
							            				break;
							            			}
							            		} else {
							            			switch (age) {
							            			case 0:
							            				String[] rgb1 = getConfig().getString("growth-particles.stage1-rgb").split(", ");
							            				rgbParticles(rgb1, block);
							            				break;
							            			case 1:
							            				String[] rgb2 = getConfig().getString("growth-particles.stage2-rgb").split(", ");
							            				rgbParticles(rgb2, block);
							            				break;
							            			case 2:
							            				String[] rgb3 = getConfig().getString("growth-particles.stage3-rgb").split(", ");
							            				rgbParticles(rgb3, block);
							            				break;
							            			case 3:
							            				String[] rgb4 = getConfig().getString("growth-particles.stage4-rgb").split(", ");
							            				rgbParticles(rgb4, block);
							            				break;
							            			case 4:
							            				String[] rgb5 = getConfig().getString("growth-particles.stage5-rgb").split(", ");
							            				rgbParticles(rgb5, block);
							            				break;
							            			case 5:
							            				String[] rgb6 = getConfig().getString("growth-particles.stage6-rgb").split(", ");
							            				rgbParticles(rgb6, block);
							            				break;
							            			case 6:
							            				String[] rgb7 = getConfig().getString("growth-particles.stage7-rgb").split(", ");
							            				rgbParticles(rgb7, block);
							            				break;
							            			}
							            		}
							            	}
							            }
						            }
						        }
						    }
						}
					}
				}
			}
		}, 0L, 10L);
	}
	
	public void onDisable() {
		getLogger().info("\n-    -    -    -    -\n"
				+ " >>  CropsPlus "+version+" by Binekrasik\n"
				+ " >>  Disabled!\n"
				+ "-    -    -    -    -");
	}
	
	public CropsPlus() {}
	
	private void rgbParticles(String[] rgb, Block block) {
		Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])), 1);
		block.getLocation().getWorld().spawnParticle(Particle.REDSTONE, block.getLocation().getX() + 0.5, block.getLocation().getY(), block.getLocation().getZ() + 0.5, getConfig().getInt("growth-particles.amount"), 0.15, 0, 0.15, dustOptions);
	}
}
