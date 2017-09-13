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

import formation.jpa.dao.DaoEnseigner;
import formation.jpa.dao.DaoMatiere;
import formation.jpa.dao.DaoPersonne;
import formation.jpa.model.Enseigner;
import formation.jpa.model.EnseignerKey;
import formation.jpa.model.Formateur;
import formation.jpa.model.Views;

/**
 * @author ajc
 *
 */
@RestController
public class EnseignerRestController {

	@Autowired
	private DaoEnseigner daoEnseigner;

	@Autowired
	private DaoPersonne daoPersonne;

	@Autowired
	private DaoMatiere daoMatiere;

	@RequestMapping(value = "/enseigner", method = RequestMethod.GET)
	@JsonView(Views.Enseigner.class)
	public ResponseEntity<List<Enseigner>> list() {
		return new ResponseEntity<>(daoEnseigner.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/enseigner", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Enseigner enseigner, UriComponentsBuilder uCB) {
		Enseigner tmp = (Enseigner) daoEnseigner.find(enseigner.getId());
		if (tmp == null) {
			daoEnseigner.create(enseigner);
			URI uri = uCB.path("/enseigner/{id}")
					.buildAndExpand(
							enseigner.getId().getFormateur().getId() + ":" + enseigner.getId().getMatiere().getCode())
					.toUri();
			HttpHeaders header = new HttpHeaders();
			header.setLocation(uri);
			return new ResponseEntity<>(header, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/enseigner/{id}", method = RequestMethod.GET)
	@JsonView(Views.Enseigner.class)
	public ResponseEntity<Enseigner> find(@PathVariable("id") String id) {
		Integer idFormateur = Integer.valueOf(id.split(":")[0]);
		Integer idMatiere = Integer.valueOf(id.split(":")[1]);

		EnseignerKey idEnseigner = new EnseignerKey((Formateur) daoPersonne.find(idFormateur),
				daoMatiere.find(idMatiere));

		Enseigner tmp = (Enseigner) daoEnseigner.find(idEnseigner);

		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Enseigner>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/enseigner/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		Integer idFormateur = Integer.valueOf(id.split(":")[0]);
		Integer idMatiere = Integer.valueOf(id.split(":")[1]);

		EnseignerKey idEnseigner = new EnseignerKey((Formateur) daoPersonne.find(idFormateur),
				daoMatiere.find(idMatiere));

		Enseigner tmp = (Enseigner) daoEnseigner.find(idEnseigner);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoEnseigner.delete(tmp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/enseigner/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody Enseigner enseigner) {
		Integer idFormateur = Integer.valueOf(id.split(":")[0]);
		Integer idMatiere = Integer.valueOf(id.split(":")[1]);

		EnseignerKey idEnseigner = new EnseignerKey((Formateur) daoPersonne.find(idFormateur),
				daoMatiere.find(idMatiere));

		Enseigner tmp = (Enseigner) daoEnseigner.find(idEnseigner);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoEnseigner.update(enseigner);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
