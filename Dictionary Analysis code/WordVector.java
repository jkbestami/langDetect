  
import java.io.*;
import java.util.*;


/* Class to implement a Word Vector in order to encode the data for NN
 * */
public class WordVector{
	
  List<String[]> gramList;  //list of array of ngrams, each entry in the list
 
  List<int[]> vectorList;   //list with each entry: array representing
                            //coordinates of vector representing each word 
  
  List<String> wordList;    //list of word to be translated into vectors
  
	
  public static void main(String args[]) {
    
    WordVector wv = new WordVector("fr2.txt");
    wv.writeToFile("fr2.csv", wv.vectorList, "french");
    WordVector wv2 = new WordVector("en2.txt");
    wv.writeToFile("en2.csv", wv.vectorList, "english");
  }
  
  /* Constructor, takes filename as input, generates word/gram lists, and then 
   * computes the vector representation of each word and generates vectorList
   * param: String the name of the input file 
   * pre: input file should be formatted st each word is on a line
   * post:wordList is generated using input file
   *      gramList is generated using predetermined ngrams 
   *      vectorList generated with vectors of words in wordlist
   * */
  public WordVector(String s){

    wordList = readFile(s);
    gramList = new ArrayList<String[]>(); 

    //Most common 2-grams for each language
    gramList.add( "aa al ka as st te ut tt ls".split("\\s+"));
    gramList.add( "ek ke am be ee el ld ds bl lo ok kk an na ar rd pl".split("\\s+"));
    gramList.add( "oe eg ge nb la nd de es ed et er ev ve li in bi id ".split("\\s+"));
    gramList.add( "dd ik kt ie ez ze bo on ou br ra re en ng ru ui is".split("\\s+"));
    gramList.add( "se da ac ch ht ti ig di do dr ai ri ij du ur ne eb".split("\\s+"));
    gramList.add( "gr ro ei kl le ef sc ha ak ho nk lu ko op pp pe pa".split("\\s+"));
    gramList.add( "ns sp pi jk zi it ga ap rm oo or fi ts go ot nn ni".split("\\s+"));
    gramList.add( "eh rv he gd em me mo ep pr af hi hr uw sl sn po ta ".split("\\s+"));
    gramList.add( "to tr of fe va vi vo ew wa we zo og zw wo om mm gi".split("\\s+"));
    gramList.add( "ol gs ec ag kn kr ad eu un ki ma at rk mi il je ss".split("\\s+"));
    gramList.add( "nt hu sm ll pt ic mp tu ba so rl vl rb vr ul wi za".split("\\s+"));
    gramList.add( "tj rs rt ab no si im co os rg ov tg rw th lt fg sa od ja fs ks us".split("\\s+"));
    gramList.add( "le de un qu es po ou da an".split("\\s+"));
    gramList.add( "pa pl lu su so on av ve au ce et tt se ma ai co om ".split("\\s+"));
    gramList.add( "mm to no fa us ss eu bi ie pe en nc or do ur mo oi".split("\\s+"));
    gramList.add( "in sa nt tr ir el ll vo pr ri ef ff fe ca mi il li".split("\\s+"));
    gramList.add( "io be gi iq ta te em mp gr ro up ns jo ep pu ui br".split("\\s+"));
    gramList.add( "ru xe fo ue er re is fr ra rn ni ch he nd al lo ac ".split("\\s+"));
    gramList.add( "ct ti ut st ot uv ea ia ar rd va ex pt rm od du it".split("\\s+"));
    gramList.add( "fi iv rt ha ec uc cr ap pp ba as bo rs vi mb la nn ".split("\\s+"));
    gramList.add( "ne si me ol hi if rr ev vr ja am dr rv ic ge tu ua".split("\\s+"));
    gramList.add( "at os ib bl rc di ju ul cl pi go je op ub im nv oy".split("\\s+"));
    gramList.add( "ho nf og xp ci of ad ag ei ob ig ga af oc cu xi nu".split("\\s+"));
    gramList.add( "id gn ng ip bu mu ph na rg sp gu ab rl ug ud fl lt cc sc um th".split("\\s+"));
    gramList.add( "th an fo ha hi wi it no ar".split("\\s+"));
    gramList.add( "fr ro ou al av ne mo or wa il ho om ca ab bo pa ag".split("\\s+"));
    gramList.add( "se ea rc re bu on ot he in rm ma at ti io im si ic".split("\\s+"));
    gramList.add( "ew us er co nt ta ac es we el ge vi ie li fi ir rs".split("\\s+"));
    gramList.add( "be ee wo ul rv ce so cl pr ri da ba to pe op pl is".split("\\s+"));
    gramList.add( "na am ov ve st em ai lt ex la as os od du uc ct ak".split("\\s+")); 
    gramList.add( "sh te po ci ad ol nu um mb su le va bl ig gh up pp".split("\\s+")); 
    gramList.add( "me ss sa go oo id de pu ub sc ch ug ev rd iv mp gr".split("\\s+"));
    gramList.add( "do un nd en ra ni ua fu og if ga ay rn tr mm lo ep".split("\\s+"));
    gramList.add( "et ef di ec au oc ff ed dr ph ip pi ru fa mi sp ia".split("\\s+"));
    gramList.add( "ut ow rt cu ur ll oa cc rr bi tu nc lu gu ui ng gi".split("\\s+"));
    gramList.add( "tl cr pt ns qu tt eg ob fe rg ke ib br hu ue ap ki xp nn nv ck fl vo".split("\\s+"));
    gramList.add( "de di un vo da mi si ic au".split("\\s+"));
    gramList.add( "is ni ch ei in al uc we er rd ha na ac wi ir be".split("\\s+"));
    gramList.add( "ne no oc zu wa ab nu bi me eh du ur ma se pr ro".split("\\s+"));
    gramList.add( "ze en at tt ka an ge eg sc ho ar hr nt te so ol".split("\\s+"));
    gramList.add( "ah re ie es ed ke wo or il he im mm ll li io on".split("\\s+"));
    gramList.add( "sa ag gi ib je et tz dr eu ue am it oh hn nd el".split("\\s+"));
    gramList.add( "rs st tw ut ns ts hl la ru le ta ad hi ga rl as".split("\\s+"));
    gramList.add( "ss rn hm id ht ng vi gu sp ko om ve rg fa fr ra".split("\\s+"));
    gramList.add( "lt eb ia nn us ew po ig rb bl em ec fi rh rz tl".split("\\s+"));
    gramList.add( "ck kl lu nz mo op nf of pa rt th ti sg zi fe nk ".split("\\s+"));
    gramList.add( "af rk gl tu br ri os rf fo ek gr ku kt to ob rm ".split("\\s+"));
    gramList.add( "kr hw rw bu ff ba ez ul tr ot pi ug rr mu um ef".split("\\s+"));
    gramList.add( "su ik lo mp pe hu uf lg ld ak ea ft if iv fl".split("\\s+"));
    
    //Most common trigrams for each language
    gramList.add( "ast ste tte eke eel eld".split("\\s+"));
    gramList.add( "kke aan aar ard loe".split("\\s+"));
    gramList.add( "ege bel ela lan and nde bes est ede ".split("\\s+"));
    gramList.add( "ete ter erd eve eli lin dde lie eze ond ran ren eng nge ".split("\\s+"));
    gramList.add( "rui ise ach cht hti tig ige ndi raa rij ist nee een geb".split("\\s+"));
    gramList.add( "den gro roe eid ide gek kle lee gel lij ges esc sch cha ".split("\\s+"));
    gramList.add( "ake kel lde hou ppe pel ele ens ing ijk uit tin ang erm ".split("\\s+"));
    gramList.add( "ven oor ged kte gev ene ote ten rin gen hoo che eme mel".split("\\s+"));
    gramList.add( "nst rde gep ger era eer ere ken end epe chi chr ouw nne".split("\\s+"));
    gramList.add( "sta taa sto str get tro gew waa mme gra ali igs ngs ech".split("\\s+"));
    gramList.add( "rig nke laa dig pro loo ati mer erk oed sse pla ant ree".split("\\s+"));
    gramList.add( "der ans cho ind sla tee ell lle spe sti ich tre stu nte".split("\\s+"));
    gramList.add( "baa oer erb wee wer erv ier ett hei del ver ers art ade ".split("\\s+"));
    gramList.add( "men ent tie tra see ate ord hte tel erg rge ame erl age".split("\\s+"));
    gramList.add( "ron ove rst boe itg tge isc ort rte ert tje ite aat ont".split("\\s+"));
    gramList.add( "afg fge ker per eri roo eur rie mee her voo wij toe doo".split("\\s+"));
    gramList.add( "pou son ett mai com omm".split("\\s+"));
    gramList.add( "tou fai enc cou our san ent ntr air ell".split("\\s+"));
    gramList.add( "pri ill ion iqu eur tem emp rou ain lle".split("\\s+"));
    gramList.add( "que uel ser tre ris ise con ont ran anc".split("\\s+"));
    gramList.add( "che mon ond sou act cti tio aut res est".split("\\s+"));
    gramList.add( "tro ouv uve mpl omp pre rem ter pro ive out".split("\\s+"));
    gramList.add( "tra par art rti ect cte teu ale iss ssa app por".split("\\s+"));
    gramList.add( "bou ver ven sur ute fon gra and pla per ers".split("\\s+"));
    gramList.add( "onn nne ert rta tai iti tiq cha rai ais men sti lon mme".split("\\s+"));
    gramList.add( "ure end nda erv man sit itu ati pos ssi tan vie ien".split("\\s+"));
    gramList.add( "ule lem eme sem lie for ita ren ndr int imp mpo ort orm".split("\\s+"));
    gramList.add( "nde nve sse ire rec ant lla ass enn ine onc".split("\\s+"));
    gramList.add( "min ist str sen erc ari nta ens ndi gen rte nte".split("\\s+"));
    gramList.add( "att tte mar ign inc ten age ons nst dis tri uti".split("\\s+"));
    gramList.add( "ffi fic ici rat cen ili rma ica cat ite fin ina".split("\\s+"));
    gramList.add( "nan cie tur nat spo nna nai nce sio nse ste all".split("\\s+"));
    gramList.add( "ind abl ess tiv ret nti tie lan ena nal ble lis".split("\\s+"));
    gramList.add( "tal lai nen isi col tat ate isa sat len sta qui ali".split("\\s+"));
    gramList.add( "ear the for mat ati tio".split("\\s+"));
    gramList.add( "sit her con ont nta sin ine lin ser".split("\\s+"));
    gramList.add( "lic fin pri ric ove sta tat int hea".split("\\s+"));
    gramList.add( "wor pro duc sho ste pos lea eas abl".split("\\s+"));
    gramList.add( "igh por ess war ide rou ver com omp".split("\\s+"));
    gramList.add( "rea man und nde gen ene ner era res".split("\\s+"));
    gramList.add( "ive ers gra ame eme men par cou nte".split("\\s+"));
    gramList.add( "ter nat ion ona cen ent sto tor tra".split("\\s+"));
    gramList.add( "omm eve rep tai sen fic cat ate are".split("\\s+"));
    gramList.add( "shi che spe pec cia ind log sio ect".split("\\s+"));
    gramList.add( "cti oun ort rel lat rit mer eri ica".split("\\s+"));
    gramList.add( "car pla oll acc hin our ren edi ntr ist".split("\\s+"));
    gramList.add( "tur ure per inc din ire rec cha han ang rat tin chi".split("\\s+"));
    gramList.add( "rin den ita pre art tit ins sur ran anc ert sti mon".split("\\s+"));
    gramList.add( "ali lit ten isi col lle rti tic ere rai ain ini nin cre enc ori".split("\\s+"));
    gramList.add( "rie nce est ndi iti ele lec all mar act eat str eco tte app kin".split("\\s+"));
    gramList.add( "mpl tre ree ina ing tan and lan exp tiv tie ass har ell ssi".split("\\s+"));
    gramList.add( "ill ani eli nti rac dis end ifi uti ici cor tri der rem min".split("\\s+"));
    gramList.add( "ris ari ons nst ens ili ack nsi nne att pen ant sse arr".split("\\s+"));
    gramList.add( "sic ich ein wer ine pro ".split("\\s+"));
    gramList.add( "ege sch unt nte ahr hre".split("\\s+"));
    gramList.add( "die ese wie ied ede ord".split("\\s+"));
    gramList.add( "isc che mme ion one all".split("\\s+"));
    gramList.add( "bei etz war ber ere rei".split("\\s+"));
    gramList.add( "eit nde der ers rst ste".split("\\s+"));
    gramList.add( "eut wei men ens tsc chl".split("\\s+"));
    gramList.add( "lan and run end zei lle".split("\\s+"));
    gramList.add( "sta hie gan erl tte ach".split("\\s+"));
    gramList.add( "ass sse ter ern ite den cht ing iel".split("\\s+"));
    gramList.add( "kom omm ver erg ang nge gen ene man ebe".split("\\s+"));
    gramList.add( "ier eru kon rau ehe ind des ess hne gew lie ges".split("\\s+"));
    gramList.add( "est geb tei eig ige sie ger era gel hal arb rbe lei ".split("\\s+"));
    gramList.add( "ech age alt lte esc chi erh bes tli lic ent lun ini fre par ".split("\\s+"));
    gramList.add( "art rte mei eis ist iti ins sge hin int mit ele zie ran".split("\\s+"));
    gramList.add( "ell cha haf itt nst nne eic hei rge ate erf ann chn tis".split("\\s+"));
    gramList.add( "tel ieb tun und ete for ati tio chw erw ffe lau han min rit".split("\\s+"));
    gramList.add( "eil spr rec ten tra set bun rie tan uch ung eri ric hte nis nze".split("\\s+"));
    gramList.add( "eme ali aus nie chr rsc leg her auf str tre ert nun tie tig ant tri".split("\\s+"));
    gramList.add( "vor sti bet erk ort erb ren sen etr fer lis org rat ris".split("\\s+"));
    gramList.add( "aste teke okke aard oege".split("\\s+"));
    gramList.add( "iert inan ters eleg ziel egen echt lisc stra ungs rist isti enti ment".split("\\s+"));
    


    //Most common 4-grams for each language
    gramList.add( "lich alis gend inst esta atis abge verb tier eits sier gele iere ausg usge".split("\\s+"));
    gramList.add( "ente mini inis nist verw zeic ichn haft betr eing esti reib orge eder tand".split("\\s+"));
    gramList.add( "akti annt itte eset vert ertr echn stri erla erte nges stim timm vors erwe".split("\\s+"));
    gramList.add( "zeit fahr schu schr chri folg ersc rsch chie lege cher hand rtei erle ktio".split("\\s+"));
    gramList.add( "stan vers such eric rich chte geri nisc verl teil land blic geme eins unge".split("\\s+"));
    gramList.add( "weis nach rach spre rech eche best ndes acht setz bund chen nati iona onal".split("\\s+"));
    gramList.add( "eich reic atio tion schw chwe erwa sich iche offe ende spie inne mein piel".split("\\s+"));
    gramList.add( "leic ents chei erge ring inge erfo chne iege tisc stel tell iebe unde elle".split("\\s+"));
    gramList.add( "tlic frei arte eist iste inte eben fall beit seit gese scha chaf mitt glei ".split("\\s+"));
    gramList.add( "esse gest este ster gebe eige iter arbe rbei lang alte gesc esch schi amme erha halt".split("\\s+"));
    gramList.add( "nehm ehme steh weit eite eide icht alle komm ange omme regi gier ieru erun inde".split("\\s+"));
    gramList.add( "erst rste nsch deut euts utsc tsch schl ande stad erli asse nter tern erne".split("\\s+"));
    gramList.add( "eine durc gege unte jahr ahre iede orde isch sche ione bere erei reit nder".split("\\s+"));
    gramList.add( "para mina prom ator conc inat arri repr conv vati".split("\\s+"));
    gramList.add( "ativ grap raph pect ifie ermi rmin ress ndin esse sent llin eter anti ensi".split("\\s+"));
    gramList.add( "ende erat ulat lati nder tran rans assi asse ecte rest ific fica char".split("\\s+"));
    gramList.add( "mini trat inst ntin isio appr tati atte trib ribu ibut enti onsi ound ".split("\\s+"));
    gramList.add( "resp spon ract sign riti tica reas enta abil bili ilit este miss issi".split("\\s+"));
    gramList.add( "ucti erti trea entl cons onst nstr stru truc ruct ster posi siti erin".split("\\s+"));
    gramList.add( "stra disc tain lect utio reco conf dist istr stri esen teri indi subs".split("\\s+"));
    gramList.add( "reat ctiv itie ange ligh eque essi ssio sion renc side iden trac".split("\\s+"));
    gramList.add( "cess esti dica lica icat ompl mple stan expe peri rate crea".split("\\s+"));
    gramList.add( "olle arti prov fere pres prin trai ance diti itio elec alle tabl iste acti atur requ quir".split("\\s+"));
    gramList.add( "vent part tmen scri ptio ranc prop oper isti stin nten visi coll".split("\\s+"));
    gramList.add( "coun ount medi ontr ctur ture ecto ctor chan rati atin over dent".split("\\s+"));
    gramList.add( "ente stor comm ment ffic cati spec peci olog ecti ctio port rela".split("\\s+"));
    gramList.add( "gene nera rese iver vers ogra emen inte nter nati tion iona cent".split("\\s+"));
    gramList.add( "cont serv stat prod rodu oduc duct supp ight ubli comp ompa unde".split("\\s+"));
    gramList.add( "form orma mati atio ther".split("\\s+"));
    gramList.add( "tour anda ureu ntai cept tair sati itai onda".split("\\s+"));
    gramList.add( "stra reco iste vert indi dica radi stan".split("\\s+"));
    gramList.add( "lect chan enta tati igne conv onte ptio ateu tran rans isat inst nsta orti nver lair reme fica mpos ourn".split("\\s+"));
    gramList.add( "prof essi ssio sion nnel nten enan esse ssen nati cess assi able tten rman ance ture ienn enne nnen".split("\\s+"));
    gramList.add( "conf trai erve ress lati tive ompa tabl ette teme tent enti ntie teur rent tien icie spec pect".split("\\s+"));
    gramList.add( "titu inte nter econ seme ment disp anch ionn onna nnai nair aire once vers ique onse eill".split("\\s+"));
    gramList.add( "trib ribu utio ffic auto rati cent orma mati proc roch ommu mmun icat cati pres".split("\\s+"));
    gramList.add( "imen atte even mont conc posi osit siti conn tend uran cons onst stru stri".split("\\s+"));
    gramList.add( "liqu ntra nist istr anta itio omme prop ropo orte niqu aill ille ente".split("\\s+"));
    gramList.add( "form mett ande inve isse trou rouv dire irec rect ecti asse main".split("\\s+"));
    gramList.add( "emen uver neme pren rend endr uell llem impo mpor port orta".split("\\s+"));
    gramList.add( "pour aiss esti mmen serv mand rais aiso atio vien leme".split("\\s+"));
    gramList.add( "issa rapp bour ours oute fond onne cert tiqu".split("\\s+"));
    gramList.add( "rest ouve empl comp rodu part arti cteu".split("\\s+"));
    gramList.add( "ntre repr cont ontr fran ranc acti ctio tion".split("\\s+"));
    gramList.add( "comm cour entr elle quel".split("\\s+"));
    gramList.add( "verh verk verv verl verm vert".split("\\s+"));
    gramList.add( "onge ater inke verw olle oorg verz deri opge door orge oort".split("\\s+"));
    gramList.add( "bloe eren ghei ijks egel hand ring deli voor oors ucht eltj".split("\\s+"));
    gramList.add( "inne isse teri teli neer egen oord atie enst amen ment leer arti eest etje alis ltje oede eder".split("\\s+"));
    gramList.add( "ieve kker aats keri erig waar ikke arte erin lder choo inte stan rach verd inde wate werk".split("\\s+"));
    gramList.add( "rste stel tell teru uitg itge uits tsch akke tisc isch elaa laar aars ertj rtje".split("\\s+"));
    gramList.add( "stra isee orde dere esse chte hter tere erve erge erli rond over verg erst".split("\\s+"));
    gramList.add( "alle uize ndel appe ardi vers ersc rsch aart ette tter onne emen eerd".split("\\s+"));
    gramList.add( "schu stee elle spel arde erke icht stre stro ante geld baar".split("\\s+"));
    gramList.add( "dige krui ings asse nder rech aans sche scho chri ijde".split("\\s+"));
    gramList.add( "schi schr gesp nnen gest staa omme echt ighe hede oeke".split("\\s+"));
    gramList.add( "gele lijk nste erde nger gere reke eken ende".split("\\s+"));
    gramList.add( "nsch ling inge ijke ting aang ange even gene".split("\\s+"));
    gramList.add( "gesc esch scha elde houd iste ster oppe ppel".split("\\s+"));
    gramList.add( "enge acht chti htig tige eide ngel elij nges".split("\\s+"));
    gramList.add( "land ande este eter terd elin idde onde rand".split("\\s+"));
    
    //Most common single characters and sequentially duplicated characters
    gramList.add( "aa pp ss ee tt ll kk oo mm nn".split("\\s+"));
    gramList.add( "tt mm ss ll ff pp nn rr cc oo".split("\\s+"));
    gramList.add( "ee pp ss oo mm ff ll rr tt nn".split("\\s+"));
    gramList.add( "tt mm ll ss nn pp ff rr aa ee".split("\\s+"));
    gramList.add( "a e t l i d n s r o ".split("\\s+"));
    gramList.add( "e u n s o a c t i r".split("\\s+"));
    gramList.add( "t a n o i r l e c s".split("\\s+"));
    gramList.add( "e i u n a s h l r t".split("\\s+"));
    
      vectorList = new ArrayList<int[]>();
      for(String word: wordList){
        vectorList.add(makeNeuronArray(word));
      }
  }
  
