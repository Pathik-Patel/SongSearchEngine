 package songsearchengine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class SongSearchEngine {
    public static void main(String[] args) {
        // The URL of the webpage to scrape
        String url = "https://goldstandardsonglist.com/Pages_Sort_2a/Sort_2a.htm";
        // The filename of the CSV file to write to
        String csvFile = "songs.csv";
        FileWriter writer = null;
        
        try {
        	
        	 System.out.println("\nFetching Songs from URL....");
        	 
            // Create a new FileWriter to write to the CSV file
            writer = new FileWriter(csvFile);
            
            // Use Jsoup to connect to the webpage and get its HTML content
            Document doc = Jsoup.connect(url).get();
            // Select all table rows (tr elements) in the HTML document
            Elements songElements = doc.select("tr");
            int row_counter = 0;
            // Iterate over each table row
            for (Element songElement : songElements) {
            	row_counter++;
            	// Select all table cells (td elements) within the current table row
            	Elements songElement_details = songElement.select("td");
            	int counter = 0;
            	// Initialize variables to store the song's details
            	String released_year = "";
            	String title = "";
            	String genre = "";
            	String artist = "";
            	// Iterate over each table cell in the current table row
            	for (Element song_detail_element : songElement_details) {
                	counter++;
                	// Determine which column the current table cell corresponds to
                	// and store its text value in the appropriate variable
                	if(counter == 1) {
                		released_year = song_detail_element.text();
                	}
                	else if(counter == 2) {
                		title = song_detail_element.text();
                	}
					else if(counter == 3) {
						genre = song_detail_element.text();	
					}
					else if(counter == 4) {
						// Store the artist's name and replace any commas with pipe characters
						artist = song_detail_element.text();
						artist = artist.replace(",", "|");
//						System.out.println(artist); // Print the artist's name for debugging purposes
					}
                }
            	// Ignore the first 6 table rows (which contain table headers)
            	if(row_counter>6) {
            		// Write the song's details to the CSV file
            		writer.append(released_year).append(",").append(title).append(",").append(genre).append(",").append(artist).append("\n");
            	}
            }
            
            System.out.println("\nCSV File of Songs Created from Given URL.\n");
            
            Scanner scan = new Scanner(System.in);
            boolean exit = false;
            
            while(!exit){

                System.out.println("Please select which operation you want to perform. ( Give input as 1 or 2 etc... ");
                System.out.println("1. Search a Song by Name.\n2. Filter songs by Genre or Release Year.\n3. Sort songs alphabatically based on Released Year, Genre and Name. ");
                System.out.println("4. Perform Spelling Checking for Songs name.\n5. exit");
                String searchWord = scan.nextLine();

        	    switch (searchWord) {
        	        case "1":
        	            SearchSongs.main_clone();
        	            break;
        	        case "2":
        	            FilterSongs.main_clone();
        	            break;
        	        case "3":
        	              AlphabeticallySorting.main_clone();
        	            break;
        	        case "4":
        	            SongsSpellChecking.main_clone();
        	            break;
        	        case "5":
        	            exit= true;
        	            System.out.print("You Exited!");
        	            break;
        	        default:
        	            System.out.println("Invalid attribute");
        	            return;
        	    }
	
            }
            
                        
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Flush the FileWriter and close it
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
