import java.util.*;

class Animal{

	protected String nombre;
	protected String especie;
	protected int edadActual;
	protected int edadLimite;
	protected int precio;



	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("MARINO:");
		Marino mar = new Marino("X");
		System.out.print("Ingrese nombre:"); 		mar.setNombre(sc.nextLine());
		System.out.print("Ingrese Especie: "); 		mar.setEspecie(sc.nextLine());
		System.out.print("Ingrese Edad: "); 		mar.setEdadActual(Integer.parseInt(sc.nextLine()));
		System.out.print("Ingrese Edad Limite: ");	mar.setEdadLimite(Integer.parseInt(sc.nextLine()));
		System.out.print("Ingrese Precio: $"); 		mar.setPrecio(Integer.parseInt(sc.nextLine()));
		System.out.print("Ingrese Tipo de agua: ");	mar.setTipoAgua(sc.nextLine());

		mar.detalles();
		System.out.println("TERRESTRE: ");
		Terrestre ter = new Terrestre();
		System.out.print("Ingrese nombre:"); 				ter.setNombre(sc.nextLine());
		System.out.print("Ingrese Especie: "); 				ter.setEspecie(sc.nextLine());
		System.out.print("Ingrese Edad: "); 				ter.setEdadActual(Integer.parseInt(sc.nextLine()));
		System.out.print("Ingrese Edad Limite: ");			ter.setEdadLimite(Integer.parseInt(sc.nextLine()));
		System.out.print("Ingrese Precio: $"); 				ter.setPrecio(Integer.parseInt(sc.nextLine()));
		System.out.print("Ingrese Tipo de Movimiento: ");	ter.setTipoMovimiento(sc.nextLine());
		ter.detalles();

	}

	public Animal(){

	}

	public int annosPorVivir(){
		return this.edadLimite - this.edadActual;
	}

	public void detalles(){
		System.out.println("=========================================================");
		System.out.println("Nombre: " + this.nombre + " | Especie: " + this.especie);
		System.out.println("Edad: " + this.edadActual + " | Edad Limite: " + this.edadLimite);
		System.out.println("Edad por vivir:" + annosPorVivir());
	}







	public String getEspecie() {
	    return especie;
	}

	public void setEspecie(String especie) {
	    this.especie = especie;
	}

	public String getNombre() {
	    return nombre;
	}

	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}

	public int getEdadActual() {
	    return edadActual;
	}

	public void setEdadActual(int edadActual) {
		if(edadActual >= 0)
	    	this.edadActual = edadActual;
	    else
	    	System.out.println("No puede ser edad negativa.");
	}

	public int getEdadLimite() {
	    return edadLimite;
	}

	// EJEMPLO
	public void setEdadLimite(int edadLimite) {
		if (edadLimite >= 0)
	    	this.edadLimite = edadLimite;
	    else
	    	System.out.println("No puede ser edad negativa.");
	}



	public int getPrecio() {
	    return precio;
	}

	public void setPrecio(int precio) {
		if(precio >= 0)
	    	this.precio = precio;
	     else
	    	System.out.println("No puede ser precio negativo.");
	}
}
