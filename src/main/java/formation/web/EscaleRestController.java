/**
 * 
 */
package formation.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import vol.metier.dao.AeroportDao;
import vol.metier.dao.EscaleDao;
import vol.metier.dao.VolDao;
import vol.metier.model.Escale;
import vol.metier.model.Views;

/**
 * @author ajc
 *
 */
@RestController
public class EscaleRestController {

	@Autowired
	private EscaleDao daoEscale;

	@Autowired
	private VolDao volDao;

	@Autowired
	private AeroportDao aeroportDao;

	@RequestMapping(value = "/escale", method = RequestMethod.GET)
	@JsonView(Views.Escale.class)
	public ResponseEntity<List<Escale>> list() {
		return new ResponseEntity<>(daoEscale.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/escale", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Escale escale, UriComponentsBuilder uCB) {
		Escale tmp = (Escale) daoEscale.find(escale.);
		if (tmp == null) {
			daoEscale.create(escale);
			URI uri = uCB.path("/escale/{id}")
					.buildAndExpand(escale.getId().getFormateur().getId() + ":" + escale.getId().getMatiere().getCode())
					.toUri();
			HttpHeaders header = new HttpHeaders();
			header.setLocation(uri);
			return new ResponseEntity<>(header, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/escale/{id}", method = RequestMethod.GET)
	@JsonView(Views.Escale.class)
	public ResponseEntity<Escale> find(@PathVariable("id") String id) {
		Integer idFormateur = Integer.valueOf(id.split(":")[0]);
		Integer idMatiere = Integer.valueOf(id.split(":")[1]);

		EscaleKey idEscale = new EscaleKey((Formateur) daoPersonne.find(idFormateur), daoMatiere.find(idMatiere));

		Escale tmp = (Escale) daoEscale.find(idEscale);

		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Escale>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/escale/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		Integer idFormateur = Integer.valueOf(id.split(":")[0]);
		Integer idMatiere = Integer.valueOf(id.split(":")[1]);

		EscaleKey idEscale = new EscaleKey((Formateur) daoPersonne.find(idFormateur), daoMatiere.find(idMatiere));

		Escale tmp = (Escale) daoEscale.find(idEscale);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoEscale.delete(tmp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/escale/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody Escale escale) {
		Integer idFormateur = Integer.valueOf(id.split(":")[0]);
		Integer idMatiere = Integer.valueOf(id.split(":")[1]);

		EscaleKey idEscale = new EscaleKey((Formateur) daoPersonne.find(idFormateur), daoMatiere.find(idMatiere));

		Escale tmp = (Escale) daoEscale.find(idEscale);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoEscale.update(escale);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
