/******************************************************************************** 
 * Criado em(Created on): 14/08/2018
 * Autor(Author)        : Bruno Lima Neves
 *******************************************************************************/
package com.gympass.test.business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gympass.test.model.KartRace;

/**
 * @author bruno.lima.neves
 */
public class KartRaceBusiness {
	
	/**
	 * The Constant for KART_RACE_TIME_POSITION.
	 */
	private static final int KART_RACE_TIME_POSITION = 0;
	
	/**
	 * The Constant for KART_RACE_ID_POSITION.
	 */
	private static final int KART_RACE_ID_POSITION = 1;
	
	/**
	 * The Constant for KART_RACE_NAME_POSITION.
	 */
	private static final int KART_RACE_NAME_POSITION = 3;
	
	/**
	 * The Constant for KART_RACE_LAP_NUMBER_POSITION.
	 */
	private static final int KART_RACE_LAP_NUMBER_POSITION = 4;
	
	/**
	 * The Constant for KART_RACE_LAP_TIME_POSITION.
	 */
	private static final int KART_RACE_LAP_TIME_POSITION = 5;
	
	/**
	 * The Constant for KART_RACE_LAP_AVERAGE_POSITION.
	 */
	private static final int KART_RACE_LAP_AVERAGE_POSITION = 6;
	
