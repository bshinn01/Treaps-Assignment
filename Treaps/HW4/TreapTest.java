package HW4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TreapTest {
	
	Treap<Integer> t = new Treap<Integer>();

	@Test
	//add(E key)
	void testadd1() {
		t.add(7);
		assertTrue(t.add(3));
	}
	@Test
	//add(E key, int priority)
	void testadd2() {
		t.add(4 ,19);
		t.add(2 ,31);
		t.add(6 ,70);
		t.add(1 ,84);
		assertFalse(t.add(4,6));
	}
	@Test
	//delete(E key)
	void testdelete() {
		t.add(4 ,19);
		t.add(2 ,31);
		t.add(6 ,70);
		t.add(1 ,84);		
		assertTrue(t.delete(6));
	}
	@Test
	//find(E key)
	void testfind1() {
		t.add(4 ,19);
		t.add(2 ,31);
		t.add(6 ,70);
		t.add(1 ,84);
		assertTrue(t.find(4));
	}
	@Test
	void testfind2() {
		t.add(4 ,19);
		t.add(2 ,31);
		t.add(6 ,70);
		t.add(1 ,84);
		assertFalse(t.find(5));
	}

}
