package songsearchengine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class AlphabeticallySorting {
	
	public static void main_clone() throws IOException {
		// Create a scanner to read user input from the console
	    Scanner scanner = new Scanner(System.in);

	    // Prompt the user to enter the attribute to sort by
	    
	    // Create an empty list to store the songs
	    List<Song> songs = new ArrayList<>();

	    // Use a try-with-resources block to create a BufferedReader to read the song data from a CSV file
	    try (BufferedReader br = new BufferedReader(new FileReader("songs.csv"))) {
	        String line;

	        // Use a counter to skip the header row of the CSV file
	        int counter = 0;

	        // Read each line of the CSV file
	        while ((line = br.readLine()) != null) {
	            counter++;

	            // Skip the header row of the CSV file
	            if (counter != 1) {
	                try {
	                    // Split the line into an array of values
	                    String[] values = line.split(",");

	                    // Create a new Song object from the values and add it to the list of songs
	                    Song song = new Song(values[1], values[3], Integer.parseInt(values[0]), values[2]);
	                    songs.add(song);
	                } catch (Exception e) {
	                    // If there is an error parsing the line, skip it
	                }
	            }
	        }
	    }
	    
	    boolean exit = false;
        
        while(!exit){
        	System.out.print("Enter the attribute to sort by (name, singer, released year, genre): \nEnter 'exit' to Exit\n");
    	    String attribute = scanner.nextLine();
    	    
    	    if(attribute.equals("name")) {
    	    	System.out.println(attribute);
    	    	quickSort(songs, Comparator.comparing(Song::getName));
    	    }
    	    else if(attribute.equals("singer")) {
    	    	quickSort(songs, Comparator.comparing(Song::getSinger));
    	    }
			else if(attribute.equals("released year")) {
				quickSort(songs, Comparator.comparing(Song::getReleasedYear));
			    	    }
			else if(attribute.equals("genre")) {
				quickSort(songs, Comparator.comparing(Song::getGenre));
			}
			else if(attribute.equals("exit")) {
				exit = true;
	        	System.out.println("You exited!!");
			    	    }    	    // Sort the list of songs based on the user-specified attribute using a switch statement
    	    for (Song song : songs) {
    	        System.out.println(song);
    	    }
        	
        }
        
	    // Print each song in the sorted list to the console
	    

	    	}
    
	public static void main(String[] args) throws IOException {
	main_clone();    
	}

	private static <T> void quickSort(List<T> list, Comparator<T> comparator) {
	    // Call the helper method with the start and end indices of the entire list
	    quickSort(list, comparator, 0, list.size() - 1);
	}

	private static <T> void quickSort(List<T> list, Comparator<T> comparator, int low, int high) {
	    // If the start index is less than the end index, the list has more than one element and needs sorting
	    if (low < high) {
	        // Partition the list into two sub-lists and find the index of the partition
	        int partitionIndex = partition(list, comparator, low, high);
	        // Recursively sort the left sub-list (elements less than the partition)
	        quickSort(list, comparator, low, partitionIndex - 1);
	        // Recursively sort the right sub-list (elements greater than the partition)
	        quickSort(list, comparator, partitionIndex + 1, high);
	    }
	}

	private static <T> int partition(List<T> list, Comparator<T> comparator, int low, int high) {
	    // Set the pivot as the last element in the sub-list
	    T pivot = list.get(high);
	    // Initialize the index of the smaller element
	    int i = low - 1;
	    // Iterate over the sub-list from the start to the second-to-last element
	    for (int j = low; j < high; j++) {
	        // If the current element is less than or equal to the pivot element
	        if (comparator.compare(list.get(j), pivot) < 0) {
	            // Increment the index of the smaller element
	            i++;
	            // Swap the current element with the smaller element
	            swap(list, i, j);
	        }
	    }
	    // Swap the pivot element with the element immediately after the smaller elements
	    swap(list, i + 1, high);
	    // Return the index of the pivot element
	    return i + 1;
	}

	private static <T> void swap(List<T> list, int i, int j) {
	    // Swap the elements at the specified indices in the list
	    T temp = list.get(i);
	    list.set(i, list.get(j));
	    list.set(j, temp);
	}

	private static class Song {
	    private String name;
	    private String singer;
	    private int releasedYear;
	    private String genre;

	    public Song(String name, String singer, int releasedYear, String genre) {
	        // Initialize the Song object with the specified values
	        this.name = name;
	        this.singer = singer;
	        this.releasedYear = releasedYear;
	        this.genre = genre;
	    }

	    public String getName() {
	        return name;
	    }

	    public String getSinger() {
	        return singer;
	    }

	    public int getReleasedYear() {
	        return releasedYear;
	    }

	    public String getGenre() {
	        return genre;
	    }

	    @Override
	    public String toString() {
	        // Return a formatted string representation of the Song object
	        return "Title: " + name + "\t" + "Release Year: " + releasedYear + "\n" + "Genre: " + genre + "\t" + "Artist: " + singer + "\n";
	    }
	}

}