package net.lemoon.tactitech.block.entity.custom;

import dev.mayaqq.nexusframe.api.multiblock.Multiblock;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.lemoon.tactitech.Tactitech;
import net.lemoon.tactitech.block.ModBlocks;
import net.lemoon.tactitech.block.custom.BasicAmmoniaExtractor;
import net.lemoon.tactitech.block.entity.ImplementedInventory;
import net.lemoon.tactitech.block.entity.ModBlockEntities;
import net.lemoon.tactitech.recipe.BasicAmmoniaExtractorRecipe;
import net.lemoon.tactitech.recipe.BasicAmmoniaExtractorRecipeInput;
import net.lemoon.tactitech.recipe.ModRecipes;
import net.lemoon.tactitech.screen.custom.BasicAmmoniaExtractorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class BasicAmmoniaExtractorEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    //input output creation
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;

    private static final int ENERGY_CRAFTING_AMOUNT = 25;

    private static final int ENERGY_TRANSFER_AMOUNT = 160;

    //energy storage
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(64000, ENERGY_TRANSFER_AMOUNT, ENERGY_TRANSFER_AMOUNT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };


    //unused fluid storage
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return (FluidConstants.BUCKET / 81) * 16; // 1 Bucket = 81000 Droplets = 1000mB || * 16 ==> 16,000mB = 16 Buckets
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
            getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    };

    //progress indicator
    public BasicAmmoniaExtractorEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTRACTOR_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BasicAmmoniaExtractorEntity.this.progress;
                    case 1 -> BasicAmmoniaExtractorEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: BasicAmmoniaExtractorEntity.this.progress = value;
                    case 1: BasicAmmoniaExtractorEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    //insert logic for pipes

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(pos).get(BasicAmmoniaExtractor.FACING);

        if(side == null) {
            return false;
        }

        if(side == Direction.DOWN) {
            return false;
        }

        if(side == Direction.UP) {
            return slot == INPUT_SLOT;
        }

        return switch (localDir) {
            default -> //NORTH
                    side == Direction.NORTH && slot == INPUT_SLOT ||
                            side == Direction.EAST && slot == INPUT_SLOT ||
                            side == Direction.WEST && slot == INPUT_SLOT;
            case EAST ->
                    side.rotateYCounterclockwise() == Direction.NORTH && slot == INPUT_SLOT ||
                            side.rotateYCounterclockwise() == Direction.EAST && slot == INPUT_SLOT ||
                            side.rotateYCounterclockwise() == Direction.WEST && slot == INPUT_SLOT;
            case SOUTH ->
                    side.getOpposite() == Direction.NORTH && slot == INPUT_SLOT ||
                            side.getOpposite()  == Direction.EAST && slot == INPUT_SLOT ||
                            side.getOpposite()  == Direction.WEST && slot == INPUT_SLOT;
            case WEST ->
                    side.rotateYClockwise() == Direction.NORTH && slot == INPUT_SLOT ||
                            side.rotateYClockwise() == Direction.EAST && slot == INPUT_SLOT ||
                            side.rotateYClockwise() == Direction.WEST && slot == INPUT_SLOT;
        };
    }


    //extract logic for pipes
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(BasicAmmoniaExtractor.FACING);



        if(side == Direction.UP) {
            return false;
        }

        // Down extract
        if(side == Direction.DOWN) {
            return slot == OUTPUT_SLOT;
        }

        // backside extract
        // right extract
        return switch (localDir) {
            default ->  side == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side == Direction.EAST && slot == OUTPUT_SLOT;

            case EAST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == OUTPUT_SLOT;

            case SOUTH ->  side.getOpposite() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.getOpposite() == Direction.EAST && slot == OUTPUT_SLOT;

            case WEST -> side.rotateYClockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYClockwise() == Direction.EAST && slot == OUTPUT_SLOT;
        };
    }

// visual stuff
    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.tactitech.extractor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BasicAmmoniaExtractorScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("basic_ammonia_extractor.progress", progress);
        nbt.putInt("basic_ammonia_extractor.max_progress", maxProgress);
        nbt.putLong("basic_ammonia_extractor.energy", energyStorage.amount);
        SingleVariantStorage.writeNbt(this.fluidStorage, FluidVariant.CODEC, nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("basic_ammonia_extractor.progress");
        maxProgress = nbt.getInt("basic_ammonia_extractor.max_progress");
        energyStorage.amount = nbt.getLong("basic_ammonia_extractor.energy");
        super.readNbt(nbt, registryLookup);
        SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, registryLookup);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }
        if (!world.isClient) {
            if (world.getBlockState(pos.down()).getBlock() != ModBlocks.SOLIDIFIED_AMMONIA) {
                // Check if the block below is the ammonia core
                // Break the extractor block if not placed on the ammonia core
            }
                // Implement your logic for producing ammonia dust here
                else if (hasRecipe() && canInsertIntoOutputSlot()) {
                    increaseCraftingProgress();
                    useEnergyForCrafting();
                    world.setBlockState(pos, state.with(BasicAmmoniaExtractor.LIT, true));
                    markDirty(world, pos, state);

                    if (hasCraftingFinished()) {
                        craftItem();
//                useFluidForCrafting();
                        resetProgress();
                    }
                } else {
                    world.setBlockState(pos, state.with(BasicAmmoniaExtractor.LIT, false));
                    resetProgress();
                }

        }
        //multiblock testing



    }

    //unused fluid filling
    private void fillFluidTank() {
        if(inventory.get(0).isOf(Items.LAVA_BUCKET) && (fluidStorage.variant.isOf(Fluids.LAVA) || fluidStorage.isResourceBlank())) {
            try(Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.insert(FluidVariant.of(Fluids.LAVA), 1000, transaction);
                inventory.set(0, new ItemStack(Items.BUCKET));
                transaction.commit();
            }
        } else if(inventory.get(0).isOf(Items.WATER_BUCKET) && (fluidStorage.variant.isOf(Fluids.WATER) || fluidStorage.isResourceBlank())) {
            try(Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.insert(FluidVariant.of(Fluids.WATER), 1000, transaction);
                inventory.set(0, new ItemStack(Items.BUCKET));
                transaction.commit();
            }
        } else if(fluidStorage.variant.isOf(Fluids.WATER) || fluidStorage.isResourceBlank()) {
            try(Transaction transaction = Transaction.openOuter()) {
                this.fluidStorage.insert(FluidVariant.of(Fluids.WATER), 1000, transaction);
                inventory.set(0, new ItemStack(Items.BUCKET));
                transaction.commit();
            }
        }
    }


    private void useEnergyForCrafting() {
        try(Transaction transaction = Transaction.openOuter()) {
            this.energyStorage.extract(ENERGY_CRAFTING_AMOUNT, transaction);
            transaction.commit();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeEntry<BasicAmmoniaExtractorRecipe>> recipe = getCurrentRecipe();

        //this.removeStack(INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().output().getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipe.get().value().output().getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<BasicAmmoniaExtractorRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().getResult(null);
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output) && hasEnoughEnergyToCraft();
//                && hasEnoughFluidToCraft();
    }

//    private boolean hasEnoughFluidToCraft() {
//        return this.fluidStorage.getAmount() >= 1000;
//    }

    private boolean hasEnoughEnergyToCraft() {
        return this.energyStorage.amount >= (long) ENERGY_CRAFTING_AMOUNT * maxProgress;
    }

    private Optional<RecipeEntry<BasicAmmoniaExtractorRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager()
                .getFirstMatch(ModRecipes.EXTRACTOR_TYPE, new BasicAmmoniaExtractorRecipeInput(inventory.get(INPUT_SLOT)), this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}