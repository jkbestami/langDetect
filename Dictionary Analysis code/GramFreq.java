  
import java.io.*;
import java.util.*;


/* Class used to determine most common ngrams of a given length
 * also contains useful "util"-like commands: trimming a list
 *                                            remove short words
 *                                            replace spec chars
 *                                            toLowerCase
 *                                            printList to file
 *                                            removeAfterFirst
 *                                                                                 
 * 
 * This class is very useful when manipulating word databases.
 * 
 * */
public class GramFreq{
	
	List<String> wordList;              //list of words
	List<String> ngramList;             //list of ngrams
	Map<String, Long> ngramOccurences;  //map from ngram--->Occurence
	List<String> maxListStr;            //list of most occuring ngrams
	
  public static void main(String[] args) {
		/*
     * modify depending on function needed
     * */
	}	
	
	
	public GramFreq(String filename){
		wordList = readFile(filename);
		ngramList = new ArrayList<String>();
		maxListStr = new ArrayList<String>();
	}
	
  /*  useful function, give it filename and List of string and it will 
   *  write every string on a line
   * 
   * */
	public void writeToFile(String filename, List<String> listToWrite){
	try{
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		for(String s : listToWrite){
			writer.println(s);
		}
		writer.close();
	} catch (Exception e) {
		System.err.format("Exception occurred trying to read '%s'.", filename);
	}
	}
	
  /*  Removes small words from a file.
   *  
   *  Param: filename, int: shortest size of word allowed
   *  post: removes words shorter than limit from input file
   * */
  public void removeSmallWords(String input, int size){
  
  try{  
    File inputFile = new File(input);
    File tempFile = new File("myTempFile.txt");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;

    while((currentLine = reader.readLine()) != null) {
    // trim newline when comparing with lineToRemove
    String trimmedLine = currentLine.trim();
    if(trimmedLine.length()<size) continue;
    writer.write(currentLine + System.getProperty("line.separator"));
    }
    writer.close(); 
    reader.close(); 
    tempFile.renameTo(inputFile);
  }catch(Exception e){
	}
  }
    /*
     * Only keeps first word of every line
     * param: filename
     * post: every word after first of every line is removed from input file.
     * */
    public void removeAfterFirst(String input){
  
  try{  
    File inputFile = new File(input);
    File tempFile = new File("myTempFile.txt");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;

    while((currentLine = reader.readLine()) != null) {
    String[] splited = currentLine.split("\\s+");
    writer.write(splited[0] + System.getProperty("line.separator"));
    }
    writer.close(); 
    reader.close(); 
    tempFile.renameTo(inputFile);
  }catch(Exception e){
	}
  }
  /* Cuts down the list to size given in param, does not truncate but takes
   * every word at regular intervals. (bcz maybe word database is alphabetical)
   * param : filename, int:size wanted 
   * 
   * */
  public void trimList(String input, int size){
  
  try{  
    File inputFile = new File(input);
    File tempFile = new File("myTempFile.txt");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;
    int i = 0;
    int j = wordList.size()/size; 
    while((currentLine = reader.readLine()) != null) {
      i++;
    // trim newline when comparing with lineToRemove
    String trimmedLine = currentLine.trim();
    if(i%j!=0) continue;
    writer.write(currentLine + System.getProperty("line.separator"));
    }
    writer.close(); 
    reader.close(); 
    tempFile.renameTo(inputFile);
  }catch(Exception e){
	}
  }
  
  /*  Converts text file given in parameter to lowercase
   * 
   * */
    public void toLowerCase(String input){
  
  try{  
    File inputFile = new File(input);
    File tempFile = new File("myTempFile.txt");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String currentLine;

    while((currentLine = reader.readLine()) != null) {
    currentLine = currentLine.toLowerCase();
    writer.write(currentLine + System.getProperty("line.separator"));
    }
    writer.close(); 
    reader.close(); 
    tempFile.renameTo(inputFile);
  }catch(Exception e){
	}
  }
  
