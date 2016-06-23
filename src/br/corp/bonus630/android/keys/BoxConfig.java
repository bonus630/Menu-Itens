package br.corp.bonus630.android.keys;

import java.io.File;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

import android.graphics.Rect;

@SuppressLint("DefaultLocale")
public class BoxConfig extends BoxLayout{
	
	private File file;
	public static String result = "";

	public BoxConfig(int width, int height, int left, int top) {
		super(width, height, left, top);
	
	}

	@Override
	protected void draw(Canvas canvas) {
		super.draw(canvas);
		if(file != null)
		{
			canvas.drawText("Path: "+file.getAbsolutePath().replace(file.getName(),""),90,54, this.paintFont);
				if(file.isFile())
				{
					String type = FileManager.getType(file);
					if(type.contains("image"))
					{
						canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_PICTURE_ACTIVED,new Rect(50,54,AssetPosition.ICONE_PICTURE_ACTIVED.width()*2+50,AssetPosition.ICONE_PICTURE_ACTIVED.height()*2+54),null);
						
					}
					if(type.contains("audio"))
					{
						canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_AUDIO_ACTIVED,new Rect(50,54,AssetPosition.ICONE_AUDIO_ACTIVED.width()*2+50,AssetPosition.ICONE_AUDIO_ACTIVED.height()*2+54),null);
						
					}
					if(type.contains("video"))
					{
						canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_VIDEO_ACTIVED,new Rect(50,54,AssetPosition.ICONE_VIDEO_ACTIVED.width()*2+50,AssetPosition.ICONE_VIDEO_ACTIVED.height()*2+54),null);
						
					}
					if(type.contains("text"))
					{
						canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_TEXT_ACTIVED,new Rect(50,54,AssetPosition.ICONE_TEXT_ACTIVED.width()*2+50,AssetPosition.ICONE_TEXT_ACTIVED.height()*2+54),null);
					}
					if(type.contains("application"))
					{
						canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_APPLICATION_ACTIVED,new Rect(50,54,AssetPosition.ICONE_APPLICATION_ACTIVED.width()*2+50,AssetPosition.ICONE_APPLICATION_ACTIVED.height()*2+54),null);
						
					}
					if(type.contains("unknow"))
					{
						canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_UNKNOW_ACTIVED,new Rect(50,54,AssetPosition.ICONE_UNKNOW_ACTIVED.width()*2+50,AssetPosition.ICONE_UNKNOW_ACTIVED.height()*2+54),null);
						
					}
					if(type.contains("zip")||type.contains("rar"))
					{
						canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_ITEM_ACTIVED,new Rect(50,54,AssetPosition.ICONE_ITEM_ACTIVED.width()*2+50,AssetPosition.ICONE_ITEM_ACTIVED.height()*2+54),null);
						
					}
					canvas.drawText("Type: "+type,220,94, this.paintFont);
					canvas.drawText("Size: "+convertBytes(file.length())+"",90,94, this.paintFont);
					}
				String f = "File: ";
				if(file.isDirectory())
				{
					f = "Folder: ";
					canvas.drawText("Files: "+file.list().length+"",90,94, this.paintFont);
					canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ICONE_FOLDER_ACTIVED,new Rect(50,54,AssetPosition.ICONE_FOLDER_ACTIVED.width()*2+50,AssetPosition.ICONE_FOLDER_ACTIVED.height()*2+54),null);
					
				}
				canvas.drawText(f+file.getName(),90,74, this.paintFont);
				if(result != "")
					canvas.drawText(result,this.left+this.width -150,94, this.paintFont);
				result = "";
				
	}
		
	}
	
	private String convertBytes(long bytes) {
		int unit =1000 ;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = ( "kMGTPE").charAt(exp-1)+"";
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	public void setFile(File file)
	{
		this.file = file;
		
	}

}
