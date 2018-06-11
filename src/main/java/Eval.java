import java.lang.Math;
import org.encog.app.analyst.AnalystFileFormat; 
import org.encog.app.analyst.EncogAnalyst; 
import org.encog.app.analyst.csv.normalize.AnalystNormalizeToEGB;
import org.encog.app.analyst.csv.normalize.AnalystNormalizeCSV; 
import org.encog.app.analyst.script.normalize.AnalystField; 
import org.encog.app.analyst.wizard.AnalystWizard; 
import org.encog.util.arrayutil.NormalizationAction; 
import org.encog.util.csv.CSVFormat; 
import java.io.File;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.Encog;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.simple.EncogUtility;

/* Class used to implement the classification of a word into french/english
 * Relies heavily on encog framework, requires:
 * Trained encog-compatible Neural Network (.eg format)
 * Outside function to regularize inputted word 
 * 
 * */
public class Eval {

  BasicNetwork net;         //neural network, (already trained!)


	/*  Basic constructor
   * param: String, filename of .eg file that implements the NN
   * post: BasicNetwork is set to the NN indicated by input file
   * */
	public Eval(String s){
    net = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(s));
	}
	
	/* feeds the input vector located in src/main/resources/new.egb to NN
   * denormalize fct is called on the output produced by NN and printed
   * pre: new.egb must contain NORMALIZED vector of input word
   * */
  public void evaluate(){
	
	try {
    
		BufferedMLDataSet in = new BufferedMLDataSet(
                                      new File("src/main/resources/new.egb"));
                                      
    double[] inArray = in.get(0).getInputArray();
    String s = "";
    for(int i = 0; i<inArray.length ; i++){
		  s = s + inArray[i] + " ";
		}
    
    BasicMLData input = new BasicMLData(inArray);
    input.setData(inArray);

    MLData output = net.compute(input);
    System.out.println(denormalize(output));
		} catch (Throwable t) {
			t.printStackTrace();
		}
	
	}
	/* denormalize function, takes output of NN denormalizes it into a point,
   * this point is then check if its closer with the point English or French
   * English has point = [1,0]
   * French  has point = [0,1]
   * 
   * NOTE: these coords values and denormalize function are SPECIFIC TO THIS 
   * SET OF DATA, should you want to change data/NN you should probly change
   * these values, add dimensions etc
   * 
   * returns: String, "English" or "French", corresponding to closest
   * */
  public String denormalize(MLData o){
    double[] pts  = o.getData();
    double xo  = pts[0];
    double yo  = pts[1];

    
    double xf  = 0;
    double yf  = 1;
   
    double xe  = 1;
    double ye  = 0;
  
    String s  = "";
    double distf = Math.sqrt((xo-xf)*(xo-xf) + (yo-yf)*(yo-yf));
    double diste = Math.sqrt((xo-xe)*(xo-xe) + (yo-ye)*(yo-ye));

    
    if(distf<diste){
      s = "French";
      return s;
    }
    else{
      s = "English";
      return s;    
    }
    
  }

  /*normalize function, reads vector from file(.csv formatted), uses EncogAnalyst
   * to retrieve normalizing function from file(.ega), and rewrites the nrmlzed
   * vector to file (.egb formatted)
   * pre: locations of file be the same, not empty etc
   * post: vector in new.egb is written with normalized values
   * */
  public void normalize(){
    
    //input file
		File rawFile = new File("src/main/resources/rawInput.csv");
    //output file
    File out = new File("src/main/resources/new.egb");
    
    //encog analyst object created and updated with .ega
    EncogAnalyst analyst = new EncogAnalyst();
    analyst.load(new File("src/main/resources/Vectors.ega"));
    
    //retrieve normalizing fction from .ega
    AnalystNormalizeToEGB norm = new AnalystNormalizeToEGB();
    norm.analyze(rawFile, false, CSVFormat.ENGLISH, analyst);
    norm.normalize(out);
  }
  
  public static void main(String[] args) {
		
  String word = ""; 
  Scanner sc = new Scanner(System.in);
  String intro = "Enter word to be evaluated by the neural network";
  String error = "ONE WORD!";
  boolean err = false;
  
  while(true){
    do{
      err = false;
      System.out.println(intro);
      intro = "again:";
      word  = (sc.nextLine()).trim();
     if(word.contains(" ")){
      err = true;
      intro = error;
     }
    }while(err);
      
    try{
      //write scanned file into input file named word.txt (to normalize it)
      PrintWriter writer = new PrintWriter("src/main/resources/word.txt", "UTF-8");
      writer.println(word);
      writer.close();
    } catch (Exception e) {
    
    }
    //build WordVector file with word.txt, write result vector in rawInput.csv
    String s = "src/main/resources/word.txt";
    WordVector wv = new WordVector(s); 
    wv.writeToFile("src/main/resources/rawInput.csv", wv.vectorList, "english");
      
    
    String FILENAME = "src/main/resources/Vectors_train.eg"; //trained NN 
    try {
      Eval program = new Eval(FILENAME);
      program.normalize();
      program.evaluate();
    } catch (Throwable t) {
      t.printStackTrace();
    } finally {
      Encog.getInstance().shutdown();
    }
      
  }
  }
}
