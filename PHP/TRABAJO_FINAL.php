<html>
	<!-- Los campos para la configuracion de la conexion a la bd estan en la linea 72-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	  <style>
	  	
		body{
			background-image: url(http://www.paritariojmc.cl/images/monumento.jpg);
			background-size: cover;
			background-repeat: no-repeat;
			font-family: Verdana, Geneva, sans-serif;
		}
		.notas {
			background-color: white;
			color: black;
			/*padding: 13px;*/
			margin-top: 10px;
			border: 1px solid grey;
		}

		table.notas td {
			padding: 10px;
			transition: 0.5s;
		}
		table.notas tr:hover td{
			background-color: #E2E2E2;
			transition: 0.5s;
		}

		.error{
			border: 1px solid red;
			background-color: #FACBCB;
		}

		.alumno{
			background: white;
			border: 1px solid gray;
			padding: 10px;
		}

		.boton{
			border-radius: 5px;
			color: white;
			background: #37c50b;
			border: 1px solid green;
			padding: 6px;
			font-size: 17px;
			font-weight: bold;
			transition: 0.5s;
		}
		.boton:hover{
			background: #23DA05;
			transition: 0.5s;
		}
		#consultar{
			margin: auto auto;
			background-color: #fff;
			width: 398px;
			padding: 26px;
			border-radius: 14px;
			box-shadow: 0px 0px 16px 0px green;

		}

		.buho {
			height: 200px;
			width: 127px;
			position: fixed;
			bottom: 10px;
			right: 10px;
		}

	  </style>
	</head>
	<body>
		<?php
			header("Content-Type: text/html;charset=utf-8");
			$ip = "localhost";
			$user = "cabros";
			$pw = "tuinf";
			$db = "academico";

			function ShowAlumno($codigox){
				global $ip, $user, $pw, $db;
				$conn = mysqli_connect($ip, $user, $pw);
				if (!$conn){
					print("Error de conexion: " . mysqli_connect_error());
					exit;
				}
				mysqli_select_db($conn, $db);
				mysqli_query($conn, "SET NAMES 'utf8'");
				$Result = mysqli_query($conn, "SELECT alumnos.nombre, alumnos.idCurso, cursos.nombre as curso FROM alumnos, cursos WHERE IdAlumno=$codigox and alumnos.idCurso=cursos.IdCurso;");
				if (!$Result) {
					echo "Error MySQL: " . mysqli_error($conn);
				}
				else{
					if (mysqli_affected_rows($conn) == 0){
						echo "<h3 style=\"color:red;\"> No se encontro ningun alumno con código $codigox</h3>";
					}
					else{
						if($Fila = mysqli_fetch_assoc($Result)) {
							echo "<table class=\"alumno\">
								<tr>
									<td style=\"text-align: right;\">Nombre:</td>
									<td>$Fila[nombre]</td>
								</tr>
								<tr>
									<td style=\"text-align: right;\">Curso:</td>
									<td>$Fila[idCurso] $Fila[curso]</td>
								</tr>
							</table>";
						}
						mysqli_free_result($Result);
						$Result = mysqli_query($conn, "SELECT A.nombre, ROUND(AVG(B.nota)) promedio from ramos A left outer join (SELECT * FROM notas where notas.IdAlumno=$codigox) B on A.IdRamo=B.IdRamo GROUP BY A.IdRamo;");
						if (!$Result) {
							echo "Error MySQL: " . mysqli_error($conn);
						}
						else{
							if (mysqli_affected_rows($conn) == 0){
								echo "<h3 style=\"color:red;\"> Error</h3>";
							}
							else{
								echo "<table class=\"notas\" cellspacing=\"0\">";
								while($Fila = mysqli_fetch_assoc($Result)) {
									echo "<tr class=\"hovereable\">
											<td>$Fila[nombre]</td>
											<td style=\"text-align: right;\">" . (is_null($Fila['promedio']) ? "---" : $Fila['promedio']) . "</td>
										  </tr>";
								}
								echo "<tr><td colspan=\"2\">----------------------------------------------------</td></tr>";
								mysqli_free_result($Result);
								$Result = mysqli_query($conn, "SELECT AVG(ROUND(TMP.promedio)) as pfinal FROM (SELECT alumnos.IdAlumno as alumno, ramos.nombre, AVG(notas.nota) as promedio from alumnos, ramos, notas WHERE alumnos.idAlumno=$codigox AND notas.IdAlumno=alumnos.IdAlumno and notas.IdRamo=ramos.IdRamo group by alumnos.IdAlumno, ramos.nombre ORDER by ramos.IdRamo) as TMP;");

								if (mysqli_affected_rows($conn) == 0){
									echo "<tr><td colspan=2 style=\"color:red;\">No se pudo calcular el promedio</td></tr>";
								}
								else{
									$Fila = mysqli_fetch_assoc($Result);
									echo "<tr><td>Promedio</td><td>". round($Fila['pfinal']) . "</td></tr>";
								}
								echo "</table>";

							}
							mysqli_free_result($Result);
						}
					}
					
				}
				mysqli_close($conn);
			}

			

			$codigo = $nameErr = "";
			$clase = "";
			$str = "";

			if ($_SERVER["REQUEST_METHOD"] == "POST") {
				if(empty($_POST['codigo'])){
					$nameErr = "Se requiere código";
					$clase = "error";
				}
				elseif(!preg_match("/^\d+$/", $_POST['codigo'])){
					$nameErr = "El código debe ser numerico";
					$clase = "error";
					$codigo = $_POST['codigo'];
				}
				elseif(strlen($_POST['codigo']) > 5){
					$nameErr = "El código no debe superar los 5 numeros";
					$clase = "error";
					$codigo = $_POST['codigo'];
				}
				else{
					$codigo = $_POST['codigo'];
					
				}
			}

							
			echo "<center><div id=\"consultar\"><table>
					<tr>
						<form action=\"notas.php\" method=\"post\">
							<td>Código:</td>
							<td><input class=\"$clase\" style=\"padding:7px;\" type=\"text\" name=\"codigo\" value=\"$codigo\"></td>
							<td><input class=\"boton\"type=\"submit\" value=\"Buscar\"></td>
						</form>
					</tr>";
			echo "</table><br>";
			if($nameErr != ""){
				echo "<h3 style=\"color: red;\"><td>$nameErr</h3>";
			}
			if($_SERVER["REQUEST_METHOD"] == "POST" && $clase != "error"){
				ShowAlumno($codigo);
			}
			echo "</div></center>"; 

		?>
		<!--<img class="buho" src="http://pumpernickelpixie.com/wp-content/uploads/2015/06/33.gif"\>-->
	</body>
</html>

