package com.tsoft.bot.backend.base;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.tsoft.bot.backend.objects.ServiceObjects;
import com.tsoft.bot.both.utility.ExcelReader;
import com.tsoft.bot.both.utility.FileHelper;
import com.tsoft.bot.both.utility.JsonUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;

import static net.serenitybdd.rest.SerenityRest.given;

public class BaseClass {
    protected String URL;
    protected String METODO;
    protected String valueOfJsonpath;
    protected String valueOfRegex;


    public void setTestDetails(String testcase, String description)
    {
        String details = "CASO: " + testcase + ",\n" + description;
        Serenity.recordReportData().withTitle("Detalles del caso").andContents(details);
    }

    public String dataExcel(String col) throws Throwable {
        String testCase = ExcelReader.getTestCase();

        int countPage = Integer.parseInt(testCase) - 1;
        return ExcelReader.getImportedData().get(countPage).get(col);
    }


    private RequestSpecification ReqManager = SerenityRest.given();
    protected Response currentResponse;

    public void get()
    {
        currentResponse = ReqManager.get(URL).then().extract().response();
    }

    public void post()
    {
        currentResponse =  ReqManager.post(URL).then().extract().response();
    }
    public void put()
    {
        currentResponse =  ReqManager.put(URL).then().extract().response();
    }
    public void patch()
    {
        currentResponse =  ReqManager.patch(URL).then().extract().response();
    }
    public void delete()
    {
        currentResponse =  ReqManager.delete(URL).then().extract().response();
    }
    public void addBody(String stringBody) throws IOException {
        if (!stringBody.equals(""))
        {
            String body;
            if (stringBody.contains("ARCHIVO"))
            {
                String archivo = stringBody.split("\"")[1].trim();
                if (!archivo.contains(":"))
                    archivo = FileHelper.getProjectFolder() + "/src/test/resources/extrafiles/" + archivo;
                body = FileUtils.readFileToString(new File(archivo), StandardCharsets.UTF_8);
                if (body.contains("echo %"))
                    body = changeStringWithParameters(body);
                ReqManager.body(body);
                Serenity.recordReportData().withTitle("agregamos el body").andContents(body);
            }
            else
            {
                if (stringBody.contains("echo %"))
                    stringBody = changeStringWithParameters(stringBody);
                ReqManager.body(stringBody);
                Serenity.recordReportData().withTitle("agregamos el body").andContents(stringBody);
            }
        }
    }

    public void setURL(String url)
    {
        if (url.contains("echo %"))
            this.URL = changeStringWithParameters(url);
        else
            this.URL = url;
        Serenity.recordReportData().withTitle("configuramos la url").andContents(this.URL);
    }

    public void setHeaders(String stringHeaders) throws IOException {
        if (!stringHeaders.equals(""))
        {
            if (stringHeaders.contains("echo %"))
                stringHeaders = changeStringWithParameters(stringHeaders);
            String[] headers = extractValues(stringHeaders,"KEYS");
            String[] values = extractValues(stringHeaders,"VALUES");
            String report = "";
            for (int i = 0; i < headers.length; i++)
            {
                ReqManager.header(headers[i],values[i]);
                report += headers[i]+": "+values[i]+"\n";
            }
            Serenity.recordReportData().withTitle("headers").andContents(report);
        }
    }
    public void setCookies(String[] names, String[] values)
    {
        for (int i = 0; i < names.length; i++)
        {
            ReqManager.cookie(names[i],values[i]);
        }
    }

    public String[] extractValues(String data,String value) throws IOException {
        String dataProcess;
        String[] values;
        if (data.contains("ARCHIVO"))
        {
            String archivo = data.split("\"")[1].trim();
            if (!archivo.contains(":"))
                archivo = FileHelper.getProjectFolder() + "/src/test/resources/extrafiles/" + archivo;
            dataProcess = FileUtils.readFileToString(new File(archivo), StandardCharsets.UTF_8);
            if (dataProcess.contains("echo %"))
                dataProcess = changeStringWithParameters(dataProcess);
        }
        else
        {
            dataProcess = data;
            if (dataProcess.contains("echo %"))
                dataProcess = changeStringWithParameters(dataProcess);

        }
        dataProcess = dataProcess.replace("\n","");
        dataProcess = dataProcess.replace("\r","");
        int separator = 0;
        if (dataProcess.contains("&"))
            separator = 1;
        else if (dataProcess.contains(","))
            separator = 2;
        String[] array1 = dataProcess.split("[&,]");
        values = new String[array1.length];
        for (int i = 0; i < array1.length; i++)
        {
            String[] current;
            if (separator == 1)
                current = array1[i].split("=");
            else if (separator == 2)
                current = array1[i].split(":");
            else
                current = array1[i].split(":");

            if (value.equals("KEYS"))
                values[i] = current[0];
            else if (value.equals("VALUES"))
                values[i] = current[1];
        }
        return values;
    }

