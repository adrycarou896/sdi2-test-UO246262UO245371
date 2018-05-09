package com.uniovi;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class PruebaMongo {

	public static void main(String[] args) {
		
		try {
			//Recuperamos la base de datos.
			 DB database = new MongoClient().getDB("mongodb://admin:sdi@ds029655.mlab.com:29655/redsocial");
			 
			 //Recuperamos los valores de la colección, previamente hemos introducido 
			 //unos valores desde la consola de mongo con db.things.save({name:"mongoDB"})
			 DBCollection coleccion = database.getCollection("things");
			 
			 //Recuperamos el elemento
			 DBObject documento = coleccion.findOne();
			 System.out.println( documento );
			 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
