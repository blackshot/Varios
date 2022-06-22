$currentPrincipal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
$isAdmin = $currentPrincipal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

if(!$isAdmin) {
    Write-Host "Ejecuta este script como administrador!"
    exit;
}

function Expand-EnvironmentVariablesRecursively($unexpanded) {
    $previous = ''
    $expanded = $unexpanded
    while($previous -ne $expanded) {
        $previous = $expanded
        $expanded = [System.Environment]::ExpandEnvironmentVariables($previous)
    }
    return $expanded
}

$instalaciones = "C:\\Program Files\\Java";

$Files = @(Get-ChildItem $instalaciones -Attributes Directory)
$Files | % { $i = 0 } {
    $i++;
    Write-Output "$i)   $($_.Name)";
}
do {
    try {
        [int]$seleccion = Read-Host -Prompt 'Seleccionar carpeta: ';

        if($seleccion -In 1..$Files.Count) {
            $carpeta = $Files[$seleccion-1].FullName;
            Write-Output "Seleccionado: $carpeta";
        }
        else {
            $seleccion = $null;
        }
    }
    catch {
        $seleccion = $null;
    }    
} while ($null -eq $seleccion -or $seleccion -eq 0);

[Environment]::SetEnvironmentVariable('JAVA_HOME',$carpeta, 'Machine')
$env:JAVA_HOME = $carpeta;
$env:Path = Expand-EnvironmentVariablesRecursively([System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User"));
Write-Host "JAVA_HOME establecido en: $([Environment]::GetEnvironmentVariable('JAVA_HOME'))')";