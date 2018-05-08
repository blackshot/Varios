/*#################################################################
###  Creditos a Matias Gaete que hizo todo el esqueleto de la     #
###  clase, yo solo le agregué funcionalidades y corregí errores. #
###################################################################*/

#include "stdafx.h"
#include <iostream>
#include <stdio.h>
#include <string>
#include <conio.h>
#include <windows.h>


using namespace std;

// UTILIDADES //////////////////////////////////////////////////

void gotoxy(int x, int y, int fore = 7, int back = 0)
{
	HANDLE hcon;
	hcon = GetStdHandle(STD_OUTPUT_HANDLE);
	string color;
	switch (back) {
	case 10: color = "A"; break;
	case 11: color = "B"; break;
	case 12: color = "C"; break;
	case 13: color = "D"; break;
	case 14: color = "E"; break;
	case 15: color = "F"; break;
	default: color = to_string(back);
	}
	switch (fore) {
	case 10: color += "A"; break;
	case 11: color += "B"; break;
	case 12: color += "C"; break;
	case 13: color += "D"; break;
	case 14: color += "E"; break;
	case 15: color += "F"; break;
	default: color += to_string(fore);
	}
	color = "0x00" + color;
	DWORD COLORE = strtol(color.c_str(), 0, 0);
	SetConsoleTextAttribute(hcon, COLORE);
	COORD dwPos;
	dwPos.X = x;
	dwPos.Y = y;
	SetConsoleCursorPosition(hcon, dwPos);
}

static bool isFloat(const string& string) {
	string::const_iterator it = string.begin();
	bool puntodecimal = false;
	int minSize = 0;
	if (string.size()>0 && (string[0] == '-' || string[0] == '+')) {
		it++;
		minSize++;
	}
	while (it != string.end()) {
		if (*it == ',') {
			if (!puntodecimal) puntodecimal = true;
			else break;
		}
		else if (!isdigit(*it) && ((*it != 'f') || it + 1 != string.end() || !puntodecimal)) {
			break;
		}
		++it;
	}
	return string.size()>minSize && it == string.end();
}

static bool isNumeric(const string& string)
{
	if (isdigit(string[0]) || (string.size() > 1 && (string[0] == '-' || string[0] == '+')))
	{
		for (string::size_type i{ 1 }; i < string.size(); ++i)
			if (!isdigit(string[i]))
			{
				return false;
			}
		return true;
	}
	return false;
}

// FIN UTILIDADES ////////////////////////////////////////

class TextBox
{
public:
	void Read();
	string GetText();
	string SubString(int Posicion, int Cantidad);
	int ToInt();
	float ToFloat();
	int Len();
	int MaxLen();
	bool Prompt;
	void SetBackColor(int Valor);
	void SetForeColor(int Valor);
	void SetPos(int x, int y);
	COORD GetPos();
	void SetFormat(string Valor);
	int GetBackColor();
	int GetForeColor();
	bool ErrorStatus();
	void Show();
	void SetPreText(string Valor);
	TextBox();
protected:
	int Row;
	int Col;
	int BackColor;
	int ForeColor;
	bool Error;
	void Trim();
	string Text;
	string Format;

};

TextBox::TextBox() {
	BackColor = 0;
	ForeColor = 7;
}

void TextBox::Trim()
{
	size_t inicio = Text.find_first_not_of(' ');
	if (string::npos == inicio)
	{
		Text.clear();
			return;
	}
	size_t fin = Text.find_last_not_of(' ');
	Text = Text.substr(inicio, (fin - inicio + 1));
}

void TextBox::SetFormat(string Valor)
{
	int Contador;
	bool ValidacionOk = true;
	for (Contador = 0; Contador < Valor.length() - 1 && ValidacionOk; Contador++) {
		if (Valor[Contador] != 'A' && Valor[Contador] != '9' && Valor[Contador] != 'X') {
			ValidacionOk = false;
		}
	}
	if (ValidacionOk) {
		Format = Valor;
	}
	else {
		Format = "";
	}
}

string TextBox::GetText()
{
	return Text;
}

