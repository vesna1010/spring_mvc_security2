package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import college.model.Address;

@Component
public class AddressFormatter implements Formatter<Address> {

	@Override
	public String print(Address address, Locale locale) {
		return address.toString();
	}

	@Override
	public Address parse(String text, Locale locale) throws ParseException {
		String[] arrayAddress = { "", "", "" };
		String[] parts = text.split("-");

		for (int i = 0; i < parts.length; i++) {
			arrayAddress[i] = parts[i].trim();
		}

		return new Address(arrayAddress[0], arrayAddress[1], arrayAddress[2]);
	}

}

