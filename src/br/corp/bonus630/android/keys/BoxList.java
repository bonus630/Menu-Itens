package br.corp.bonus630.android.keys;

import java.io.File;
import java.util.ArrayList;


import android.graphics.Canvas;

import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

public class BoxList extends BoxLayout {

	int gridSizeY, numItems;
	int index = 0;
	private ArrayList<Rect> rectsList;
	ArrayList<ItemView> ItemList;
	//precisamos arrumar isso aqui
	ArrayList<File> fileHistory,listFiles;
	ArrayList<Integer> indexHistory;
	private boolean appExit = true;
	int left, top, rigth, bottom;
	

	public BoxList(int width, int height, int left, int top,File initialDir) {
		super(width, height, left, top);
		this.rigth = left + width;
		this.bottom = top + height;
		this.top = top;
		this.left = left;
		fileHistory = new ArrayList<File>();
		indexHistory = new ArrayList<Integer>();
		this.fillFileHistory(initialDir);
		generateRectsList();
		this.fillList();
		updateItens();
	}

	private void fillFileHistory(File initialDir) {
		ArrayList<File> tempArray = new ArrayList<File>();
		while(initialDir!=null)
		{
			tempArray.add(initialDir);
			initialDir = initialDir.getParentFile();
		}
		for(int i = tempArray.size()-1;i>0;i--)
		{
			this.setFile(tempArray.get(i));
		}
	}

	private void generateRectsList() {
		this.gridSizeY = 10;
		int left = this.left + 10;
		int top = this.top + 10;
		rectsList = new ArrayList<Rect>();
		for (int i = 0; i < this.gridSizeY; i++) {
			rectsList.add(new Rect(left, top + i * ItemView.getItemHeight(),
					this.rigth, top + (i + 1) * ItemView.getItemHeight()));
		}

	}

	public boolean setFile(File file) {
		//precisamos arrumar isso aqui
		if (file.isDirectory() && !file.equals(fileHistory.size() - 1)) {
			fileHistory.add(file);
			appExit = false;
			return true;
		}
		return false;
	}
	
	public void setFocus() {
		this.onFocus = true;

	}

	public void removeFocus() {
		this.onFocus = false;

	}

	public boolean getFocus() {
		return this.onFocus;
	}

	private boolean removeFile() {
		//precisamos arrumar isso aqui
		if (fileHistory.size() > 1) {
			fileHistory.remove(fileHistory.size() - 1);
			return true;
		} else
			appExit = true;
		
		return false;
	}

	private void setParentIndex(int index)
	{
		indexHistory.add(index);
	}
	private int getParentIndex() {
		if(indexHistory == null || indexHistory.isEmpty())
			return 0;
		if (indexHistory.size() > 1) {
			int retur = indexHistory.get(indexHistory.size()-1);
			indexHistory.remove(indexHistory.size() - 1);
			return retur;
		}
		return indexHistory.get(0);
		
		 
	}

	

	public boolean getAppExit() {
		return this.appExit;
	}

	protected void fillList() {
		File file = fileHistory.get(fileHistory.size() - 1);
		
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			numItems = files.length;
			this.listFiles = new ArrayList<File>();
					
					for (int i = 0; i < numItems; i++) {
						if(files[i].canRead())
							this.listFiles.add(files[i]);
					}
			
			
			numItems = this.listFiles.size();
			if (ItemList == null) {
				this.ItemList = new ArrayList<ItemView>();
			}
			ItemList.clear();
				for (int i = 0; i < numItems; i++) {
					
					this.ItemList.add(new ItemView(this.listFiles.get(i), i));

				}
				
			}

		
		
		if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
			Log.e("if", "nunca chega aqui!!!");

		} else {

			Log.e("if", Environment.getExternalStorageState() + " - "
					+ Environment.MEDIA_MOUNTED);
		}
		
	}

	public void updateList(int keyCode) {
		if(keyCode == 0)
			this.fillList();
			this.updateItens();
		if(!this.onFocus)
			return;
		

		if (this.onFocus)
		
			this.updateF(keyCode);
		this.updateItens();
	}

	private void updateF(int keyCode) {
		switch (keyCode) {

		case Joypad.DOWN:

			index++;
			break;
		case Joypad.UP:

			index--;
		
			break;

		case Joypad.CROSS:
			if (setFile(this.ItemList.get(index).getFile())) {
				setParentIndex(index);
				this.fillList();
				index = 0;
			} else {
				processFile();
			}
			break;
		case Joypad.CIRCLE:
						
			if (removeFile()) {
				
				this.fillList();
				index = this.getParentIndex();
				
			}

			break;
	
		}
		if (index < 0)
			index = 0;
		if(numItems !=0){
		if (index > numItems - 1)
			index = numItems - 1;
		}

	}

	public File getFile() {
		if (this.ItemList.isEmpty()||this.ItemList ==null) {
			if (fileHistory.size() > 1) {
				return fileHistory.get(fileHistory.size() - 1);
			}
		}
		//Isto está gerando um erro 
		try{
			return ItemList.get(this.index).getFile();
		}
		catch(ArrayIndexOutOfBoundsException erro){
			Log.e("Array",index+" - "+erro.getMessage());
			return ItemList.get(0).getFile();}

	}
	
	
	public String getPath(){
		return this.getFile().getParent();
	}
	
	private void processFile() {

	}

	protected void updateItens() {
		if (!this.ItemList.isEmpty()) {
			for (ItemView item : this.ItemList) {

				item.update(index, this.onFocus);

			}
		}
	
	}

	@Override
	protected void draw(Canvas canvas) {
		super.draw(canvas);
		
		if (!this.ItemList.isEmpty()) {
			int j = this.gridSizeY - 1;
			for (int i = 0; i < this.gridSizeY; i++) {
				if (index > this.gridSizeY - 1) {
					this.ItemList.get(index - j).draw(rectsList.get(i), canvas);
				} else {
					if (i < this.ItemList.size())
						this.ItemList.get(i).draw(rectsList.get(i), canvas);
				}
				j--;
			}
			if (this.gridSizeY < this.ItemList.size() && index < this.ItemList.size() - 1 && this.onFocus)
				canvas.drawBitmap(
						MainActivity.bitmap,
						AssetPosition.ARROW_DOWN,
						new Rect(this.rigth - AssetPosition.ARROW_DOWN.width()
								- 4, this.top + 30, this.rigth
								+ AssetPosition.ARROW_DOWN.width() - 4,
								this.top + 30
										+ AssetPosition.ARROW_DOWN.height() * 2),
						null);
			if (index > this.gridSizeY && this.onFocus)
				canvas.drawBitmap(MainActivity.bitmap, AssetPosition.ARROW_UP,
						new Rect(this.rigth - AssetPosition.ARROW_UP.width()
								- 4, this.top + 10, this.rigth
								+ AssetPosition.ARROW_UP.width() - 4, this.top
								+ 10 + AssetPosition.ARROW_UP.height() * 2),
						null);

		} else {
			

			canvas.drawText("Folder Empty", this.left + 30, this.top + 30, this.paintFont);
		}
	}

}
