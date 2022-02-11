import java.util.ArrayList;

/*
 * Record actor's name and list of movies in which actor had a role.
 */



public class ActorRecord implements Comparable<ActorRecord>{
  public String name;
  public ArrayList<String> movies;
  ActorRecord pred;

  public ActorRecord(String n) {
    name = n;
    movies = new ArrayList<String>();
  }

  /* Add a movie to the list for this actor.  */
  public void addMovie(String m) {
    movies.add(m);
  }

  public boolean appearedIn(String movie) {
    return movies.contains(movie);
  }
  
  ActorRecord predActor(ActorRecord current) {
		this.pred = current;
		return pred;
	}
  
  public int compareTo(ActorRecord other)
  {
     if ((name.compareTo(other.name))>0)
        return 1;
        
     else if ((name.compareTo(other.name))<0)
        return -1;
        
     else 
        return 0;  
     
  }
  
  public String toString(){
    String s = name;
    for(String m : movies)
      s += " "+m;
    return s;
  }
  
}
