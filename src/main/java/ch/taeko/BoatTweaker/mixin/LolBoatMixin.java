package ch.taeko.BoatTweaker.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BoatEntity.class)
public abstract class LolBoatMixin extends Entity {

    @Shadow @Nullable public abstract Entity getPrimaryPassenger();

    public LolBoatMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    protected void onBlockCollision(BlockState state) {
        if (this.hasPassengers()) {

            Entity driver = this.getPrimaryPassenger();
            float speedTest = this.speed;

            BlockState block1 =
                    world.getBlockState(new BlockPos(driver.getX() + 1,
                            Math.ceil(driver.getY()), driver.getZ() + 1));
            BlockState block1p =
                    world.getBlockState(new BlockPos(driver.getX() + 1,
                            Math.ceil(driver.getY())+1, driver.getZ() + 1));

            BlockState block2 =
                    world.getBlockState(new BlockPos(driver.getX() - 1,
                            Math.ceil(driver.getY()), driver.getZ() - 1));
            BlockState block2p =
                    world.getBlockState(new BlockPos(driver.getX() - 1,
                            Math.ceil(driver.getY()) + 1, driver.getZ() - 1));

            BlockState block3 =
                    world.getBlockState(new BlockPos(driver.getX() - 1,
                            Math.ceil(driver.getY()), driver.getZ() + 1));
            BlockState block3p =
                    world.getBlockState(new BlockPos(driver.getX() - 1,
                            Math.ceil(driver.getY()) + 1, driver.getZ() + 1));

            BlockState block4 =
                    world.getBlockState(new BlockPos(driver.getX() + 1,
                            Math.ceil(driver.getY()), driver.getZ() - 1));
            BlockState block4p =
                    world.getBlockState(new BlockPos(driver.getX() + 1,
                            Math.ceil(driver.getY()) + 1, driver.getZ() - 1));

            if (       (block1.getBlock().getSlipperiness() > 0.8 && block1p.isAir())
                    || (block2.getBlock().getSlipperiness() > 0.8 && block2p.isAir())
                    || (block3.getBlock().getSlipperiness() > 0.8 && block3p.isAir())
                    || (block4.getBlock().getSlipperiness() > 0.8 && block4p.isAir())) {
                this.setPos(this.getX(), Math.ceil(driver.getY()) + 1.4, this.getZ());
                this.speed = speedTest;
            }
        }

        /*
        if (this.hasPassengers() && !state.isAir()) {
            System.out.println((state.getBlock().getSlipperiness() > 0.6) + " LOLRIP FJKLDJéFLDKJFHLD" + state.getBlock().getSlipperiness());
            Entity driver = this.getPassengerList().get(0);
            this.setPos(this.getX(), driver.getY()+1, this.getZ());

        }
    }*/
    }
}