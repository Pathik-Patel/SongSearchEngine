package songsearchengine;


import java.util.*;
import java.util.function.Predicate;

public class MusicGraph {
	
    private List<MusicEntry> entries;
    private Map<MusicEntry, List<MusicEntry>> adjacencyList;

    public MusicGraph(List<MusicEntry> entries) {
        this.entries = entries;
        this.adjacencyList = new HashMap<>();
        for (MusicEntry entry : entries) {
            adjacencyList.put(entry, new ArrayList<>());
        }
        buildGraph();
    }

    private void buildGraph() {
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                MusicEntry entry1 = entries.get(i);
                MusicEntry entry2 = entries.get(j);
                if (entry1.getGenre().equals(entry2.getGenre())
                        || entry1.getArtist().equals(entry2.getArtist())
                        || entry1.getYear() == entry2.getYear()) {
                    adjacencyList.get(entry1).add(entry2);
                    adjacencyList.get(entry2).add(entry1);
                }
            }
        }
    }

    public List<MusicEntry> searchByGenre(String genre) {
    	
        return bfs(entries.get(0), entry -> entry.getGenre().equals(genre));
    }

    public List<MusicEntry> searchByArtist(String artist) {
        return bfs(entries.get(0), entry -> entry.getArtist().equals(artist));
    }

    public List<MusicEntry> searchByYear(int year) {
        return bfs(entries.get(0), entry -> entry.getYear() == year);
    }

    private List<MusicEntry> bfs(MusicEntry start, Predicate<MusicEntry> predicate) {
        List<MusicEntry> result = new ArrayList<>();
        Queue<MusicEntry> queue = new LinkedList<>();
        Set<MusicEntry> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            MusicEntry current = queue.poll();
            if (predicate.test(current)) {
                result.add(current);
            }
            for (MusicEntry neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return result;
    }
}
