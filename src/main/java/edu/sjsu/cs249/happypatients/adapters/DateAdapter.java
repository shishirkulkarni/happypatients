package edu.sjsu.cs249.happypatients.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date>{
	private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public String marshal(Date v) throws Exception {
		// TODO Auto-generated method stub
		return fmt.format(v);
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		try {
			return fmt.parse(v);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
