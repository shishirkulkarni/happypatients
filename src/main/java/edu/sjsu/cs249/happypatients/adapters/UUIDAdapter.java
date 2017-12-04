package edu.sjsu.cs249.happypatients.adapters;

import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UUIDAdapter extends XmlAdapter<String, UUID> {

	@Override
	public String marshal(UUID v) throws Exception {
		// TODO Auto-generated method stub
		return v.toString();
	}

	@Override
	public UUID unmarshal(String v) throws Exception {
		// TODO Auto-generated method stub
		return UUID.fromString(v);
	}

}
