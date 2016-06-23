package br.corp.bonus630.android.keys;

import android.graphics.Canvas;
import android.graphics.Rect;

public class BoxTc extends BoxLayout{

	private final char[] charMap = new char[]{'A','B','C','D','E','F','G','H','I','L','M','N','O','P','Q','R','S','T','U','V','W',
										'/','X','a','b','c','d','e','f','g','h','i','j','õ','l','m','n','o','p',
										'q','r','s','t','u','v','x','á','z','0','1','2','3','4','5','6','7','8',
										'9','"','Í',':','+','?','Ú','.','-',' '};
	
	private final int numCol = 13;
	private final int NUMCHARS = charMap.length;
	
	Rect[] cursorGrid = new Rect[NUMCHARS];
	public BoxTc(int width, int height, int left, int top) {
		super(720, 260, 60, 190);
		this.paintFont.setTextSize(38.0f);
		
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		
		int left = this.left + 50;
		int top = this.top + 50;
		int c = 0;
		int l = 0;
		int spacing = 46;
		for(int i = 0; i <NUMCHARS;i++)
		{
			canvas.drawText(String.valueOf(charMap[i]), left+(c*spacing), top+(l*spacing), this.paintFont);
			cursorGrid[i] = new Rect(left+(c*spacing)-36, top+(l*spacing)-18,left+(c*spacing)+AssetPosition.CURSOR.width()*2-36, top+(l*spacing)+AssetPosition.CURSOR.height()*2-18);
			c++;
			
			
			if(c>numCol)
			{
				c = 0;l++;
			}
		
		}
		
		canvas.drawBitmap(MainActivity.bitmap, AssetPosition.CURSOR, cursorGrid[index], null);
		
	}

	public char update() {
		return charMap[index];
	}
	public void update(int keyCode)
	{
		switch (keyCode) {
		case Joypad.RIGHT:
			if(index<NUMCHARS-1)
				index++;
			break;
		case Joypad.LEFT:
			if(index>0)
				index--;
			break;
		case Joypad.DOWN:
			if(index<NUMCHARS)
				index+=numCol+1;
			if(index > NUMCHARS-1)
				index = NUMCHARS -1;
			break;
		case Joypad.UP:
			if((index-=numCol+1)<0)
				index = 0;
			break;
		default:
			break;
		}
	
	}

}
