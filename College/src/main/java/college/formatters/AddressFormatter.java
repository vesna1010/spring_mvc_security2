package college.formatters;

import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

import college.model.Address;

public class AddressFormatter implements Formatter<Address> {

	@Override
	public String print(Address address, Locale locale) {
		if (address == null)
			return "";
		else
			return address.toString();
	}

	@Override
	public Address parse(String text, Locale locale) throws ParseException {
		Address address = new Address();
		String[] array ={"", "", ""};
		if (text != null) {
			String[] parts = text.split("-");
			for (int i = 0; i < parts.length; i++)
				array[i] = parts[i].trim();
		}
		address.setCity(array[0]);
		address.setStreet(array[1]);
		address.setState(array[2]);
		return address;
	}

}
