package T145.elementalcreepers.entities;

import java.util.List;

import T145.elementalcreepers.config.ModConfig;
import T145.elementalcreepers.entities.ai.EntityAIThrowTNT;
import T145.elementalcreepers.entities.base.EntityBaseCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityHydrogenCreeper extends EntityBaseCreeper {

	protected EntityAIThrowTNT throwingAI = new EntityAIThrowTNT(this, 8D);

	public EntityHydrogenCreeper(World world) {
		super(world);
		tasks.addTask(4, throwingAI); // avoid ocelots first to nuke them!
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true; // be sure the thrown TNT doesn't kill the creeper
	}

	@Override
	public void createExplosion(int explosionPower, boolean griefingEnabled) {
		float radius = getPowered() ? ModConfig.explosionRadii.hydrogenCreeperRadius * 1.5F : ModConfig.explosionRadii.hydrogenCreeperRadius;
		AxisAlignedBB bb = new AxisAlignedBB(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius);
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, bb);

		if (!entities.isEmpty()) {
			for (Entity entity : entities) {
				if (entity instanceof EntityLivingBase && !(entity.isImmuneToExplosions())) {
					EntityLivingBase creature = (EntityLivingBase) entity;
					throwingAI.throwTNT(creature, true);
				}
			}
		}
	}
}