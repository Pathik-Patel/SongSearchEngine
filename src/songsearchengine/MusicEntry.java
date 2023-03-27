package songsearchengine;

public class MusicEntry {
    private String name;
    private String artist;
    private String genre;
    private int year;

    public MusicEntry(String name, String artist, String genre, int year) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return name + "|||" + artist + "|||" + year + "|||" + genre + "";
    }
}
