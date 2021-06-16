/**
 * 
 */
package assignment2019.codeprovided;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author ace18zz
 * This class is extended AbstractWineSampleCellar
 * Include reading functionalities of wine sample data sets and queries method
 */
public class WineSampleCellar extends AbstractWineSampleCellar{

	List<Query> qlist = new ArrayList<>();
	// WineProperty propertyToCompare;
	public WineSampleCellar(String redWineFilename, String whiteWineFilename, String queryFilename) {
		super(redWineFilename, whiteWineFilename, queryFilename);
	}



	@Override
	public List<Query> readQueries(List<String> queryList) {

		Iterator<String> it =  queryList.iterator();
		it.next();//read and take away the first word "select"
		while(it.hasNext()) {
			List<WineType> wslist = new ArrayList<WineType>();
			List<QueryCondition> queryConditionList = new ArrayList<>();
			//read the next word to make sure the type of the wine
			do {
				WineType type = WineType.valueOf(it.next().toUpperCase());
				wslist.add(type);

			}

			while(it.next().equals("or"));//if read word "or" than do that loop again to get another type
			do {
				WineProperty wp = WineProperty.fromFileIdentifier(it.next());
				// read op as operator
				String op = it.next();
				Double va = Double.valueOf(it.next());// get the value
				QueryCondition con = new QueryCondition(wp,op,va);
				queryConditionList.add(con);
			}
			while(it.hasNext()&&it.next().equals("and"));// if there are next word and that word is "and" then do this loop again
			WineType Wine;
			/*check the type in the list 
			 * if there are two type here
			 * which means the wine type here should 
			 * be All
			 */
			if(wslist.size()==2) {
				Wine = WineType.ALL;
			}else {
				Wine =wslist.get(0);
			}
			Query qy= new Query(this.getWineSampleList(Wine),queryConditionList,Wine);
			qlist.add(qy);

		}

		return qlist;

	}

	@Override
	public void updateCellar() {
		// defined the WineTypr.All
		List<WineSample> alllist = new ArrayList<WineSample>();

		alllist.addAll(getWineSampleList(WineType.RED));
		alllist.addAll(getWineSampleList(WineType.WHITE));

		wineSampleRacks.put(WineType.ALL, alllist);

	}

	@SuppressWarnings("unchecked")
	/* this method is used for 
	 * 
	 */
	public static List<WineSample> getSaveSamples (List<WineSample>list,WineProperty property){
		@SuppressWarnings("rawtypes")
		List wineps = new ArrayList<>();
		for(int i =0;i<list.size();i++) {
			wineps.add(list.get(i).getProperty(property));
			Collections.sort(wineps);


		}
		return wineps;

	}


	@Override
	public void displayQueryResults(Query query) {
		// TODO Auto-generated method stub

		List<WineSample> wineSamples=query.solveQuery();
		System.out.println("****** Query  ******");
		System.out.println("* select "+query.getWineType()+" where "+ query.getQueryConditionList()+" *" );
		System.out.println("In total, "+wineSamples.size() +" "+ query.getWineType() +" wine samples match your query");
		System.out.println("The list of "+ query.getWineType() +" wine samples are:");	
		for(WineSample samples:wineSamples) {
			System.out.println("[ "+samples.getType()+" wine, sample #"+samples.getId()
			+" f_acid: "+ samples.getFixedAcidity()+" v_acid: "+ samples.getVolatileAcidity()
			+" c_acid: "+ samples.getCitricAcid()+" r_sugar: "+ samples.getResidualSugar()
			+" chlorid: "+ samples.getChlorides()+" f_sulf: "+ samples.getFreeSulfurDioxide()
			+" t_sulf: "+ samples.getTotalSulfurDioxide()+" dens: "+ samples.getDensity()
			+" pH: "+ samples.getpH()+" sulph: "+ samples.getSulphates()
			+" alc: "+ samples.getAlcohol()+" qual: "+ samples.getQuality()+" ]");

		}
		System.out.println("********************");
	}


	@Override
	public List<WineSample> bestQualityWine(WineType wineType) {
		// TODO Auto-generated method stub
		List<WineSample> alllist = getWineSampleList(WineType.ALL);

		double max = 0;
		for (WineSample allwine : alllist) {

			if (allwine.getQuality() > max) {
				max = allwine.getQuality();

			}
		}
		for (WineSample allwine : alllist) {
			if (allwine.getQuality() == max ) {
				int id = allwine.getId();
				WineType type = allwine.getType();
				//				
				System.out.println("* Wine ID "+id+" of type "+type+" with a quality score of "+max );
			}
		}	
		return alllist;
	}

	@Override
	public List<WineSample> worstQualityWine(WineType wineType) {
		// TODO Auto-generated method stub
		List<WineSample> alllist = getWineSampleList(WineType.ALL);

		double min = 10;
		for (WineSample wwine : alllist) {
			if (wwine.getQuality() < min) {
				min = wwine.getQuality();
			}
		}
		for (WineSample wwine : alllist) {
			if (wwine.getQuality() == min ) {
				int id = wwine.getId();
				WineType type = wwine.getType();				
				System.out.println("* Wine ID "+id+" of type "+type+" with a quality score of "+min );
			}
		}	
		return alllist;
	}


	@Override
	public List<WineSample> highestPH(WineType wineType) {
		List<WineSample> alllist = getWineSampleList(WineType.ALL);

		double max = 0;
		for (WineSample Hph : alllist) {
			if (Hph.getpH() > max) {
				max = Hph.getpH();
			}
		}
		for (WineSample Hph : alllist) {
			if (Hph.getpH() == max ) {
				int id = Hph.getId();
				WineType type = Hph.getType();				
				System.out.println("* Wine ID "+id+" of type "+type+" with a PH score of "+max );
			}
		}	
		return alllist;
	}


	@Override
	public List<WineSample> lowestPH(WineType wineType) {
		List<WineSample> alllist = getWineSampleList(WineType.ALL);

		double min = 10;
		for (WineSample lph : alllist) {
			if (lph.getpH() < min) {
				min = lph.getpH();
			}
		}
		for (WineSample lph : alllist) {
			if (lph.getpH() == min ) {
				int id = lph.getId();
				WineType type = lph.getType();				
				System.out.println("* Wine ID "+id+" of type "+type+" with a PH score of "+min );
			}
		}	
		return alllist;
	}


	@Override
	public double highestAlcoholContent(WineType wineType) {
		List<WineSample> rlist = getWineSampleList(WineType.RED);

		double max = 0;
		for (WineSample hal : rlist) {
			if (hal.getAlcohol() > max) {
				max = hal.getAlcohol();
			}
		}	
		System.out.println("*The highest value of alcohol grade of red wines :" +max  );
		return max;
	}



	@Override
	public double lowestCitricAcid(WineType wineType) {
		List<WineSample> wlist = getWineSampleList(WineType.WHITE);

		double min = 10 ;
		for (WineSample lca : wlist) {
			if (lca.getCitricAcid()< min) {
				min = lca.getCitricAcid();
			}
		}	
		System.out.println("* The lowest value of citric acid of white wines :" +min  );
		return min;
	}

	@Override
	public double averageAlcoholContent(WineType wineType) {
		List<WineSample> wlist = getWineSampleList(WineType.WHITE);


		double sum = 0;
		for (WineSample aac : wlist) {
			sum += aac.getAlcohol();
		}
		double avg = sum/wlist.size();
		System.out.println("* The average value of alcohol grade of white wines :" +avg  );

		return avg;

	}

}
