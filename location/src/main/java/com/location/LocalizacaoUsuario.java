package com.location;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocalizacaoUsuario {
    public static void main(String[] args) {
        try {
            String ip = obterMeuIP();
            String cidade = obterCidadePorIP(ip);
            System.out.println("Cidade do usuario: " + cidade);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Funcao para obter o endereco IP do usuario
    public static String obterMeuIP() throws IOException {
        URL url = new URL("https://ipinfo.io/ip");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String ip = reader.readLine();
        reader.close();

        return ip;
    }

    // Funcao para obter a cidade com base no endereco IP
    public static String obterCidadePorIP(String ip) throws IOException {
        URL url = new URL("https://ipinfo.io/" + ip + "/json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        String cidade = jsonObject.get("city").getAsString();

        return cidade;
    }
}
