package io.github.darioteles.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import io.github.darioteles.utils.DataUtils;

public class DataDiferencialDiasMatcher extends TypeSafeMatcher<Date> {
	
	private Integer diasAdicionais;
	
	public DataDiferencialDiasMatcher(Integer diasAdicionais) {
		this.diasAdicionais = diasAdicionais;
	}
	
	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean matchesSafely(Date data) {
		// TODO Auto-generated method stub
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(diasAdicionais));
	}

}