	/**
	 * getKartDataFrom
	 * Get Object List that represents each kart log register from the fileName parameter.
	 * @param fileName the whole file name.
	 * @return List<KartRace>.
	 */
	public static List<KartRace> getKartDataFrom(final String fileName){
		
		final List<KartRace> list = new ArrayList<>();
		KartRace kartRace;
		
		BufferedReader leitor;
		
		try {
			leitor = new BufferedReader(new FileReader(fileName));
			
			//The firt log line will not be considered.
			leitor.readLine();
			
			String linha;
			
			while ((linha = leitor.readLine()) != null) {
				 String info[]  = linha.split("\\s+");
				 
				 kartRace = new KartRace();				 
				 kartRace.setTime(LocalTime.parse(info[KART_RACE_TIME_POSITION]));
				 kartRace.setId(Integer.valueOf(info[KART_RACE_ID_POSITION]));
				 kartRace.setName(info[KART_RACE_NAME_POSITION]);
				 kartRace.setLapNumber(Integer.valueOf(info[KART_RACE_LAP_NUMBER_POSITION]));				 
				 //Java LocalTime Bug to parse without the whole format				 
				 kartRace.setLapTime(LocalTime.parse("00:0"+info[KART_RACE_LAP_TIME_POSITION]));
				 kartRace.setLapAverage(Double.valueOf(info[KART_RACE_LAP_AVERAGE_POSITION].replace(",", ".")));
				 
				 list.add(kartRace);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * Method to print on the console the main information required from the test:
	 * A partir de um input de um arquivo de log do formato acima, montar o resultado da corrida com as seguintes informações:
	 * -> Posição Chegada;
	 * -> Código Piloto;
	 * -> Nome Piloto;
	 * -> Qtde Voltas Completadas;
	 * -> Tempo Total de Prova.
	 * 
	 * @param listRace the kart log Objet list read from the file.
	 */
	public static void executeMainFlow(final List<KartRace> listRace) {
		Stream<KartRace> streamKartRace = listRace.stream();
		
		/*
		 * The Ordering logic is:
		 * 1- Reverse Sort the KartRace List from the lapNumber;
		 * 2- Sort the KartRace from the LapTime;
		 * 3- Distinct the list from the Id attribute (distinct() use the Override KartRace equals method).
		 * 4- Return the Operation in a new List. 
		 */
		List<KartRace> kartResult
			= streamKartRace.sorted(Comparator.comparing(KartRace::getLapNumber).reversed()
									.thenComparing(KartRace::getLapTime))
							.distinct()			
							.collect(Collectors.toList());
		
		int position = 1;
		for(final KartRace item : kartResult) {
			
			System.out.println("--------------");
			System.out.println("Resultado:" );			
			System.out.println("Posição Chegada: " + position);
			System.out.println("Código Piloto: " + item.getId());
			System.out.println("Nome Piloto: " + item.getName());
			System.out.println("Qtde.Voltas Completadas: " + item.getLapNumber());
			
			List<KartRace> listLogFromRacerID = getListFrom(item.getId(), listRace);
			
			System.out.println("Tempo Total de Prova: " + sumLapTimeByPlayer(listLogFromRacerID));
			
			System.out.println("Melhor Volta: " + getBestLapFrom(listLogFromRacerID));
			
			System.out.println("Velocidade Média: " + getAverageFrom(listLogFromRacerID));
			
			System.out.println("--------------");
			position++;
		}
		
		System.out.println("Melhor Volta da Corrida: " + getBestLapTimeFrom(listRace));
		
	}
	
	/**
	 * sumLapTimeByPlayer.
	 * Method to sum all the lap time by pilot id. 
	 * @param listLogFromKacerID list Racer.
	 * @return
	 */
	private static LocalTime sumLapTimeByPlayer(final List<KartRace> listLogFromKacerID) {
		LocalTime lapRacerSum = LocalTime.MIN;

		for(final KartRace item : listLogFromKacerID) {
			final LocalTime lapTimeItem = item.getLapTime();
			
			lapRacerSum
				= lapRacerSum.plusHours(lapTimeItem.getHour())
					   		 .plusMinutes(lapTimeItem.getMinute())
					         .plusSeconds(lapTimeItem.getSecond())
					         .plusNanos(lapTimeItem.getNano());
		}
		
		return lapRacerSum;		
	}
	
	/**
	 * Method to get all the Racer Log by ID.
	 * getListFrom.
	 * @param id RacerID.
	 * @param listRace racerLog List.
	 * @return List<KartRace>.
	 */
	private static List<KartRace> getListFrom(final Integer id, final List<KartRace> listRace) {
		
		Stream<KartRace> stream = listRace.stream();		
		
		// Get all the Kart Race log from the Racer id.
		List<KartRace> collect = stream.filter
				(p -> p.getId().equals(id))
					.collect(Collectors.toList());
		
		return collect;
	}
	
	/**
	 * getBestLapFrom.
	 * Method to get the best Lap time From the Kart Racer.
	 * @param listLogFromRacerID
	 * @return String.
	 */
	private static String getBestLapFrom(final List<KartRace> listLogFromRacerID) {		
		Optional<KartRace> min = listLogFromRacerID.stream().min(Comparator.comparing(KartRace::getLapTime));		
		return min.get().getLapTime().toString() + "(N." + min.get().getLapNumber() + ")";
	}
	
	/**
	 * getAverageFrom.
	 * Method to get the average
	 * @param listLogFromRacerID parameter.
	 * @return
	 */
	private static String getAverageFrom(final List<KartRace> listLogFromRacerID) {
		OptionalDouble average = listLogFromRacerID.stream().mapToDouble(KartRace::getLapAverage).average();
		return String.valueOf(average.getAsDouble());
	}
	
	/**
	 * getBestLapTimeFrom.
	 * Method to get the Best Lap Time from all the Kart Race.
	 * @param listRace KartRace List.
	 * @return String.
	 */
	private static String getBestLapTimeFrom(final List<KartRace> listRace) {
		Optional<KartRace> min = listRace.stream().min(Comparator.comparing(KartRace::getLapTime));
		
		final KartRace bestLap = min.get();
		
		final StringBuilder builder = new StringBuilder();
		builder.append(bestLap.getLapTime())
			   .append(" Piloto: ")
			   .append(bestLap.getId() + " - " + bestLap.getName())
			   .append(" (N. Volta: ").append(bestLap.getLapNumber()).append(")");
		
		return builder.toString();
	}
}