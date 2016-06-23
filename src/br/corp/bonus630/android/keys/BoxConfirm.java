package br.corp.bonus630.android.keys;

import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class BoxConfirm extends BoxLayout{

	public enum ResultDialog
	{
		Yes,No
	}
	
	
	private String caption,text;
	private ResultDialog result;
	private Rect r;
	private int minWidth = 200;
	private int minHeight = 140;
	
	public BoxConfirm(Canvas canvas)
	{
		
		super(200,140,canvas.getWidth()/2-100,canvas.getHeight()/2-70);
		result = ResultDialog.No;
		this.canvas = canvas;
	}
	
	public BoxConfirm(int width, int height, int left, int top) {
		super(width, height, left, top);
		
	}
	
	public ResultDialog Show(Canvas canvas,String caption,String text)
	{
		this.onFocus = true;
		this.caption = caption;
		this.text = text;
		this.draw(canvas);
		return result;
	}

	@Override
	protected void draw(Canvas canvas) {

		int textsize = (int)this.paintFont.measureText(this.text);
		if(textsize>minWidth-40)
		{
			updateSize(textsize+40,minHeight,canvas.getWidth()/2-(textsize+40)/2,canvas.getHeight()/2-minHeight/2);
		}
		else
		{
			updateSize(minWidth,minHeight,canvas.getWidth()/2-minWidth/2,canvas.getHeight()/2-minHeight/2);
		}
		Paint p = new Paint();
		p.setARGB(175, 0, 0, 0);
		canvas.drawPaint(p);
		super.draw(this.canvas);
		
		
		
		canvas.drawText(this.caption, this.left+20, this.top+30, this.paintFont);
		canvas.drawText(this.text, this.left+20, this.top+50, this.paintFont);
		if(result == ResultDialog.Yes)
		{
			
			r = new Rect(this.left + (this.width -160),this.top +(this.height - 40),this.left + (this.width -160)+AssetPosition.CURSOR2.width()*2,this.top +(this.height - 40)+AssetPosition.CURSOR2.height()*2);
		}
		else
		{
			
			r = new Rect(this.left + (this.width -80),this.top +(this.height - 40),this.left + (this.width -80)+AssetPosition.CURSOR2.width()*2,this.top +(this.height - 40)+AssetPosition.CURSOR2.height()*2);
		}
		canvas.drawBitmap(MainActivity.bitmap,AssetPosition.CURSOR2,r,null);
		canvas.drawText("YES", this.left + (this.width -120), this.top +(this.height - 20), this.paintFont);
		canvas.drawText("NO", this.left + (this.width - 40), this.top + (this.height -20), this.paintFont);
		
		
		
	}
	public void update(int code)
	{
		if(!this.onFocus)
			return;
		if(code == Joypad.RIGHT || code ==Joypad.LEFT)
		{Log.e("key c",code+"");
			if(result == ResultDialog.No)
			{
				result = ResultDialog.Yes;
				
			}
			else
			{
				result = ResultDialog.No;
			
			}
		}
		if(code == Joypad.CROSS || code == Joypad.CIRCLE)
			try {
				result = ResultDialog.No;
				this.finalize();
				super.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
	}

}
