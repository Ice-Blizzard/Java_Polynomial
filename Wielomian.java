package project77;

public class Wielomian {
	// wielomiany reprezentujê jako zmienna ilosc mówi¹c¹ ile mamy
	// niezerowych sk³adniktów i tablicê dwuwymiarow¹ reprezentuj¹c¹ pary
	// a * x^n - a - iloœæ i n - stopieñ
	private int ilosc;
	private double[] wspolczynnik;
	private int[] stopien;
	
	//jednomian a * x^s
	public Wielomian(double a, int s) {
		if (a == 0) {
			ilosc = 0;
		}
		else {
			ilosc = 1;
			this.wspolczynnik = new double[1];
			this.stopien = new int[1];
			this.wspolczynnik[0] = a;
			this.stopien[0] = s;
		}
	}
	
	// n - stopien maksymalnego stopnia, tablica n+1 elemnetów o wspo³czynnikach w kolejnoœci rosn¹cego stopnia
	public Wielomian(int n, double[] tab) {
		ilosc = 0;
		for (int k = 0; k <= n; k++) {
			if (tab[k] != 0) {
				ilosc++;
			}
		}
		if (ilosc != 0) {
			wspolczynnik = new double[ilosc];
			stopien = new int[ilosc];
			int v = 0;
			for (int k = 0; k <= n; k++) {
				if (tab[k] != 0) {
					wspolczynnik[v] = tab[k];
					stopien[v] = k;
					v++;
				}
			}
		}	
	}
	
	public static Wielomian plus(Wielomian jeden, Wielomian dwa) {
		if (jeden.ilosc == 0) {
			return dwa;
		}
		if (dwa.ilosc == 0) {
			return jeden;
		}
		// algorytm g¹siennicy
		Wielomian wynik = new Wielomian(0, 0);
		wynik.ilosc = 0;
		wynik.wspolczynnik = new double[jeden.ilosc + dwa.ilosc];
		wynik.stopien = new int[jeden.ilosc + dwa.ilosc];
		int i = -1;
		int j = -1;
		while (i + j < jeden.ilosc + dwa.ilosc - 2) {
			if (i == jeden.ilosc - 1) {
				wynik.wspolczynnik[wynik.ilosc] = dwa.wspolczynnik[j + 1];
				wynik.stopien[wynik.ilosc] = dwa.stopien[j + 1];
				wynik.ilosc++;
				j++;
			}
			else {
				if (j == dwa.ilosc - 1) {
					wynik.wspolczynnik[wynik.ilosc] = dwa.wspolczynnik[i + 1];
					wynik.stopien[wynik.ilosc] = dwa.stopien[i + 1];
					wynik.ilosc++;
					i++;
				}
				else {
					if (jeden.stopien[i + 1] == dwa.stopien[j + 1]) {
						if ((jeden.wspolczynnik[i + 1] == (-1) * dwa.wspolczynnik[j + 1])) {
							i++;
							j++;
						}
						else {
							wynik.wspolczynnik[wynik.ilosc] = dwa.wspolczynnik[j + 1] +  jeden.wspolczynnik[i + 1];
							wynik.stopien[wynik.ilosc] = dwa.stopien[j + 1];
							wynik.ilosc++;
							i++;
							j++;
						}
					}
					else {
						if (jeden.stopien[i + 1] < dwa.stopien[j + 1]) {
							wynik.wspolczynnik[wynik.ilosc] = dwa.wspolczynnik[i + 1];
							wynik.stopien[wynik.ilosc] = dwa.stopien[i + 1];
							wynik.ilosc++;
							i++;
						}
						else {
							wynik.wspolczynnik[wynik.ilosc] = dwa.wspolczynnik[j + 1];
							wynik.stopien[wynik.ilosc] = dwa.stopien[j + 1];
							wynik.ilosc++;
							j++;
						}
					}
				}
			}
		}
		// pozbywam siê niepotrzebnych pustych pól tablicy
		Wielomian wynik2 = new Wielomian(0, 0);
		wynik2.ilosc = wynik.ilosc;
		if (wynik2.ilosc != 0) {
			wynik2.wspolczynnik = new double[wynik2.ilosc];
			wynik2.stopien = new int[wynik2.ilosc];
			for (int k = 0; k < wynik2.ilosc; k++) {
				wynik2.wspolczynnik[k] = wynik.wspolczynnik[k];
				wynik2.stopien[k] = wynik.stopien[k];
			}
		}
		return wynik2;
	}
	
    public static double spot(double baza, int pot) {
        if (pot == 0)
            return 1;
        if (pot % 2 == 1)
            return spot(baza, pot - 1) * baza;
        else {
            double a = spot(baza, pot / 2);
            return a * a;
        }
    }
	
