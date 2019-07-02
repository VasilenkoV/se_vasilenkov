package sef.module9.activity;

import java.util.*;

/**
 * Implementation of a Radar 
 * 
 *
 */
public class RadarImpl implements Radar{


	private List<RadarContact> contactList = new ArrayList<>();
	/**
	 *  Constructs a new Radar 
	 */
	public RadarImpl(){

	}
	
	
	/* (non-Javadoc)
	 * @see sef.module8.activity.Radar#addContact(sef.module8.activity.RadarContact)
	 */
	public RadarContact addContact(RadarContact contact) {
		contactList.add(contact);
		return contact;
	}

	/* (non-Javadoc)
	 * @see sef.module8.activity.Radar#getContact(java.lang.String)
	 */
	public RadarContact getContact(String id) {
		for (RadarContact radarContact : contactList) {
			if (radarContact.getContactID().equals(id)) {
				return radarContact;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see sef.module8.activity.Radar#getContactCount()
	 */
	public int getContactCount() {

		return contactList.size();
	}

	/* (non-Javadoc)
	 * @see sef.module8.activity.Radar#removeContact(java.lang.String)
	 */
	public RadarContact removeContact(String id) {
		for (RadarContact radarContact : contactList) {
			if (radarContact.getContactID().equals(id)) {
				contactList.remove(radarContact);
				return radarContact;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see sef.module8.activity.Radar#returnContacts()
	 */
	public List<RadarContact> returnContacts() {
		return new ArrayList<>(contactList);
	}

	/* (non-Javadoc)
	 * @see sef.module8.activity.Radar#returnContacts(java.util.Comparator)
	 */
	public List<RadarContact> returnContacts(Comparator<RadarContact> comparator) {
		List<RadarContact> returnContr = new ArrayList<>(contactList);
		returnContr.sort(comparator);
		return returnContr;
//		return Collections.sort(arg0, new DistanceComparator());
	}

	
}
