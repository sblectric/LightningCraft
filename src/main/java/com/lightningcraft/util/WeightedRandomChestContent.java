package com.lightningcraft.util;

import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
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

    public WeightedRandomChestContent(Item p_i45311_1_, int p_i45311_2_, int minimumChance, int maximumChance, int itemWeightIn)
    {
        super(itemWeightIn);
        this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
        this.minStackSize = minimumChance;
        this.maxStackSize = maximumChance;
    }

    /** Generate chest contents */
    public static void generateChestContents(Random random, List<WeightedRandomChestContent> listIn, TileEntityChest dispenser, int max)
    {
        for (int i = 0; i < max; ++i)
        {
            WeightedRandomChestContent weightedrandomchestcontent = (WeightedRandomChestContent)WeightedRandom.getRandomItem(random, listIn);
            int j = weightedrandomchestcontent.minStackSize + random.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);

            if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j)
            {
                ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
                itemstack1.stackSize = j;
                dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack1);
            }
            else
            {
                for (int k = 0; k < j; ++k)
                {
                    ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
                    itemstack.stackSize = 1;
                    dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack);
                }
            }
        }
    }
}