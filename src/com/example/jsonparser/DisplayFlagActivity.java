package com.example.jsonparser;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class DisplayFlagActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_display_flag);
		setContentView(new CanvasDrawer(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_flag, menu);
		return true;
	}

	private class CanvasDrawer extends View {
		
		Paint paint = new Paint();

		public CanvasDrawer(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onDraw(Canvas canvas) {
			paint.setColor(Color.RED);
			paint.setStrokeWidth(0);
			canvas.drawRect(0, 0, 150, 150, paint);
			canvas.drawRect(300, 0, 550, 150, paint);
			canvas.drawRect(0, 300, 150, 450, paint);
			canvas.drawRect(300, 300, 550, 450, paint);
			paint.setColor(Color.WHITE);
			canvas.drawRect(150, 0, 200, 200, paint);
			canvas.drawRect(0, 250, 150, 300, paint);
			canvas.drawRect(300, 250, 550, 300, paint);
			canvas.drawRect(150, 250, 200, 450, paint);
			canvas.drawRect(250, 250, 300, 450, paint);
			canvas.drawRect(250, 0, 300, 200, paint);
			canvas.drawRect(0, 150, 150, 200, paint);
			canvas.drawRect(300, 150, 550, 200, paint);
			paint.setColor(Color.BLUE);
			canvas.drawRect(200, 0, 250, 450, paint);
			canvas.drawRect(0, 200, 550, 250, paint);
			
			
		}
	}
}
