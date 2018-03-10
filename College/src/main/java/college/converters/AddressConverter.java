package college.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import college.model.Address;

@Component
public class AddressConverter implements Converter<String, Address> {

	@Override
	public Address convert(String text) {
		String[] arrayAddress = { "", "", "" };
		String[] parts = text.split("-");

		for (int i = 0; i < parts.length; i++) {
			arrayAddress[i] = parts[i].trim();
		}

		return new Address(arrayAddress[0], arrayAddress[1], arrayAddress[2]);
	}

}
