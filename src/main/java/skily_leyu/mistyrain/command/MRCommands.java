package skily_leyu.mistyrain.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class MRCommands {

    public MRCommands(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandMRTime());
        event.registerServerCommand(new CommandDebug());
    }

}
