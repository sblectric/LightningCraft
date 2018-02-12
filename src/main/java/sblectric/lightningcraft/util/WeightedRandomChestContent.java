package sblectric.lightningcraft.util;

import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandom;

/** Restoring dropped support */
public class WeightedRandomChestContent extends WeightedRandom.Item
{
    /** The Item/Block ID to generate in the Chest. */
    public ItemStack theItemId;
    /** The minimum stack size of generated item. */
    public int minStackSize;
    /** The maximum stack size of generated item. */
    public int maxStackSize;

    public WeightedRandomChestContent(Item item, int meta, int minimumChance, int maximumChance, int itemWeightIn)
    {
        super(itemWeightIn);
        this.theItemId = new ItemStack(item, 1, meta);
        this.minStackSize = minimumChance;
        this.maxStackSize = maximumChance;
    }

    /** Generate chest contents */
    public static void generateChestContents(Random random, List<WeightedRandomChestContent> listIn, TileEntityChest dispenser, int max)
    {
        for (int i = 0; i < max; ++i)
        {
            WeightedRandomChestContent wrcc = WeightedRandom.getRandomItem(random, listIn);
            int j = wrcc.minStackSize + random.nextInt(wrcc.maxStackSize - wrcc.minStackSize + 1);

            if (wrcc.theItemId.getMaxStackSize() >= j)
            {
                ItemStack itemstack1 = wrcc.theItemId.copy();
                itemstack1.setCount(j);
                dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack1);
            }
            else
            {
                for (int k = 0; k < j; ++k)
                {
                    ItemStack itemstack = wrcc.theItemId.copy();
                    itemstack.setCount(1);
                    dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack);
                }
            }
        }
    }
}