  /* useful kind of "util" function, give it a filename and a list of int arr
   * and a string to append at end it will print it
   * each on a line as a comma separated file (.csv) as required by Encog.
   * pre: listToWrite being empty throws exception obviously
   * post: output file created as described
   *  */
  public void writeToFile(String filename, List<int[]> listToWrite, String s){
	try{
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		for(int[] v : listToWrite){
			for(int i = 0; i<v.length; i++){
        writer.print(v[i] + ".0,");
      }
   writer.println(s);
		}
		writer.close();
	} catch (Exception e) {
		System.err.format("Exception occurred trying to read '%s'.", filename);
	}
	}
  

  /* This is the function that "does all the work", give it a word it will
   * return vector representation of it.
   * for each entry in ngramList (which are lists/arrays of ngrams)
   * it will count how many of the ngrams are present in the word,
   * this will be the value of the coord at this index
   * do so for all 193 coordinates of the vector and return.
   * */
  public int[] makeNeuronArray(String word){
      
      int[] ret = new int[193];
      int index = 0;
      
      for(String[] gram : gramList){
        for(int i = 0; i<gram.length ; i++){
          if(word.contains(gram[i])){
            ret[index]++;
          }
        }
        index++;
      }
      ret[192] = word.length();
      
      return ret;
  }
  /* in order to regularize the data, decided to get rid of "special" character
   * and replace them with non-accented equivalents
   * 
   * param: String filename
   * returns: List<String>, each entry being the word after conversion
   * post: the returned list will be guaranteed spec char free.
   * throws: the usual empty file opening errors when file is empty etc
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
			if(line.matches("[a-zA-Z][a-zA-Z]+"))       //being extra sure
				words.add(line);	
		}
		
		reader.close();
		return words;
	}catch (Exception e){
		System.err.format("Exception occurred trying to read '%s'.", filename);
		return null;
	}
	
	}
  
}
