package net.lemoon.tactitech.fluid.custom;

import net.lemoon.tactitech.fluid.ModFluids;
import net.lemoon.tactitech.item.ModItems;
//import net.lemoon.tactitech.particles.ModParticleTypes;
//import net.lemoon.tactitech.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class IrradiatedAmmoniaFluid extends FlowableFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_IRRADIATED_AMMONIA;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.IRRADIATED_AMMONIA;
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!state.isStill() && !(Boolean)state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        }
    }

//    public ParticleEffect getParticle() {
//        return ModParticleTypes.DRIPPING_GEDRITED_WATER;
//    }

    private void playSplashSound(WorldAccess world, BlockPos pos){
        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

//    @Override
//    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
//        if (direction == Direction.DOWN) {
//            FluidState fluidState2 = world.getFluidState(pos);
//            if (this.isIn(ModTags.Fluids.GEDRITED_WATER) && (fluidState2.isIn(FluidTags.WATER) && !fluidState2.isIn(ModTags.Fluids.GEDRITED_WATER))) {
//                if (state.getBlock() instanceof FluidBlock) {
//                    world.setBlockState(pos, Blocks.COARSE_DIRT.getDefaultState(), Block.NOTIFY_ALL);
//                }
//                this.playSplashSound(world, pos);
//                return;
//            }
//
//        }
//        super.flow(world, pos, state, direction, fluidState);
//    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected int getMaxFlowDistance(WorldView world) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Override
    public Item getBucketItem() {
        return ModFluids.IRRADIATED_AMMONIA_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
                //direction == Direction.DOWN && fluid.isIn(ModTags.Fluids.GEDRITED_WATER);
    }

    @Override
    public int getTickRate(WorldView world) {
        return 20;
    }

    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluids.IRRADIATED_AMMONIA_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    public static class Flowing
            extends IrradiatedAmmoniaFluid {
        public Flowing(){
        }

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(new Property[]{LEVEL});
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still
            extends IrradiatedAmmoniaFluid {
        public Still(){
        }

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }
}