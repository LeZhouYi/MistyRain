/**
 * 水石
 * @author Skily
 * @version 1.0.0
 */
public class BlockWaterStone {
    
    public BlockWaterStone(){
        this.setHardness(MRProperty.stoneHardness);
        this.setResistance(MRProperty.dirtHardness);
        this.setSoundType(SoundType.GLASS);
        this.setMaterial(Material.STONE);
    }

}
