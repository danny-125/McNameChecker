package me.danny125.namesniper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	public static String newline = System.getProperty("line.separator");
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	public static boolean isUsernameTaken(String username) throws IOException {
	       URL url = new URL("https://playerdb.co/api/player/minecraft/" + username);       

	        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
	        con.setRequestMethod("GET");
	        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	        try {
	        con.connect();
	        
	        String str = con.getContent().toString();
	        System.out.println(str);

	        con.disconnect();
	        return true;
	        }
	        catch(Exception e) {
	        	if(username.length() > 2) {
	        	return false;
	        	}
	        	return true;
	        }
	}
	public static void main(String args[]) throws IOException {	
	    JPanel middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Valid usernames go here" ) );

	    // create the middle panel components

	    JTextArea display = new JTextArea ( 16, 58 );
	    display.setEditable ( false ); // set textArea non-editable
	    JScrollPane scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

	    //Add Textarea in to middle panel
	    middlePanel.add ( scroll );
	    
	    JFrame f = new JFrame("Name Checker by danny125#3343");
	    f.setSize(700, 400);
	    f.setLocation(300,200);

	    final JButton button = new JButton("Open Text File");
	    f.getContentPane().add(BorderLayout.SOUTH, button);

	    
	    f.add ( middlePanel );
	    button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String usernamelist = null;
				String text = null;
				
				
				infoBox("Select your text file", "Please select text file");
		        JFileChooser chooser = new JFileChooser();
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		                    usernamelist = chooser.getSelectedFile().getAbsolutePath().toString();
		        }
				Path path = Paths.get(usernamelist);
				infoBox("Please standby as the usernames are being checked", "Please standby");
				List<String> lines = null;
				try {
					lines = Files.readAllLines(path, StandardCharsets.UTF_8);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String allUsernames = "";
				for (String line : lines) {
					try {
						if(!isUsernameTaken(line)) {
							allUsernames = allUsernames + line + newline;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				infoBox("Finished checking usernames", "Finished");
				display.setText(allUsernames);
			}
			
	    });
	    
	    f.setVisible(true);
	}
}
