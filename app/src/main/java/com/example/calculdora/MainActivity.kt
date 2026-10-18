package com.example.calculdora

/**
 * Calculdora - A simple calculator app.
 *
 * Author: Marvin
 * Created: 2026
 * Language: Kotlin + Jetpack Compose
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculdora.ui.theme.CalculdoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculdoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculadora(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Calculadora(modifier: Modifier = Modifier) {
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var operador by remember { mutableStateOf("+") }
    var resultado by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Calculadora",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = numero1,
            onValueChange = { numero1 = it },
            label = { Text("Primer número") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = numero2,
            onValueChange = { numero2 = it },
            label = { Text("Segundo número") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pantalla de la calculadora
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = resultado,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de operador, todos con el mismo ancho
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("+", "-", "×", "÷").forEach { op ->
                if (op == operador) {
                    Button(
                        onClick = { operador = op },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = op, fontSize = 20.sp)
                    }
                } else {
                    OutlinedButton(
                        onClick = { operador = op },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = op, fontSize = 20.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botones Calcular y Limpiar
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = {
                    numero1 = ""
                    numero2 = ""
                    resultado = ""
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(text = "Limpiar")
            }
            Button(
                onClick = {
                    val res = calcular(numero1, numero2, operador)
                    resultado = if (res == "Ingresa números válidos" || res == "No se puede dividir entre cero") {
                        res
                    } else {
                        "$numero1 $operador $numero2 = $res"
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Calcular")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Author credit
        Text(
            text = stringResource(R.string.author_credit),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

fun calcular(a: String, b: String, operador: String): String {
    val num1 = a.toDoubleOrNull()
    val num2 = b.toDoubleOrNull()

    if (num1 == null || num2 == null) {
        return "Ingresa números válidos"
    }

    val resultado = when (operador) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "×" -> num1 * num2
        "÷" -> {
            if (num2 == 0.0) {
                return "No se puede dividir entre cero"
            }
            num1 / num2
        }
        else -> return "Operador no válido"
    }

    // Si no tiene decimales, mostrar sin la parte decimal
    return if (resultado % 1 == 0.0) {
        resultado.toLong().toString()
    } else {
        resultado.toString()
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    CalculdoraTheme {
        Calculadora()
    }
}
