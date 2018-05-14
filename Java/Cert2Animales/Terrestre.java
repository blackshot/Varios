class Terrestre extends Animal{
	private String tipoMovimiento;

	public Terrestre(){
		super();
	}

	@Override
	public void detalles(){
		System.out.println("=========================================================");
		System.out.println("Nombre: " + this.nombre + " | Especie: " + this.especie);
		System.out.println("Edad: " + this.edadActual + " | Edad promedio: " + this.edadLimite);
		System.out.println("Tipo: Terrestre | Tipo de Mov: " + this.tipoMovimiento);
		System.out.println("Precio: $" + precioEspecial());
		System.out.println("=========================================================");
	}

	public int precioEspecial(){
		float vida;
		int auxPrecio;
		auxPrecio = 0;
		vida = (this.edadActual * 100.0f) / this.edadLimite;  
		System.out.println("DEBUG: " + vida);
		if (vida <= 20.0f)
			auxPrecio = 3000;
		else if(vida > 20.0f && vida < 60.0f)
			auxPrecio = 1000;
		else
			auxPrecio = 0;

		return this.precio + auxPrecio;
	}

	public String getTipoMovimiento() {
	    return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
	    this.tipoMovimiento = tipoMovimiento;
	}
}