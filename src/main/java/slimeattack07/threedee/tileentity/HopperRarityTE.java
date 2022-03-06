package slimeattack07.threedee.tileentity;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import slimeattack07.threedee.init.TDTileEntityTypes;

/**
 * 
 * @author Mojang, with small changes by SlimeAttack07 to only filter specific rarity items.
 *
 */
public class HopperRarityTE extends RandomizableContainerBlockEntity implements Hopper {
   public static final int MOVE_ITEM_SPEED = 8;
   public static final int HOPPER_CONTAINER_SIZE = 5;
   private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
   private int cooldownTime = -1;
   private long tickedGameTime;

   public HopperRarityTE(BlockPos pos, BlockState state) {
      super(TDTileEntityTypes.TD_HOPPERRARITY.get(), pos, state);
   }

   public void load(CompoundTag tag) {
      super.load(tag);
      items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
      if (tryLoadLootTable(tag)) {
         ContainerHelper.loadAllItems(tag, items);
      }

      cooldownTime = tag.getInt("TransferCooldown");
   }

   protected void saveAdditional(CompoundTag tag) {
      super.saveAdditional(tag);
      if (!trySaveLootTable(tag)) {
         ContainerHelper.saveAllItems(tag, items);
      }

      tag.putInt("TransferCooldown", cooldownTime);
   }

   public int getContainerSize() {
      return items.size();
   }

   public ItemStack removeItem(int index, int amount) {
      unpackLootTable((Player)null);
      return ContainerHelper.removeItem(getItems(), index, amount);
   }

   public void setItem(int index, ItemStack item) {
      unpackLootTable((Player)null);
      getItems().set(index, item);
      if (item.getCount() > getMaxStackSize()) {
         item.setCount(getMaxStackSize());
      }

   }

   protected Component getDefaultName() {
      return new TranslatableComponent("container.hopper");
   }

   public static void pushItemsTick(Level level, BlockPos pos, BlockState state, HopperRarityTE te) {
      --te.cooldownTime;
      te.tickedGameTime = level.getGameTime();
      if (!te.isOnCooldown()) {
         te.setCooldown(0);
         tryMoveItems(level, pos, state, te, () -> {
            return suckInItems(level, te);
         });
      }

   }

   private static boolean tryMoveItems(Level level, BlockPos pos, BlockState state, HopperRarityTE te, BooleanSupplier supplier) {
      if (level.isClientSide) {
         return false;
      } else {
         if (!te.isOnCooldown() && state.getValue(HopperBlock.ENABLED)) {
            boolean flag = false;
            if (!te.isEmpty()) {
               flag = ejectItems(level, pos, state, te);
            }

            if (!te.inventoryFull()) {
               flag |= supplier.getAsBoolean();
            }

            if (flag) {
               te.setCooldown(8);
               setChanged(level, pos, state);
               return true;
            }
         }

         return false;
      }
   }

