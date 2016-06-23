package br.corp.bonus630.android.keys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

import android.util.Log;



public class FileManager extends AsyncTask<File, Integer, Boolean> {

	public enum FileAction {
		num, copy, delete, move, rename, newfolder
	}

	private FileAction action;
	private KeysView uiView;
	private String actionText = "";

	public FileManager(KeysView uiView)
	{
		this.uiView = uiView;
		
	}
	public void setAction(FileAction action) {
		this.action = action;
	}

	public FileAction getAction() {
		return this.action;
	}

	private File renameFile(File ori, File dest) {
		int i = 2;
		File copy = new File(dest.getPath() + File.separatorChar
				+ ori.getName());
		while (copy.exists()) {
			if (ori.isDirectory()) {
				copy = new File(dest.getPath() + File.separatorChar
						+ ori.getName() + "(" + i + ")");
			}
			if (ori.isFile()) {
				String oriName = ori.getName();
				copy = new File(dest.getPath()
						+ File.separatorChar
						+ oriName.substring(0, oriName.lastIndexOf("."))
						+ "("
						+ i
						+ ")"
						+ oriName.substring(oriName.lastIndexOf("."),
								oriName.length()));
			}
			i++;
		}

		return copy;
	}
	
	private boolean copyFile(File ori,File dest) throws IOException
	{
		try {
			InputStream i = new FileInputStream(ori);

			OutputStream o = new FileOutputStream(this.renameFile(ori,
					dest));

			byte[] b = new byte[1024];
			int leng;
			while ((leng = i.read(b)) > 0)
				o.write(b, 0, leng);
			i.close();
			o.close();
			return true;
		} catch (FileNotFoundException e) {
			Log.e("io", e.getMessage());
			return false;
		}
	}
	
	private void copyDirectory(File ori,File dest) throws IOException
	{
		File nDir = new File(dest.getPath()
				+ File.separatorChar
				+ ori.getName());
		nDir.mkdir();
		
		//fazer a copiar de todos os filhos dos subdiretorios
		
		File[] itens = ori.listFiles();
		for (File item : itens) {
			if(item.isFile())
			{
				copyFile(item, nDir);
			}
			if(item.isDirectory())
			{
				copyDirectory(item,nDir);
			}
		}
		
	}
	private boolean process(File[] file) throws IOException {
		boolean r = false;
		actionText = ""; 
		if (this.action == FileAction.copy) {
			File ori = file[0];
			File dest = file[1];
			if (dest.isFile())
				dest = dest.getParentFile();
			if (ori.isFile()) {
				return this.copyFile(ori, dest);
			}
			if(ori.isDirectory())
			{
				copyDirectory(ori,dest);
			}
			actionText = "Copy: "; 
		}
		
		if (this.action == FileAction.move) {

			try {
				File ori = file[0];
				File dest = file[1];
				Log.e("file",
						dest.getParent() + File.separatorChar + ori.getName());
				if (dest.isDirectory())
					ori.renameTo(new File(dest.getPath() + File.separatorChar
							+ ori.getName()));
				if (dest.isFile())
					ori.renameTo(new File(dest.getParent() + File.separatorChar
							+ ori.getName()));
				
				actionText = "Move: "; 
				r = true;
			} catch (Exception e) {
				Log.e("io", e.getMessage());
			}

		}
		if (this.action == FileAction.delete) {

			try {
				File ori = file[0];
				if (ori.isFile())
					ori.delete();
				if(ori.isDirectory())
					this.deleteDirectory(ori);
				actionText = "Delete: "; 
				r = true;
			} catch (Exception e) {
				Log.e("io", e.getMessage());
			}

		}
		if (this.action == FileAction.newfolder) {

			try {
				File ori = file[0];
				File dest = file[1];
				if (dest.isFile())
					dest = dest.getParentFile();
				actionText = "New Folder: "; 
				r = this.newFolder(ori, dest);
				
			} catch (Exception e) {
				Log.e("io", e.getMessage());
			}

		}
		if (this.action == FileAction.rename) {

			try {
				File dest = file[0];
				File ori = file[1];
				Log.e("io", ori.getPath());
				Log.e("io", ori.getPath().substring(0,ori.getPath().lastIndexOf("/")));
				if(ori.isDirectory())
					ori.renameTo(new File(ori.getPath().substring(0,ori.getPath().lastIndexOf("/")+1)+dest.getPath()));
				if(ori.isFile())
					ori.renameTo(new File(ori.getPath()+File.separatorChar+dest.getName()));
				
				actionText = "Rename: "; 
				r = true;
			} catch (Exception e) {
				Log.e("io", e.getMessage());
			}

		}
		this.action = FileAction.num;
		
		return r;
	}


	private void deleteDirectory(File ori) {
		File[] itens = ori.listFiles();
		for (File item : itens) {
			if(item.isFile())
				item.delete();
			if(item.isDirectory())
			{
				deleteDirectory(item);
				item.delete();
			}
		}
		ori.delete();
		
	}
	private boolean newFolder(File ori,File dest)
	{
	
		File nDir = this.renameFile(ori, dest);
		return nDir.mkdir();
	}
	
	@Override
	protected Boolean doInBackground(File... params) {

		boolean r = false;
		
		try {
			r = this.process(params);
		} catch (IOException e) {
			Log.e("Ex", e.getMessage() + "-" + e.getLocalizedMessage());

		}

		
		return r;
	}

	@Override
	protected void onPostExecute(Boolean result) {
	
		String resultS = "";
		if(result)
			resultS = "Ok!";
		if(!result)
			resultS = "Fails!";
		BoxConfig.result = actionText+resultS;
		uiView.update(0);
		//Atualizar a ui aqui
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	public static String getType(File ori)
	{
		String type = null;
	    try {
	        @SuppressWarnings("deprecation")
			URL u = ori.toURL();
	        URLConnection uc = null;
	        uc = u.openConnection();
	        type = uc.getContentType();
	        Log.e("type",type);
	    } catch (Exception e) {
	    	Log.e("erro",e.getMessage());
	        e.printStackTrace();
	    }
	    return type;
	}

}
