package songsearchengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchSongs {
	
	public static void main_clone() {
		// Initialize scanner for user input
        Scanner scan = new Scanner(System.in);
        
        // Set name of CSV file to read from
        String csvFile = "songs.csv";
        String line;
        String[] fields;
        
        // Create a HashMap to store song data
        Map<String, String> data = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read first line of CSV file (header)
            br.readLine();
            
            // Read each subsequent line of CSV file
            while ((line = br.readLine()) != null) {
                try {
                    // Split line into fields using comma delimiter
                    fields = line.split(",");
                    
                    // Add song data to HashMap using song title as key
                    data.put(fields[1], "Release Year: " + fields[0] + "\n" + "Genre: " + fields[2] + "\n" + "Artist: " + fields[3] + "\n"); 
                }
                catch (Exception e) {
                    // Ignore any lines with missing or invalid data
                }
            }
        } catch (IOException e) {
            // Handle any errors that occur while reading the CSV file
        }

        	boolean exit = false;
        
        while(!exit){
        
        	// Prompt user to enter a search term
         System.out.println("Enter a song name you would like to search: \nor write 'exit' to Exit!");
            String searchWord = scan.nextLine();

            if(searchWord.equals("exit")) {
            	System.out.println("You Exited!");
            	exit = true;
            	break;
            }
            
            
            // Iterate over each entry in the HashMap
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String song_title = entry.getKey();
                
                // Check if song title contains the search term (case-insensitive)
                if (song_title.toLowerCase().contains(searchWord.toLowerCase())) {
                    String song_data = entry.getValue();
                    
                    // Print out song title and associated data
                    System.out.println("Title: " + song_title + "\n" + song_data);
                }
            }

        }
        
		
	}
	
	
    public static void main(String[] args) {
    
    	main_clone();     }
}
