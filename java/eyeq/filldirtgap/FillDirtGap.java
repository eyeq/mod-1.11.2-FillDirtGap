package eyeq.filldirtgap;

import eyeq.filldirtgap.event.FillDirtGapEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static eyeq.filldirtgap.FillDirtGap.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
public class FillDirtGap {
    public static final String MOD_ID = "eyeq_filldirtgap";

    @Mod.Instance(MOD_ID)
    public static FillDirtGap instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new FillDirtGapEventHandler());
    }
}
