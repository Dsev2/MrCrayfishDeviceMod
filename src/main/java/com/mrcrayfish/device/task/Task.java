package com.mrcrayfish.device.task;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class Task 
{
	private Callback callback = null;
	private boolean success = false;
	
	public void setCallback(Callback callback)
	{
		this.callback = callback;
	}
	
	public void callback()
	{
		if(callback != null)
		{
			callback.execute(success);
		}
	}
	
	protected void setSuccessful()
	{
		this.success = true;
	}
	
	protected boolean isSucessful()
	{
		return this.success;
	}
	
	public abstract String getName();
	
	
	/**
	 * Called before the request is sent off to the server. 
	 * You should store you data you want to send into the NBT Tag
	 * 
	 * @param nbt The NBT to be sent to the server
	 */
	public abstract void prepareRequest(NBTTagCompound nbt);
	
	/**
	 * Called when the request arrives to the server. Here you can perform actions
	 * with your request. Data attached to the NBT from {@link #prepareRequest()} can be
	 * accessed from the NBT tag parameter.
	 * 
	 * @param nbt The NBT Tag received from the client
	 */
	public abstract void processRequest(NBTTagCompound nbt, World world, EntityPlayer player);
	
	public abstract void prepareResponse(NBTTagCompound nbt);
	
	public abstract void processResponse(NBTTagCompound nbt);
}