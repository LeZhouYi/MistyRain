package skily_leyu.mistyrain.utility.type;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.config.MRProperty;

public class MRPot {

    private int soilSize; //泥土格子数量
    private int plantSize; //植物格子数量
    private int tankSize; //储水量
    private int fertilizerSize; //储肥料量

    private List<String> soils; //泥土白名单
    private List<String> fluids; //储水白名单

    public int getSoidSize(){
        return this.soilSize;
    }

    public int getPlantSize(){
        return this.plantSize;
    }

    public int getTankSize(){
        return this.tankSize;
    }

    public int getFertilizerSize(){
        return this.fertilizerSize;
    }

    public boolean isSuitSoil(ItemStack itemStack){
        return this.soils.contains(itemStack.getItem().getRegistryName().toString());
    }

    public List<String> getFluids(){
        return this.fluids;
    }
    public static class PotSetting{

        private Map<String,MRPot> potMap;

        public Map<String,MRPot> getPotMap(){
            return this.potMap;
        }

        @Nullable
        public MRPot getMRPot(String key){
            if(potMap.containsKey(key)){
                return this.potMap.get(key);
            }
            return null;
        }

        public MRPot getDeafultMRPot(){
            return getMRPot(MRProperty.WOODEN_BASE);
        }

    }

}