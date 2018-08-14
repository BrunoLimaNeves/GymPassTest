/******************************************************************************** 
 * Criado em(Created on): 14/08/2018
 * Autor(Author)        : Bruno Lima Neves
 *******************************************************************************/
package com.gympass.test.main;

import java.util.List;

import com.gympass.test.business.KartRaceBusiness;
import com.gympass.test.model.KartRace;

/**
 * @author bruno.lima.neves
 */
public class Test {
		
	public static void main(String[] args){
		
		List<KartRace> listRace = KartRaceBusiness.getKartDataFrom(args[0]);
		KartRaceBusiness.executeMainFlow(listRace);
		
	}
}