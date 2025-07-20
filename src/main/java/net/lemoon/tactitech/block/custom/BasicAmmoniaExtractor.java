package net.lemoon.tactitech.block.custom;

import com.mojang.serialization.MapCodec;
import dev.mayaqq.nexusframe.api.multiblock.Multiblock;
import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.block.ModBlocks;
import net.lemoon.tactitech.block.entity.ModBlockEntities;
import net.lemoon.tactitech.block.entity.custom.BasicAmmoniaExtractorEntity;
//import net.lemoon.tactitech.mixin.ExtractorBlockMixin;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.function.Predicate;

public class BasicAmmoniaExtractor extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final MapCodec<BasicAmmoniaExtractor> CODEC = createCodec(BasicAmmoniaExtractor::new);
    public boolean IsMultiBlockNotAssembled = false;

    public BasicAmmoniaExtractor(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    /* FACING */

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    /* BLOCK ENTITY */

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BasicAmmoniaExtractorEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (world.getBlockEntity(pos) instanceof BasicAmmoniaExtractorEntity basicAmmoniaExtractorEntity) {
                ItemScatterer.spawn(world, pos, basicAmmoniaExtractorEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Unique
    private static final HashMap<BlockPos, Multiblock> Multiblocks = new HashMap<>();

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {


        if (world.isClient){
            return (ItemActionResult.SUCCESS);
        }else {
            getForgeMultiblock(pos);
            Multiblock structure = Multiblocks.get(pos);
            if (structure.check(pos, world)) {
                Tactitech.LOGGER.info("Structure matched!");
                IsMultiBlockNotAssembled = false;

            } else {
                Tactitech.LOGGER.info("Structure didn't match!");
                IsMultiBlockNotAssembled = true;
            }
        }
        if (!world.isClient) {
            if(!IsMultiBlockNotAssembled) {
                NamedScreenHandlerFactory screenHandlerFactory = ((BasicAmmoniaExtractorEntity) world.getBlockEntity(pos));

                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }

        }



        return ItemActionResult.SUCCESS;
    }

//    multibock test
//
//    private void onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ItemActionResult> cir) {
//        if (world.isClient) {
//            cir.setReturnValue(ItemActionResult.SUCCESS);
//        } else {
//            getForgeMultiblock(pos);
//            Multiblock structure = Multiblocks.get(pos);
//            if (structure.check(pos, world)) {
//                Tactitech.LOGGER.info("Structure matched!");
//                skibidi=true;
//            } else {
//                Tactitech.LOGGER.info("Structure didn't match!");
//            }
//        }
//    }
    @Unique
    private static void getForgeMultiblock(BlockPos pos) {
        if (Multiblocks.get(pos) == null) {
            Multiblocks.put(pos, new Multiblock(multiblock(), getPredicates(), true));
        }
    }

    @Unique
    private static char[][][] multiblock() {
        return new char[][][]{
                {
                        {'g', 'l', 'g'},
                        {'l', 'g', 'l'},
                        {'g', 'l', 'g'}
                },
                {
                        {'a', 'g', 'a'},
                        {'g', '$', 'g'},
                        {'a', 'g', 'a'}
                },
                {
                        {'a', 't', 'a'},
                        {'t', 'a', 't'},
                        {'a', 't', 'a'}
                }
        };
    }

    @Unique
    private static HashMap<Character, Predicate<BlockState>> getPredicates() {
        HashMap<Character, Predicate<BlockState>> predicates = new HashMap<>();
//        Block yourCustomBlock = ModBlocks.BASIC_AMMONIA_EXTRACTOR;
        predicates.put('a', BlockStatePredicate.ANY);
        predicates.put('$', BlockStatePredicate.forBlock(ModBlocks.BASIC_AMMONIA_EXTRACTOR));
        predicates.put('g', BlockStatePredicate.forBlock(Blocks.GILDED_BLACKSTONE));
        predicates.put('l', BlockStatePredicate.forBlock(ModBlocks.SOLIDIFIED_AMMONIA));
        predicates.put('t', BlockStatePredicate.forBlock(Blocks.GLASS));
        return predicates;
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.EXTRACTOR_BE, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    /* LIT */

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT)) {
            return;
        }

        double xPos = (double)pos.getX() + 0.5;
        double yPos = pos.getY();
        double zPos = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.15) {
            world.playSound(xPos, yPos, zPos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }

       Direction direction = state.get(FACING);
        Direction.Axis axis = direction.getAxis();

        double defaultOffset = random.nextDouble() * 0.6 - 0.3;
        double xOffsets = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : defaultOffset;
        double yOffset = random.nextDouble() * 6.0 / 8.0;
        double zOffset = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : defaultOffset;

        world.addParticle(ParticleTypes.SMOKE, xPos + xOffsets, yPos + yOffset, zPos + zOffset, 0.0, 0.0, 0.0);

        if(world.getBlockEntity(pos) instanceof BasicAmmoniaExtractorEntity basicAmmoniaExtractorEntity && !basicAmmoniaExtractorEntity.getStack(1).isEmpty()) {
            world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, basicAmmoniaExtractorEntity.getStack(1)),
                    xPos + xOffsets, yPos + yOffset, zPos + zOffset, 0.0, 0.0, 0.0);
        }

    }
}
