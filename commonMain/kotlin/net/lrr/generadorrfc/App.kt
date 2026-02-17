package net.lrr.generadorrfc

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource

import generadorrfc.composeapp.generated.resources.Res
import generadorrfc.composeapp.generated.resources.compose_multiplatform

import kotlin.text.contains

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showRFC by remember { mutableStateOf("RFC") }
        var nombre by remember{mutableStateOf("")}
        var apellidoPaterno by remember{mutableStateOf("")}
        var apellidoMaterno by remember{mutableStateOf("")}
        var fecha by remember{mutableStateOf("")}
        var dialog by remember{mutableStateOf(false)}



        val rfc = generator(nombre, apellidoPaterno,apellidoMaterno,fecha)

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Card(
                modifier = Modifier.padding(10.dp).fillMaxWidth())
                {
                    Box(
                        modifier = Modifier.padding(10.dp),
                        contentAlignment = Alignment.Center)
                    {
                        Text(
                            text = "RFC: $rfc",

                        )
                    }
                }

            Text(showRFC)
            OutlinedTextField(
                value = nombre, onValueChange = {
                    if (validar.onlyLetters(it)) {
                        nombre = it
                    } else {
                        dialog = true
                    }
                },
                label = { Text("Nombre") },
                modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = apellidoPaterno, onValueChange = {   if (validar.onlyLetters(it)) {
                    apellidoPaterno = it
                } else {
                    dialog = true
                }
                },
                label = { Text("Primer Apellido") },
                modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = apellidoMaterno, onValueChange = {   if (validar.onlyLetters(it)) {
                    apellidoMaterno = it
                } else {
                    dialog = true
                }
                },
                label = { Text("Segundo Apellido") },
                modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
               value = fecha, onValueChange = { fecha = it },label = { Text("DD/MM/YYYY") }
                ,modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            if(dialog){
              AlertDialog(
                  onDismissRequest = { dialog = false },
                  confirmButton =
                  {
                      TextButton(onClick = { dialog = false }) {
                          Text("OK")
                      }
                  },
                  title = { Text("Error") },
                  text = { Text("El nombre solo lleva letras")}
              )
            }



        }
    }
}

fun generator(nombre: String, apellidoPaterno: String, apellidoMaterno: String, fecha: String): String {
    val nombre = nombre.uppercase()
    val apellidoPaterno = apellidoPaterno.uppercase()
    val apellidoMaterno = apellidoMaterno.uppercase()
    val vocal = obtenerVocal(apellidoPaterno)
    val fecha = obtenerFecha(fecha)

    val inicialnombre = nombre.getOrElse(0){'X'}
    val inicialPrimerapellido = apellidoPaterno.getOrElse(0){'X'}
    val inicialSegundoapellido = apellidoMaterno.getOrElse(0){'X'}

    val homoclave = (1..3).map { ('A'..'Z').random() }.joinToString ("")

    return "$inicialPrimerapellido$vocal$inicialSegundoapellido$inicialnombre$fecha$homoclave"
}
fun obtenerVocal(apellidoPaterno: String): Char{
    val vocales = "AEIOU"
    val letra = apellidoPaterno.uppercase()
    for(i in 1 until letra.length){
        if(vocales.contains(letra[i])){
            return letra[i]
        }
    }
    return 'X'
}
fun obtenerFecha(fecha: String): String{
    if(fecha.length != 10)return "XXXXXX"
    val dia = fecha.substring(0,2)
    val mes = fecha.substring(3,5)
    val año = fecha.substring(8,10)
    return "$año$mes$dia"
}
object validar{
    fun onlyLetters(texto: String): Boolean{
        for (l in texto){
            if(!l.isLetter() && l != ' '){
                return false
            }
        }
        return true
    }
}