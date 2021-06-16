/**
 * 
 */
package assignment2019.codeprovided;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
//import java.awt.List;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import java.util.List;

/**
 * @author ace18zz
 * This class include the main  
 * and print out 10 questions, create the new window for GUI and 
 * change the size of GUI window
 */
public class WineSampleBrowser extends JFrame{


	/**
	 * @param args
	 */
	//	public int getWineSampleCount(WineType wineType) {
	//		return 0;
	//	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			args = new String[] {
					"winequality-red.csv",
					"winequality-white.csv",
					"queries.txt"			
			};
		}

		//Instance variables
		String redWineFile = args[0];
		String whiteWineFile = args[1];
		String queriesFile = args[2];

		WineSampleCellar cellar = new WineSampleCellar(redWineFile,whiteWineFile,queriesFile);

		WineSampleBrowser wsb = new WineSampleBrowser();
		Container contentPane = wsb.getContentPane();
		WineSampleBrowserPanel wsbp = new WineSampleBrowserPanel(cellar);


		contentPane.add(wsbp);
		// create the new GUI window
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		wsb.setSize((int)(screen.width/1.5),(int)(screen.height/1.5));	
		wsb.setLocation(new Point((int)(screen.width/5),(int)(screen.height/5)));

		wsb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wsb.setVisible(true);


		int whiteNumber = cellar.getWineSampleCount(WineType.WHITE);
		int redNumber = cellar.getWineSampleCount(WineType.RED);
		int allNumber = cellar.getWineSampleCount(WineType.ALL);
		// 10 questions of part one in hand out
		System.out.print("Q1. Total number of wine samples: ");
		System.out.println(allNumber);
		System.out.print("Q2. Number of RED wine samples: ");
		System.out.print(redNumber);
		System.out.print(" out of ");
		System.out.println(allNumber);
		System.out.print("Q3. Number of WHITE wine samples:");
		System.out.print(whiteNumber);
		System.out.print(" out of ");
		System.out.println(allNumber);
		System.out.println("Q4. The best quality wine samples are: " );
		cellar.bestQualityWine(WineType.ALL);
		System.out.println("Q5. The worst quality wine samples are: " );
		cellar.worstQualityWine(WineType.ALL);
		System.out.println("Q6. The highest PH of wine samples are: " );
		cellar.highestPH(WineType.ALL);
		System.out.println("Q7. The lowest PH of wine samples are: " );
		cellar.lowestPH(WineType.ALL);
		System.out.println("Q8. The highest value of alcohol grade of red wine samples are: " );
		cellar.highestAlcoholContent(WineType.RED);
		System.out.println("Q9. The the lowest value of citric acid of white wines: " );
		cellar.lowestCitricAcid(WineType.WHITE);
		System.out.println("Q10. The the average value of alcohol grade of white wines: " );
		cellar.averageAlcoholContent(WineType.WHITE);


		java.util.List<String> textQueries=  AbstractWineSampleCellar.readQueryFile(queriesFile);
		List<Query> qlist=cellar.readQueries(textQueries);
		//print out every query in the query list on console
		for(Query qs:qlist) {
			cellar.displayQueryResults(qs);
		}


	}
}



