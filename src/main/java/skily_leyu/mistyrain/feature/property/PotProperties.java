package skily_leyu.mistyrain.feature.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class PotProperties {

    /**花盆相关默认设置 */
	public static final PotProperty WOOD_NORMAL = new PotProperty().setName("wooden_normal").setSlotSize(1)
                        .setStackSize(1).setTankSize(1).setVolumeSize(1).
                        setBlockList(new ArrayList<Item>(Arrays.asList(Item.getItemFromBlock(Blocks.GRASS))))
                        .setFluidList(new ArrayList<Fluid>(Arrays.asList(FluidRegistry.WATER)));

    /**花盆属性默认配置集*/
    private static List<PotProperty> potProperties = new ArrayList<>();
    static{
        potProperties.add(WOOD_NORMAL);
    }

    /**属性相关获取方法 */
    public static PotProperty getPotProperty(String name){
        for(PotProperty potProperty:potProperties){
            if(potProperty.getName().equals(name)){
                return potProperty;
            }
        }
        return potProperties.get(0);
    }

    /**
     * 注册花盆属性，不校验name是否重复
     * @param potProperty
     */
    public static void registerPotProperty(PotProperty potProperty){
        potProperties.add(potProperty);
    }

}