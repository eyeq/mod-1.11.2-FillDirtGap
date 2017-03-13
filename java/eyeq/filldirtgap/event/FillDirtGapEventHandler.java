package eyeq.filldirtgap.event;

import eyeq.util.entity.player.EntityPlayerUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FillDirtGapEventHandler {
    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        if(itemStack.getItem() != Item.getItemFromBlock(Blocks.DIRT)) {
            return;
        }
        EntityPlayer player = event.getEntityPlayer();
        World world = player.getEntityWorld();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();
        if(block != Blocks.FARMLAND && block != Blocks.GRASS_PATH) {
            return;
        }
        if(!player.isSneaking()) {
            return;
        }

        EnumFacing facing = event.getFace();
        EnumHand hand = event.getHand();
        boolean isSuccess = false;
        if(facing == EnumFacing.UP) {
            isSuccess = onPlayerInteract(world, player, hand, itemStack, pos, EnumFacing.NORTH);
            isSuccess |= onPlayerInteract(world, player, hand, itemStack, pos, EnumFacing.EAST);
            isSuccess |= onPlayerInteract(world, player, hand, itemStack, pos, EnumFacing.SOUTH);
            isSuccess |= onPlayerInteract(world, player, hand, itemStack, pos, EnumFacing.WEST);
        } else if(facing == EnumFacing.NORTH || facing == EnumFacing.EAST || facing == EnumFacing.SOUTH || facing == EnumFacing.WEST) {
            isSuccess = onPlayerInteract(world, player, hand, itemStack, pos, facing);
        }
        if(isSuccess) {
            event.setCanceled(true);
        }
    }

    private boolean onPlayerInteract(World world, EntityPlayer player, EnumHand hand, ItemStack itemStack, BlockPos pos, EnumFacing facing) {
        pos = pos.offset(facing.getOpposite());
        if(world.getBlockState(pos).getBlock() != Blocks.AIR) {
            return false;
        }
        if(world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
            return false;
        }
        EntityPlayerUtils.onItemUse(player, world, itemStack, pos, facing, new Vec3d(pos), hand);
        return true;
    }
}
