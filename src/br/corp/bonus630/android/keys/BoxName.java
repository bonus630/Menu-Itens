package br.corp.bonus630.android.keys;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BoxName extends BoxLayout{

	Rect name,arrow_down,arrow_up;
	String nameFile = "New Folder";
	char tempChar;
	
	public BoxName(int width, int height, int left, int top) {
		super(520, 140, 260, 50);
		name = new Rect(400,105,800,135);
		arrow_up = new Rect(396,100-AssetPosition.ARROW_DOWN.height()*2,396+AssetPosition.ARROW_DOWN.width()*2,100);
		arrow_down = new Rect(396,140,396+AssetPosition.ARROW_UP.width()*2,140+AssetPosition.ARROW_UP.height()*2);
		
		this.index = 0;
	}
	public void draw(Canvas canvas){
		super.draw(canvas);
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		canvas.drawText(this.nameFile, name.left, name.centerY()+4, this.paintFont);
		canvas.drawBitmap(MainActivity.bitmap,AssetPosition.ARROW_UP, arrow_down, null);
		canvas.drawBitmap(MainActivity.bitmap,AssetPosition.ARROW_DOWN, arrow_up, null);
	}
	public void setNameFile(String nameFile)
	{
		this.nameFile = nameFile;
	}
	public String getNameFile()
	{
		return this.nameFile;
	}
	public void update(String name) {
		this.nameFile = name;
		
	}
	
	public void update(int keyCode)
	{
		switch (keyCode) {
		case Joypad.CIRCLE:
			if(index>0){
				//apaga o caracter anterior e volta o carro;
				arrow_up.offsetTo(arrow_up.left-10, arrow_up.top);
				arrow_down.offsetTo(arrow_down.left-10, arrow_down.top);
				
				index--;
				updateName(false);
				
			}
			break;
		case Joypad.CROSS:
			//seleciona o caracter e avança o carro;
			arrow_up.offsetTo(arrow_up.left+10, arrow_up.top);
			arrow_down.offsetTo(arrow_down.left+10, arrow_down.top);
			updateName(true);
			index++;
			break;
		default:
			break;
		}
	}
	public void update(char car) {
		
		this.tempChar = car;
	}
	
	private void updateName(boolean insert)
	{
		if(insert){
			if(this.nameFile.length()>index)
			{
				this.nameFile = this.nameFile.subSequence(0, index).toString()
						+tempChar+this.nameFile.subSequence(index+1, this.nameFile.length()).toString();
			}
			else
				this.nameFile += tempChar;
		}
		else
		{
			this.nameFile = this.nameFile.subSequence(0, index).toString();
					
		}
		}
	
	}


