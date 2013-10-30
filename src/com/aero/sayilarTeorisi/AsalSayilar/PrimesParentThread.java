package com.aero.sayilarTeorisi.AsalSayilar;

public class PrimesParentThread extends Thread
{
	protected int threadNumber;
	protected double executeTime = 0;
	public boolean completed = false;
	
	public PrimesParentThread(int n)
	{
		this.threadNumber = n;
	}
	
	public double getExecuteTime()
	{
		return this.executeTime;
	}
	
	
}
