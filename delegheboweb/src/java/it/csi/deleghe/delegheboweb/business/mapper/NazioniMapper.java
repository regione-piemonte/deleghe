/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.delegheboweb.business.mapper;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import it.csi.deleghe.delegheboweb.dto.delegheboweb.Nazioni;

public class NazioniMapper {
	
	public static List<Nazioni> createModelNazioni(String jsonSource) throws Exception {

        List<Nazioni> result = new ArrayList<>();

        try {
        	if(!"".equals(jsonSource)) {
        		JSONArray jsonArray = new JSONArray(jsonSource);

        		for (int i = 0; i < jsonArray.length(); i++) {
        			JSONObject jsonObject = jsonArray.getJSONObject(i);
        			Nazioni nazioni = new Nazioni();
        			nazioni.setDesc(getStringFromJsonObject(jsonObject, "desc"));
        			
        			result.add(nazioni);
        		}
        	}

        } catch (JSONException e) {
            throw new Exception(e);
        }

        return result;
    }
    
    private static String getStringFromJsonObject(JSONObject jsonObject, String tipoCampo) throws JSONException {
        return (jsonObject.has(tipoCampo) && !jsonObject.isNull(tipoCampo)) ? jsonObject.getString(tipoCampo) : "";
    }

}
