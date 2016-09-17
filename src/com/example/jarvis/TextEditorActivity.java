package com.example.jarvis;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.gesture.GestureLibrary;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
//import android.support.v7.app.ActionBarActivity;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

//import com.itextpdf.text.pdf.PdfDocument;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TextEditorActivity extends Activity implements OnInitListener {
	
	RelativeLayout mylayout;
	ArrayList<String> text;
	GestureLibrary mLibrary;
	EditText ed1;
	TextView tv;
	String cp=null;
	Typeface tf;
	int clr,face,font=0;
	int bold=tf.BOLD;
	String creatf,temporary;
	private TextToSpeech tts;
	private int group1Id = 1;
	 int prev,code,creat,again=0;
	File save,fold,open;
	
	   FileInputStream fis=null;  
       DataInputStream in=null;  
       InputStreamReader isr=null;  
       BufferedReader br=null;  
        ClipboardManager clipBoard;
	String[] ques ;
	int homeId = Menu.FIRST;
	int del=0;
	int go=0;
	
	protected static final int RESULT_SPEECH = 1;
	protected static final int FILE_CHOOSER = 2;
	protected static final int MY_DATA_CHECK_CODE = 3;
	
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		String backgroundColor=intent.getStringExtra("background");
		setContentView(R.layout.activity_text_editor);
		
	 ed1=(EditText)findViewById(R.id.editTextPassword);
	 mylayout=(RelativeLayout) findViewById(R.id.relativeLayout);
	 switch (backgroundColor) {
	case "red":
		ed1.setBackgroundColor(Color.RED);
		mylayout.setBackgroundColor(Color.RED);
		break;
	case "green":
		ed1.setBackgroundColor(Color.GREEN);
		mylayout.setBackgroundColor(Color.GREEN);
		break;
		
	case "yellow":
		ed1.setBackgroundColor(Color.YELLOW);
		mylayout.setBackgroundColor(Color.YELLOW);
		break;
		
	case "blue":
		ed1.setBackgroundColor(Color.CYAN);
		mylayout.setBackgroundColor(Color.CYAN);
		break;
	default:
		break;
	}
	 tv=(TextView)findViewById(R.id.textView1);
	 ques=new String[]{"what","how","which","when","whom","whose","where","why","who","what time","how many","how much","how long"};
	 File fold=new File("/sdcard/ourdata");
	 fold.mkdir();
	clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
	 fold.setWritable(true);
	fold.setReadable(true);
	fold.setExecutable(true);
	ed1.setText("\n");
	tv.setText("New File");
	
	if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1){
	     // don't use it.
	} else {
	     // use the new API :
	     // myView.setOnDragListener(...);
	
	
ed1.setCustomSelectionActionModeCallback(new Callback() {
	
	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		 menu.add(0, 11, 0, "JARVIS").setIcon(R.drawable.speak);
		
		
		return false;
	}
	
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO Auto-generated method stub
		String id=item.getTitle().toString();
		if(id=="JARVIS")
		{
			spk();
			
		}
		return false;
	}
});
	}
	
	  Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(group1Id, homeId, homeId, "Speak!!");
		
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			Intent intent=new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent );
			return true;
		}
		switch(id)
		{
		case 1:
			
			
			spk();
		
		
		
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void spk()
	{
		
		Intent intent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
		
		Log.d("dd","dd");

		try {
			startActivityForResult(intent, RESULT_SPEECH);
			
		} catch (ActivityNotFoundException a) {
			Toast t = Toast.makeText(getApplicationContext(),
					"Ops! Your device doesn't support Speech to Text",
					Toast.LENGTH_SHORT);
			t.show();
		}
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				tts=new TextToSpeech(this, this);
		
				

				tts.setLanguage(Locale.US);
				
			} 
			else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
		
		
		   if ((requestCode == FILE_CHOOSER) && (resultCode == -1)) {
			   Bundle bundle = data.getExtras();
			   
			   String fileSelected= bundle.getString("fileSelected");
			   File f=new File(fileSelected);
		        //String fileSelected = data.getStringExtra("fileSelected");
		        Toast.makeText(this, fileSelected, Toast.LENGTH_SHORT).show();
		        
		        
		        if(del==1)
		        {
		        	if(fileSelected!=null)
			        {
			        open=new File(fileSelected);
			        }
		        	boolean deleted = open.delete();
		        	if(deleted==true)
		        	{
		        		Toast.makeText(this, fileSelected+" deleted!", Toast.LENGTH_SHORT).show();
		        	}
		        	del=0;
		        	
		        }
		        
		        if(fileSelected.contains(".pdf"))
		        {
		        	
		        	  /*BufferedReader br = null;
		              String response = null;
		              try {
		            	  FileOutputStream outputStream=new FileOutputStream(f.getAbsoluteFile());
		            	  
		            	  try
		            	  {
		            		  //outputStream=new OutputStream(fileSelected.);
		            	  PdfReader reader1 = new PdfReader(fileSelected);
		            	  PdfStamper stamper = new PdfStamper(reader1, outputStream); 
		            	  //stamper.getWriter();
		            	 
		            	  stamper.getWriter().setPdfVersion(PdfWriter.PDF_VERSION_1_5); 
		            	  stamper.close(); 
		            	  }
		            	  catch(Exception e)
		            	  {e.printStackTrace();}
		            	  
		            	  
		            	  
		                  StringBuffer output = new StringBuffer();
		                  String fpath =fileSelected;

		                  PdfReader reader = new PdfReader(new FileInputStream(fpath));
		                  PdfReaderContentParser parser = new PdfReaderContentParser(reader);

		                  StringWriter strW = new StringWriter();

		                  TextExtractionStrategy strategy;
		                  for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		                      strategy = parser.processContent(i,
		                              new SimpleTextExtractionStrategy());

		                      strW.write(strategy.getResultantText());

		                  }

		                  response = strW.toString();
		                  ed1.setText(response)
		                  Log.d("aa",response);

		              } catch (IOException e) {
		                  e.printStackTrace();
		                  
		              }
		        	
		        	
		        	
		        	
		        	
		        	*/
		        	  BufferedReader br = null;
		              String response = null;
		              try {
		                  StringBuffer output = new StringBuffer();
		                  String fpath = fileSelected;

		                  PdfReader reader = new PdfReader(new FileInputStream(fpath));
		                  PdfReaderContentParser parser = new PdfReaderContentParser(reader);

		                  StringWriter strW = new StringWriter();

		                  TextExtractionStrategy strategy;
		                  for (int i = 1; i <= reader.getNumberOfPages(); i++) {
		                      strategy = parser.processContent(i,
		                              new SimpleTextExtractionStrategy());

		                      strW.write(strategy.getResultantText());

		                  }

		                  response = strW.toString();
		                  ed1.setText(response);
		                  Log.d("asd",response);

		              } catch (IOException e) {
		                  e.printStackTrace();
		               
		              }

		       
		        	
		        }
		        
		        /*else if(fileSelected.contains(".docx"))
		        {
		        
		        	FileInputStream fis = new FileInputStream(inputFile);
		        	POIFSFileSystem fileSystem = new POIFSFileSystem(fis);
		        	// Firstly, get an extractor for the Workbook
		        	POIOLE2TextExtractor oleTextExtractor = 
		        	   ExtractorFactory.createExtractor(fileSystem);
		        	// Then a List of extractors for any embedded Excel, Word, PowerPoint
		        	// or Visio objects embedded into it.
		        	POITextExtractor[] embeddedExtractors =
		        	   ExtractorFactory.getEmbededDocsTextExtractors(oleTextExtractor);
		        	
		        	
		        	
		        }*/
		        else if(fileSelected.contains(".txt"))
		        
		        {

			         if(fileSelected!=null)
			        {
			        open=new File(fileSelected);
			       
			        StringBuilder text=new StringBuilder();
			        try
			        {
			        	BufferedReader br=new BufferedReader(new FileReader(open));
			        	String line;
			        	while ((line = br.readLine()) != null) {
			                text.append(line);
			                text.append('\n');
			        	}
			        }
			        	catch(IOException ie)
			        	{
			        		ie.printStackTrace();
			        	}
			        
			        ed1.setText("\n"+text);
			        String[] arrayOfString = fileSelected.split("/");
			        tv.setText(arrayOfString[arrayOfString.length-1].toUpperCase());
		        	
		        	
		        }
		        }
		        
		        else if(fileSelected.contains(".go"))
		        {
		        
		         if(fileSelected!=null)
		        {
		        open=new File(fileSelected);
		        
		        StringBuilder text=new StringBuilder();
		        try
		        {
		        	BufferedReader br=new BufferedReader(new FileReader(open));
		        	String line;
		        	while ((line = br.readLine()) != null) {
		                text.append(line);
		                text.append('\n');
		        	}
		        }
		        	catch(IOException ie)
		        	{
		        		ie.printStackTrace();
		        	}
		        
		        String []arr=text.toString().split(" ",4);
		        for(int i=0;i<3;i++)
		        {
		        	Log.d("asd",Integer.parseInt(arr[i])+"");
		        }
		        switch(Integer.parseInt(arr[0]))
		        {
		        case 1:

					ed1.setTextColor(Color.RED);
					clr=1;
					break;
		        case 2:
		        	
		        	ed1.setTextColor(Color.BLACK);
					clr=2;
					break;
		        case 3:
		        	ed1.setTextColor(Color.WHITE);
					clr=3;
					break;
					
		        case 4:
		        	ed1.setTextColor(Color.YELLOW);
					clr=4;
					break;
		        case 5:
		        	ed1.setTextColor(Color.BLUE);
					clr=5;
					break;
		        case 6:
		        	ed1.setTextColor(Color.GREEN);
					clr=6;
					break;
					
					default:
						break;
		        
		        
		        
		        
		        
		        
		        }
		        
		        switch(Integer.parseInt(arr[1]))
		        {
		        
		        case 1:
		        	ed1.setTypeface(null, Typeface.ITALIC);
					Toast.makeText(this, "italic", Toast.LENGTH_SHORT).show();
					face=1;
					break;
		        case 2:
		        	ed1.setTypeface(null, Typeface.BOLD);
					Toast.makeText(this, "italic", Toast.LENGTH_SHORT).show();
					face=2;
					break;
		        case 3:
		        	Spanned res =Html.fromHtml("<u>"+arr[3]+"</u>");
					ed1.setText(res);
					Toast.makeText(this, "underlined", Toast.LENGTH_SHORT).show();
					face=3;
					break;
					default:
						break;
		        	
		        
		        
		        }
		        
		        switch(Integer.parseInt(arr[2]))
		        {
		        case 1:
		        	ed1.setTypeface(Typeface.SERIF);
					font=1;
					break;
		        case 2:
		        	tf=Typeface.createFromAsset(getAssets(),"Comic Spans.otf");
					ed1.setTypeface(tf);
					Toast.makeText(this, "cmoic", Toast.LENGTH_SHORT).show();
					font=2;
					break;
		        case 3:
		        	tf=Typeface.createFromAsset(getAssets(),"pacifo.ttf");
					ed1.setTypeface(tf);
					Toast.makeText(this, "pacifo", Toast.LENGTH_SHORT).show();
					font=3;
					break;
		        case 4:
		        	tf=Typeface.createFromAsset(getAssets(),"journal.ttf");
					ed1.setTypeface(tf);
					Toast.makeText(this, "journal", Toast.LENGTH_SHORT).show();
					font=4;
					break;
					default:
						break;

		        
		        
		        
		        
		        
		        }
		        clr=0;face=0;font=0;
		        
		        
		        
		        
		        
		        
		        
		        ed1.setText("\n"+text);
		        String[] arrayOfString = fileSelected.split("/");
		        tv.setText(arrayOfString[arrayOfString.length-1].toUpperCase());
		        //ed1.setText("\r");
		        ed1.setText(arr[3]);
		        }
		        }
		        	
		        	
		        	
		        }
		switch (requestCode) {
	case RESULT_SPEECH: {
		if (resultCode == RESULT_OK && null != data) {
			Log.d("in","here");
			//ArrayList<String> text1 = data
					//.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			 text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			 int fl=0;
			 String e="";
			 if(text!=null)
			 {
			 e=text.get(0);
			 Log.d("dfdf",e);
			 
			 }
			 if(again==1)
			 {
			 if(text.contains("yes")||text.contains("yup"))
			 {
				 Log.d("aaa","aaaa");
					//Log.d("writeable",fold.canWrite()+"o");
					tts.setSpeechRate(1.1f);
					  tts.speak("By what name Master ....",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
					  creat=1;
					  /*tts.setSpeechRate(0.8f);
					  tts.speak("Save as ",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }  
					  
					  */
					 spk();
					  ed1.setText("");
						 tv.setText("New File");
						 again=0;
						 
				 
			 }
			 
			 else {
				 ed1.setText("");
				 tv.setText("New File");
				 again=0;
			 }
			 again=0;
			 }
			 if(creat==1)
			 {
				 if(e.contains("store"))
				 {
					 String pdfname="apple";
					 String []tt=e.split(" ");
					 if(tt.length>1)
					 {
						 pdfname=tt[1];
					 }
					 try
					 {
					 try{
						File myFile1 = new File("/sdcard/ourdata/"+pdfname+".pdf");
			            myFile1.createNewFile();
			            Document pdfDoc = new Document(); 
			            PdfWriter writer=PdfWriter.getInstance(pdfDoc,new FileOutputStream(myFile1));
			            
			            pdfDoc.open();  
			            pdfDoc.setMarginMirroring(true);  
			            pdfDoc.setMargins(36, 72, 108,180);  
			            pdfDoc.topMargin();  
			            Font myfont = new Font();  
			            Font bold_font = new Font();  
			            bold_font.setStyle(Font.BOLD);  
			            bold_font.setSize(10);  
			            myfont.setStyle(Font.NORMAL);  
			            myfont.setSize(10);  
			            pdfDoc.add(new Paragraph("\n"));
			            
			            
			            fis = new FileInputStream(myFile1);  
		                in = new DataInputStream(fis);  
		                isr=new InputStreamReader(in);  
		                br = new BufferedReader(isr);  
		                String strLine;  
		 
		                 
		 
		                    Paragraph para =new Paragraph(ed1.getText().toString()+"\n",myfont);  
		                    para.setAlignment(Element.ALIGN_JUSTIFIED);  
		                    pdfDoc.add(para);  
		                    
		                    pdfDoc.close();   
		                    
			        }  
			 
			        catch(Exception e3) {  
			            System.out.println("Exception: " + e3.getMessage());  
			        }  
			        finally {  
			 
			            if(br!=null)  
			            {  
			                br.close();  
			            }  
			            if(fis!=null)  
			            {  
			                fis.close();  
			            }  
			            if(in!=null)  
			            {  
			                in.close();  
			            }  
			            if(isr!=null)  
			            {  
			                isr.close();  
			            }  
			 
			        }  
			 
					 }catch(Exception e3)
					 {
						 e3.printStackTrace();
					 }
			            
			            creat=0;
			            e="";

				 }
			
				 else{
				 
				 creatf=e;
				 e="";
				 creat=0;
				 
				 try
					{
					 String t=".txt";
					 if(font!=0||clr!=0||face!=0)
					 {
						 t=".go";
						 
					 }
						Toast.makeText(this, "save "+creatf, Toast.LENGTH_LONG).show();
						tv.setText(creatf);
					File myFile = new File("/sdcard/ourdata/"+creatf+t);
		            myFile.createNewFile();
		            
		           String tt=String.valueOf(clr)+" "+String.valueOf(face)+" "+String.valueOf(font)+" "+ed1.getText().toString();
		           
		          
		            
		            FileOutputStream fOut = new FileOutputStream(myFile);
		            OutputStreamWriter myOutWriter = 
		                                    new OutputStreamWriter(fOut);
		            
		            myOutWriter.append(tt);
		            myOutWriter.close();
		            fOut.close();
		            
		      
		            
					}
					catch(Exception e11)
					{
						e11.printStackTrace();
					}
				 }
				 
			 }
			 for(int i=0;i<ques.length;i++)
				 
			 {
				 Log.d("ff","ff");
				 
				 if(e.contains(ques[i]))
				 {
					 e=e+"? ";
					
					 fl=1;
					 break;
				 }
				 
			 }
			
			 if(fl==0 && e!="")
			 {
				 e=e+". ";
			 }
			 
			 
			 
			        if(e!="")
			        {
				String output = Character.toUpperCase(e.charAt(0)) + e.substring(1);
				e=output;
			        }
			     
				
			
			
			
			if(e.toLowerCase().contains("jarvis")||(e.toLowerCase().contains("ja")&&e.toLowerCase().contains("v")&&e.toLowerCase().contains("r")))
			{
				code=1;
				Log.d("asd",e);
				String[] x = e.split("\\s");
				if(x.length>1)
				Log.d("aa","l  "+x[1]);
				
				if(e.toLowerCase().contains("save"))
				{
					
					Log.d("aaa","aaaa");
					//Log.d("writeable",fold.canWrite()+"o");
					tts.setSpeechRate(1.1f);
					  tts.speak("By what name Master ....",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
					  creat=1;
					  /*tts.setSpeechRate(0.8f);
					  tts.speak("Save as ",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }  
					  
					  */
					  spk();
					
				}
				else if(e.toLowerCase().contains("delete"))
				{
					tts.setSpeechRate(1.1f);
					  tts.speak("Opening File System  Master to delete files",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
					  del=1;
					Intent intent = new Intent(this, FileChooser.class);
					ArrayList<String> extensions = new ArrayList<String>();
					extensions.add(".pdf");
					extensions.add(".txt");
					extensions.add(".docx");
					extensions.add(".go");
					intent.putStringArrayListExtra("filterFileExtension", extensions);
					startActivityForResult(intent, FILE_CHOOSER);
					
					
					
				}
				else if(e.toLowerCase().contains("open"))
				{
					tts.setSpeechRate(1.1f);
					  tts.speak("Opening File System  Master ....",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
					Intent intent = new Intent(this, FileChooser.class);
					ArrayList<String> extensions = new ArrayList<String>();
					extensions.add(".pdf");
					extensions.add(".txt");
					extensions.add(".docx");
					extensions.add(".go");
					intent.putStringArrayListExtra("filterFileExtension", extensions);
					startActivityForResult(intent, FILE_CHOOSER);
				}
				else if(e.toLowerCase().contains("undo") || e.toLowerCase().contains("and do"))
				{
					String[] y = ed1.getText().toString().split("\\s");
					String tt="";
					for(int i=0;i<y.length-1;i++)
					{
						tt=tt+" "+y[i];
					}
					ed1.setText(tt);
				
				}
				
				else if(e.toLowerCase().contains("clear")|| e.toLowerCase().contains("no"))
				{
					ed1.setText("");
					
				}
				
				else if(e.toLowerCase().contains("copy"))
				{
					if(ed1.getText().toString()!=null)
					{
					clipBoard.setText(ed1.getText().toString().substring(ed1.getSelectionStart(), ed1.getSelectionEnd()));
					}
					//cp=ed1.getText().toString().substring(ed1.getSelectionStart(), ed1.getSelectionEnd());
					Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show();
				}
				
				else if(e.toLowerCase().contains("paste"))
				{
					if(ed1.getText().toString()!=null)
					{
					ed1.append(clipBoard.getText());
					}
					/*if(cp!=null)
					{
						ed1.append(" "+cp);
					}
					else
					{
						ed1.append(" "+cp);

					}
					*/
					Toast.makeText(this, "pasted", Toast.LENGTH_SHORT).show();

					
				}
				else if(e.toLowerCase().contains("all"))
				{
					if(ed1.getText().toString()!=null)
					{
				
					 tts.setSpeechRate(1.1f);
					  tts.speak("You have typed so far .."+ed1.getText().toString(),TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
					}
					else
					{
						 tts.setSpeechRate(1.1f);
						  tts.speak("Nothing there to recite",TextToSpeech.QUEUE_ADD,null);
						  while(tts.isSpeaking())
						  {
							 
						  }
						
						
					}
				}
				else if(e.toLowerCase().contains("appless"))
				{
					 tts.setSpeechRate(1.1f);
					  tts.speak("I am here today thanks to the efforts of Omkar and Lele",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }

			
					
				}
				
				else if(e.toLowerCase().contains("search"))
				{
					String[] tp=e.split(" ");
					String ett=tp[tp.length-1];
					ett=ett.replace(".", "");
					Log.d("asf",ett);
					/*if(ett.contains("."))
							{
					
								ett=[ett.indexOf(".")-1];
						
							}*/
					Toast.makeText(this,"Searching "+ett+" ....",Toast.LENGTH_SHORT).show();
					
					

	                int ofe = ed1.getText().toString().toLowerCase().indexOf(ett,0);   
	                Log.d("asdfg",ofe+"");
	                Spannable WordtoSpan = new SpannableString( ed1.getText() );
	                int gps=0;
	        for(int ofs=0;ofs<ed1.getText().toString().length() && ofe!=-1;ofs=ofe+1)
	        {       
	        	

	              ofe = ed1.getText().toString().toLowerCase().indexOf(ett,ofs);   
	                  if(ofe == -1)
	                  {
	                	  
						  break;
						  
	                	  
	                  }
	                      
	                  else
	                      {                       
	  	                Log.d("asdfg","yo"+"");
	  	                gps=1;
	                      WordtoSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), ofe, ofe+ett.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                      ed1.setText(WordtoSpan, TextView.BufferType.SPANNABLE);
	                      gps=1;
	                      }


	        }  
	        if(gps==0)
	        {
	        tts.setSpeechRate(1.1f);
			  tts.speak("No such word exists",TextToSpeech.QUEUE_ADD,null);
			  while(tts.isSpeaking())
			  {
				 
			  }
	        }
					
					
					
				}
				
				else if(e.toLowerCase().contains("colour"))
				{
					
					if(e.toLowerCase().contains("red"))
					{
						
						ed1.setTextColor(Color.RED);
						clr=1;
					
						
					}
					
					if(e.toLowerCase().contains("black"))
					{
						
						ed1.setTextColor(Color.BLACK);
						clr=2;
						
					}
					
					if(e.toLowerCase().contains("white"))
					{
						
						ed1.setTextColor(Color.WHITE);
						clr=3;
						try
						{
							
							  
							
							
							
							
							
							
							
							
						/*Document doc = new Document();
					    PdfWriter.getInstance(doc, new FileOutputStream("urgentz.pdf"));
					    doc.open();
					   // Image image = Image.getInstance ("urgentzImageahslkdhaosd.jpg");
					    doc.add(new Paragraph("Your text blah bleh"));
					    //doc.add(image);               
					    doc.close();*/
						}
						catch(Exception e1)
						{e1.printStackTrace();}
						
					}
					if(e.toLowerCase().contains("yellow"))
					{
						
						ed1.setTextColor(Color.YELLOW);
						clr=4;
						
					}
					if(e.toLowerCase().contains("blue"))
					{
						
						ed1.setTextColor(Color.BLUE);
						clr=5;

					}
					if(e.toLowerCase().contains("green"))
					{
						
						ed1.setTextColor(Color.GREEN);
						clr=6;

						
					}
					
				}
				
				else if(e.toLowerCase().contains("cut"))
				{
					cp=ed1.getText().toString().substring(0,ed1.getSelectionStart());
					Toast.makeText(this, "cut", Toast.LENGTH_SHORT).show();
					ed1.setText(cp);


					
				}
				
				
				else if(e.toLowerCase().contains("buy")||e.toLowerCase().contains("bye") || e.toLowerCase().contains("by"))
				{
					tts.setSpeechRate(1.1f);
					  tts.speak("Thankyou  visit again ",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
					finish();
				}
				
				else if(e.toLowerCase().contains("new"))
				{
					Log.d("yo","asdf");
					tts.setSpeechRate(1.1f);
					  tts.speak("Should I save this file? ",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
						Log.d("yo","asdf");

					  spk();
					  again=1;

					
				}
				
				
				else if(e.toLowerCase().contains("set")||e.toLowerCase().contains("style")||e.toLowerCase().contains("font"))
				{
					if(e.toLowerCase().contains("serif"))
					{
						ed1.setTypeface(Typeface.SERIF);
						font=1;
					
						Toast.makeText(this, "serif", Toast.LENGTH_SHORT).show();
					}
					else if(e.toLowerCase().contains("comic"))
					{
						tf=Typeface.createFromAsset(getAssets(),"Comic Spans.otf");
						ed1.setTypeface(tf);
						Toast.makeText(this, "cmoic", Toast.LENGTH_SHORT).show();
						font=2;

						
					}
					else if(e.toLowerCase().contains("pacific"))
					{
						tf=Typeface.createFromAsset(getAssets(),"Pacifo.ttf");
						ed1.setTypeface(tf);
						Toast.makeText(this, "pacifo", Toast.LENGTH_SHORT).show();
						font=3;


					}
					else if(e.toLowerCase().contains("journal"))
					{
						tf=Typeface.createFromAsset(getAssets(),"journal.ttf");
						ed1.setTypeface(tf);
						Toast.makeText(this, "journal", Toast.LENGTH_SHORT).show();
						font=4;

					}
					
					else if(e.toLowerCase().contains("italic"))
					{
						
						
						ed1.setTypeface(null, Typeface.ITALIC);
						Toast.makeText(this, "italic", Toast.LENGTH_SHORT).show();
						face=1;


					}
					else if(e.toLowerCase().contains("bold"))
					{
						
						
						ed1.setTypeface(null, Typeface.BOLD);
						Toast.makeText(this, "bold", Toast.LENGTH_SHORT).show();
						face=2;

						

					}
					else if(e.toLowerCase().contains("under"))
					{
						
						Spanned res =Html.fromHtml("<u>"+ed1.getText()+"</u>");
						ed1.setText(res);
						Toast.makeText(this, "underlined", Toast.LENGTH_SHORT).show();
						face=3;

					

						
					}
					
					
					
					else
					{
						tts.speak("Please repeat master",TextToSpeech.QUEUE_ADD,null);
						  while(tts.isSpeaking())
						  {
							 
						  }
						
					}
					
					
				}
				else
				{
					
					tts.setSpeechRate(1.1f);
					  tts.speak("Please repeat master ",TextToSpeech.QUEUE_ADD,null);
					  while(tts.isSpeaking())
					  {
						 
					  }
					
				}
				
			
			}
			if(code==0)
			{
				e=" "+e;
			ed1.append(e);
			
			}
			code=0;
		
		
		}
		
}
	
}
		   
		
	}
	
	
	public void onInit(int status) {		
		if (status == TextToSpeech.SUCCESS) {
		
			Toast.makeText(this, 
					"Text-To-Speech engine is initialized", Toast.LENGTH_SHORT).show();
			float y=(float)0.75;
			Log.d("ss",y+"");
			tts.setSpeechRate(1.1f);
			tts.setPitch(0.6f);
		
			tts.speak(" Hello master ", TextToSpeech.QUEUE_ADD, null);
			
		}
		else if (status == TextToSpeech.ERROR) {
			Toast.makeText(this, 
					"Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
		}
	}
	
	
	public void onDestroy()
	{
		if(tts!=null)
		{
		tts.stop();
		tts.shutdown();
		}
		super.onDestroy();
		
	
	}

	public void onPause()
	{
		if(tts!=null)
		{
		tts.stop();
		
	
		}
		super.onPause();
	}


}