package br.corp.bonus630.android.keys;


import android.graphics.Canvas;
import android.graphics.Paint;



public class BoxMessage extends BoxLayout{


	private String caption,text;
	
	private int minWidth = 200;
	private int minHeight = 140;
	
	public BoxMessage()
	{
		super(200,140,200,200);
	}
	
	public BoxMessage(int width, int height, int left, int top) {
		super(width, height, left, top);
		
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
		p.setARGB(255, 0, 0, 0);
		canvas.drawPaint(p);
		super.draw(canvas);
		
		
		
		canvas.drawText(this.caption, this.left+20, this.top+30, this.paintFont);
		canvas.drawText(this.text, this.left+20, this.top+80, this.paintFont);
		
		
		
		
	}
	public void Show(Canvas canvas,String caption,String text)
	{
		
		this.onFocus = true;
		this.caption = caption;
		this.text = text;
		this.draw(canvas);
		
	}
	public void update(int code)
	{
		if(!this.onFocus)
			return;
	
		if(code == Joypad.CROSS || code == Joypad.CIRCLE)
			try {
			
				this.finalize();
				super.finalize();
			} catch (Throwable e) {
				
				e.printStackTrace();
			}
	}


}
