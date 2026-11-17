package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.lumi.model.MensajeChat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewModel : ViewModel() {

    val mensajes = mutableStateListOf<MensajeChat>()

    var textoNuevo by mutableStateOf("")
        private set

    var estaCargando by mutableStateOf(false)
        private set

    private val apiKey = "AQ.Ab8RN6JfG9x45SMl0mA8mNug40moDoUag35on-GW3MgThWesGg"
    private val primaryModel = "gemini-3.8-flash"

    private val instruccionPsicologo = """
        Eres estrictamente Lumi, un asistente virtual de apoyo emocional, salud mental y orientación psicológica enfocado en jóvenes.
        REGLAS OBLIGATORIAS QUE DEBES SEGUIR:
        1. Tu único propósito es brindar escucha empática, apoyo emocional, validación de sentimientos y consejos prácticos de bienestar mental.
        2. RECHAZA DE FORMA AMABLE PERO ROTUNDA cualquier solicitud que no esté relacionada con la psicología, las emociones o el bienestar mental (por ejemplo: recetas de cocina, hacer pasteles, escribir código de programación, tareas académicas o temas ajenos).
        3. Si te piden algo fuera de tu rol, responde con empatía redirigiendo al tema emocional. Ejemplo: "Eso suena interesante, pero mi propósito aquí es acompañarte y hablar sobre cómo te sientes y tu bienestar emocional. ¿Cómo ha estado tu día?"
        4. Nunca des diagnósticos clínicos formales ni recetes medicamentos, pero mantén siempre un tono cercano, cálido, breve y comprensivo.
    """.trimIndent()

    init {
        mensajes.add(
            MensajeChat(
                texto = "¡Hola! Soy Lumi, tu espacio seguro. ¿Cómo te sientes el día de hoy?",
                esDeLumi = true,
                hora = obtenerHoraActual()
            )
        )
    }

    fun onTextoChange(nuevoTexto: String) {
        textoNuevo = nuevoTexto
    }

    fun enviarMensaje() {
        if (textoNuevo.isBlank() || estaCargando) return

        val mensajeUsuario = textoNuevo.trim()
        textoNuevo = ""

        mensajes.add(
            MensajeChat(
                texto = mensajeUsuario,
                esDeLumi = false,
                hora = obtenerHoraActual()
            )
        )

        estaCargando = true

        viewModelScope.launch {
            try {
                val respuestaIA = consultarGeminiConRespaldo()
                mensajes.add(
                    MensajeChat(
                        texto = respuestaIA,
                        esDeLumi = true,
                        hora = obtenerHoraActual()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                mensajes.add(
                    MensajeChat(
                        texto = "En este momento tengo mucha demanda en el servidor, dame unos segunditos e inténtalo de nuevo. Aquí sigo contigo.",
                        esDeLumi = true,
                        hora = obtenerHoraActual()
                    )
                )
            } finally {
                estaCargando = false
            }
        }
    }

    private suspend fun consultarGeminiConRespaldo(): String = withContext(Dispatchers.IO) {
        val payload = construirPayload()


        var resultado = llamarModelo(primaryModel, payload)
        if (resultado.first in 200..299) {
            return@withContext extraerTextoRespuesta(resultado.second)
        }


        if (resultado.first == 503 || resultado.first == 429) {
            delay(1500)
            resultado = llamarModelo(primaryModel, payload)
            if (resultado.first in 200..299) {
                return@withContext extraerTextoRespuesta(resultado.second)
            }
        }


        val modelosRespaldo = obtenerModelosFlashRespaldo()
        for (modeloAlterno in modelosRespaldo) {
            val resAlterno = llamarModelo(modeloAlterno, payload)
            if (resAlterno.first in 200..299) {
                return@withContext extraerTextoRespuesta(resAlterno.second)
            }
        }

        val errorMsg = try {
            JSONObject(resultado.second).getJSONObject("error").getString("message")
        } catch (_: Exception) {
            "HTTP ${resultado.first}: ${resultado.second}"
        }
        throw Exception(errorMsg)
    }

    private fun construirPayload(): String {
        val contentsArray = JSONArray()
        for (msg in mensajes.drop(1)) {
            if (msg.texto.startsWith("En este momento tengo mucha demanda")) continue
            val role = if (msg.esDeLumi) "model" else "user"
            val partObj = JSONObject().put("text", msg.texto)
            val contentObj = JSONObject()
                .put("role", role)
                .put("parts", JSONArray().put(partObj))
            contentsArray.put(contentObj)
        }

        val systemInstructionObj = JSONObject().put(
            "parts",
            JSONArray().put(JSONObject().put("text", instruccionPsicologo))
        )

        val generationConfigObj = JSONObject()
            .put("temperature", 0.7)
            .put("maxOutputTokens", 350)

        return JSONObject()
            .put("system_instruction", systemInstructionObj)
            .put("contents", contentsArray)
            .put("generationConfig", generationConfigObj)
            .toString()
    }

    private fun llamarModelo(modelo: String, payload: String): Pair<Int, String> {
        val url = URL("https://generativelanguage.googleapis.com/v1beta/models/$modelo:generateContent")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            setRequestProperty("x-goog-api-key", apiKey)
            connectTimeout = 30000
            readTimeout = 30000
            doOutput = true
        }

        connection.outputStream.use { os ->
            os.write(payload.toByteArray(Charsets.UTF_8))
        }

        val responseCode = connection.responseCode
        val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
        val responseText = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() } ?: ""
        return Pair(responseCode, responseText)
    }

    private fun obtenerModelosFlashRespaldo(): List<String> {
        return try {
            val url = URL("https://generativelanguage.googleapis.com/v1beta/models")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("x-goog-api-key", apiKey)
                connectTimeout = 15000
                readTimeout = 15000
            }
            if (conn.responseCode !in 200..299) return emptyList()

            val body = conn.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
            val models = JSONObject(body).optJSONArray("models") ?: return emptyList()
            val lista = mutableListOf<String>()

            for (i in 0 until models.length()) {
                val m = models.getJSONObject(i)
                val shortName = m.optString("name", "").removePrefix("models/")
                val methods = m.optJSONArray("supportedGenerationMethods")?.toString() ?: ""

                if (
                    shortName.contains("flash", ignoreCase = true) &&
                    !shortName.equals(primaryModel, ignoreCase = true) &&
                    !shortName.contains("image", ignoreCase = true) &&
                    !shortName.contains("audio", ignoreCase = true) &&
                    !shortName.contains("tts", ignoreCase = true) &&
                    methods.contains("generateContent")
                ) {
                    lista.add(shortName)
                }
            }
            lista
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun extraerTextoRespuesta(responseText: String): String {
        val jsonResponse = JSONObject(responseText)
        val candidates = jsonResponse.optJSONArray("candidates")
            ?: throw Exception("La IA no devolvió respuesta.")

        val parts = candidates.getJSONObject(0)
            .getJSONObject("content")
            .getJSONArray("parts")

        val textoFinal = StringBuilder()
        for (i in 0 until parts.length()) {
            val part = parts.getJSONObject(i)
            if (!part.optBoolean("thought", false) && part.has("text")) {
                textoFinal.append(part.getString("text"))
            }
        }

        return textoFinal.toString().trim().ifEmpty {
            "Lo siento, no pude generar una respuesta en este momento."
        }
    }

    private fun obtenerHoraActual(): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date())
    }
}