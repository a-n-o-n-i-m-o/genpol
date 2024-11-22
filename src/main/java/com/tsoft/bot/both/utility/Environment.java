package com.tsoft.bot.both.utility;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:qa.properties" // mention the property file name
})
public interface Environment extends Config {


    @Key("VTIME.DB.LPG.HOST")
    String getDbVitimeHost();
    @Key("VTIME.DB.LPG.PORT")
    String getDbVitimePort();
    @Key("VTIME.DB.LPG.DATABASE")
    String getDbVitimeDataBase();
    @Key("VTIME.DB.LPG.USERNAME")
    String getDbVitimeUser();
    @Key("VTIME.DB.LPG.PASSWORD")
    String getDbVitimePass();

    @Key("FENIX.DB.LPG.HOST")
    String getDbFenixHost();
    @Key("FENIX.DB.LPG.PORT")
    String getDbFenixPort();
    @Key("FENIX.DB.LPG.DATABASE")
    String getDbFenixDataBase();
    @Key("FENIX.DB.LPG.USERNAME")
    String getDbFenixUser();
    @Key("FENIX.DB.LPG.PASSWORD")
    String getDbFenixPass();

    @Key("INSUNIX.DB.LPE.HOST")
    String getDbInsunixHostLPE();
    @Key("INSUNIX.DB.LPE.PORT")
    String getDbInsunixPortLPE();
    @Key("INSUNIX.DB.LPE.DATABASE")
    String getDbInsunixDataBaseLPE();
    @Key("INSUNIX.DB.LPE.SCHEMA")
    String getDbInsunixSchemaLPE();
    @Key("INSUNIX.DB.LPE.USERNAME")
    String getDbInsunixUserLPE();
    @Key("INSUNIX.DB.LPE.PASSWORD")
    String getDbInsunixPassLPE();

    @Key("INSUNIX.DB.LPG.HOST")
    String getDbInsunixHostLPG();
    @Key("INSUNIX.DB.LPG.PORT")
    String getDbInsunixPortLPG();
    @Key("INSUNIX.DB.LPG.DATABASE")
    String getDbInsunixDataBaseLPG();
    @Key("INSUNIX.DB.LPG.SCHEMA")
    String getDbInsunixSchemaLPG();
    @Key("INSUNIX.DB.LPG.USERNAME")
    String getDbInsunixUserLPG();
    @Key("INSUNIX.DB.LPG.PASSWORD")
    String getDbInsunixPassLPG();

    @Key("INSUNIX.DB.LPV.HOST")
    String getDbInsunixHostLPV();
    @Key("INSUNIX.DB.LPV.PORT")
    String getDbInsunixPortLPV();
    @Key("INSUNIX.DB.LPV.DATABASE")
    String getDbInsunixDataBaseLPV();
    @Key("INSUNIX.DB.LPV.SCHEMA")
    String getDbInsunixSchemaLPV();
    @Key("INSUNIX.DB.LPV.USERNAME")
    String getDbInsunixUserLPV();
    @Key("INSUNIX.DB.LPV.PASSWORD")
    String getDbInsunixPassLPV();



}
