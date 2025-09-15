package com.dontouchat.sharkswithlasers.block.entity;

import com.dontouchat.sharkswithlasers.recipe.SifterRecipe;
import com.dontouchat.sharkswithlasers.screen.SifterMenu;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;

public class SifterBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(7);

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private final int INPUT_SLOT = 0;
    private final int[] OUTPUT_SLOTS = {1,2,3,4,5,6};


    public SifterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SIFTER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex)
                {
                    case 0 -> SifterBlockEntity.this.progress;
                    case 1 -> SifterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex)
                {
                    case 0 -> SifterBlockEntity.this.progress = pValue;
                    case 1 -> SifterBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }
    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition,inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.sharks_with_lasers.sifter_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SifterMenu(pContainerId, pPlayerInventory,this,this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.putInt("sifter_block.progress",progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("sifter_block.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState)
    {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel,pPos,pState);

            if(hasProgressFinished()){
                craftItem();
                resetProgress();
            }
        }else{
            resetProgress();
        }
    }

    private boolean hasRecipe() {
        Optional<SifterRecipe> recipe = getCurrentRecipe();

        return recipe.isPresent();
    }
    private Optional<SifterRecipe> getCurrentRecipe(){
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(SifterRecipe.Type.INSTANCE, inventory, level);
    }
    private void craftItem() {
        Optional<SifterRecipe> recipe = getCurrentRecipe();
        ItemStack[] results = {
                recipe.get().getResultItem(null),
                recipe.get().getSpareItem(null,level)
        };
        this.itemHandler.extractItem(INPUT_SLOT,1,false);
        putItemsInOutputSlots(results);
    }

    private void putItemsInOutputSlots(ItemStack[] results) {
        ArrayList<ItemStack> remainders = new ArrayList<>();
        for(ItemStack result : results)
        {
            boolean placed = false;
            for(int slot : OUTPUT_SLOTS)
            {
                if(canInsertIntoSlot(result,slot))
                {
                    this.itemHandler.setStackInSlot(slot, new ItemStack(result.getItem(),
                            this.itemHandler.getStackInSlot(slot).getCount() + result.getCount()));
                    placed = true;
                    break;
                }
                else if (canSplitStackInSlot(result,slot)) {
                    int split = this.itemHandler.getStackInSlot(slot).getMaxStackSize()
                            - this.itemHandler.getStackInSlot(slot).getCount();
                    this.itemHandler.setStackInSlot(slot, new ItemStack(result.getItem(),
                            this.itemHandler.getStackInSlot(slot).getCount() + split));
                    ItemStack[] splitStack = {new ItemStack(result.getItem(), result.getCount() - split)};
                    putItemsInOutputSlots(splitStack);
                    placed = true;
                    break;
                }
            }
            if(!placed)
            {
                remainders.add(result);
            }
        }
        for(ItemStack stack : remainders) {
            spawnItem(this.level, stack, 1, Direction.UP, new Position() {
                @Override
                public double x() {
                    return SifterBlockEntity.this.getBlockPos().getX();
                }

                @Override
                public double y() {
                    return SifterBlockEntity.this.getBlockPos().getY();
                }

                @Override
                public double z() {
                    return SifterBlockEntity.this.getBlockPos().getZ();
                }
            });
        }
    }

    public static void spawnItem(Level pLevel, ItemStack pStack, int pSpeed, Direction pFacing, Position pPosition) {
        double d0 = pPosition.x();
        double d1 = pPosition.y();
        double d2 = pPosition.z();
        if (pFacing.getAxis() == Direction.Axis.Y) {
            d1 -= 0.125D;
        } else {
            d1 -= 0.15625D;
        }

        ItemEntity itementity = new ItemEntity(pLevel, d0, d1, d2, pStack);
        double d3 = pLevel.random.nextDouble() * 0.1D + 0.2D;
        itementity.setDeltaMovement(pLevel.random.triangle((double)pFacing.getStepX() * d3, 0.0172275D * (double)pSpeed), pLevel.random.triangle(0.2D, 0.0172275D * (double)pSpeed), pLevel.random.triangle((double)pFacing.getStepZ() * d3, 0.0172275D * (double)pSpeed));
        pLevel.addFreshEntity(itementity);
    }

    private boolean canSplitStackInSlot(ItemStack stack, int slot) {
        return this.itemHandler.getStackInSlot(slot).is(stack.getItem()) &&
                this.itemHandler.getStackInSlot(slot).getCount() <
                        this.itemHandler.getStackInSlot(slot).getMaxStackSize();
    }

    private boolean canInsertIntoSlot(ItemStack stack, int slot) {
        return this.itemHandler.getStackInSlot(slot).isEmpty() ||
                (this.itemHandler.getStackInSlot(slot).is(stack.getItem()) &&
                this.itemHandler.getStackInSlot(slot).getCount() + stack.getCount() <=
                this.itemHandler.getStackInSlot(slot).getMaxStackSize());
    }

    private void increaseCraftingProgress() {
        progress ++;
    }
    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }
    private void resetProgress() {
        progress = 0;
    }
}