  /* Reads a file by line, replaces spec char, and adds it to wordList
   * param : filename
   * post: wordList is updated as required
   * returns: wordList
   * */
	public List<String> readFile(String filename){
		
		List<String> words = new ArrayList<String>();
		
	try{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
    
		while ((line = reader.readLine()) != null){
      if(line.contains("ë"))
				line.replace('ë', 'e');
			if(line.contains("é"))
				line.replace('é', 'e');
			if(line.contains("è"))
				line.replace('è', 'e');
			if(line.contains("ê"))
				line.replace('ê', 'e');
			if(line.contains("à"))
				line.replace('à', 'a');
			if(line.contains("â"))
				line.replace('â', 'a');
			if(line.contains("ù"))
				line.replace('ù', 'u');
			if(line.contains("ç"))
				line.replace('ç', 'c');
			if(line.contains("û"))
				line.replace('û', 'u');
      if(line.contains("ü"))
				line.replace('ü', 'u');
			if(line.contains("ô"))
				line.replace('ô', 'o');
			if(line.contains("ï"))
				line.replace('ï', 'i');
			if(line.contains("î"))
				line.replace('î', 'i');
      if(line.contains("ß"))
				line.replace("ß", "ss");
			if(line.matches("[a-zA-Z][a-zA-Z]+"))
				words.add(line);	
		}
		
		reader.close();
		return words;
	}catch (Exception e){
		System.err.format("Exception occurred trying to read '%s'.", filename);
		return null;
	}
	
	}
	/*  generate ngrams that are sequence of IDENTICAL LETTERS, as in 'aa'
   *  param: n: size of sequence(n)  t: how many ngrams     w:not used
   *  
   * post: ngramList is updated with t numbers of IDENTICAL LETTERS ngrams 
   *        that occur the most in a text
   * */
	public void generateNgrams(int n, int t, int w){
		ngramOccurences = new HashMap<String, Long>();
			List<String> countList = new ArrayList<String>();
			for(String s : wordList){
				int maxStartIndex = s.length() - n;
				for (int i = 0; i < maxStartIndex; i++){
					String ngram = s.substring(i, i + n);
					
					if(ngram.charAt(0)==ngram.charAt(1)){
						countList.add(ngram);
						if(!ngramList.contains(ngram)||ngramList.isEmpty()){
							ngramList.add(ngram);
						}					
					}
				}		
			}
			
			List<Long> maxList = new ArrayList<Long>();
			
			for(int i = 0; i<t; i++){
				maxList.add((long)0);
			}
				
			for(String s : ngramList){
				long i = Collections.frequency(countList, s);
				Collections.sort(maxList);
				if(i>maxList.get(0))
					maxList.set(0 , i);
				ngramOccurences.put(s, i);	
			}
			
			for(String s : ngramList){
				long m = maxList.get(0);
				if(ngramOccurences.get(s)>=m)
					maxListStr.add(s);
				
			}
	}	
		
	/*  generate t most occuring n-grams in a text, updates ngramList according
   *  param: n: size of sequence(n)  t: how many ngrams 
   *  
   * post: ngramList is updated with t numbers of ngrams that occur the most 
   * */
	public void generateNgrams(int n, int t){
		ngramOccurences = new HashMap<String, Long>();
		List<String> countList = new ArrayList<String>();
		for(String s : wordList){
			int maxStartIndex = s.length() - n;
			for (int i = 0; i < maxStartIndex; i++){
				String ngram = s.substring(i, i + n);
				countList.add(ngram);
				if(!ngramList.contains(ngram)||ngramList.isEmpty())
					ngramList.add(ngram);
			}		
		}
		
		List<Long> maxList = new ArrayList<Long>();
		
		for(int i = 0; i<t; i++){
			maxList.add((long)0);
		}
			
		for(String s : ngramList){
			long i = Collections.frequency(countList, s);
			Collections.sort(maxList);
			if(i>maxList.get(0))
				maxList.set(0 , i);
			ngramOccurences.put(s, i);	
		}
		
		for(String s : ngramList){
			long m = maxList.get(0);
			if(ngramOccurences.get(s)>=m)
				maxListStr.add(s);
			
		}
	}
}
	