void TextBox::Read()
{
	int Contador;
	int Cantidad_Total;
	char Tecla;
	Cantidad_Total = Format.length();
	if (Format == "")
	{
		Format = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	}
	Cantidad_Total = Format.length();
	if (Prompt)
	{
		gotoxy(Col, Row, ForeColor, BackColor);
		for (Contador = 0; Contador < Cantidad_Total; Contador++)
		{
			cout << '.';
		}
	}
	Tecla = 0;
	Text.clear();
	for (Contador = 0; Contador < Cantidad_Total && Tecla != 13; ) {
		gotoxy(Col + Contador, Row, ForeColor, BackColor);
		Tecla = _getch();
		if (Tecla == 8 && Text.length() > 0) {
			Contador--;
			Text = Text.substr(0, Contador);
			if (Prompt)
				cout << Tecla << '.' << Tecla;
			else
				cout << Tecla << ' ' << Tecla;
		}
		else {
			switch (Format[Contador]) {
			case 'A':
				if ((Tecla >= 'a' && Tecla <= 'z') || (Tecla >= 'A' && Tecla <= 'Z')) {
					cout << Tecla;
					Text = Text + Tecla;
					Contador++;
				}
				break;
			case '9':
				if ((Tecla >= '0' && Tecla <= '9')) {
					cout << Tecla;
					Text = Text + Tecla;
					Contador++;
				}
				break;
			case 'X':
				if (Tecla != 8 && Tecla != 13) {
					cout << Tecla;
					Text = Text + Tecla;
					Contador++;
				}
				break;
			}
		}

	}
	Trim();
	Show();
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 7);
}

int TextBox::ToInt()
{
	if (isNumeric(Text))
		return stoi(Text);
	else {
		Error = true;
		return -1;
	}
}

float TextBox::ToFloat()
{
	if (isFloat(Text))
		return stof(Text);
	else {
		Error = true;
		return -1.0f;
	}
}

string TextBox::SubString(int Posicion, int Cantidad)
{
	string aux;
	if (Text.length() >= Posicion + Cantidad)
	{
		for (int i = Posicion; i < Cantidad + Posicion; i++)
		{
			aux = aux + Text.substr(i, 1);
		}
	}
	else
	{
		cout << endl << "La cantidad excede al largo del texto" << endl;
		Error = true;
	}
	return aux;
}

int TextBox::Len()
{
	return Text.length();
}

int TextBox::MaxLen()
{
	return Format.length();
}

void TextBox::SetBackColor(int Valor)
{
	if (0 < Valor < 15)
		BackColor = Valor;
	else
	{
		BackColor = 0;
		Error = true;
	}
}

void TextBox::SetForeColor(int Valor)
{
	if (0 < Valor < 15)
		ForeColor = Valor;
	else
	{
		ForeColor = 7;
		Error = true;
	}
}

void TextBox::SetPos(int x, int y)
{
	Row = y;
	Col = x;
}

COORD TextBox::GetPos()
{
	COORD Pos;
	Pos.X = Col;
	Pos.Y = Row;
	return Pos;
}

int TextBox::GetBackColor()
{
	return BackColor;
}

int TextBox::GetForeColor()
{
	return ForeColor;
}

bool TextBox::ErrorStatus()
{
	return Error;
}

void TextBox::Show()
{
	gotoxy(Col, Row, ForeColor, BackColor);
	for (int Contador = 0; Contador < Format.length(); Contador++)
	{
		if (Text.length() > 0 && Contador < Text.length())
		{
			cout << Text;
			Contador = Text.length();
		}
		cout << ' ';
	}
}

void TextBox::SetPreText(string Valor)
{
	Text = Valor;
}

int main()
{
	TextBox Nombre;
	TextBox Apellidos;

	Nombre.Prompt = true;
	Nombre.SetPos(35, 3);
	Nombre.SetForeColor(0);
	Nombre.SetBackColor(15);
	Nombre.SetFormat("XXXXXXXXXXXXXXX");
	Nombre.SetPreText("Nombre");
	Nombre.Show();
	Apellidos.SetPos(Nombre.GetPos().X + Nombre.MaxLen() + 1, Nombre.GetPos().Y);
	Apellidos.SetForeColor(13);
	Apellidos.SetBackColor(8);
	Apellidos.Prompt = true;
	Apellidos.SetFormat("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	Apellidos.SetPreText("Apellidos");
	Apellidos.Show();
	_getch();
	Nombre.Read();
	Apellidos.Read();
	//gotoxy(0, 2);
	cout << endl << "Bienvenido " << Nombre.GetText() << " " << Apellidos.GetText() << endl;
	cout << endl << "Valor ingresado: " << Nombre.GetText() << " Largo: " << Nombre.Len() << endl
		<< "Substring(0, 3): " << Nombre.SubString(0, 3) << endl;

	system("pause");

	return 0;
}