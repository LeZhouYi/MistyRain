package skily_leyu.mistyrain.feature.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import skily_leyu.mistyrain.feature.properties.property.CanProperty;

public class CanProperties {

    /**水壶相关默认设置 */
    public static final CanProperty WOODEN_NORMAL = new CanProperty().setName("wooden_normal").setVolume(4000)
                .setVolumePerCollect(1000).setRadius(1).setEfficiency(1000)
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

}
