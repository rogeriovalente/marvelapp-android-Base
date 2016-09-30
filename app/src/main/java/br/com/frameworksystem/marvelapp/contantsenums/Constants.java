package br.com.frameworksystem.marvelapp.contantsenums;

/**
 * Created by rogerio.valente on 26/09/2016.
 */

public interface Constants {

  public static final String CHARACTER_TABLE = "character";

  public static final String EVENT_TABLE = "event";

  public static final String DDL_CHARACTER = "CREATE TABLE [character] (\n" +
      " [id] INTEGER IDENTITY (1,1), \n " +
      " [name] VARCHAR(100), \n" +
      " [description] TEXT, \n" +
      " [link] TEXT, \n" +
      " [image] TEXT, \n" +
      " [favorite] INTEGER DEFAULT 0, \n" +
      " CONSTRAINT [] PRIMARY KEY([id]));\n";

  public static final String DDL_EVENT = "CREATE TABLE [event] (\n" +
      " [id] INTEGER INDENTITY (1, 1), \n" +
      " [name] VARCHAR(100), \n" +
      " [description] TEXT, \n" +
      " [image] TEXT, \n" +
      " [link] TEXT, \n" +
      " CONSTRAINT [] PRIMARY KEY ([id]));";

  public static final String SERVICETAG = "SEV";

  public static final String BASEURL = "http://gateway.marvel.com";

  public static final String API_CHARACTER = BASEURL + "/v1/public/characters";

  public static final String API_EVENTS = BASEURL + "/v1/public/events";

  public static final String API_CHARACTER_COMICS = API_CHARACTER + "/";
}
