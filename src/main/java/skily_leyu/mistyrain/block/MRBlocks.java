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
import skily_leyu.mistyrain.block.basic.BlockRainStone;
import skily_leyu.mistyrain.block.basic.BlockSpringMud;
import skily_leyu.mistyrain.block.basic.BlockSwordStone;
import skily_leyu.mistyrain.block.basic.BlockWaterStone;
import skily_leyu.mistyrain.block.plant.flowingcyan.BlockFlowingCyanBranch;
import skily_leyu.mistyrain.block.plant.flowingcyan.BlockFlowingCyanCross;
import skily_leyu.mistyrain.block.plant.flowingcyan.BlockFlowingCyanLeaves;
import skily_leyu.mistyrain.block.plant.flowingcyan.BlockFlowingCyanLog;
import skily_leyu.mistyrain.block.plant.flowingcyan.BlockFlowingCyanRoot;
import skily_leyu.mistyrain.block.plant.flowingcyan.BlockFlowingCyanSapling;
import skily_leyu.mistyrain.creativetab.MRCreativeTabs;
import skily_leyu.mistyrain.utility.MRUtils;

/**
 * 方块注册表
 * @author Skily
 * @version 1.0.0
 */
public class MRBlocks {
    
//	public static final Block springMud = getRegistryBlock(BlockSpringMud.class,"spring_mud","springMud");
//	public static final Block rainStone = getRegistryBlock(BlockRainStone.class,"rain_stone","rainStone");
//	public static final Block waterStone = getRegistryBlock(BlockWaterStone.class,"water_stone","waterStone");
//	public static final Block swordStone = getRegistryBlock(BlockSwordStone.class,"sword_stone","swordStone");

//	public static final Block flowingCyanSapling = getRegistryBlock(BlockFlowingCyanSapling.class,"flowing_cyan_sapling","flowingCyanSapling");
//	public static final Block flowingCyanRoot = getRegistryBlock(BlockFlowingCyanRoot.class,"flowing_cyan_root","flowingCyanRoot");
//	public static final Block flowingCyanLog = getRegistryBlock(BlockFlowingCyanLog.class,"flowing_cyan_log","flowingCyanLog");
//	public static final Block flowingCyanLeaves = getRegistryBlock(BlockFlowingCyanLeaves.class,"flowing_cyan_leaves","flowingCyanLeaves");
//	public static final Block flowingCyanCross = getRegistryBlock(BlockFlowingCyanCross.class,"flowing_cyan_cross","flowingCyanCross");
//	public static final Block flowingCyanBranch = getRegistryBlock(BlockFlowingCyanBranch.class,"flowing_cyan_branch","flowingCyanBranch");

	protected static Block getRegistryBlock(Class<? extends Block> classIn, String registryName, String unlocalizedName) {
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
//			registry.registerAll(springMud,rainStone,waterStone,swordStone);
//			registry.registerAll(flowingCyanSapling,flowingCyanRoot,flowingCyanLog,flowingCyanLeaves,flowingCyanCross,flowingCyanBranch);
//			MRUtils.registerPlantSoil(springMud);
       }

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			MistyRain.getLogger().info("Registrying items[MistryRain]");
			final IForgeRegistry<Item> registry = event.getRegistry();
//			registerItem(registry, springMud,rainStone,waterStone,swordStone);
//			registerItem(registry, flowingCyanSapling,flowingCyanRoot,flowingCyanLog,flowingCyanLeaves,flowingCyanCross,flowingCyanBranch);
		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void registerItemModels(final ModelRegistryEvent event) {
			MistyRain.getLogger().info("Registrying item models[MistryRain]");
//			registerItemModel(0, springMud, rainStone, waterStone,swordStone);
//			registerItemModel(0, flowingCyanSapling,flowingCyanRoot,flowingCyanLog,flowingCyanLeaves,flowingCyanCross,flowingCyanBranch);
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
