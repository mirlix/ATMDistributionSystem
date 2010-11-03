package infcup.gui;

import infcup.City;
import infcup.Point;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class Main  {
	
	private String inputFile;
	private Shell shell;
	private Canvas canvas;
	
	private City city;
	
	public static void main(String[] args) {
		Main m = new Main();
		m.openGUI();
	}
	
	public void createMenus() {
		Menu menuBar = new Menu(shell, SWT.BAR);
	    
	    MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText("&File");

	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);
	    
	    MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
	    openItem.setText("&Open");
	    openItem.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected ( SelectionEvent e ) {
	    		 FileDialog fd = new FileDialog(shell, SWT.OPEN);
	    	        fd.setText("Open");
	    	        fd.setFilterPath("C:/");
	    	        String[] filterExt = { "*.txt", "*.doc", ".rtf", "*.*" };
	    	        fd.setFilterExtensions(filterExt);
	    	        inputFile = fd.open();
	    	        run();
	    	}
		});
	    
	    shell.setMenuBar(menuBar);
	}
	

	
	public void openGUI() {
		 	Display display = new Display();
		    shell = new Shell(display);
		    shell.setText("Drawing Example");

		    canvas = new Canvas(shell, SWT.NONE);
		    canvas.setSize(150, 150);
		    canvas.setLocation(20, 20);
		    
		    shell.open();
		    shell.setSize(200, 220);
		    
		    createMenus();
			

		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch())
		        display.sleep();
		    }
		    display.dispose();
	}
	
	public void run() {
		//Emulate a loaded file, fill the city with predefined values
		city = new City();
		Point[] points = new Point[4];
		
		points[0] = new Point(10,10);
		points[1] = new Point(40,10);
		points[2] = new Point(40,40);
		points[3] = new Point(10,40);
		
		city.addDistrict(points);
		
		Point[] points2 = new Point[6];
		
		points2[0] = new Point(20,20);
		points2[1] = new Point(50,20);
		points2[2] = new Point(50,50);
		points2[3] = new Point(20,50);
		points2[4] = new Point(30,55);
		points2[5] = new Point(30,30);
		
		city.addDistrict(points2);
		
		city.drawDistricts(canvas);
		
	}
	
	
}