    public void setParameters(String stringParameters) throws IOException {
        if (!stringParameters.equals(""))
        {
            if(stringParameters.contains("echo %"))
                stringParameters = changeStringWithParameters(stringParameters);
            String[] keys = extractValues(stringParameters,"KEYS");
            String[] values = extractValues(stringParameters,"VALUES");
            String report = "";

            for (int i = 0; i < keys.length; i++)
            {
                ReqManager.param(keys[i],values[i]);
                report += keys[i]+"="+values[i]+"\n";
            }
            Serenity.recordReportData().withTitle("parametros").andContents(report);
        }
    }
    public void setParam(String key, String value)
    {
        ReqManager.param(key,value);
    }
    public void setCookie(String key, String value)
    {
        ReqManager.cookie(key,value);
    }
    public void setHeader(String key, String value)
    {
        ReqManager.header(key,value);
    }
    public void configureMethod(String Metodo)
    {
        this.METODO = Metodo;
        Serenity.recordReportData().withTitle("configuramos el método").andContents(this.METODO);
    }
    public void sendRequest()
    {
        if (this.METODO.equals("GET"))
            get();
        else if (this.METODO.equals("POST"))
            post();
        else if (this.METODO.equals("PUT"))
            put();
        else if (this.METODO.equals("PATCH"))
            patch();
        else if (this.METODO.equals("DELETE"))
            delete();
    }

    public void saveRegex(String stringRegexValues, Response response) throws Throwable {
        if (!stringRegexValues.equals(""))
        {
            String[] regexValuesStrings = stringRegexValues.split("(_regex;\r\n|_regex;)");
            int x = 0;
            while (x < regexValuesStrings.length)
            {
                String regex, group, columnCompare,scope,paramToSave,source,report;
                String[] values = regexValuesStrings[x].split("',last,'");
                regex = values[0].split("regex'")[1];
                group = values[1];
                columnCompare = values[2];
                scope = values[3];
                paramToSave = values[4].replace("',end","");

                if (scope.equals("") || (scope.equals("BODY")) )
                    source = response.getBody().asString();
                else if (scope.equals("HEADERS"))
                    source = response.getHeaders().toString();
                else if (scope.equals("COOKIES"))
                    source = response.getCookies().toString();
                else
                    source = "scope has not been defined";

                regExprExtractor(regex,source,group);
                report = "expresión regular: '"+regex;
                if (!group.equals(""))
                    report +="' - grupo de busqueda: '"+group+"'\n";
                else
                    report += "' - grupo de busqueda: '1'\n";
                if (!scope.equals(""))
                    report += "buscado en: '" + scope+"'\nvalor obtenido: '"+this.valueOfRegex+"'";
                else
                    report += "buscado en: 'BODY'\nvalor obtenido: '"+this.valueOfRegex+"'";

                if (!columnCompare.equals("")) {
                    String valToCompare = dataExcel(columnCompare);
                    if (!valToCompare.equals("")) {
                        assertEquals(valToCompare,this.valueOfRegex);
                        report += " - valor esperado: '" + valToCompare + "'";
                    }

                }
                Serenity.recordReportData().withTitle("Validador regex N°" + (x+1)).andContents(report);
                if (!paramToSave.equals("")) {
                    saveParameter(paramToSave,this.valueOfRegex);
                    this.valueOfRegex = "";
                }
                x++;
            }

        }
    }

    public void regExprExtractor(String expression, String source, String group)
    {
        if (!expression.equals(""))
        {
            int groupNumber;
            if (group.equals(""))
                groupNumber = 1;
            else
                groupNumber = Integer.parseInt(group);
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(source);
            if (matcher.find())
                this.valueOfRegex = matcher.group(groupNumber);
            else
                this.valueOfRegex = "";
        }
    }

    public String regExprExtractor(String expression, String source, int group)
    {
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(source);
        if (matcher.find())
            return matcher.group(group);
        else
            return  "";
    }

