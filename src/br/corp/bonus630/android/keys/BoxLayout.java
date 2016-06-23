package br.corp.bonus630.android.keys;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;



public abstract class BoxLayout {
	
	private Rect top_right,top_left,bottom_right,bottom_left,box;
	private Rect[] top_mid,bottom_mid,mid_left,mid_right,center;
	protected Canvas canvas;
	protected int width,height,left,top;
	protected int scale = 2;
	protected boolean onFocus = false;
	protected int index;
	protected Paint paintFont;
	private Paint gradient;
	
	
	public BoxLayout(int width, int height, int left, int top) {
		
		this.width = width;
		this.height = height;
		this.left = left;
		this.top = top;
		paintFont = new Paint();
		paintFont.setTextSize(26.0f);
		paintFont.setTypeface(MainActivity.font);
		paintFont.setColor(Color.WHITE);
		genGrid();
			
	}
	
	
	protected void updateSize(int width, int height, int left, int top)
	{
		this.width = width;
		this.height = height;
		this.left = left;
		this.top = top;
		genGrid();
	}
	
	protected void draw(Canvas canvas)
	{
		if(this.canvas == null)
			this.canvas = canvas;
		Paint b = new Paint();
		b.setColor(Color.BLACK);
		
		for(int i = 0;i<center.length;i++)
		{
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_INNER, center[i], null);
		}
		
		for(int i = 0;i<top_mid.length;i++)
		{
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_BOTTOM_MID, bottom_mid[i], null);
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_TOP_MID, top_mid[i], null);
		}
		for(int i = 0;i<mid_left.length;i++)
		{
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_MID_LEFT, mid_left[i], null);
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_MID_RIGHT, mid_right[i], null);
		}
		
		canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_TOP_LEFT, top_left, null);
		canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_TOP_RIGHT, top_right, null);
		canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_BOTTOM_LEFT, bottom_left, null);
		canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BOX_BOTTOM_RIGHT, bottom_right, null);
		canvas.drawRect(box, gradient);
		
		
		
	}
	protected void genGrid()
	{
		LinearGradient lg = new LinearGradient(this.left, this.top, this.left, this.top+this.height, Color2.ALPHA_WHITE, Color2.ALPHA_BLACK, TileMode.REPEAT);
		
		
		gradient = new Paint();
		gradient.setStyle(Style.FILL);
		gradient.setShader(lg);
		
		//preciso corrigir os restos não inteiros, para completar corretamente o preenchimento das box com as texturas
		int right = this.width+this.left;
		int bottom =  this.top+this.height;
		int boxS,boxH,num,numL,numC;
		
		
		//Gera os cantos das boxes
		top_left = new Rect(this.left,this.top,(AssetPosition.BOX_TOP_LEFT.width())*scale+this.left,(AssetPosition.BOX_TOP_LEFT.height())*scale+this.top);
		top_right = new Rect(right-(AssetPosition.BOX_TOP_RIGHT.width()*scale),this.top,right,(AssetPosition.BOX_TOP_RIGHT.height())*scale+this.top);
		bottom_left = new Rect(this.left,bottom - (AssetPosition.BOX_BOTTOM_LEFT.height()*scale),this.left+(AssetPosition.BOX_BOTTOM_LEFT.width()*scale),bottom);
		bottom_right = new Rect(right-(AssetPosition.BOX_BOTTOM_RIGHT.width()*scale),bottom - (AssetPosition.BOX_BOTTOM_RIGHT.height()*scale),right,bottom);
		//fim dos cantos
		
		
		//Gera o topo e a base
		 boxS = (AssetPosition.BOX_BOTTOM_MID.width()*scale);
		 boxH = (AssetPosition.BOX_BOTTOM_MID.height()*scale);
		 
			 num = this.width / boxS;
		
		top_mid = new Rect[num];
		bottom_mid = new Rect[num];
		for(int i = 0;i<num;i++)
		{
			top_mid[i] = new Rect(this.left+(boxS*i),this.top,this.left+((i+1)*boxS),this.top+boxH);
			bottom_mid[i] = new Rect(this.left+(boxS*i),bottom - boxH,this.left+((i+1)*boxS),bottom);
		}
		boxS = (AssetPosition.BOX_MID_LEFT.width()*scale);
		boxH = (AssetPosition.BOX_MID_LEFT.height()*scale);
		
		
			num = this.height / boxH;
		
		mid_left = new Rect[num];
		mid_right = new Rect[num];
		
		for(int i=0;i<num;i++)
		{
			mid_left[i] = new Rect(this.left,this.top+(boxH*i),this.left+boxS,this.top+(boxH*(i+1)));
			mid_right[i] = new Rect(right-boxS,this.top+(boxH*i),right,this.top+(boxH*(i+1)));
		}
		//Fim do topo e da base
				
		box = new Rect(this.left,this.top,right,bottom);
		
		//Gera o preenchimento
		boxS =AssetPosition.BOX_INNER.width()*scale;
		boxH = AssetPosition.BOX_INNER.height()*scale;
		
		numL = (this.width / boxS);
		numC = (this.height /boxH);
		num = (numL) * (numC);
		
		center = new Rect[num];
		int c = 0;int l = 0;
		for(int i = 0;i<num;i++)
		{
			
			center[i] = new Rect(this.left+boxS*l,this.top+(boxH*c),this.left+(boxS*(l+1)),this.top+(boxS*(c+1)));
			if (l < numL-1) {
				l++;

			} else {
				l = 0;
				c++;
			}
			
		}
		//Fim do preenchimento
	
		
		
	}
}
