package br.corp.bonus630.android.keys;


import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Rect;

public class BoxOptions extends BoxLayout {

	Canvas canvas;
	Rect r;
	private final Options[] options = new Options[]{new Options(optionOp.rename, "Rename"),new Options(optionOp.newfolder, "New Folder"),new Options(optionOp.options, "Options"),
			new Options(optionOp.about, "About"),new Options(optionOp.exit, "Exit")};
	private final int numOptions = options.length;
	
	public enum optionOp
	{
		rename,newfolder,options,about,exit
	}
	
	public BoxOptions(int width, int height, int left, int top) {
		super(width, height, left, top);
		
	}
	public BoxOptions(Canvas canvas)
	{
	
		super(200,200,50,50);
		this.canvas = canvas;
		r = new Rect(this.left +10,this.top+10,this.left+10+AssetPosition.CURSOR2.width()*2,this.top+10+AssetPosition.CURSOR2.height()*2);
		this.index = 0;
		
	}
	public void draw(Canvas canvas)
	{
		Paint p = new Paint();
		p.setARGB(175, 0, 0, 0);
		canvas.drawPaint(p);
		super.draw(this.canvas);
		int top = this.top +30;
		for(int i = 0; i<numOptions;i++)
			canvas.drawText(options[i].text, this.left+50,top+30*i, this.paintFont);
		
		canvas.drawBitmap(MainActivity.bitmap,AssetPosition.CURSOR2,r,null);
	}
	
	public void update(int keyCode)
	{
		if(!this.onFocus)
			return;
		switch (keyCode) {
		case Joypad.DOWN:
			if(index <4)
			index++;
			break;
		case Joypad.UP:
			if(index >0)
			index--;
			break;
		default:
			break;
		}
		r.offsetTo(this.left+10, this.top+10+(30*index));
	}
	
	
	public void show(Canvas canvas)
	{
		this.onFocus = true;
		this.draw(canvas);
	}
	public optionOp getOptionOp()
	{
		return this.options[index].option_op;
	}
	
	public class Options
	{
		public optionOp option_op;
		public String text;
		
		public Options(optionOp option_op,String text)
		{
			this.option_op = option_op;
			this.text = text;
		}
	
	}
}
