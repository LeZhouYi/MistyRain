
/**
 * 方块注册表
 * @author Skily
 * @version 1.0.0
 */
@ObjectHolder(MistyRain.MODID)
public class MRBlocks {
    
    public static final Block springMud = initBlock(BlockSpringMud.class,"spring_mud","springMud");
    public static final Block rainStone = initBlock(BlockRainStone.class,"rain_stone","rainStone");
    public static final Block waterStone = initBlock(BlockWaterStone.class,"water_stone","waterStone");
    public static final Block swordStone = initBlock(BlockSwordStone.class,"sword_stone","swordStone");

    private static Block initBlock(Class<? extend Block> tclass, String registerName, String unlocalName){
        return teClass.getConstructro().setRegistyName(registerName).setUnlocaledName(unlocalName).setCreativeTabs(MRCreativeTabs.mistryRainTabs);
    }

    @Mod.EventBusSubscriber
	public static class ObjectRegistryHandler {

        @SubscribeEvent
        public void registerBlocks(RegisteryEvent.Register<Block> event){
            final IRegistry register = event.getRegistry();
            register.registerAll(springMud,rainStone,waterStone,swordStone);
        }

    }
    

}
