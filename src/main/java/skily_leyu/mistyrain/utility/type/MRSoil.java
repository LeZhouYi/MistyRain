package skily_leyu.mistyrain.utility.type;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class MRSoil {
    private Map<String,List<String>> soilMap;

    public MRSoil(Map<String,List<String>> soilMap){
        this.soilMap = soilMap;
    }

    /**
     * 检测是否是合适的泥土
     * @param key
     * @param itemStack
     * @return
     */
    public boolean isSuitSoil(String key, ItemStack itemStack){
        if(this.soilMap.containsKey(key)){
            return this.soilMap.get(key).contains(itemStack.getItem().getRegistryName().toString());
        }
        return false;
    }

}
