package com.tsoft.bot.frontend.database;

import com.tsoft.bot.both.utility.Environment;
import com.tsoft.bot.both.utility.Excel;
import com.tsoft.bot.frontend.config.PropertiesFile;
import com.tsoft.bot.frontend.objects.ExcelObjects;
import org.aeonbits.owner.ConfigFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class genpolDB {
    protected static genpolDB instance = null;
    private Connection connection = null;
    private final PropertiesFile properties = PropertiesFile.getInstance();

    private genpolDB() {

    }

    public static genpolDB getInstance() throws Exception {
        if (instance == null)
            instance = new genpolDB();
        return instance;
    }

    private void loadConnection(String core, String company)  {
        try {
            Environment testEnvironment = ConfigFactory.create(Environment.class);
            String conUrl;
            switch (core.toUpperCase()) {
                case "FENIX":
                    String connectionUrl =
                            "jdbc:oracle:thin:@" + testEnvironment.getDbFenixHost() + ":" + testEnvironment.getDbFenixPort()
                                    + "/" + testEnvironment.getDbFenixDataBase();
                    connection = DriverManager.getConnection(connectionUrl,testEnvironment.getDbFenixUser(),testEnvironment.getDbFenixPass());
                    break;
                case "VTIME":
                    conUrl = "jdbc:oracle:thin:@" + testEnvironment.getDbVitimeHost() + ":" + testEnvironment.getDbVitimePort()
                            + "/" + testEnvironment.getDbVitimeDataBase();
                    connection = DriverManager.getConnection(conUrl,testEnvironment.getDbVitimeUser(),testEnvironment.getDbVitimePass());
                    break;
                case "INSUNIX":
                    switch (company.toUpperCase()) {
                        case "LPG":
                            String connUrlLPG =
                                    "jdbc:informix-sqli:" +
                                        "//" + testEnvironment.getDbInsunixHostLPG()  +
                                        ":" + testEnvironment.getDbInsunixPortLPG()  +
                                        "/" + testEnvironment.getDbInsunixSchemaLPG()  +
                                        ":informixserver=" + testEnvironment.getDbInsunixDataBaseLPG()  + ";" +
                                        "user=" + testEnvironment.getDbInsunixUserLPG() + ";" +
                                        "password=" + testEnvironment.getDbInsunixPassLPG() ;
                                DriverManager.registerDriver((Driver)Class.forName("com.informix.jdbc.IfxDriver").newInstance());
                                connection = DriverManager.getConnection(connUrlLPG);
                            break;
                        case "LPE":
                            String connUrlLPE =
                                    "jdbc:informix-sqli:" +
                                            "//" + testEnvironment.getDbInsunixHostLPE()  +
                                            ":" + testEnvironment.getDbInsunixPortLPE()  +
                                            "/" + testEnvironment.getDbInsunixSchemaLPE()  +
                                            ":informixserver=" + testEnvironment.getDbInsunixDataBaseLPE()  + ";" +
                                            "user=" + testEnvironment.getDbInsunixUserLPE() + ";" +
                                            "password=" + testEnvironment.getDbInsunixPassLPE() ;
                            DriverManager.registerDriver((Driver)Class.forName("com.informix.jdbc.IfxDriver").newInstance());
                            connection = DriverManager.getConnection(connUrlLPE);
                            break;
                        case "LPV":
                            String connUrlLPV =
                                    "jdbc:informix-sqli:" +
                                            "//" + testEnvironment.getDbInsunixHostLPV()  +
                                            ":" + testEnvironment.getDbInsunixPortLPV()  +
                                            "/" + testEnvironment.getDbInsunixSchemaLPV()  +
                                            ":informixserver=" + testEnvironment.getDbInsunixDataBaseLPV()  + ";" +
                                            "user=" + testEnvironment.getDbInsunixUserLPV() + ";" +
                                            "password=" + testEnvironment.getDbInsunixPassLPV() ;
                            DriverManager.registerDriver((Driver)Class.forName("com.informix.jdbc.IfxDriver").newInstance());
                            connection = DriverManager.getConnection(connUrlLPV);
                            break;
                        default:
                            throw new Exception("Error en el nombre de la aplicación - copañia");
                    }
                    break;
                default:
                    throw new Exception("Error en el nombre de la aplicación - core");
            }
        } catch (Exception e) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public String getPolicy(String core, String company,String tc) throws Exception {
        loadConnection(core,company);
        String query;
        switch (tc.toUpperCase()) {
            case "TC001":
                query = "query para FENIX DB";
                break;
            case "TC002":
                query = "SELECT *  FROM policy WHERE  NBRANCH=59 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC003":
                query = "SELECT *  FROM policy WHERE  NBRANCH=64 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC004":
            case "TC005":
            case "TC006":
            case "TC007":
            case "TC008":
                query = "SELECT *  FROM policy WHERE  NBRANCH=6 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC009":
                query = "SELECT *  FROM policy WHERE  NBRANCH=23 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC010":
            case "TC011":
                query = "SELECT *  FROM policy WHERE  branch=23 AND STATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC012":
            case "TC013":
            case "TC014":
                query = "SELECT *  FROM policy WHERE  NBRANCH=57 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC015":
            case "TC016":
                query = "SELECT *  FROM policy WHERE  NBRANCH=21 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC017":
            case "TC018":
            case "TC019":
                query = "SELECT *  FROM policy WHERE  branch=21 AND STATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC020":
            case "TC021":
            case "TC022":
            case "TC023":
                query = "SELECT *  FROM policy WHERE  branch=31 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            case "TC024":
            case "TC025":
            case "TC026":
            case "TC027":
            case "TC028":
                query = "SELECT *  FROM policy WHERE  branch=80 AND SSTATUS_POL=4 ORDER BY DCOMPDATE DESC";
                break;
            default:
                throw new Exception("Error en el nombre de la aplicación - core");
        }
        ResultSet res = connection.createStatement().executeQuery(query);
        res.next();
        return res.getString(4);
    }
    public String getEffectDate(String core, String company, String policy) throws Exception {
        loadConnection(core,company);
        String query;
        String certificado;
        String fecha;
        switch (core.toUpperCase()) {
            case "FENIX":
                query = "query para FENIX DB";
                break;
            case "VTIME":
                query = "select  ph.scertype, ph.nbranch as cod_ramo, r.descripcion as ramo, ph.npolicy, ph.dcompdate as fecha_registro ,ph.deffecdate as fecha_efecto, ph.nreceipt, ph.ntype_hist as ID_TIPO_TRANSACCION, t165.SDESCRIPT as TIPO_TRANSACCION, ph.nproceedingnum as nro_tramite_asociado" +
                        "from INSUDB.POLICY_HIS PH inner join INSUDB.table165 t165" +
                        "ON (ph.ntype_hist=t165.ntype_hist) inner join insudb.ramo r" +
                        "on (ph.nbranch=r.codramo)" +
                        "where npolicy="+policy;
                break;
            case "INSUNIX":
                query = "SELECT * FROM policy_his WHERE POLICY = '" + policy + "'";
                break;
            default:
                throw new Exception("Error en el nombre de la aplicación - core");
        }
        ResultSet res = connection.createStatement().executeQuery(query);
        res.next();
        switch (core.toUpperCase()) {
            case "FENIX":
                certificado = res.getString(10);
                //fecha = res.getString(10);
                break;
            case "VTIME":
                certificado = res.getString(8);
                break;
            case "INSUNIX":
                certificado = res.getString(2);
                break;
            default:
                throw new Exception("Error de tabla");
        }

        StringBuilder output = new StringBuilder();
        output.append(new SimpleDateFormat("dd/MM/yyyy").format(res.getDate(1)));

        Excel.save(ExcelObjects.GENPOL_CERTIFICATE, certificado);
        Excel.save(ExcelObjects.GENPOL_POLICY_START_DATE, output.toString());
        closeConnection();
        return output.toString();
    }
    public String getDBDetails() {
        return  "+----------+--------------------+\n" +
                "|    Detalles de DB             |\n" +
                "+----------+--------------------+\n" +
                "| HOST     |" + properties.getDBProperties("HOST") + "                    ".substring(properties.getDBProperties("HOST").length()) + "|\n" +
                "| PORT     |" + properties.getDBProperties("PORT") + "                    ".substring(properties.getDBProperties("PORT").length()) + "|\n" +
                "| DATABASE |" + properties.getDBProperties("DATABASE") + "                    ".substring(properties.getDBProperties("DATABASE").length()) + "|\n" +
                "| USER     |" + properties.getDBProperties("USERNAME") + "                    ".substring(properties.getDBProperties("USERNAME").length()) + "|\n" +
                "+----------+--------------------+\n";
    }
    public String getDBDetails(String core, String company) {
        String schemaDetail = "";
        if (core.equals("INSUNIX"))
            schemaDetail = "| SCHEMA   |" + properties.getDBProperties("SCHEMA", core, company) + "                                        ".substring(properties.getDBProperties("SCHEMA", core, company).length()) + "|\n";
        return  "+----------+----------------------------------------+\n" +
                "|    Detalles de DB " + core + " - " + company + "                                ".substring(core.length() + company.length() + 3) + "|\n" +
                "+----------+----------------------------------------+\n" +
                "| HOST     |" + properties.getDBProperties("HOST", core, company) + "                                        ".substring(properties.getDBProperties("HOST", core, company).length()) + "|\n" +
                "| PORT     |" + properties.getDBProperties("PORT", core, company) + "                                        ".substring(properties.getDBProperties("PORT", core, company).length()) + "|\n" +
                "| DATABASE |" + properties.getDBProperties("DATABASE", core, company) + "                                        ".substring(properties.getDBProperties("DATABASE", core, company).length()) + "|\n" + schemaDetail +
                "| USER     |" + properties.getDBProperties("USERNAME", core, company) + "                                        ".substring(properties.getDBProperties("USERNAME", core, company).length()) + "|\n" +
                "+----------+----------------------------------------+\n";
    }
}
