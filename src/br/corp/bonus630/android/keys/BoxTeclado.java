package br.corp.bonus630.android.keys;

import android.graphics.Canvas;
import android.graphics.Color;

public class BoxTeclado {
	BoxTc boxTc;
	BoxName boxName;
	Canvas canvas;
	public String fileName;
	public boolean showMe = false;
	public BoxTeclado(Canvas canvas)
	{
		boxTc = new BoxTc(0, 0, 0, 0);
		boxName = new BoxName(0,0,0,0);
		this.canvas = canvas;
	}
	
	private void draw(Canvas canvas)
	{
		if(!showMe)return;
		canvas.drawColor(Color.BLACK);
		boxTc.draw(canvas);
		boxName.draw(canvas);
	}
	public void show(Canvas canvas,String fileName)
	{
		this.draw(canvas);
	}
	public void setFileName(String fileName)
	{
		boxName.setNameFile(fileName);
	}
	public void update(int keyCode)
	{
		if(!showMe)return;
		if(keyCode == Joypad.LEFT ||keyCode == Joypad.DOWN || keyCode == Joypad.RIGHT || keyCode == Joypad.UP)
			boxTc.update(keyCode);
		if(keyCode == Joypad.CROSS)
			boxName.update(boxTc.update());
		if(keyCode == Joypad.CIRCLE)
			boxName.update(' ');
		boxName.update(keyCode);
		if(keyCode == Joypad.START)
		{
			this.fileName = boxName.getNameFile();
			this.showMe = false;
			try {
				this.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		if(keyCode == Joypad.SELECT)
		{
			this.showMe = false;
			
		}
		this.draw(this.canvas);
	}
}