	public static double punkt(double x, Wielomian w) {
		if (w.ilosc == 0) {
			return 0;
		}
		else {
			double wynik = 0;
			for (int i = 0; i < w.ilosc; i++) {
				wynik = wynik + w.wspolczynnik[i] * spot(x, w.stopien[i]);
			}
			return wynik;
		}
	}
	
	public static Wielomian razy(Wielomian jeden, Wielomian dwa) {
		if (jeden.ilosc == 0) {
			return jeden;
		}
		if (dwa.ilosc == 0) {
			return dwa;
		}
		Wielomian wynik = new Wielomian(0, 0);
		wynik.ilosc = jeden.ilosc * dwa.ilosc;
		wynik.wspolczynnik = new double[jeden.ilosc * dwa.ilosc];
		wynik.stopien = new int[jeden.ilosc * dwa.ilosc];
		int v = 0;
		for (int i = 0; i < jeden.ilosc; i++) {
			for (int j = 0; j < dwa.ilosc; j++) {
				wynik.wspolczynnik[v] = jeden.wspolczynnik[i] * dwa.wspolczynnik[j];
				wynik.stopien[v] = jeden.stopien[i] + dwa.stopien[j];
				v++;
			}
		}
		for (int i = 0; i < wynik.ilosc; i++) {
			for (int j = 1; j < wynik.ilosc - i; j++) {
				if(wynik.stopien[j - 1] > wynik.stopien[j]) {
					double a = wynik.wspolczynnik[j - 1];
					int b = wynik.stopien[j - 1];
					wynik.wspolczynnik[j - 1] = wynik.wspolczynnik[j];
					wynik.stopien[j - 1] = wynik.stopien[j];
					wynik.wspolczynnik[j] = a;
					wynik.stopien[j] = b;
				}
			}
		}
		double a = wynik.wspolczynnik[0];
		int vvv = 0;
		// zliczenie ró¿nych niezerowych stopni
		for (int i = 1; i < wynik.ilosc; i++) {
			if (wynik.stopien[i] != wynik.stopien[i - 1]) {
				if (a != 0) {
					vvv++;
				}
				a = 0;
			}
			a = a + wynik.wspolczynnik[i];
			if (i == (wynik.ilosc - 1)) {
				if (a != 0) {
					vvv++;
				}
			}
		}
		Wielomian wynik2 = new Wielomian(0, 0);
		wynik2.ilosc = vvv;
		wynik2.wspolczynnik = new double[vvv];
		wynik2.stopien = new int[vvv];
		double aa = wynik.wspolczynnik[0];
		int vv = 0;
		// zliczenie ró¿nych niezerowych stopni
		for (int i = 1; i < wynik.ilosc; i++) {
			if (wynik.stopien[i] != wynik.stopien[i - 1]) {
				if (aa != 0) {
					wynik2.wspolczynnik[vv] = aa;
					wynik2.stopien[vv] = wynik.stopien[i - 1];
					vv++;
				}
				aa = 0;
			}
			aa = aa + wynik.wspolczynnik[i];
			if (i == (wynik.ilosc - 1)) {
				if (aa != 0) {
					wynik2.wspolczynnik[vv] = aa;
					wynik2.stopien[vv] = wynik.stopien[i];
					v++;
				}
			}
		}
		return wynik2;
	}
	
	public static void prezentacja(Wielomian jeden) {
		if (jeden.ilosc == 0) {
			System.out.println("0");
		}
		else {
			if (jeden.ilosc == 1) {
				System.out.print(jeden.wspolczynnik[0]);
				System.out.print("x^");
				System.out.println(jeden.stopien[0]);
			}
			else {
				for (int i = 0; i < jeden.ilosc; i++) {
					if (i == 0) {
						System.out.print(jeden.wspolczynnik[i]);
						System.out.print("x^");
						System.out.print(jeden.stopien[i]);
					}
					else {
						if (i == jeden.ilosc - 1) {
							if (jeden.wspolczynnik[i] > 0) {
								System.out.print(" + ");
							}
							System.out.print(jeden.wspolczynnik[i]);
							System.out.print(" ");
							System.out.print("x^");
							System.out.println(jeden.stopien[i]);
						}
						else {
							if (jeden.wspolczynnik[i] > 0) {
								System.out.print(" + ");
							}
							System.out.print(" ");
							System.out.print(jeden.wspolczynnik[i]);
							System.out.print(" ");
							System.out.print("x^");
							System.out.print(jeden.stopien[i]);
						}
					}
				}
			}
		}
	}

}
