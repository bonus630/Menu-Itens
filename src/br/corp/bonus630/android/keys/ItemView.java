package br.corp.bonus630.android.keys;

import java.io.File;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Rect;
import android.graphics.Typeface;

public class ItemView {
	
	private File desc;
	private int index;
	private Typeface font;
	
	private Rect r;
	private boolean selected = false;
	private boolean clicked = false;
	private boolean focused = false;
	private final static int width = 100;
	private final static int height = 28;
	private final static int marginL = 4;
	private final static int marginT = 4;
	
	
	public ItemView(File desc, int index) {
		super();
		this.desc = desc;
		this.index = index;
		this.font = MainActivity.font;
	
	}

	public static int getItemWidth()
	{
		return width+marginL;
	}
	
	public static int getItemHeight()
	{
		return height+marginT;
	}
	
	public File getFile() {
		if(this.selected)
			return this.desc;
		return null;
	}

	public void setDesc(File desc) {
		this.desc = desc;
	}

	public int getIndex() {
		return index;
	}
	
	public int getIndex(File file)
	{
		int i = -1;
			if(this.desc.equals(file))
				return this.index;
		return i;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void draw(Rect position,Canvas canvas)
	{
		Paint paint2 = new Paint();
		paint2.setColor(Color2.ASSETS_ALPHA);
		if(this.focused)
		{
			r = new Rect(position.left+4,position.top,position.left+4+AssetPosition.CURSOR.width()*2,position.top+AssetPosition.CURSOR.height()*2);
			canvas.drawBitmap(MainActivity.bitmap,AssetPosition.CURSOR,r,paint2);
		}
		
		Paint paint3 = new Paint();
		paint3.setTextSize(26.0f);
		paint3.setTypeface(this.font);
		if(this.selected)
			paint3.setColor(Color.YELLOW);
		else
			paint3.setColor(Color.WHITE);
	
		
		Paint paint4 = new Paint();
		paint4.setColor(Color.WHITE);
		
		
		if(this.desc.isFile())
			canvas.drawText(": FILE", position.right-98, position.centerY(), paint3);
			
		if(this.desc.isDirectory())
			canvas.drawText(": FOLDER", position.right-98, position.centerY(), paint3);
			
		String fileName = this.desc.getName();
		if(fileName.length()>23)
			fileName = fileName.substring(0,22)+"...";
		
		canvas.drawText(fileName, position.left+AssetPosition.CURSOR.width()+30, position.centerY(), paint3);
	
	}
	
	public void update(int index,boolean focus)
	{
		
		if(this.index == index)
			this.selected = true;
		else
			this.selected = false;
		if(focus && this.selected)
		{
			this.focused = true;
		}
		else
			this.focused = false;
		
	}
	
	public void clicked()
	{
		if(this.clicked)
			this.clicked = false;
		else
			this.clicked = true;
	}

	
	
}

	
