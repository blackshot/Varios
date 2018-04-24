import java.util.*;
import java.math.*;


class MergeSort
{
	public static List<Integer> listado = new ArrayList<Integer>();
	public static void main(String[] args) {
		listado.add(1);
		listado.add(3);
		listado.add(1);
		listado.add(9);
		listado.add(7);
		listado.add(4);
		listado.add(1);
		listado.add(8);
		listado.add(2);
		listado.add(1);
		listado.add(0);
		listado.add(9);

		merge_sort(listado);
		for (Integer num : listado ) {
			System.out.println(num);
		}
		System.out.println();
	}

	public static void merge_sort(final List<Integer> lista)
	{
		if (lista.size() != 1)
		{
			List<Integer> izquierda = new ArrayList<Integer>();
			List<Integer> derecha = new ArrayList<Integer>();
			boolean logicalswitch = true;
			while (!lista.isEmpty()) {
				if(logicalswitch)
					izquierda.add(lista.remove(0));
				else
					derecha.add(lista.remove(0));
				logicalswitch = !logicalswitch;
			}		

			merge_sort(izquierda);
			merge_sort(derecha);

			lista.addAll(merge(izquierda, derecha));
		}
	}

	public static List<Integer> merge(List<Integer>izquierda, List<Integer>derecha)
	{
		List<Integer> resultado = new ArrayList<>();


		while (!izquierda.isEmpty() && !derecha.isEmpty())
		{
			if(izquierda.get(0).compareTo(derecha.get(0)) <= 0)
				resultado.add(izquierda.remove(0));
			else
				resultado.add(derecha.remove(0));
		}
        while (!izquierda.isEmpty())
        	resultado.add(izquierda.remove(0));

        while(!derecha.isEmpty())
        	resultado.add(derecha.remove(0));
		return resultado;
	}

}