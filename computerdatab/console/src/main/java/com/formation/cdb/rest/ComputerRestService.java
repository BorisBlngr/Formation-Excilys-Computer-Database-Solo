package com.formation.cdb.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formation.cdb.model.dto.ComputerDto;

/**
 * @author excilys
 *
 */
@Service
public class ComputerRestService {
    private static String baseUrl = "";
    private static final String URL_FIND = "computers";
    private static final String URL_FIND_ONE = "computers/id/%1$d";
    private static final String URL_FIND_PAGE = "computers/pages/%1$d/max/%2$d";

    Configuration config;
    Parameters params = new Parameters();

    ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructeur.
     */
    public ComputerRestService() {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("conf.properties"));
        try {
            config = builder.getConfiguration();

        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
        baseUrl = config.getString("services.url");
    }

    /**
     * @return computerDtoLits e
     * @throws JsonParseException e
     * @throws JsonMappingException e
     * @throws IOException e
     */
    public List<ComputerDto> getComputers() throws JsonParseException, JsonMappingException, IOException {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        URL url = new URL(baseUrl + URL_FIND);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        computerDtoList = mapper.readValue(response.toString(), new TypeReference<List<ComputerDto>>() {
        });
        return computerDtoList;
    }

}
