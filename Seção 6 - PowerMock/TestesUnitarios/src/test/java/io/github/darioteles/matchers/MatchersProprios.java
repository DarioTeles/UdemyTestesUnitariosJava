package io.github.darioteles.matchers;

import java.util.Calendar;

/**
 * Exemplificação do uso de Matchers proprios.
 */
public class MatchersProprios {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencialDiasMatcher eHojeComDiferecialDeDias(Integer dias) {
		return new DataDiferencialDiasMatcher(dias);
	}
	
	public static DataDiferencialDiasMatcher eHoje() {
		return eHojeComDiferecialDeDias(0);
	}
	
}
