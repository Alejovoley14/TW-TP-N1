package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorOperaciones {
	@RequestMapping("/operar/{operacion}/{cadena}")
		public ModelAndView irAOperar(@PathVariable String operacion,@PathVariable String cadena) {
		ModelMap modelo = new ModelMap();
		
		modelo.put("op",operacion);
		modelo.put("cad",cadena);
		
		switch(operacion){
		case "pasarAMayuscula": modelo.put("result",pasarAMayuscula(cadena));
		break;
		case "pasarAMinuscula": modelo.put("result",pasarAMinuscula(cadena));
		break;
		case "cantidadDeCaracteres": modelo.put("result",cantidadDeCaracteres(cadena));
		break;		
		case "invertirOrden": modelo.put("result",invertirOrden(cadena));
		break;
		}
		
		return new ModelAndView("operar", modelo);
	}
	
	
		public String pasarAMayuscula(String cad){
			return cad.toUpperCase();
		}
		
		public String pasarAMinuscula(String cad){
			return cad.toLowerCase();
		}
		
		public Integer cantidadDeCaracteres(String cad){
			return cad.length();
		}
		
		public String invertirOrden(String cad){
			String invertida = "";
			for (int x=cad.length()-1;x>=0;x--)
				invertida = invertida + cad.charAt(x);
			
			return invertida;
		}
}
