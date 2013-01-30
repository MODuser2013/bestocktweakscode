package bestock.smartquarry;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityQuarry extends TileEntity implements IInventory{

	int FuelInBurn = 12; //each coal = 12, lava = 24
	
	boolean AllFuelBurnt = false; //happens when there is no fuel in the inventory slot
	
	public TileEntityQuarry() {
		
	}
	
	@Override
	public void updateEntity()
	{
		//checks if fuel, space and if active are all true
		
	}

	

	private boolean validFuel(int id)
	{
		
		return true;
	}
	private int amountFuel(int id)
	{
		
		return 12;
	}
	private void updateFuel()
	{
		//keeps track of fuel
		if(FuelInBurn == 0)
		{
			int id = inventory[27].itemID;
			int amount = inventory[27].stackSize;
			if(validFuel(id) && amount > 0)
			{
				decrStackSize(27, 1); //pulls out 1 of fuel item
				FuelInBurn += amountFuel(id);
			}
			else
			{
				AllFuelBurnt = true;
			}
		}
		else
		{
			FuelInBurn -= 1;
		}
	}
	private boolean isMineableBlock(int id)
	{
		//checks if block is mineable
		if(id == Block.bedrock.blockID || id == Block.chest.blockID || id == this.blockType.blockID)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	private boolean isStairBlock(int x, int y, int z)
	{
		//keeps track of the stairs
		return false;
	}
	private void mineBlock(int x, int y, int z)
	{
		//mines block and adds to inventory
		World curWorld = getWorldObj();
		int bId = curWorld.getBlockId(x, y, z);
		int meta = curWorld.getBlockMetadata(x,y,z);
		List<ItemStack> blockDrops = Block.blocksList[bId].getBlockDropped(curWorld, x, y, z, meta, 0);
		
		for (ItemStack item : blockDrops) 
		{
			float var = 0.7F;
			double dx = curWorld.rand.nextFloat() * var + (1.0F - var) * 0.5D;
			double dy = curWorld.rand.nextFloat() * var + (1.0F - var) * 0.5D;
			double dz = curWorld.rand.nextFloat() * var + (1.0F - var) * 0.5D;
			EntityItem entityitem = new EntityItem(curWorld, this.xCoord + dx, this.yCoord + dy, this.zCoord + dz, item);
		
			entityitem.lifespan = 1200;
			entityitem.delayBeforeCanPickup = 10;
		}
		this.getWorldObj().setBlockWithNotify(x, y, z, 0); //sets block to air
	}
	@Override
	public void onInventoryChanged()
	{
		//check for coal and update fuel
		if(validFuel(inventory[27].itemID))
		{
			//resets fuel flag letting the quarry continue
			AllFuelBurnt = false;
		}
	}
	
	
	
	//INVENTORY CODE
	private ItemStack inventory[] = new ItemStack[28]; //actual inventory
	
	@Override
	public int getSizeInventory() {
		return inventory.length; /*storage + fuel*/
		}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return inventory[slotIndex];
		}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {
		// This gets the stack with the slot number you want
		ItemStack stack = getStackInSlot(slotIndex);

		// Then it checks to make sure it has something in it
		if(stack != null){
		// Then it checks to make sure that it has something that is equal or lesser than the amount you want to add
		if(stack.stackSize <= amount){
		setInventorySlotContents(slotIndex, null);
		} else{
		stack = stack.splitStack(amount);
		if(stack.stackSize == 0){
		setInventorySlotContents(slotIndex, null);
		}
		}
		}
		// Then it returns the stack
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		// This gets the stack in the slot you chose
		ItemStack stack = getStackInSlot(slotIndex);

		// This checks to make sure it has something in it
		if(stack != null){
		setInventorySlotContents(slotIndex, null);
		}

		// Then it returns the stack
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		// This checks to make sure the stack is not nothing, and then makes sure the stack is not going over the limit
		// Of the stack
		if(stack != null && stack.stackSize > getInventoryStackLimit()){
		stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {return "smartquarry.inv";}

	@Override
	public int getInventoryStackLimit() {return 64;}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {/*DUMMY METHOD*/}

	@Override
	public void closeChest() {/*DUMMY METHOD*/}
}
