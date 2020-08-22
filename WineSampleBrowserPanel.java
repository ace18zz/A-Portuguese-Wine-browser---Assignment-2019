/**
 * 
 */
package assignment2019.codeprovided;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

/**
 * @author ace18zz
 * This class is extended AbstractWineSampleBrowserPanel
 * to create the listener and add the functions
 *  
 */
public class WineSampleBrowserPanel extends AbstractWineSampleBrowserPanel{


	// extends from Abstract
	public WineSampleBrowserPanel(AbstractWineSampleCellar cellar) {
		super(cellar);
	}

	@Override
	public void addListeners() {
		// add all the ActionListener
		buttonAddFilter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				addFilter();

			}
		});
		//clean all the filters
		buttonClearFilters.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {

				clearFilters();

			}

		});
		// to update
		comboWineTypes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				updateWineList();
				updateStatistics();
			}

		});	

	}

	@Override
	public void addFilter() {
		String wineproperty = (String)comboProperties.getSelectedItem();
		String op = (String) comboOperators.getSelectedItem();
		String input = value.getText();
		//fixed different name 
		if(wineproperty.equals("Citric Acidity")) {
			wineproperty="Citric Acid";
		}
		if(wineproperty.equals("pH")) {
			wineproperty="PH";
		}
		double d = Double.parseDouble(input);
		WineProperty news = WineProperty.valueOf(wineproperty.replace(" ", ""));
		QueryCondition nq = new QueryCondition(news,op,d);
		//creat a new query condition list to keep the result
		queryConditionList.add(nq);
		queryConditionsTextArea.setText(queryConditionList.toString());

		updateWineList();
	}

	@Override

	public void clearFilters() {
		// clean up the panel
		queryConditionList.clear();
		queryConditionsTextArea.setText("");

	}

	@Override
	public void updateStatistics() {

		// Check filtered data are not zero than continue that steps

		if(filteredWineSampleList.size() ==0) {
			statisticsTextArea.append("No data");
			return;
		}
		// print out the titles
		statisticsTextArea.setText("");
		statisticsTextArea.append("\t");
		for (WineProperty pname: WineProperty.values()) {
			statisticsTextArea.append(pname.toString()+"	");
		}
		statisticsTextArea.append("\n");
		// print out the max value of each property
		statisticsTextArea.append(" Max\t");
		for (WineProperty pname: WineProperty.values()) {
			List<WineSample> max1 = WineSampleCellar.getSaveSamples(filteredWineSampleList, pname);
			statisticsTextArea.append(max1.get(max1.size()-1)+"\t");
			if(pname.toString().length()>15) {
				statisticsTextArea.append("\t");
			}

		}
		// print out the min value of each property
		statisticsTextArea.append("\n Min\t");
		for (WineProperty pname: WineProperty.values()) {
			List<WineSample> min = WineSampleCellar.getSaveSamples(filteredWineSampleList, pname);
			statisticsTextArea.append(min.get(0)+"\t");
			if(pname.toString().length()>15) {
				statisticsTextArea.append("\t");
			}

		}
		// print out the avg value of each property
		statisticsTextArea.append("\n Average\t");
		//print out the WineProperty as title
		for (WineProperty pname: WineProperty.values()) {
			double sum = 0;
			for (int i = 0;i<filteredWineSampleList.size();i++) {
				sum += filteredWineSampleList.get(i).getProperty(pname);//get the sum of the value 
			}
			double avg = sum/filteredWineSampleList.size();
			statisticsTextArea.append((String.format("%.2f",avg))+"\t");
			// to make sure the number and the name at same level
			if(pname.toString().length()>15) {
				statisticsTextArea.append("\t");
			}

		}
		// print out all the number
		statisticsTextArea.append("\n");
		statisticsTextArea.append("Showing "+ filteredWineSampleList.size()+ "out of 6497");


	}

	@Override
	public void updateWineList() {
		// call the executeQuery to update the filteredWineSampleList
		executeQuery();
		if(filteredWineSampleList.size() ==0) {
			filteredWineSamplesTextArea.setText("No data");
			return;
		}
		// print put the titles
		filteredWineSamplesTextArea.setText("");
		filteredWineSamplesTextArea.append("Wine Type\t"+"ID\t");
		for (WineProperty pname: WineProperty.values()) {
			filteredWineSamplesTextArea.append(pname.toString()+"\t");
		}
		filteredWineSamplesTextArea.append("\n");
		for(WineSample samples:filteredWineSampleList) {
			//print out the wine samples	
			filteredWineSamplesTextArea.append(samples.getType()+"\t"+samples.getId()
			+"\t"+ samples.getFixedAcidity()+"\t"+ samples.getVolatileAcidity()
			+"\t"+ samples.getCitricAcid()+"\t"+ samples.getResidualSugar()
			+"\t"+samples.getChlorides()+"\t"+ samples.getFreeSulfurDioxide()
			+"\t"+"\t"+ samples.getTotalSulfurDioxide()+"\t"+"\t"+ samples.getDensity()
			+"\t"+ samples.getpH()+"\t"+ samples.getSulphates()
			+"\t"+ samples.getAlcohol()+"\t"+ samples.getQuality());
			filteredWineSamplesTextArea.append("\n");
		

		}
		updateStatistics();


	}

	@Override
	public void executeQuery() {
		// get all the result from search
		//update filteredWineSampleList
		String list = (String) comboWineTypes.getSelectedItem();
		WineType winet = WineType.valueOf(list.replace(" ", ""));
		List<WineSample> checklist = new ArrayList<>();
		if(winet.equals(WineType.ALL)) {
			checklist= cellar.getWineSampleList(WineType.ALL);
		}else if (winet.equals(WineType.RED)) {
			checklist= cellar.getWineSampleList(WineType.RED);
		}else {
			checklist= cellar.getWineSampleList(WineType.WHITE);
		}

		Query querys = new Query(checklist,queryConditionList,winet);
		filteredWineSampleList = querys.solveQuery();

	}



}
