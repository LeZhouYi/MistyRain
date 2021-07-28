package skily_leyu.mistyrain.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.mixed.generate.MRBookGenerator;

/**
 * 读取MR相关的json配置文件
 * @author Skily
 * @version 1.0.0
 */
public class MRReader {

    public static MRBookGenerator herbalsBook;

    public MRReader(FMLPreInitializationEvent event) {
        herbalsBook = loadJson(MRBookGenerator.class, "herbals_book");
    }

    private <T> T loadJson(Class<T> jsonClass, String registryName) {
        String path = "/assets/" + MistyRain.MODID + "/" + "mrconfig/" + registryName + ".json";
		return loadJson(jsonClass, registryName, path);
	}

	public static <T> T loadJson(Class<T> jsonClass, String registryName, String path) {
		T result = null;
		try (Reader reader = new InputStreamReader(MRReader.class.getResourceAsStream(path), "UTF-8")) {
			Gson gson = new GsonBuilder().create();
			result = gson.fromJson(reader, jsonClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
