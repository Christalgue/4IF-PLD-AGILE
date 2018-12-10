package main.java.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.java.entity.AtomicPath;
import main.java.entity.Bow;
import main.java.entity.Circuit;

public class Serializer {
	
	public void serializer(String path, List<Circuit> circuits) {
		Circuit circuit;
		try {
			for(int i = 0; i<circuits.size(); i++) {
				circuit = circuits.get(i);
				BufferedWriter writer = new BufferedWriter(new FileWriter(path+"Tournée_"+(i+1)));
				
				writer.write("Livraison n°"+(i+1)+" :");
				writer.newLine();
				
				int deliveryNumber = 0;
				for(AtomicPath atomicPath : circuit.getPath()) {
					String start = "Chemin de la Livraison n°"+deliveryNumber;
					String end = " à la Livraison n°"+(deliveryNumber+1)+" :";
					if(atomicPath.getStartNode().getClass().getSimpleName().contains("Repository")) 
						start =  "Chemin de l'entrepôt";
					if(atomicPath.getEndNode().getClass().getSimpleName().contains("Repository")) 
						end =  " à l'entrepôt :";
					
					writer.write("  "+start + end);
					writer.newLine();
					
					for(Bow bow : atomicPath.getPath()) {
						writer.write("    ->");
						
					}
				}
				
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
