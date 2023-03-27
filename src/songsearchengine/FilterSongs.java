package songsearchengine;

import java.io.*;
import java.util.*;

public class FilterSongs {
    public static void main_clone()throws IOException {
    	// create a list to store MusicEntry objects
        List<MusicEntry> entries = new ArrayList<>();
        
        // read data from "songs.csv" file and store it in entries list
        try (BufferedReader reader = new BufferedReader(new FileReader("songs.csv"))) {
            int counter = 0; // counter to skip first row (header)
            String line;
            while ((line = reader.readLine()) != null) {
                counter++;
                if (counter != 1) { // skip header row
                    String[] parts = line.split(",");
                    if (parts.length > 3) {
                        // extract data from the line and create a MusicEntry object
                        String name = parts[1];
                        String artist = parts[3];
                        String genre = parts[2];
                        int year = Integer.parseInt(parts[0]);
                        entries.add(new MusicEntry(name, artist, genre, year));
                    }
                }
            }
        }
        
        // create a MusicGraph object from the entries list
        MusicGraph graph = new MusicGraph(entries);
        
        // read user input and search for songs based on genre or year
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.print("Enter search query (genre/year): \nor enter 'exit' to exit.");
            String query = scanner.nextLine();
            if(query.equals("exit")) {
            	System.out.print("You exited successfully!");
            	exit = true;
            	break;
            }
            if (query.isEmpty()) {
                break; // exit loop if user enters nothing
            }
            if (query.equals("genre")) {
                System.out.print("Enter genre: ");
                String genre = scanner.nextLine();
                List<MusicEntry> result = graph.searchByGenre(genre);
                if (result.isEmpty()) {
                    System.out.println("No results found.");
                } else {
                    System.out.println("Search results:");
                    // print each MusicEntry object in the result list
                    for (MusicEntry entry : result) {
                        String[] fields = entry.toString().split("\\|\\|\\|");
                        System.out.println("Name: " + fields[0] + "\n" + "Released Year: " + fields[2] + "\n" + "Genre: " + fields[3] + "\n" + "Artist: " + fields[1] + "\n");
                    }
                }
            } else if (query.equals("year")) {
                System.out.print("Enter year: ");
                int year = Integer.parseInt(scanner.nextLine());
                List<MusicEntry> result = graph.searchByYear(year);
                if (result.isEmpty()) {
                    System.out.println("No results found.");
                } else {
                    System.out.println("Search results:");
                    // print each MusicEntry object in the result list
                    for (MusicEntry entry : result) {
                        String[] fields = entry.toString().split("\\|\\|\\|");
                        System.out.println("Name: " + fields[0] + "\n" + "Released Year: " + fields[2] + "\n" + "Genre: " + fields[3] + "\n" + "Artist: " + fields[1] + "\n");
                    }
                }
            } else {
                System.out.println("Invalid query.");
            }
        }

    }
	
	public static void main(String[] args) throws IOException {
		main_clone();     }
}
