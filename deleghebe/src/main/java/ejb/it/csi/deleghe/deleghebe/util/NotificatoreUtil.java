/*******************************************************************************
* Copyright Regione Piemonte - 2022
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/
package it.csi.deleghe.deleghebe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

public class NotificatoreUtil {
	
	private static final Charset STD_CHARSET = Charset.forName("UTF-8");

	@Inject
    private LogUtil log;
	@Inject
	private ParamsUtil paramsUtil;
	
	private Properties prop;

	private boolean isEnabled;
	
	private class JSONObj {
		private int indent = 0;
		private String name;
		private Object value;
		private List<JSONObj> elements = null;
		
		public JSONObj () {
			this.elements = new ArrayList<>();
		}
		public JSONObj (String name, JSONObj parent) {
			this.name = name;
			this.elements = new ArrayList<>();
			this.indent = parent.indent+1;
			parent.put(name, this);
		}
		private JSONObj (String name, Object value, int indent) {
			this.name = name;
			this.value = value;
			this.indent = indent;
		}
		
		public void put (String name, Object value) {
			elements.add(new JSONObj (name, value, indent+1));
		}
		
		public String toString () {
			StringBuilder sb = new StringBuilder();
			if (elements != null) {
				if (!elements.isEmpty()) {
					sb.append("{\n");
					for (Iterator<JSONObj> itr = elements.iterator(); itr.hasNext();) {
						JSONObj jsonObject = itr.next();
						sb.append(jsonObject.toString()).append(itr.hasNext()? ",\n" : "\n");
					}
					sb.append(getIndentation()).append("}");
				}
			} else {
				boolean quotation = !(value instanceof JSONObj) && !value.getClass().isArray();
				sb.append(getIndentation()).append('"').append(name).append('"').append(": ");
				if (quotation) sb.append('"');
				if (value.getClass().isArray())
					sb.append(toString((Object[])value));
				else
					sb.append(value.toString());
				if (quotation) sb.append('"');
			}
			return sb.toString();
		}
		
		private String toString (Object[] array) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 0; i < array.length; i++) {
				boolean quotation = !(array[i] instanceof JSONObj) && !array[i].getClass().isArray();
				if (quotation) sb.append('"');
				sb.append(array[i].toString());
				if (quotation) sb.append('"');
				sb.append((i<array.length-1)? "," : "");
			}
			sb.append("]");
			return sb.toString();
		}
		
		private String getIndentation() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < indent; i++) sb.append("\t");
			return sb.toString();
		}
	}

	/* ******************************************************** */
	/*															*/
	/* ******************************************************** */
	
	public NotificatoreUtil () {
		final String METHOD_NAME = "NotificatoreUtil";
		try (InputStream input = getClass().getResourceAsStream("notificatore.properties")) {

			this.prop = new Properties();
			// load a properties file
			prop.load(new InputStreamReader(input, STD_CHARSET));

		} catch (IOException ex) {
			log.error(METHOD_NAME, "Errore lettura file notificatore.properties: %s", ex, ex.getMessage());
			throw new IllegalArgumentException(ex);
		}
		
		String enabledProperty = StringUtils.trim(prop.getProperty("enabled"));
		this.isEnabled = "true".equalsIgnoreCase(enabledProperty) || "s".equalsIgnoreCase(enabledProperty);
		
	}

	public void callNotificatore (String scope, String operation, String operator, String cf, Map<String,String> replacements) {
    	final String METHOD_NAME = "callNotificatore";
    	
    	if(!isEnabled) {
    		log.warn(METHOD_NAME, "Notificatore disabilitato. Per abilitarlo impostare parametro di configurazione notificatore.enabled a true.");
    		return;
    	}
    	
		try {
			URL url = new URL(prop.getProperty("endpoint"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-authentication", paramsUtil.getParametro("NOTIFICATORE_TOKEN"));

			String jsonBody = getJsonBody(scope, operation, operator, cf, replacements);
			OutputStream os = conn.getOutputStream();
			os.write(jsonBody.getBytes(STD_CHARSET));
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new IllegalStateException("Errore del servizio di notifica: HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), STD_CHARSET));

			String output;
			log.debug(METHOD_NAME, "Output from Server ....");
			while ((output = br.readLine()) != null) {
				log.debug(METHOD_NAME, "%s", output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			log.error(METHOD_NAME, "Errore url endpoint: %s", e, e.getMessage());
			throw new IllegalStateException("Errore url endpoint: ", e);
		} catch (IOException e) {
			log.error(METHOD_NAME, "Errore richiamo servizio notificatore: %s", e, e.getMessage());
			throw new IllegalStateException("Errore richiamo servizio notificatore: ", e);
		}
	}
	
	private String getJsonBody (String scope, String operation, String operator, String cf, Map<String,String> replacements) {
    	final String METHOD_NAME = "getJsonBody";

    	replacements.put("@HREF@", prop.getProperty("application.url"));
    	
    	NotificatoreUtil.JSONObj joRoot = new JSONObj();
		joRoot.put("uuid", UUID.randomUUID());
		
		NotificatoreUtil.JSONObj joPLoad = new JSONObj("payload", joRoot);
		joPLoad.put("id", UUID.randomUUID());
		joPLoad.put("user_id", cf);
		joPLoad.put("tag", "r_piemon,sanita,deleghe,comunicazioni");
		
		NotificatoreUtil.JSONObj joEmail = new JSONObj("email", joPLoad);
		joEmail.put("subject", prop.getProperty(scope+"."+operation+".email_subject"));
		joEmail.put("body", replace(prop.getProperty(scope+"."+operation+".email_body."+operator), replacements));
		joEmail.put("template_id", "deleghe-template.html");
		
		NotificatoreUtil.JSONObj joSms = new JSONObj("sms", joPLoad);
		joSms.put("content", replace(prop.getProperty(scope+"."+operation+".sms."+operator), replacements));
		
		NotificatoreUtil.JSONObj joPush = new JSONObj("push", joPLoad);
		joPush.put("title", prop.getProperty(scope+"."+operation+".push_title"));
		joPush.put("body", replace(prop.getProperty(scope+"."+operation+".push_body."+operator), replacements));
		joPush.put("call_to_action", prop.getProperty(scope+"."+operation+".push_action."+operator));
		
		NotificatoreUtil.JSONObj joMex = new JSONObj("mex", joPLoad);
		joMex.put("title", prop.getProperty(scope+"."+operation+".mex_title"));
		joMex.put("body", replace(prop.getProperty(scope+"."+operation+".mex_body."+operator), replacements));
		joMex.put("call_to_action", prop.getProperty(scope+"."+operation+".mex_action."+operator));

		String jsonBody = joRoot.toString();
		
		log.debug(METHOD_NAME, "endpoint: %s", prop.getProperty("endpoint"));
		log.debug(METHOD_NAME, "%s", jsonBody);

		return jsonBody;
	}
	
	private String replace(String text, Map<String,String> replacements) {
		for (Entry<String, String> entry : replacements.entrySet()) {
			text = text.replaceAll(entry.getKey(), entry.getValue());
		}
		return text;
	}

}
