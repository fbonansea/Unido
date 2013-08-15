package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import javax.swing.JTree;

import persistencia.Conexion;

public class Arbol {

	Conexion c=new Conexion();
	Connection cn= c.getConexion();
	Float[][] bom = new Float[10][10];
	String[][] desc = new String[100][100];
	int i,j=0;
	ArrayList<Nodo> padresPrincipales = new ArrayList<>();
	//desde original
	public Arbol(){

//Obtener descripcion de Articulos
		try {
			StringBuilder sb=new StringBuilder();
			ResultSet descripcion = cn.prepareStatement("select a.id,descripcion_str from Articulo a,Descripcion d where a.descripcion_id=d.id").executeQuery();
			int f=0,c=0;
			while (descripcion.next()) {
		        	desc[f][c]=descripcion.getString("id");
		        	c++;
		        	desc[f][c]=descripcion.getString("descripcion_str");
					System.out.println("id: "+desc[f][0]);
					System.out.println("desc: "+desc[f][1]);
		        	
		        	f++;
		        	c=0;
		        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
//OBTENER PADRES PRINCIPALES
		try {
			StringBuilder sb=new StringBuilder();
			ResultSet padresP = cn.prepareStatement("Select distinct padre from BOM where padre not in (select hijo from BOM)").executeQuery();
			while (padresP.next()) {
		        	Nodo n =new Nodo (padresP.getInt("padre"));
				    padresPrincipales.add(n);
		        	for (int d=0; d<desc.length;d++)
					 { 
		        		if(desc[d][0]!=null && desc[d][0].equals((n.GetValor()).toString()))
						{
							n.setDescripcion(desc[d][1]);
						}

						
					 }
		        	
		        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
			
 //OBTENER TODA LA BOM
			try {
				ResultSet result= cn.prepareStatement("Select padre,hijo,cantidad,um_id from BOM").executeQuery();
		        
		        while (result.next()) {
		        	bom[i][j] = (float)((Integer)result.getObject("padre"));
		        	  j++;
		        	bom[i][j] = (float)((Integer)result.getObject("hijo"));
		        	 j++;
		        	bom[i][j] = Float.valueOf(result.getObject("cantidad").toString());
		        	 j++;
			        bom[i][j] = (float)((Integer)result.getObject("um_id"));
		        	  i++;
		        	j=0;  
		        }
//Para cada padrePrincipal le arma el arbol		        
		     for(Nodo a: padresPrincipales)
		     {
			     ArmaArbol(bom, a);
		     }
					} catch (Exception e) {
						e.printStackTrace();
				}
		MostrarArbol();	
			
	}
	
	//bom: lo que levanto de la tabla BOM transformado en un array
	//k: posicion de la bom que se esta usando en este momento
	
	private void ArmaArbol(Float bom[][],Nodo nodo)
	{
				//   La primera vez entra con el nodo padreprincipal
				//	 recorre la bom hasta encontrar q el padre coincide, 
				//	 una vez que lo encuentra setea al hijo segun la segunda columna de la bom
				//	 y llama a la funcion con el hijo 
				//	 cuando no tiene mas hijo, aumenta el k y pregunta de ahi para abajo (bom[k][0])= nodo.getPadre
				//	 
					try{	
						
						for (int k=0; k<bom.length;k++)
						{	
							Float padre= nodo.GetValor().floatValue(); 
						while (bom[k][0]!= null &&(bom[k][0].equals(padre)) && k<20)
					        { 
							 //System.out.println("Valor: "+nodo.GetValor().toString());
							 Nodo h = new Nodo(bom[k][1]);
							 
							 h.setCantidad(bom[k][2]);
							 h.setUm(bom[k][3]);
							 for (int d=0; d<desc.length;d++)
							 { 
								// System.out.println("desc: "+desc[d][0]+"-nodoV:"+h.GetValor() );
								if(desc[d][0]!=null && desc[d][0].equals((h.GetValor()).toString()))
								{
									//System.out.println("entro");
									h.setDescripcion(desc[d][1]);
								}
							 }
							 nodo.AgregarHijo(h);
							 System.out.println("Valor:"+nodo.GetValor()+" cantidad:"+h.getCantidad()+" desc:"+h.getDescripcion());
							 h.setPadre(nodo);
							 
							 System.out.println("Padre: "+nodo.GetValor()+"--hijo:"+h.GetValor());
							 ArmaArbol(bom,h);
							 k++;
							  
					        }
						}
						
						 return;
					  }catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
	}
	
	
	private void MostrarArbol(){
		Jtree j=new Jtree(padresPrincipales);
	}
	
	
	
	
	
	private StringBuilder MostrarArbol (StringBuilder sb, Nodo padre)
	{
			
//			Jtree j = new Jtree(padresPrincipales);
			
		
			System.out.println(sb.toString());
			System.out.println("||||||||||||||||||||||");
			sb.append(padre.GetValor());
			if(padre.GetHijos()!=null){
				
				sb.append("\n \t |");
				
					for (Nodo nodoH:padre.GetHijos()){
						System.out.println("hijo->"+nodoH.GetValor());
//						sb.append("\t");
//						System.out.println(sb.toString());
//						System.out.println("---------------");
						
						MostrarArbol(sb, nodoH);
						sb.append("\n \t |");
//						sb.append("\n \t |");
//						sb.append("\t");
//						sb.append(nodoH.GetValor());
//						System.out.println(sb.toString());
					}
			}
				
				
//				Jtree j = new Jtree(padresPrincipales);
				return sb;
	}


}
