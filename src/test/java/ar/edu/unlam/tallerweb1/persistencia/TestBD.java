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
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;

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

}
