package skily_leyu.mistyrain.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.utility.type.MRBook;
import skily_leyu.mistyrain.utility.type.MRPlant.PlantSetting;
import skily_leyu.mistyrain.utility.type.MRPot.PotSetting;

public class MRSettings {

    public static MRBook herbalsBook;
	public static PlantSetting animalPlantMap;
	public static PotSetting potMap;

    public MRSettings(FMLPreInitializationEvent event){
		herbalsBook = loadJson(MRBook.class, "herbals_book");
		animalPlantMap = loadJson(PlantSetting.class, "animal_plant");
		potMap = loadJson(PotSetting.class, "animal_plant_pot");
    }

    private <T> T loadJson(Class<T> jsonClass, String registryName) {
		return loadJson(jsonClass, registryName, MistyRain.MODID);
	}

	public static <T> T loadJson(Class<T> jsonClass, String registryName, String ModID) {
		String site = "/assets/" + ModID + "/" + "settings/" + registryName + ".json";
		T result = null;
		try (Reader reader = new InputStreamReader(MRSettings.class.getResourceAsStream(site), "UTF-8")) {
			Gson gson = new GsonBuilder().create();
			result = gson.fromJson(reader, jsonClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Nullable
	public static MRBook getBook(String registryName){
		switch(registryName){
			case "herbals_book":
				return herbalsBook;
			default:
				return null;
		}
	}

}