import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import java.text.*;
import java.util.*;

public class Staff extends Shell {
	private Table table;
	SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
	int rowCount = 3;
	int columnCount = 5;
	int tableStartValue = 0; // Wenn man "Next-Button" drückt, muss diese
								// Variable hochgezaehlt werden
	private long databaseTime; //!!!

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Staff shell = new Staff(display);
			
			/**GLEICHE FUNKTIONALITAET DES NEXT-BUTTONS AUF DER LEERTASTE***/
			shell.addKeyListener(new KeyListener() {
	            public void keyPressed(KeyEvent event) {
	                //switch (event.keyCode) {
	                //case SWT.SPACE:
	            	if (event.keyCode == SWT.SPACE)
	                  System.out.println("Space gedrueckt!");
	                //case SWT.ESC:
	                //  System.out.println(SWT.ESC);
	                //  break;
	                //}
	              }
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
				}
	          });
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Staff(Display display) {
		super(display, SWT.SHELL_TRIM);
		setMinimumSize(new Point(730, 560));
		FormLayout formLayout = new FormLayout();
		formLayout.marginBottom = 1;
		formLayout.marginHeight = 1;
		formLayout.marginLeft = 1;
		formLayout.marginRight = 1;
		formLayout.marginTop = 1;
		formLayout.spacing = 1;
		setLayout(formLayout);
		
		//Open Connection to database
		DBConnection dbconnection = new DBConnection();

		
		Image Editor_back = new Image(display, 										//<- .....
			    Login.class.getResourceAsStream(
			      "Editor_Backdrop.jpg"));	
		Image IKS = new Image(display, 										//<- .....
			    Login.class.getResourceAsStream(
			      "IKS.jpg"));	
		Image Running_btn = new Image(display, 										//<- .....
			    Login.class.getResourceAsStream(
			      "Running_btn.jpg"));	
		Image Time_btn = new Image(display, 										//<- .....
			    Login.class.getResourceAsStream(
			      "Time_btn.jpg"));	
		
		
		this.setBackgroundMode(SWT.INHERIT_FORCE);
		this.setBackgroundImage(Editor_back);
		this.setImage(IKS);
		
		
		
		
		Composite Time_comp = new Composite(this, SWT.NONE);
		Time_comp.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_Time_comp = new FormData();
		fd_Time_comp.right = new FormAttachment(0, 231);
		fd_Time_comp.bottom = new FormAttachment(0, 145);
		fd_Time_comp.top = new FormAttachment(0, 14);
		fd_Time_comp.left = new FormAttachment(0, 11);
		Time_comp.setLayoutData(fd_Time_comp);
		
		Composite Running_comp = new Composite(this, SWT.NONE);
		Running_comp.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_Running_comp = new FormData();
		fd_Running_comp.top = new FormAttachment(Time_comp, 0, SWT.TOP);
		fd_Running_comp.left = new FormAttachment(Time_comp, 26);
		fd_Running_comp.right = new FormAttachment(0, 506);
		fd_Running_comp.bottom = new FormAttachment(0, 144);
		Running_comp.setLayoutData(fd_Running_comp);
		
		/***ZAEHLER-BUTTON***/
		Button Runningstamp_button = new Button(Running_comp, SWT.NONE);
		dbconnection.timerConnection();
		databaseTime = dbconnection.serverTime.getTime();
			
		display.getDefault().syncExec(new Runnable() {


				public void run() {

					long timeDifference = System.currentTimeMillis()-databaseTime;
					Date anzeigeDate = new Date(timeDifference);
					anzeigeDate.setHours(anzeigeDate.getHours()-1); //Eine Stunde abziehen, die aus mir unbekannten Gründen automatisch gesetzt ist
					Runningstamp_button.setText(hms.format(anzeigeDate)); //Ausgabe auf Label
					display.getDefault().timerExec(1000, this);
				}
			});

		Runningstamp_button.setBackgroundImage(Running_btn); 							// RUNNING BACKDROP

		Composite Buttons_comp = new Composite(this, SWT.NONE);
		FillLayout fl_Buttons_comp = new FillLayout(SWT.HORIZONTAL);
		fl_Buttons_comp.marginHeight = 20;
		fl_Buttons_comp.spacing = 20;
		fl_Buttons_comp.marginWidth = 10;
		Buttons_comp.setLayout(fl_Buttons_comp);
		FormData fd_Buttons_comp = new FormData();
		fd_Buttons_comp.top = new FormAttachment(Time_comp, 21);
		fd_Buttons_comp.right = new FormAttachment(0, 508);
		fd_Buttons_comp.left = new FormAttachment(0, 13);
		Buttons_comp.setLayoutData(fd_Buttons_comp);
		
		Composite Tabel_comp = new Composite(this, SWT.NONE);
		fd_Buttons_comp.bottom = new FormAttachment(Tabel_comp, -23);
		Tabel_comp.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		
		Button Save_button = new Button(Buttons_comp, SWT.NONE);
		Save_button.setText("SPEICHERN");
		
		Button Next_button = new Button(Buttons_comp, SWT.NONE);
		Next_button.setText("WEITER");
		
		
		/*** NEXT-BUTTON ***/
		Next_button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				dbconnection.timerConnection();
				dbconnection.getCurtime(); //Holt die aktuelle Zeit
				dbconnection.getBeitragsZeit();
				java.util.Date stopp = new Date();
				java.util.Date start = new Date();
				try {
					start = hms.parse(dbconnection.currentTime);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String temporaryString = hms.format(stopp);
				try {
					stopp = hms.parse(temporaryString);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				long ueberlauf = stopp.getTime()-start.getTime();
				ueberlauf /= 1000; //ueberlauf in Sekunden umrechnen
				System.out.println("ueberlauf: " + ueberlauf);
				if(ueberlauf > dbconnection.serverBeitragsZeit.getSeconds())
					System.out.println("Zeitüberschreitung um " + ((int) ueberlauf - dbconnection.serverBeitragsZeit.getSeconds()) + " Sekunden");
				dbconnection.setTime();
				dbconnection.timerConnection();
     		   databaseTime = dbconnection.serverTime.getTime();
				dbconnection.deleteFirstRow();
				//lösche oberste Zeile aus dem Table, wenn keine bestimmte Zeile angewählt ist
				table.remove(table.getTopIndex());
				
				//wenn bestimmte zeile im Table angewählt, so lösche diese im Table
				if(table.getSelectionIndices () != null)
					table.remove(table.getSelectionIndices ());				
			}
		});
		 
		/***LAYOUT***/
		Tabel_comp.setLayout(new FillLayout(SWT.HORIZONTAL));
		FormData fd_Tabel_comp = new FormData();
		fd_Tabel_comp.left = new FormAttachment(Time_comp, 0, SWT.LEFT);

		
		/*** ZEIT-BUTTON ***/
		Button Timestamp_button = new Button(Time_comp, SWT.NONE);
		// Zeit vom Server holen
		display.getDefault().syncExec( new Runnable() {

			public void run() {
				Timestamp_button.setText(hms.format(dbconnection.serverTime));
				display.getDefault().timerExec(1000, this);
			}
		});

		
		Timestamp_button.setBackgroundImage(Time_btn);						//  &ASEFASD
		
		FontData[] fD2 = Timestamp_button.getFont().getFontData();
		fD2[0].setHeight(30);
		fD2[0].setStyle(SWT.BOLD);	
		
		Timestamp_button.setFont( new Font(display,fD2[0]));
		Runningstamp_button.setFont( new Font(display,fD2[0]));
		
		
		fd_Tabel_comp.right = new FormAttachment(100, -79);
		fd_Tabel_comp.bottom = new FormAttachment(100);
		fd_Tabel_comp.top = new FormAttachment(0, 290);
		Tabel_comp.setLayoutData(fd_Tabel_comp);
		
		table = new Table(Tabel_comp, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI); //!!!
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn Column_position = new TableColumn(table, SWT.CENTER);
		Column_position.setWidth(100);
		Column_position.setText("Position");
		
		TableColumn Column_inhalt = new TableColumn(table, SWT.NONE);
		Column_inhalt.setWidth(100);
		Column_inhalt.setText("Inhalt");
		
		TableColumn Column_typ = new TableColumn(table, SWT.CENTER);
		Column_typ.setWidth(100);
		Column_typ.setText("Typ");
		
		TableColumn Column_dauer = new TableColumn(table, SWT.CENTER);
		Column_dauer.setWidth(100);
		Column_dauer.setText("Dauer");
		/*
		TableColumn Column_echtzeit = new TableColumn(editor_table, SWT.CENTER);
		Column_echtzeit.setWidth(100);
		Column_echtzeit.setText("Echtzeit");
		*/
		TableColumn Column_notes = new TableColumn(table, SWT.CENTER);
		Column_notes.setMoveable(true);
		Column_notes.setWidth(100);
		Column_notes.setText("Notes");
		createContents();
		
		/*** TABLE-ITEMS ***/
		dbconnection.db_query("SELECT * FROM daten");

		for (int i = 0; i < rowCount; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			for (int j = 0; j < columnCount; j++) {
				item.setText(j, dbconnection.dbinhalt[i][j]);
			}
		}
	}


	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Staff Modus");
		setSize(733, 569);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}