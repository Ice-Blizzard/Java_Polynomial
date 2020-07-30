package project77;

public class Main {

	public static void main(String[] args) {
		Wielomian w1 = new Wielomian(4.1, 3);
		Wielomian w2 = new Wielomian(0, 4);
		
		Wielomian.prezentacja(w2);
		Wielomian.prezentacja(w1);
		
		double[] tablica = new double[10];
		 
 		for (int i = 0; i < 10; i++) {
 			tablica[i] = i + 0.1;
 		}
 		Wielomian w3 = new Wielomian(9, tablica);
 		Wielomian.prezentacja(w3);
 		System.out.println(Wielomian.punkt(3.1, w3));
 		Wielomian w4 = Wielomian.plus(w1, w3);
 		Wielomian.prezentacja(w4);
 		Wielomian w5 = Wielomian.razy(w1, w3);
 		Wielomian.prezentacja(w5);
	}

}
