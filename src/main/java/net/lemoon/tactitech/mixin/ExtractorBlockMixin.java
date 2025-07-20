//package net.lemoon.tactitech.mixin;
//
//import dev.mayaqq.nexusframe.api.multiblock.Multiblock;
//import net.lemoon.tactitech.Tactitech;
//import net.lemoon.tactitech.block.ModBlocks;
//import net.lemoon.tactitech.block.entity.custom.BasicAmmoniaExtractorEntity;
//import net.lemoon.tactitech.util.ModTags;
//import net.minecraft.block.AnvilBlock;
//import net.lemoon.tactitech.block.custom.BasicAmmoniaExtractor;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.predicate.block.BlockStatePredicate;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.ItemActionResult;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.HashMap;
//import java.util.function.Predicate;
//
//@Mixin(BasicAmmoniaExtractor.class)
//public class ExtractorBlockMixin {
//
//
//    private static boolean skibidi=false;
//
//
//    @Unique
//    private static final HashMap<BlockPos, Multiblock> Multiblocks = new HashMap<>();
//
//    @Inject(method = "onUseWithItem", at = @At("HEAD"), cancellable = true)
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
//
//    @Unique
//    private static void getForgeMultiblock(BlockPos pos) {
//        if (Multiblocks.get(pos) == null) {
//            Multiblocks.put(pos, new Multiblock(multiblock(), getPredicates(), true));
//        }
//    }
//
//    @Unique
//    private static char[][][] multiblock() {
//        return new char[][][]{
//                {
//                        {'g', 'l', 'g'},
//                        {'l', 'g', 'l'},
//                        {'g', 'l', 'g'}
//                },
//                {
//                        {'a', 'g', 'a'},
//                        {'g', '$', 'g'},
//                        {'a', 'g', 'a'}
//                },
//                {
//                        {'a', 't', 'a'},
//                        {'t', 'a', 't'},
//                        {'a', 't', 'a'}
//                }
//        };
//    }
//
//    @Unique
//    private static HashMap<Character, Predicate<BlockState>> getPredicates() {
//        HashMap<Character, Predicate<BlockState>> predicates = new HashMap<>();
////        Block yourCustomBlock = ModBlocks.BASIC_AMMONIA_EXTRACTOR;
//        predicates.put('a', BlockStatePredicate.ANY);
//        predicates.put('$', BlockStatePredicate.forBlock(ModBlocks.BASIC_AMMONIA_EXTRACTOR));
//        predicates.put('g', BlockStatePredicate.forBlock(Blocks.GILDED_BLACKSTONE));
//        predicates.put('l', BlockStatePredicate.forBlock(ModBlocks.SOLIDIFIED_AMMONIA));
//        predicates.put('t', BlockStatePredicate.forBlock(Blocks.GLASS));
//        return predicates;
//    }
//}