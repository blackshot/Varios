class Marino extends Animal {
	private String tipoAgua;


	public Marino(String tipoAgua){
		super();
		this.tipoAgua = tipoAgua;
	}

	@Override
	public void detalles(){
		super.detalles();
		System.out.println("Precio: $" + this.precio);
		System.out.println("Tipo: Marino | Tipo de Agua: " + this.tipoAgua);
		System.out.println("=========================================================");
	}

	public String getTipoAgua() {
	    return tipoAgua;
	}

	public void setTipoAgua(String tipoAgua) {
	    this.tipoAgua = tipoAgua;
	}
}