package io.github.darioteles.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import io.github.darioteles.utils.DataUtils;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

	private Integer diaSemana;

	public DiaSemanaMatcher(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean matchesSafely(Date data) {
		// TODO Auto-generated method stub
		return DataUtils.verificarDiaSemana(data, diaSemana);
	}

}