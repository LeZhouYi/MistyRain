package skily_leyu.mistyrain.world.biome;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import skily_leyu.mistyrain.block.MRBlocks;
import skily_leyu.mistyrain.feature.properties.MRProperties;

public class BiomeCloudyPeak extends Biome {

//	public static IBlockState surface = MRBlocks.springMud.getDefaultState();

	public BiomeCloudyPeak(BiomeProperties properties, BiomeDecorator decorator) {
		super(properties);
		if (decorator != null) {
			this.decorator = decorator;
		}
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal){

		int seaLevel = worldIn.getSeaLevel();

        int blockType = -1;
        int depth = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int teZ = x & 15;
        int teX = z & 15;

        for (int teY = 255; teY >= 0; --teY){
			//生成基岩层
            if (teY <= rand.nextInt(5)){
                chunkPrimerIn.setBlockState(teX, teY, teZ, BEDROCK);
            }else{
				IBlockState nowState = chunkPrimerIn.getBlockState(teX, teY, teZ);
				if(nowState.getBlock()==Blocks.AIR){
					blockType=-1;
				}else if(nowState.getBlock()==Blocks.STONE){
					if(teY>=seaLevel-depth){
						//处于泥土填充范围
						if(blockType==0){
							//上方是水，下方填充泥土
							blockType=2;
						}else if(blockType==-1){
							//上方是空气，下方填充草地
							blockType=1;
						}else if(blockType==1||blockType==2){
							//上方是草地/泥土，填充泥土
							blockType=2;
						}else{
							//意外状况，填充石头
							blockType=3;
						}
					}else {
						//不处于泥土填充范围
						if(blockType!=0){
							//上方不是水，默认填充石头
							blockType=3;
						}else{
							//上方是水，填充一格泥土
							blockType=2;
						}
					}
				}else if(nowState.getBlock()==Blocks.WATER){
					//下方是水，不作任何处理
					if(blockType==-1){
						blockType=0;
					}
				}
				if(blockType>0){
					//空气和水无需替换
					chunkPrimerIn.setBlockState(teX,teY,teZ,getBlockState(blockType));
				}
			}
        }
	}

	/**
	 * 根据不同的blockType返回对应的方块，用于方块生成过程的判断
	 * @param blockType
	 * @return
	 */
	protected IBlockState getBlockState(int blockType){
		if(blockType==0){
			return Blocks.WATER.getDefaultState();
		}else if(blockType==1){
			return MRBlocks.springMud.getDefaultState().withProperty(MRProperties.HALF_STAGE, 1);
		}else if(blockType==2){
			return MRBlocks.springMud.getDefaultState().withProperty(MRProperties.HALF_STAGE, 0);
		}else if(blockType==3){
			return MRBlocks.waterStone.getDefaultState();
		}
		//blockType=0
		return Blocks.AIR.getDefaultState();
	}

}
