
/**
 * 方块注册表
 * @author Skily
 * @version 1.0.0
 */
@ObjectHolder(MistyRain.MODID)
public class MRBlocks {
    
    public static final Block springMud = initBlock(BlockSpringMud.class,"spring_mud","springMud");

    private static Block initBlock(Class<? extend Block> tclass, String registerName, String unlocalName){
        return teClass.getConstructro().setRegistyName(registerName).setUnlocaledName(unlocalName).setCreativeTabs(MRCreativeTabs.mistryRainTabs);
    }

    @Mod.EventBusSubscriber
	public static class ObjectRegistryHandler {

        @SubscribeEvent
        public void registerBlocks(RegisteryEvent.Register<Block> event){
            final IRegistry register = event.getRegistry();
            register.registerAll(springMud);
        }

    }
    

}