   private boolean inventoryFull() {
      for(ItemStack itemstack : items) {
         if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   private static boolean ejectItems(Level level, BlockPos pos, BlockState state, HopperRarityTE te) {
      Container container = getAttachedContainer(level, pos, state);
      if (container == null) {
         return false;
      } else {
         Direction direction = state.getValue(HopperBlock.FACING).getOpposite();
         if (isFullContainer(container, direction)) {
            return false;
         } else {
            for(int i = 0; i < te.getContainerSize(); ++i) {
               if (!te.getItem(i).isEmpty()) {
                  ItemStack itemstack = te.getItem(i).copy();
                  ItemStack itemstack1 = addItem(te, container, te.removeItem(i, 1), direction);
                  if (itemstack1.isEmpty()) {
                     container.setChanged();
                     return true;
                  }

                  te.setItem(i, itemstack);
               }
            }

            return false;
         }
      }
   }

   private static IntStream getSlots(Container container, Direction dir) {
      return container instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)container).getSlotsForFace(dir)) : 
    	  IntStream.range(0, container.getContainerSize());
   }

   private static boolean isFullContainer(Container container, Direction dir) {
      return getSlots(container, dir).allMatch((p_59379_) -> {
         ItemStack itemstack = container.getItem(p_59379_);
         return itemstack.getCount() >= itemstack.getMaxStackSize();
      });
   }

   private static boolean isEmptyContainer(Container container, Direction dir) {
      return getSlots(container, dir).allMatch((p_59319_) -> {
         return container.getItem(p_59319_).isEmpty();
      });
   }

   public static boolean suckInItems(Level level, Hopper hopper) {
      Boolean ret = net.minecraftforge.items.VanillaInventoryCodeHooks.extractHook(level, hopper);
      if (ret != null) return ret;
      Container container = getSourceContainer(level, hopper);
      if (container != null) {
         Direction direction = Direction.DOWN;
         return isEmptyContainer(container, direction) ? false : getSlots(container, direction).anyMatch((p_59363_) -> {
            return tryTakeInItemFromSlot(hopper, container, p_59363_, direction);
         });
      } else {
         for(ItemEntity itementity : getItemsAtAndAbove(level, hopper)) {
            if (addItem(hopper, itementity)) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean tryTakeInItemFromSlot(Hopper hopper, Container container, int index, Direction dir) {
      ItemStack itemstack = container.getItem(index);
      if (!itemstack.isEmpty() && canTakeItemFromContainer(container, itemstack, index, dir)) {
         ItemStack itemstack1 = itemstack.copy();
         ItemStack itemstack2 = addItem(container, hopper, container.removeItem(index, 1), (Direction)null);
         if (itemstack2.isEmpty()) {
            container.setChanged();
            return true;
         }

         container.setItem(index, itemstack1);
      }

      return false;
   }

   public static boolean addItem(Container container, ItemEntity entity) {
      boolean flag = false;
      ItemStack itemstack = entity.getItem().copy();
      ItemStack itemstack1 = addItem((Container)null, container, itemstack, (Direction)null);
      if (itemstack1.isEmpty()) {
         flag = true;
         entity.discard();
      } else {
         entity.setItem(itemstack1);
      }

      return flag;
   }

   public static ItemStack addItem(@Nullable Container container1, Container container2, ItemStack item, @Nullable Direction dir) {
      if (container2 instanceof WorldlyContainer && dir != null) {
         WorldlyContainer worldlycontainer = (WorldlyContainer)container2;
         int[] aint = worldlycontainer.getSlotsForFace(dir);

         for(int k = 0; k < aint.length && !item.isEmpty(); ++k) {
            item = tryMoveInItem(container1, container2, item, aint[k], dir);
         }
      } else {
         int i = container2.getContainerSize();

         for(int j = 0; j < i && !item.isEmpty(); ++j) {
            item = tryMoveInItem(container1, container2, item, j, dir);
         }
      }

      return item;
   }

   private static boolean canPlaceItemInContainer(Container container, ItemStack item, int index, @Nullable Direction dir) {
      if (!container.canPlaceItem(index, item)) {
         return false;
      } else {
         return !(container instanceof WorldlyContainer) || ((WorldlyContainer)container).canPlaceItemThroughFace(index, item, dir);
      }
   }

   private static boolean canTakeItemFromContainer(Container container, ItemStack item, int index, Direction dir) {
      return !(container instanceof WorldlyContainer) || ((WorldlyContainer)container).canTakeItemThroughFace(index, item, dir);
   }

   //TODO: This probably blocks moving items from my custom hoppers to vanilla hoppers... Remember to check if this is the case or not!
   private static ItemStack tryMoveInItem(@Nullable Container container1, Container container2, ItemStack item, int index, @Nullable Direction dir) {
      ItemStack itemstack = container2.getItem(index);
      if (canPlaceItemInContainer(container2, item, index, dir)) {
         boolean flag = false;
         boolean flag1 = container2.isEmpty();
         if (itemstack.isEmpty()) {
            container2.setItem(index, item);
            item = ItemStack.EMPTY;
            flag = true;
         } else if (canMergeItems(itemstack, item)) {
            int i = item.getMaxStackSize() - itemstack.getCount();
            int j = Math.min(item.getCount(), i);
            item.shrink(j);
            itemstack.grow(j);
            flag = j > 0;
         }

         if (flag) {
            if (flag1 && container2 instanceof HopperRarityTE) {
               HopperRarityTE hopperblockentity1 = (HopperRarityTE)container2;
               if (!hopperblockentity1.isOnCustomCooldown()) {
                  int k = 0;
                  if (container1 instanceof HopperRarityTE) {
                	  HopperRarityTE hopperblockentity = (HopperRarityTE)container1;
                     if (hopperblockentity1.tickedGameTime >= hopperblockentity.tickedGameTime) {
                        k = 1;
                     }
                  }

                  hopperblockentity1.setCooldown(8 - k);
               }
            }

            container2.setChanged();
         }
      }

      return item;
   }

   //TODO: Change HopperBlock.FACING to custom facing.
   @Nullable
   private static Container getAttachedContainer(Level level, BlockPos pos, BlockState state) {
      Direction direction = state.getValue(HopperBlock.FACING);
      return getContainerAt(level, pos.relative(direction));
   }

   @Nullable
   private static Container getSourceContainer(Level level, Hopper hopper) {
      return getContainerAt(level, hopper.getLevelX(), hopper.getLevelY() + 1.0D, hopper.getLevelZ());
   }

   public static List<ItemEntity> getItemsAtAndAbove(Level level, Hopper hopper) {
      return hopper.getSuckShape().toAabbs().stream().flatMap((p_155558_) -> {
         return level.getEntitiesOfClass(ItemEntity.class, p_155558_.move(hopper.getLevelX() - 0.5D, hopper.getLevelY() - 0.5D, hopper.getLevelZ() - 0.5D), 
        		 EntitySelector.ENTITY_STILL_ALIVE).stream();
      }).collect(Collectors.toList());
   }

   @Nullable
   public static Container getContainerAt(Level level, BlockPos pos) {
      return getContainerAt(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
   }

   @Nullable
   private static Container getContainerAt(Level level, double x, double y, double z) {
      Container container = null;
      BlockPos blockpos = new BlockPos(x, y, z);
      BlockState blockstate = level.getBlockState(blockpos);
      Block block = blockstate.getBlock();
      if (block instanceof WorldlyContainerHolder) {
         container = ((WorldlyContainerHolder)block).getContainer(blockstate, level, blockpos);
      } else if (blockstate.hasBlockEntity()) {
         BlockEntity blockentity = level.getBlockEntity(blockpos);
         if (blockentity instanceof Container) {
            container = (Container)blockentity;
            if (container instanceof ChestBlockEntity && block instanceof ChestBlock) {
               container = ChestBlock.getContainer((ChestBlock)block, blockstate, level, blockpos, true);
            }
         }
      }

      if (container == null) {
         List<Entity> list = level.getEntities((Entity)null, new AABB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), 
        		 EntitySelector.CONTAINER_ENTITY_SELECTOR);
         if (!list.isEmpty()) {
            container = (Container)list.get(level.random.nextInt(list.size()));
         }
      }

      return container;
   }

   private static boolean canMergeItems(ItemStack stack1, ItemStack stack2) {
      if (!stack1.is(stack2.getItem())) {
         return false;
      } else if (stack1.getDamageValue() != stack2.getDamageValue()) {
         return false;
      } else if (stack1.getCount() > stack1.getMaxStackSize()) {
         return false;
      } else {
         return ItemStack.tagMatches(stack1, stack2);
      }
   }

   public double getLevelX() {
      return (double)worldPosition.getX() + 0.5D;
   }

   public double getLevelY() {
      return (double)worldPosition.getY() + 0.5D;
   }

   public double getLevelZ() {
      return (double)worldPosition.getZ() + 0.5D;
   }

   public void setCooldown(int cooldown) {
      cooldownTime = cooldown;
   }

   private boolean isOnCooldown() {
      return cooldownTime > 0;
   }

   public boolean isOnCustomCooldown() {
      return cooldownTime > 8;
   }

   protected NonNullList<ItemStack> getItems() {
      return items;
   }

   protected void setItems(NonNullList<ItemStack> itemlist) {
      items = itemlist;
   }

   public static void entityInside(Level level, BlockPos pos, BlockState state, Entity entity, HopperRarityTE hopper) {
      if (entity instanceof ItemEntity && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().
    		  move((double)(-pos.getX()), (double)(-pos.getY()), (double)(-pos.getZ()))), hopper.getSuckShape(), BooleanOp.AND)) {
         tryMoveItems(level, pos, state, hopper, () -> {
            return addItem(hopper, (ItemEntity)entity);
         });
      }

   }

   protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
      return new HopperMenu(id, inventory, this);
   }

//   @Override
//   protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
//      return new net.minecraftforge.items.VanillaHopperItemHandler(this);
//   }

   public long getLastUpdateTime() {
      return tickedGameTime;
   }
}
