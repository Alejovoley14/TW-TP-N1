package ar.edu.unlam.tallerweb1.persistencia;
import ar.edu.unlam.tallerweb1.SpringTest;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.unlam.tallerweb1.modelo.Ciudad;
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.modelo.Ubicacion;

public class TestBD extends SpringTest{

	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueBusquePaisesDeHablaInglesa() {
		Pais pais1 = new Pais();
		Pais pais2 = new Pais();
		Pais pais3 = new Pais();
		pais1.setIdioma("Ingles");
		pais2.setIdioma("Español");
		pais3.setIdioma("Ingles");
		getSession().save(pais1);
		getSession().save(pais2);
		getSession().save(pais3);
		
		List <Pais> listaPaisesHablaInglesa = getSession().createCriteria(Pais.class)
				.add(Restrictions.like("idioma","Ingles"))
				.list();
		for (Pais item : listaPaisesHablaInglesa) {
			assertThat(item.getIdioma()).isEqualTo("Ingles");
		}
	}
	
	@Test
	@Transactional
	@Rollback (true)
	public void testQueBusquePaisesDelContinenteEuropeo() {
		Pais pais1 = new Pais();
		Pais pais2 = new Pais();
		Pais pais3 = new Pais();
		
		Continente continente1 = new Continente();
		continente1.setNombre("Europa");
		
		Continente continente2 = new Continente();
		continente2.setNombre("America");
		
		pais1.setContinente(continente1);
		pais2.setContinente(continente1);
		pais3.setContinente(continente2);
		
		getSession().save(pais1);
		getSession().save(pais2);
		getSession().save(pais3);
		
		List <Pais> listaPaisesEuropeos = getSession().createCriteria(Pais.class)
				.createAlias("continente", "ContinenteBuscado")
				.add(Restrictions.like("ContinenteBuscado.nombre","Europa"))
				.list();
		
		for (Pais item : listaPaisesEuropeos) {
			assertThat(item.getContinente().getNombre()).isEqualTo("Europa");
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueBusquePaisesQueEstanAlNorteDelTropicoDeCancer() {
		Pais pais1 = new Pais();
		Pais pais2 = new Pais();
		Pais pais3 = new Pais();
		
		//La latitud del tropico de cancer es de 23°26'14''
		
		Ubicacion ubicacion1 = new Ubicacion();
		ubicacion1.setLatitud(24.26);
		
		Ubicacion ubicacion2 = new Ubicacion();
		ubicacion2.setLatitud(25.26);
		
		Ubicacion ubicacion3 = new Ubicacion();
		ubicacion3.setLatitud(21.26);
		
		Ciudad ciudad1 = new Ciudad();
		ciudad1.setNombre("capital1");
		ciudad1.setUbicacionGeografica(ubicacion1);
		
		Ciudad ciudad2 = new Ciudad();
		ciudad2.setNombre("capital2");
		ciudad2.setUbicacionGeografica(ubicacion2);
		
		Ciudad ciudad3 = new Ciudad();
		ciudad3.setNombre("capital3");
		ciudad3.setUbicacionGeografica(ubicacion3);
		
		pais1.setCapital(ciudad1);
		pais2.setCapital(ciudad2);
		pais3.setCapital(ciudad3);
		
		getSession().save(pais1);
		getSession().save(pais2);
		getSession().save(pais3);
		
		
		List <Pais> listaPaisesTropico = getSession().createCriteria(Pais.class)
					.createAlias("capital", "CapitalBuscada")
					.createAlias("CapitalBuscada.ubicacionGeografica", "UbicacionBuscada")
					.add(Restrictions.gt("UbicacionBuscada.latitud",23.16))
					.list();
		
		for (Pais item : listaPaisesTropico) {
			assertThat(item.getCapital().getUbicacionGeografica().getLatitud()).isGreaterThan(23.16);
		}
	
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueBusqueCiudadesDeHeemisferioSur() {
		
		Ciudad ciudad1 = new Ciudad();
		Ciudad ciudad2 = new Ciudad();
		Ciudad ciudad3 = new Ciudad();
		
		Ubicacion ubicaion1 = new Ubicacion();
		ubicaion1.setLatitud(-10.45);
		
		Ubicacion ubicaion2 = new Ubicacion();
		ubicaion1.setLatitud(40.45);
		
		Ubicacion ubicaion3 = new Ubicacion();
		ubicaion1.setLatitud(-60.45);
		
		ciudad1.setUbicacionGeografica(ubicaion1);
		ciudad2.setUbicacionGeografica(ubicaion2);
		ciudad3.setUbicacionGeografica(ubicaion3);
		
		getSession().save(ciudad1);
		getSession().save(ciudad1);
		getSession().save(ciudad1);
		
		List <Ciudad> listaDeCiudadesSur = getSession().createCriteria(Ciudad.class)
					.createAlias("ubicacionGeografica", "UbicacionBuscada")
					.add(Restrictions.le("UbicacionBuscada.latitud",0.0))
					.list();
		
		for (Ciudad item : listaDeCiudadesSur) {
			assertThat(item.getUbicacionGeografica().getLatitud()).isLessThan(0.0);
		}
					
	}

}
