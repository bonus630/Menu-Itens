package br.corp.bonus630.android.keys;


import android.app.Activity;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

	KeysView kv;
	
	public static Bitmap bitmap;
	public static Typeface font;
	public static String list1 = null;
	public static String list2 = null;
	private boolean save,load;
	private SQLiteDatabase bancoDados = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	try{
		bancoDados = openOrCreateDatabase("keys",MODE_PRIVATE, null);
		bancoDados.execSQL("CREATE TABLE IF NOT EXISTS configs (id INTEGER PRIMARY KEY, list1 TEXT, list2 TEXT);");
	}catch(SQLException erro)
	{
		Log.e("banco",erro.getMessage());
	}
		
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.assets);
		font = Typeface.createFromAsset(this.getAssets(), "ChronoTrigger.ttf");
		if(MainActivity.list1 == null)
			MainActivity.list1 = Environment.getExternalStorageDirectory().getPath();
		if(MainActivity.list2 == null)
			MainActivity.list2 = Environment.getRootDirectory().getPath();
		load = true;
		kv = new KeysView(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		showKeyboardMessage();
		setContentView(kv);
		
		
	}
	private void showKeyboardMessage()
	{
		Configuration config = getResources().getConfiguration();
		if(config.navigationHidden==Configuration.NAVIGATIONHIDDEN_YES)
		{
			kv.joypad = false;
			
		}
		else
			kv.joypad = true;
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		kv.update(keyCode);
		if(kv.appExit)
			return super.onKeyUp(keyCode, event);
		else
			return false;
	}


	@Override
	protected void onDestroy() {
		kv.saveStates();
		saveDb();
		super.onDestroy();
	}

	

	@Override
	protected void onPause() {
		if(save)
		{
			kv.saveStates();
			saveDb();
		}
		
		super.onPause();
	}

	@Override
	protected void onRestart() {
		if(load)
		{
			loadDb();
			kv.loadStates();
		}
		
		super.onRestart();
	}

	@Override
	protected void onResume() {
		if(load)
		{
			loadDb();
			kv.loadStates();
		}
		
		
		showKeyboardMessage();
		super.onResume();
	}

	private void loadDb() {
		save = true;load = false;
		if(!bancoDados.isOpen())
			bancoDados = openOrCreateDatabase("keys",MODE_PRIVATE, null);
		
		try {
			Cursor c = bancoDados.query("configs", new String[]{"list1","list2"}, null, null, null, null, null);
			
			if(c.getCount()>0)
			{
				c.moveToFirst();
				MainActivity.list1 = c.getString(0);
				MainActivity.list2 = c.getString(1);
			}
			
			else
			{
				//Seto um diretorio padrão caso ainda não existir nenhum salvo
				MainActivity.list1 = Environment.getExternalStorageDirectory().getPath();
				MainActivity.list2 = Environment.getRootDirectory().getPath();
			}
		} catch (SQLException e) {
			Log.e("banco",e.getMessage());
		}
		finally
		{
			bancoDados.close();
		}
	}
	private void saveDb() {
		save = false;load = true;
		
		if(!bancoDados.isOpen())
			bancoDados = openOrCreateDatabase("keys",MODE_PRIVATE, null);
		try {
			Cursor c = bancoDados.query("configs", new String[]{"list1","list2"}, null, null, null, null, null);
			if(c.getCount()==0)
			{
				c.close();
				bancoDados.execSQL("INSERT INTO configs (list1,list2) VALUES ('"+MainActivity.list1+"','"+MainActivity.list2+"')");
			}
			
			else
			{
				c.close();
				bancoDados.execSQL("UPDATE configs SET list1='"+MainActivity.list1+"',list2='"+MainActivity.list2+"';");
			}
		} catch (SQLException e) {
			Log.e("banco",e.getMessage());
		}
		finally
		{
			bancoDados.close();
		}
		
	}

}
