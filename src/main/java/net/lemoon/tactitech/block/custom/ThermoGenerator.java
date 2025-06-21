package net.lemoon.tactitech.block.custom;

import com.mojang.serialization.MapCodec;
import net.lemoon.tactitech.block.entity.ModBlockEntities;
import net.lemoon.tactitech.block.entity.custom.MolecularCompounderEntity;
import net.lemoon.tactitech.block.entity.custom.ThermoGeneratorEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ThermoGenerator extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<ThermoGenerator> CODEC = ThermoGenerator.createCodec(ThermoGenerator::new);

    public ThermoGenerator(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ThermoGeneratorEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ThermoGeneratorEntity) {
                ItemScatterer.spawn(world, pos, ((ThermoGeneratorEntity) blockEntity));
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
//    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
//                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (!world.isClient) {
//            NamedScreenHandlerFactory screenHandlerFactory = ((ThermoGeneratorEntity) world.getBlockEntity(pos));
//            if (screenHandlerFactory != null) {
//                player.openHandledScreen(screenHandlerFactory);
//            }
//        }
//        return ItemActionResult.SUCCESS;
//    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            if (world.getBlockEntity(pos) instanceof ThermoGeneratorEntity energyGenerator) {
                player.openHandledScreen(energyGenerator);
            }
        }

        return ActionResult.success(world.isClient);
    }
}

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//        if (world.isClient()) {
//            return null;
//        }
//
//        return validateTicker(type, ModBlockEntities.THERMO_BE,
//                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
//    }
//}