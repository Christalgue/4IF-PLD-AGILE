package main.java.view;

import java.util.Map;
import java.util.Observable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Observer;

import javax.swing.JPanel;

import main.java.entity.Node;

public class GraphicView extends JPanel implements Observer {
	
	private int scale;
	private int viewHeight;
	private int viewWidth;
	private main.java.entity.Map map;
	private Graphics g;
	
	/**
	 * Cree la vue graphique permettant de dessiner plan avec l'echelle e dans la fenetre f
	 * @param plan
	 * @param e l'echelle
	 * @param f la fenetre
	 */
	public GraphicView(main.java.entity.Map map, Window windows) {
		super();
		//map.addObserver(this); // this observe map
		//viewHeight = map.getHauteur()*s;
		//viewWidth = map.getLargeur()*s;
		setLayout(null);
		setBackground(Color.white);
		setSize(viewWidth, viewHeight);
		//windows.getContentPane().add(this);
		this.map = map;
		calculateScale(map);
	}
	
	protected void calculateScale (main.java.entity.Map map) {
		double minLat;
		double maxLat;
		double minLong;
		double maxLong;
		
		HashMap<Long, Node> nodeMap = map.getNodeMap();
		
		for(Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
		    Node node = entry.getValue();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
