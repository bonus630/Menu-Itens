package br.corp.bonus630.android.keys;

import java.io.File;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import br.corp.bonus630.android.keys.BoxConfirm.ResultDialog;
import br.corp.bonus630.android.keys.BoxOptions.optionOp;
import br.corp.bonus630.android.keys.FileManager.FileAction;


@SuppressLint("DrawAllocation")
public class KeysView extends View {

	
	private Canvas canvas;
	
	int index;
	SoundPool menuSound;
	int soundId;
	ArrayList<ItemView> ItemList;
	private BoxList boxList, boxList2;
	private BoxConfig boxConfig;
	private BoxMessage boxMessage;
	File currentSelectedFile, currentFocusFile;
	FileManager fm;
	int numItems;
	boolean confirm,option;
	BoxConfirm boxConfirm;
	BoxOptions boxOptions;
	ResultDialog result;
	float vol = 10.0f;
	private FileAction fileAction;
	private boolean isUpdating = false;
	BoxTeclado bt;
	public boolean joypad = false;
	public boolean appExit = false;

	
	
	public KeysView(Context context) {
		super(context);
		
		menuSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundId = menuSound.load(context, R.raw.audiomenu, 1);

		boxConfig = new BoxConfig(786, 80, 34, 30);
		boxList = new BoxList(396, 336, 34, 110,new File(MainActivity.list1));
	
		boxList.setFocus();
		
		
		boxList2 = new BoxList(390, 336, 430, 110,new File(MainActivity.list2));
		
		fm = new FileManager(this);
		boxMessage = new BoxMessage();
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (this.canvas == null) {
			this.canvas = canvas;
		}
		if(!joypad)
			boxMessage.Show(canvas," Application locked", "Open your joypad for use her!!!");
		else{
		canvas.drawColor(Color.DKGRAY);
		int canvasW = canvas.getWidth();
		int canvasH = canvas.getHeight();
		int bgheight = AssetPosition.BACKGROUND.height() * 2;
		int bgwidth = AssetPosition.BACKGROUND.width() * 2;
		int numBgW = canvasW / bgwidth;
		int numBgH = canvasH / bgheight;
		for (int j = 0; j <= numBgW; j++) {
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BACKGROUND,
					new Rect(j * bgwidth, 0, (j + 1) * bgwidth, bgheight), null);
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BACKGROUND,
					new Rect(j * bgwidth, canvasH - bgheight,
							(j + 1) * bgwidth, canvasH), null);
		}
		for (int i = 0; i <= numBgH; i++) {
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BACKGROUND,
					new Rect(canvasW - bgwidth, i * bgheight, canvasW, (i + 1)
							* bgheight), null);
			canvas.drawBitmap(MainActivity.bitmap, AssetPosition.BACKGROUND,
					new Rect(0, i * bgheight, bgwidth, (i + 1) * bgheight),
					null);

		}
		
		boxList2.draw(canvas);
		boxList.draw(canvas);

		boxConfig.setFile(currentFocusFile);
		boxConfig.draw(canvas);
		if (boxConfirm == null)
			boxConfirm = new BoxConfirm(canvas);
		if (boxOptions == null)
			boxOptions = new BoxOptions(canvas);
		if (this.confirm)
		{
			if(fm.getAction() == FileAction.delete)
				result = boxConfirm.Show(canvas, "Deletar?",
					this.currentFocusFile.getName());
			if(fm.getAction() == FileAction.copy)
				result = boxConfirm.Show(canvas, "Copiar?",
					this.currentFocusFile.getName());
		}
		if(this.option){
			
			boxOptions.show(canvas);
		}
		
		
		if(bt == null)
			bt = new BoxTeclado(canvas);
		
		bt.show(canvas,"File Teste");
		
		}
		
		
		
		
	}

	public void update(int keyCode) {
		if(!joypad)
		{
			this.appExit = true;
			postInvalidate();
			return;
			
		}
			
		
		if(bt.showMe)
		{
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			bt.update(keyCode);
			if(keyCode == Joypad.START)
			{
				fm = new FileManager(this);
				fm.setAction(fileAction);
				fm.execute(new File(bt.fileName),this.currentFocusFile);
				updateBoxs(0);
			}
			postInvalidate();
			return;
		}
		
		switch (keyCode) {
		case Joypad.VOL_DOWN:
			if(vol>0)
				vol--;
		break;
		case Joypad.VOL_UP:
			if(vol<100)
				vol++;
		break;	
		case Joypad.RIGHT:
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			boxList2.setFocus();
			boxList.removeFocus();
			break;
		case Joypad.LEFT:
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			boxList.setFocus();
			boxList2.removeFocus();
			break;
		case Joypad.TRIANGLE:
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			fm = new FileManager(this);
			fm.setAction(FileAction.delete);
			this.confirm = true;
		break;
		
		case Joypad.SQUARE:
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			fm = new FileManager(this);
			fm.setAction(FileAction.copy);
			this.confirm = true;
		break;
		case Joypad.R:
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			fm = new FileManager(this);
			fm.setAction(FileAction.move);
			fm.execute(boxList.getFile(),boxList2.getFile());
			
			updateBoxs(0);
		break;
		
		case Joypad.L:
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			fm = new FileManager(this);
			fm.setAction(FileAction.move);
			fm.execute(boxList2.getFile(),boxList.getFile());
			updateBoxs(0);
		break;
		case Joypad.SELECT:
			this.option = true;
		break;
		case Joypad.START:
			
			break;
		case 0:
			updateBoxs(0);
			break;
			default:
				this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			break;
			
		}
		if (boxConfirm.onFocus) {
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			switch (keyCode) {

			case Joypad.RIGHT:
			
				boxConfirm.update(keyCode);
				break;
			case Joypad.LEFT:
				
				boxConfirm.update(keyCode);
				break;
			
			case Joypad.CIRCLE:
				
				boxConfirm.onFocus = false;
				this.confirm = false;
				break;
			case Joypad.CROSS:
				
				if (result == ResultDialog.Yes) {
					
					fm.execute(this.currentFocusFile,this.currentSelectedFile);
					updateBoxs(0);
				}
				boxConfirm.onFocus = false;
				this.confirm = false;
				

				break;

			}
		}
		else if  (boxOptions.onFocus) {
			this.menuSound.play(soundId, vol, vol, 1, 0, 1);
			switch (keyCode) {

			case Joypad.DOWN:
			
				boxOptions.update(keyCode);
				break;
			case Joypad.UP:
				
				boxOptions.update(keyCode);
				break;
			
			case Joypad.CIRCLE:
				
				boxOptions.onFocus = false;
				this.option = false;
				break;
			case Joypad.SELECT:
				
				boxOptions.onFocus = false;
				this.option = false;
				break;
			case Joypad.CROSS:
				if(boxOptions.getOptionOp() == optionOp.newfolder) {
					fileAction = FileAction.newfolder;
					bt.showMe = true;
				}
				if(boxOptions.getOptionOp() == optionOp.rename) {
					bt.setFileName(this.currentFocusFile.getName());
					fileAction = FileAction.rename;
					bt.showMe = true;
				}
				if(boxOptions.getOptionOp() == optionOp.exit) {
					//fazer
				}
				if(boxOptions.getOptionOp() == optionOp.about) {
					//fazer
					
					
				}
				boxOptions.onFocus = false;
				this.option = false;
				

			}
		}
			
		
		else{
			if(keyCode ==Joypad.CROSS){
				
				if(this.currentFocusFile.isFile())
					this.openFile(this.currentFocusFile);
			}
			updateBoxs(keyCode);
		
		}
		
		postInvalidate();
	}

	
	private void updateBoxs(int keyCode)
	{
		if(isUpdating)
			return;
		isUpdating = true;
		boxList.updateList(keyCode);
		boxList2.updateList(keyCode);
		if (boxList.getFocus()) {
			this.currentFocusFile = boxList.getFile();
			this.currentSelectedFile = boxList2.getFile();
			
		}
		if (boxList2.getFocus()) {
			this.currentFocusFile = boxList2.getFile();
			this.currentSelectedFile = boxList.getFile();
		}
		
		isUpdating = false;
	}
	
	public void loadStates()
	{
		boxList.setFile(new File(MainActivity.list1));
		boxList2.setFile(new File(MainActivity.list2));
		updateBoxs(0);
		postInvalidate();
	}
	public void saveStates()
	{
		MainActivity.list1 = boxList.getPath();
		MainActivity.list2 = boxList2.getPath();
	}
	public void appExit() {
		if(boxList.getAppExit() || boxList2.getAppExit())
			this.appExit = true;
		else
			this.appExit = false;
	}
	private void openFile(File ori) {
		
		
		Context cont = getContext();
		Intent it = new Intent();
		it.setAction(android.content.Intent.ACTION_VIEW);
		it.setDataAndType(Uri.fromFile(ori), FileManager.getType(this.currentFocusFile));
		try {
			 cont.startActivity(it);
		 
		} catch (Exception e) {
		    e.printStackTrace();
		    Log.e("erro",e.getMessage());
		   
		}
		
	}

	
}
