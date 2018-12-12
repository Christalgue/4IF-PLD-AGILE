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
import main.java.entity.Node;

public class Serializer {
	
	public static void serializer(String path, CircuitManagement circuitManager) {
		List<Circuit> circuits = circuitManager.getCircuitsList();
		Circuit circuit;
		try {
			for(int i = 0; i<circuits.size(); i++) {
				//Tournées différentes
				circuit = circuits.get(i);
				File file = new File(path+"/Tournee_"+(i+1)+".txt");
				file.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				
				writer.write("Tournee n°"+(i+1));
				writer.newLine();
				
				int deliveryNumber = 0;
				List<AtomicPath> atomicPaths = circuit.getPath();
				
				for(int indexAtomicPath = 0; indexAtomicPath<atomicPaths.size(); indexAtomicPath++) {
					//Livraisons différentes
					String start = "Chemin de la Livraison n°"+deliveryNumber;
					String end = " a la Livraison n°"+(deliveryNumber+1)+",";
					String destination;
					if(circuitManager.isDelivery(atomicPaths.get(indexAtomicPath).getStartNode()).getClass().getSimpleName().contains("Repository")) 
						start =  "Chemin de l'entrepot";
					if(circuitManager.isDelivery(atomicPaths.get(indexAtomicPath).getEndNode()).getClass().getSimpleName().contains("Repository")) 
						end =  " a l'entrepot,";
					
					double distance = atomicPaths.get(indexAtomicPath).getLength();
					int km = (int) (distance/1000);
					int m = (int) (distance - km*1000);
					
					writer.write("  "+start + end);
					
					int min = (int)(atomicPaths.get(indexAtomicPath).getDuration()/60);
					if(min==0) 
						writer.write(" duree : moins de 1min ");
					else
						writer.write(" duree : "+min+"min ");
					
					writer.write("(");
					if(km!=0)
						writer.write(km+"km ");
					writer.write(m+"m)");
					writer.newLine();
					
					List<Bow> bows = atomicPaths.get(indexAtomicPath).getPath();
					distance = 0;
					
					for(int indexBow = 1; indexBow<bows.size(); indexBow++) {
						//Rues différentes
						distance += bows.get(indexBow-1).getLength();
						if(!bows.get(indexBow-1).getStreetName().contains(bows.get(indexBow).getStreetName())) {
							writer.write("    ->");
							if (bows.get(indexBow-1).getStreetName().length()!=0)
								start = "Prendre "+bows.get(indexBow-1).getStreetName();
							else
								start = "Prendre //";
							
							if(bows.get(indexBow).getStreetName().length()!=0)
								end = " jusqu'a "+bows.get(indexBow).getStreetName();
							else
								end = " jusqu'a //";
							
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
						//Dernier chemin d'une livraison
						Node node = bows.get(bows.size()-1).getEndNode();
						
						List<Bow> endIntersection = circuitManager.getCurrentMap().getBowsIntersection(node);

						end = " jusqu'a //";
						for(Bow intersection : endIntersection) {
							if(!bows.get(bows.size()-1).getStreetName().contains(intersection.getStreetName()) && intersection.getStreetName().length()!=0) {
								end = " jusqu'a "+intersection.getStreetName();
								break;
							}
						}
						
						destination = "Vous etes arrive a destination, votre livraison doit durer environ : ";
						min = (int)(circuitManager.isDelivery(bows.get(bows.size()-1).getEndNode()).getDuration()/60);
						if(min==0)
							destination+= "moins de 1min";
						else
							destination+= min+"min";
					}
					else {
						//Dernier chemin de la tournée
						end = " jusqu'a l'entrepot";
						destination = "Vous avez termine votre tournee";
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
