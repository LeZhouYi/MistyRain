package skily_leyu.mistyrain.block;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraftforge.registries.IForgeRegistry;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.creativetabs.MRCreativeTabs;

public class MRBlocks {

    public static final Block woodenPot = getRegistryBlock(BlockMRWoodenPot.class, "wooden_pot", "woodenPot");

    private static Block getRegistryBlock(Class<? extends Block> classIn, String registryName, String unlocalizedName) {
		try {
			return classIn.getConstructor().newInstance().setRegistryName(MistyRain.MODID, registryName)
					.setUnlocalizedName(unlocalizedName).setCreativeTab(MRCreativeTabs.tabMistyRain);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return Blocks.AIR;
	}

    @Mod.EventBusSubscriber
	public static class ObjectRegistryHandler {
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			MistyRain.getLogger().info("Registrying Blocks[HerbalGarden]");
			final IForgeRegistry<Block> registry = event.getRegistry();
            registry.registerAll(woodenPot);
        }

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			MistyRain.getLogger().info("Registrying items[MistryRain]");
			final IForgeRegistry<Item> registry = event.getRegistry();
            registerItem(registry, woodenPot);
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void registerItemModels(final ModelRegistryEvent event) {
			MistyRain.getLogger().info("Registrying item models[MistryRain]");
            registerItemModel(0, woodenPot);
		}

		public static void registerItem(final IForgeRegistry<Item> registry, Block... blockList) {
			for (Block block : blockList) {
				registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}
		}

		@SideOnly(Side.CLIENT)
		public static void registerItemModel(int parMetaData, Block... parblock) {
			for (Block block : parblock) {
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), parMetaData,
						new ModelResourceLocation(block.getRegistryName(), "inventory"));
			}
		}
	}

}
