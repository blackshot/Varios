import java.util.Scanner;

class Programa{
	public static void main(String[] args) {
		while(true){
			Scanner sc = new Scanner(System.in);

			System.out.print("Ingrese numero: "); 
			System.out.println("120% =" + (Integer.parseInt(sc.nextLine())*1.2f));
		}
	}
}