package skily_leyu.mistyrain.item;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

/**
 * 物品注册表
 * @author Skily
 * @version 1.0.0
 */
public class MRItems{

    public static final Item herbalsBook = getRegistryItem(ItemMRHerbalsBook.class, "herbals_book", "herbalsBook");

    protected static Item getRegistryItem(Class<? extends Item> classIn, String registryName, String unlocalizedName) {
        try {
            return classIn.getConstructor().newInstance().setCreativeTab(MRCreativeTabs.tabMistyRain)
                    .setRegistryName(MistyRain.MODID, registryName).setUnlocalizedName(unlocalizedName);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
            return Items.AIR;
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            MistyRain.getLogger().info("Registrying items[HerbalGarden]");
            final IForgeRegistry<Item> registry = event.getRegistry();
            registry.registerAll(herbalsBook);
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerItemModels(final ModelRegistryEvent event) {
            MistyRain.getLogger().info("Registrying item models[HerbalGarden]");
            registerItemModel(0, herbalsBook);
        }

        @SideOnly(Side.CLIENT)
        public static void registerItemModel(int parMetaData, Item... parItem) {
            for (Item item : parItem) {
                ModelLoader.setCustomModelResourceLocation(item, parMetaData,
                        new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }

}