    public void saveJsonpath(String stringJsonpathVales,Response response) throws Throwable {
        if (!stringJsonpathVales.equals(""))
        {
            String[] jsonpathValuesStrings = stringJsonpathVales.split("(_jsonpath;\r\n|_jsonpath;)");
            int x = 0;
            while (x < jsonpathValuesStrings.length)
            {
                String jsonpath, columnCompare,paramToSave,report;
                String[] values = jsonpathValuesStrings[x].split("',last,'");
                jsonpath = values[0].split("jsonpath'")[1];
                if (values[1].equals("default")){
                    columnCompare = jsonpath.replace("$.","");
                }else{
                    columnCompare = values[1];
                }
                paramToSave = values[2].replace("',end","");
                if (paramToSave.equals("default")){
                    paramToSave = jsonpath.replace("$.","");
                }

                jsonPathExtractor(jsonpath,response);
                report = "query de jsonpath: '"+ jsonpath+"'\nvalor obtenido: '"+this.valueOfJsonpath+"'";
                if (!columnCompare.equals("")) {
                    String valToCompare = dataExcel(columnCompare);
                    if (!valToCompare.equals("")){
                        valToCompare = valToCompare.replace("\n","");
                        valToCompare = valToCompare.replace("\r","");
                        assertEquals(valToCompare,this.valueOfJsonpath);
                        report += " - valor esperado: '" + valToCompare + "'";
                    }
                }
                Serenity.recordReportData().withTitle("Validador jsonpath N°" + (x+1)).andContents(report);
                if (!paramToSave.equals("")) {
                    saveParameter(paramToSave,this.valueOfJsonpath);
                    this.valueOfRegex = "";
                }
                x++;
            }

        }
    }
    public void saveSimpleJSONPath(String expression, Response source) throws Throwable {
        if (!expression.equals(""))
        {
            String[] stringValues = expression.split("(;\n|;\n\r|;)");
            int x = 0;
            while (x < stringValues.length && !stringValues[x].equals(""))
            {
                String jsonpath,save,compareWith,report;
                jsonpath = stringValues[x];
                compareWith = jsonpath;
                save = "";
                if (stringValues[x].contains(",")){
                    jsonpath = stringValues[x].split(",")[0];
                    compareWith = jsonpath;
                    save = stringValues[x].split(",")[1];
                    if (save.equals("def")){
                        save = jsonpath;
                    }
                }
                report = "extraer valor de: '" + jsonpath;
                jsonpath = jsonpath.replace(".","..");
                jsonpath = "$." + jsonpath;
                Object document = Configuration.defaultConfiguration().jsonProvider().parse(source.getBody().asString());
                if(JsonPath.read(document, jsonpath).toString().contains("[[")){
                    jsonpath = jsonpath + ".*";
                }
                this.valueOfJsonpath = JsonPath.read(document, jsonpath).toString();
                this.valueOfJsonpath = this.valueOfJsonpath.replace("[","");
                this.valueOfJsonpath = this.valueOfJsonpath.replace("]","");
                this.valueOfJsonpath = this.valueOfJsonpath.replace("\"","");
                report += "'\nvalor obtenido: \n{\n\t" + this.valueOfJsonpath.replace(",",",\n\t") + "\n}";
                if(!compareWith.equals("")){
                    String valToCompare = dataExcel(compareWith);
                    System.out.println(compareWith);
                    if (!valToCompare.equals("")){
                        valToCompare = valToCompare.replace("\n","");
                        valToCompare = valToCompare.replace("\r","");
                        assertEquals(valToCompare,this.valueOfJsonpath);
                        report += " - valor esperado: \n{\n\t" + valToCompare.replace(",",",\n\t") + "\n}";
                    }
                }
                Serenity.recordReportData().withTitle("Validador simple de json path N°"+ (x+1)).andContents(report);
                if(!save.equals("")){
                    saveParameter(save,this.valueOfJsonpath);
                }
                x++;
            }
        }
    }

    public void jsonPathExtractor(String jsonPathExpression, Response source)
    {
        if (!jsonPathExpression.equals("") && source.getContentType().contains("application/json"))
        {
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(source.getBody().asString());
            this.valueOfJsonpath = JsonPath.read(document, jsonPathExpression).toString();
        }
    }

    public void saveParameter(String nameParam, String value)
    {
        if (!nameParam.equals(""))
            ServiceObjects.parameters.put(nameParam,value);
    }
    public String changeStringWithParameters(String source)
    {
        String formatString = source;
        String macthValue = regExprExtractor("echo %(.+?)%",formatString,1);
        while (!macthValue.isEmpty())
        {
            formatString = formatString.replace("echo %"+macthValue+"%",ServiceObjects.parameters.get(macthValue));
            macthValue = regExprExtractor("echo %(.+?)%",formatString,1);
        }
        return formatString;
    }

    public void validateJSONSchema(String path) throws IOException {
        if (!path.equals(""))
        {
            if (currentResponse.getContentType().contains("application/json"))
            {
                if (path.contains(":")) {
                    Schema validator = SchemaLoader.load(JsonUtils.fileToJson(path));
                    validator.validate(JsonUtils.stringToJson(currentResponse.getBody().asString()));
                    Path jsonesperado = Paths.get(path);
                    Serenity.recordReportData().withTitle("Esquema Json Esperado").fromFile(jsonesperado);
                }else{
                    Schema validator = SchemaLoader.load(JsonUtils.fileToJson(FileHelper.getProjectFolder() + "/src/test/resources/schemas/"+ path));
                    validator.validate(JsonUtils.stringToJson(currentResponse.getBody().asString()));
                    Path jsonesperado = Paths.get("src/test/resources/schemas/"+path);
                    Serenity.recordReportData().withTitle("Esquema Json Esperado").fromFile(jsonesperado);
                }
            }
            else
            {
                Serenity.recordReportData()
                        .withTitle("El response no es de tipo json")
                        .andContents("no re realizo la validación");
            }
        }
    }
}
