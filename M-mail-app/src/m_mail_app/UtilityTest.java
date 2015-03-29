package m_mail_app;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilityTest {
	Utility utl = new Utility();
	
	
	@Test
	public void testSearch(){
		utl.loadEmails();
		int y = utl.emails.size();
        utl.emails.add("kestas");	        
		assertTrue(utl.search(utl.emails, "kestas"), true);
		assertFalse(utl.search(utl.emails, "dscdccewcewcwecewcew"), false);
		System.out.println();
	}

	@Test
	public void testCollections() {
		
		String name = "Adam";
		String name2 = "Kestas";
		
		utl.addNames(name);
		utl.addPasswords(name);	
		utl.names.remove(name2);
		assertEquals("Collection size reduced by one", utl.names.size() , 1);
		assertEquals("Collection size must increse by one string",  utl.names.size(), 1);
		assertNotNull("Collection should caontain at least one String", utl.names.size() > 0);
		
		utl.addPasswords(name);
		utl.addPasswords(name2);
		
		assertSame(name, utl.passwords.get(1));		
		assertNotNull(utl.passwords.size() > 0);
		assertNotSame(name2, utl.passwords.get(0));
		
		utl.passwords.remove(name2);
		assertTrue(utl.passwords.size() == 2);

		
	}

	 
	 
	 



	

}
