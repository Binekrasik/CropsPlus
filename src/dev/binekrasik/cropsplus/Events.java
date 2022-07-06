package dev.binekrasik.cropsplus;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
	private CropsPlus plugin = CropsPlus.getPlugin(CropsPlus.class);
	private FileConfiguration config = plugin.getConfig();
	
	@EventHandler
	public void onPlayerRightClickCrop(PlayerInteractEvent event) {
		if (config.getBoolean("enable-rightclick-replant")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (event.getClickedBlock().getBlockData() instanceof Ageable) {
					Block cblock = event.getClickedBlock();
					BlockData bdata = cblock.getBlockData();
					if (((Ageable)bdata).getAge() >= ((Ageable)bdata).getMaximumAge()) {
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_GRASS_BREAK, 100, 1);
						cblock.getLocation().getWorld().spawnParticle(Particle.BLOCK_CRACK, cblock.getLocation().getX() + 0.5, cblock.getLocation().getY() + 0.5, cblock.getLocation().getZ() + 0.5, 50, 0.25, 0.25, 0.25, bdata);
						for (ItemStack drop : cblock.getDrops()) {
							cblock.getLocation().getWorld().dropItem(cblock.getLocation(), drop);
						}
						((Ageable)bdata).setAge(0);
						cblock.setBlockData(bdata);
					}
				}
			}
		}
	}
}
