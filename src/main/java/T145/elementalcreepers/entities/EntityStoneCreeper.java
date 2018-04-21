package T145.elementalcreepers.entities;

import T145.elementalcreepers.ElementalCreepers;
import T145.elementalcreepers.config.ModConfig;
import T145.elementalcreepers.entities.base.EntityBaseCreeper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Collections;

public class EntityStoneCreeper extends EntityBaseCreeper {

    public EntityStoneCreeper(World world) {
        super(world);
    }

    @Override
    public void createExplosion(int explosionPower, boolean canGrief) {
        int radius = getPowered() ? ModConfig.EXPLOSION_RADII.stone * explosionPower : ModConfig.EXPLOSION_RADII.stone;

        for (int x = -radius; x <= radius; ++x) {
            for (int y = -radius; y <= radius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    pos.setPos(posX + x, posY + y, posZ + z);
                    IBlockState state = world.getBlockState(pos);
                    Block rock = state.getBlock();

                    if (ElementalCreepers.proxy.getRockSet().contains(rock) && Math.sqrt(Math.pow(x, 2.0D) + Math.pow(y, 2.0D) + Math.pow(z, 2.0D)) <= radius) {
                        rock.dropBlockAsItem(world, pos, state, 0);
                        world.setBlockToAir(pos);
                        rock.onBlockDestroyedByExplosion(world, pos, new Explosion(world, this, 0.0D, 0.0D, 0.0D, 0.0F, Collections.emptyList()));
                    }
                }
            }
        }
    }
}