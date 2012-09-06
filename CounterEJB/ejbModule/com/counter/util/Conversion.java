package com.counter.util;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.counter.dto.ClickDTO;
import com.counter.model.Click;

/**
 * Session Bean implementation class Conversion
 */
@Stateless
@LocalBean
public class Conversion {

	public Conversion() {
	}

	public ClickDTO fromEntity(Click click) {

		ClickDTO result = new ClickDTO(click.getUserId(), click.getLatitude(),
				click.getLongitude(), click.getName(), click.getCount());

		return result;
	}

	public Collection<ClickDTO> convertClicks(Collection<Click> Clicks) {
		Collection<ClickDTO> result = new ArrayList<ClickDTO>();

		ClickDTO ClickDTO;
		for (Click Click : Clicks) {
			ClickDTO = this.fromEntity(Click);
			result.add(ClickDTO);
		}

		return result;
	}

}
