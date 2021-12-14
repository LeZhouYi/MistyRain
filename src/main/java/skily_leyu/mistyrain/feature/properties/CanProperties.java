package skily_leyu.mistyrain.feature.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import skily_leyu.mistyrain.feature.properties.property.CanProperty;

public class CanProperties {

    /**水壶相关默认设置 */
    public static final CanProperty WOODEN_NORMAL = new CanProperty().setName("wooden_normal").setVolume(4000)
                .setVolumePerCollect(1000).setRadius(1).setEfficiency(1000).setVolumePerSecond(200)
                .setFluidList(new ArrayList<Fluid>(Arrays.asList(FluidRegistry.WATER)));

    /**花盆属性默认配置集*/
    private static List<CanProperty> canProperties = new ArrayList<>();
    static{
        canProperties.add(WOODEN_NORMAL);
    }

    /**属性相关获取方法 */
    public static CanProperty getPotProperty(String name){
        for(CanProperty potProperty:canProperties){
            if(potProperty.getName().equals(name)){
                return potProperty;
            }
        }
        return canProperties.get(0);
    }

    /**
     * 注册花盆属性，不校验name是否重复
     * @param potProperty
     */
    public static void registerPotProperty(CanProperty potProperty){
        canProperties.add(potProperty);
    }

    /**方块可以获取的水 */
    public static Map<Block,Fluid> fluidMap = new HashMap<>();
    static{
        fluidMap.put(Blocks.WATER, FluidRegistry.WATER);
    }

    /**
     * 注册方块对应的水类型，若已有则覆盖
     * @param block
     * @param fluid
     */
    public void registryCollectWater(Block block,Fluid fluid){
        if(fluidMap.containsKey(block)){
            fluidMap.remove(block);
        }
        fluidMap.put(block, fluid);
    }

    /**
     * 检测方块是否可以取水
     * @param block
     * @return
     */
    public static boolean canCollectWater(Block block){
        return fluidMap.containsKey(block);
    }

    /**
     * 从特定方块获取特定水
     * @param block
     * @return
     */
    @Nullable
    public static Fluid getCollectWater(Block block){
        if(fluidMap.containsKey(block)){
            return fluidMap.get(block);
        }
        return null;
    }

}
