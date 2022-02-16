package ch.taeko.BoatTweaker.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BoatEntity.class)
public abstract class LolBoatMixin extends Entity {

    @Shadow
    @Nullable
    public abstract Entity getPrimaryPassenger();

    public LolBoatMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    protected void onBlockCollision(BlockState state) {
        if (this.hasPassengers()) {

            double offset = 2;
            Entity driver = this.getPrimaryPassenger();
            double v = Math.sqrt(Math.pow(this.getVelocity().x,2) + Math.pow(this.getVelocity().z,2));
            System.out.println(v + " = V(x) travel direction");

            BlockState block1 =
                    world.getBlockState(new BlockPos(driver.getX() + offset,
                            Math.ceil(driver.getY()), driver.getZ() + offset));
            BlockState block1p =
                    world.getBlockState(new BlockPos(driver.getX() + offset,
                            Math.ceil(driver.getY()) + 1, driver.getZ() + offset));

            BlockState block2 =
                    world.getBlockState(new BlockPos(driver.getX() - offset,
                            Math.ceil(driver.getY()), driver.getZ() - offset));
            BlockState block2p =
                    world.getBlockState(new BlockPos(driver.getX() - offset,
                            Math.ceil(driver.getY()) + 1, driver.getZ() - offset));

            BlockState block3 =
                    world.getBlockState(new BlockPos(driver.getX() - offset,
                            Math.ceil(driver.getY()), driver.getZ() + offset));
            BlockState block3p =
                    world.getBlockState(new BlockPos(driver.getX() - offset,
                            Math.ceil(driver.getY()) + 1, driver.getZ() + offset));

            BlockState block4 =
                    world.getBlockState(new BlockPos(driver.getX() + offset,
                            Math.ceil(driver.getY()), driver.getZ() - offset));
            BlockState block4p =
                    world.getBlockState(new BlockPos(driver.getX() + offset,
                            Math.ceil(driver.getY()) + 1, driver.getZ() - offset));

            if ((block1.getBlock().getSlipperiness() > 0.8 && block1p.isAir())
                    || (block2.getBlock().getSlipperiness() > 0.8 && block2p.isAir())
                    || (block3.getBlock().getSlipperiness() > 0.8 && block3p.isAir())
                    || (block4.getBlock().getSlipperiness() > 0.8 && block4p.isAir())) {
                double d = 0.228571428571429F;
                Vec3d vec3d = this.getVelocity();
                this.setVelocity(vec3d.x, d, vec3d.z);
            }
        }
    }
}
