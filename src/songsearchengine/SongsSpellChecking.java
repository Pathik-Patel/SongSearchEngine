package songsearchengine;

import java.io.*;
import java.util.*;

public class SongsSpellChecking {

    private static ArrayList<String> words = new ArrayList<>();
    public static void main_clone() throws IOException {
    	// Initialize scanner to read input from console
    	Scanner scan = new Scanner(System.in);
    	
    	// Name of the CSV file to read from
        String csvFile = "songs.csv";
        
        // String to hold a line from the CSV file
        String line;
        
        // String array to hold the fields in each line of the CSV file
        String[] fields;
        
        // Create a HashMap to store data read from the CSV file
        // The key is the song name and the value is a string containing information about the song
        Map<String, String> data = new HashMap<>();
        
        // Use a try-with-resources statement to open the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
        	// Read and discard the first line of the CSV file (header)
            br.readLine();
            
            // Loop through each line of the CSV file
            while ((line = br.readLine()) != null) {
            	
            	// Use a try-catch block to handle exceptions that may occur while processing each line of the CSV file
            	try {
            		
            		// Split the line into fields using comma as the delimiter
                    fields = line.split(",");
                    
                    // Create a string containing information about the song from the fields in the line
                    String songInfo = "Release Year: " + fields[0] + "\n" + "Genre: " + fields[2] + "\n" + "Artist: " + fields[3] + "\n";
                    
                    // Add the song name and information string to the HashMap
                    data.put(fields[1], songInfo); 
            	}
            	catch (Exception e) {
            		// If an exception occurs, do nothing and continue processing the next line of the CSV file
            	}
            	
            }
        } catch (IOException e) {
        	// If an exception occurs while opening or reading the CSV file, do nothing and continue execution
        }
        boolean exit = false;
        while(!exit) {
        	// Prompt the user to enter a song name
            System.out.print("Enter a song name: \n or type 'exit' to Exit!\n");
            
            // Read the input from the console
            Scanner scanner = new Scanner(System.in);
            String query = scanner.nextLine();
            
            if(query.equals("exit")) {
            	System.out.print("You Exited Successfully!!");
            	exit =true;
            	break;
            }
            
            // Call a method named "getTheWordsuggestion" which is not defined in this code
            // It is likely a method that takes a search query and a data structure (in this case, the HashMap "data") and returns an array of suggestions based on the query
            String[] list = getTheWordsuggestion(query, data);

            // Check if any suggestions were returned
            if(list.length > 0) {
                // If there are suggestions, loop through them and print them to the console
                if(list[0] != null) {
                    for(String item: list){
                        System.out.println(item);
                    }
                }else {
                    // If the first suggestion is null, it means no suggestions were found, so print an error message
                    System.out.println("Oops! No Suggestions found");
                }
            } else {
                // If no suggestions were returned, print an error message
                System.out.println("Oops! No suggestions found");
            }


        }
            }
    public static String[] getTheWordsuggestion(String searchWord, Map<String, String> data) throws IOException {
        
        // create a HashMap to store the edit distance of each song title from the search word
        HashMap<String, Integer> hashMap = new HashMap<>();
        
        // create an array to store the suggested words
        String[] newWords = new String[5];
        
        // loop through the song titles in the map
        for (String song_title: data.keySet()) {
            
            // calculate the edit distance between the search word and the current song title
            int editDistance = EditDistance(searchWord, song_title);
            
            // add the song title and its edit distance to the HashMap
            hashMap.put(song_title, editDistance);
        }
        
        // sort the HashMap by value (edit distance) in ascending order
        Map<String, Integer> map = sortByValue(hashMap);
        
        // loop through the sorted map and add the suggested words to the newWords array
        int rank = 0;
        for (Map.Entry<String, Integer> en : map.entrySet()) {
            if (en.getValue() != 0) {
                newWords[rank] = en.getKey();
                rank++;
                if (rank == 5){ break; }
            }
        }
        
        // return the array of suggested words
        return newWords;
    }

    // method to sort a HashMap by value (edit distance)
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
        // create a list of map entries
        List<Map.Entry<String, Integer>> listOfWords = new LinkedList<>(map.entrySet());
        
        // sort the list of entries by value (edit distance)
        listOfWords.sort(Map.Entry.comparingByValue());
        
        // create a new LinkedHashMap to store the sorted entries
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : listOfWords) {
            temp.put(aa.getKey(), aa.getValue());
        }
        
        // return the sorted map
        return temp;
    }

    // method to calculate the edit distance between two strings
    public static int EditDistance(String word1, String word2) {
        // get the length of each word
        int word1Length = word1.length();
        int word2Lenghth = word2.length();
        
        // create a 2D array to store the edit distances between substrings of the two words
        int[][] distance_Array = new int[word1Length + 1][word2Lenghth + 1];
        
        // set the initial values of the first row and column of the array
        for (int i = 0; i <= word1Length; i++) {
            distance_Array[i][0] = i;
        }
        for (int j = 0; j <= word2Lenghth; j++) {
            distance_Array[0][j] = j;
        }
        
        // loop through the substrings of the two words and calculate their edit distances
        for (int i = 0; i < word1Length; i++) {
            char char1 = word1.charAt(i);
            for (int j = 0; j < word2Lenghth; j++) {
                char char2 = word2.charAt(j);

                if (char1 == char2) {
                    distance_Array[i + 1][j + 1] = distance_Array[i][j];
                } else {
                    int value1 = distance_Array[i][j] + 1;
                    int value2 = distance_Array[i][j + 1] + 1;
                    int value3 = distance_Array[i + 1][j] + 1;

                    int min = Math.min(value1, value2);
                    min = Math.min(value3, min);
                    distance_Array[i + 1][j + 1] = min;
                }
            }
        }
        return distance_Array[word1Length][word2Lenghth];
    }

    public static void main(String[] args) throws IOException{
    	
    	main_clone();     }

}
