import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal(String word)
  {
    try
    {
      return sentiment.get(removePunctuation(word.toLowerCase()));
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  public static double totalSentiment(String fileName)
  {
    String review = textToString(fileName);
    double total = 0.0;
    while (review.indexOf(" ") > 0)
    {
  int space = review.indexOf(" ");
  String word = review.substring(0, space);
  double sentiment = sentimentVal(word);
  total+= sentiment;
  review = review.substring(space+1);
    }
    total += sentimentVal(review);
    return total;
  }

  public static int starRating(String fileName)
  {
    {
      //get total sentiment
      double sentiment = totalSentiment(fileName);
      //check sentiment against thresholds
      //return appropriate star starRating
      if (sentiment > 20){
        return 5;
      } else if (sentiment > 10){
        return 4;
      } else if (sentiment > 0){
        return 3;
      } else if (sentiment > -10){
        return 2;
      } else{
        return 1;
      }
  
    }
  }


  public static String fakeReview(String fileName, String PosNeg)
  {
	  //get the review in a string
	  String review = textToString(fileName);

	  //empty string for new review
	  String newReview = "";

    //Use POSNEg and determine iff the value of POsneg is negative or positive
	  //loop through the string
	  while (review.indexOf("*")>0 && review.length()>0)
	  {
		  //look for *, 
		  int starLoc = review.indexOf("*");
	  	  //add everything before the * to new review
		  newReview += review.substring(0, starLoc);
		  //add a random adjective to new review
//choose an adjective 
if (PosNeg.toLowerCase().equals("positive"))
  {newReview+= randomPositiveAdj();//}
		  newReview += randomAdjective();
		  //cut off old review through starred adjective
		  int spaceAfterStar = review.indexOf(" ", starLoc);
		  newReview = review.substring(spaceAfterStar);

		//   System.out.println("__");
		 System.out.println(newReview);

	  }
    else
    {
newReview+= randomNegativeAdj();
int spaceAfterStar = review.indexOf(" ", starLoc);
    }
  }
 //public static String chooseSentiment (String placeholder)
  //{
    //need a scanner to ask if user wants negative review or positive
    //ask for a bunch of adjectives in scanner 
    return newReview ;
  }
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation(String word)
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

      /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  public static String bigSHOT(String fileName, String hyperlinkblocked)
  {
    String review = textToString(fileName);
    String newKROMER = "";
    while (review.indexOf("*")>0 && review.length()>0)
	  {
		  //look for *, 
		  int starLoc = review.indexOf("*");
      int starlock = review.indexOf(" ");
	  	  //add everything before the * to new review
		  newKROMER += review.substring(starLoc, starlock+1);
      
		  //add a random adjective to new review
//choose an adjective 
if (hyperlinkblocked.toLowerCase().equals("positive"))
{newKROMER+= randomPositiveAdj();}
else {newKROMER+= randomNegativeAdj();}
		  newKROMER += randomAdjective();
		  //cut off old review through starred adjective
		  int spaceAfterStar = review.indexOf(" ", starLoc);
		  newKROMER = review.substring(spaceAfterStar);
    }
    return newKROMER;
  }
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }
}
