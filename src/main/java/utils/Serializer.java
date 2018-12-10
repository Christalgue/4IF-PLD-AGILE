package main.java.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.java.entity.AtomicPath;
import main.java.entity.Bow;
import main.java.entity.Circuit;
import main.java.entity.CircuitManagement;

public class Serializer {
	
	public static void serializer(String path, CircuitManagement circuitManager) {
		List<Circuit> circuits = circuitManager.getCircuitsList();
		Circuit circuit;
		try {
			for(int i = 0; i<circuits.size(); i++) {
				circuit = circuits.get(i);
				File file = new File(path+"/Tournée_"+(i+1)+".txt");
				file.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				
				writer.write("Tournée n°"+(i+1));
				writer.newLine();
				
				int deliveryNumber = 0;
				List<AtomicPath> atomicPaths = circuit.getPath();
				
				for(int indexAtomicPath = 0; indexAtomicPath<atomicPaths.size(); indexAtomicPath++) {
					String start = "Chemin de la Livraison n°"+deliveryNumber;
					String end = " à la Livraison n°"+(deliveryNumber+1)+",";
					String destination;
					if(circuitManager.getDeliveryByNode(atomicPaths.get(indexAtomicPath).getStartNode()).getClass().getSimpleName().contains("Repository")) 
						start =  "Chemin de l'entrepôt";
					if(circuitManager.getDeliveryByNode(atomicPaths.get(indexAtomicPath).getEndNode()).getClass().getSimpleName().contains("Repository")) 
						end =  " à l'entrepôt,";
					
					double distance = atomicPaths.get(indexAtomicPath).getLength();
					int km = (int) (distance/1000);
					int m = (int) (distance - km*1000);
					
					writer.write("  "+start + end);
					
					int min = (int)(atomicPaths.get(indexAtomicPath).getDuration()/60);
					if(min==0) 
						writer.write(" durée : moins de 1min ");
					else
						writer.write(" durée : "+min+"min ");
					
					writer.write("(");
					if(km!=0)
						writer.write(km+"km ");
					writer.write(m+"m)");
					writer.newLine();
					
					List<Bow> bows = atomicPaths.get(indexAtomicPath).getPath();
					distance = 0;
					
					for(int indexBow = 1; indexBow<bows.size(); indexBow++) {
						distance += bows.get(indexBow-1).getLength();
						if(!bows.get(indexBow-1).getStreetName().contains(bows.get(indexBow).getStreetName())) {
							writer.write("    ->");
							if (bows.get(indexBow-1).getStreetName().length()!=0)
								start = "Prendre "+bows.get(indexBow-1).getStreetName();
							else
								start = "Prendre //";
							
							if(bows.get(indexBow).getStreetName().length()!=0)
								end = " jusqu'à "+bows.get(indexBow).getStreetName();
							else
								end = " jusqu'à //";
							
							writer.write(start+end+". (");
							
							
							km = (int) (distance/1000);
							m = (int) (distance - km*1000);
							
							if(km!=0)
								writer.write(km+"km ");
							writer.write(m+"m)");
							writer.newLine();
							
							distance = 0;
						}
					}
					writer.write("    ->");
					if (bows.get(bows.size()-1).getStreetName().length()!=0)
						start = "Prendre "+bows.get(bows.size()-1).getStreetName();
					else
						start = "Prendre //";
					
					if(indexAtomicPath!=atomicPaths.size()-1) {
						if(atomicPaths.get(indexAtomicPath+1).getPath().get(0).getStreetName().length()!=0)
							end = " jusqu'à "+atomicPaths.get(indexAtomicPath+1).getPath().get(0).getStreetName();
						else
							end = " jusqu'à //";
						destination = "Vous êtes arrivé à destination, votre livraison doit durer environ : ";
						min = (int)(circuitManager.getDeliveryByNode(bows.get(bows.size()-1).getEndNode()).getDuration()/60);
						if(min==0)
							destination+= "moins de 1min";
						else
							destination+= min+"min";
					}
					else {
						end = " jusqu'à l'entrepôt";
						destination = "Vous avez terminé votre tournée";
					}
					writer.write(start+end);
					writer.write(". (");
					
					
					distance += bows.get(bows.size()-1).getLength();
					km = (int) (distance/1000);
					m = (int) (distance - km*1000);
					
					if(km!=0)
						writer.write(km+"km ");
					writer.write(m+"m)");
					writer.newLine();
					
					
					writer.write("    ->"+destination);
					writer.newLine();
					writer.newLine();
					
					deliveryNumber++;
				}
				
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
