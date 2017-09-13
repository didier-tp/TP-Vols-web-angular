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
import vol.metier.dao.VilleAeroportDao;
import vol.metier.dao.VilleDao;
import vol.metier.model.Escale;
import vol.metier.model.Views;
import vol.metier.model.VilleAeroport;
import vol.metier.model.VilleAeroportId;



@RestController
public class VilleAeroportRestController {


	@Autowired
	private VilleAeroportDao daoVilleAeroport;

	@Autowired
	private VilleDao daoVille;

	@Autowired
	private AeroportDao daoAeroport;

	@RequestMapping(value = "/villeaeroport", method = RequestMethod.GET)
	@JsonView(Views.VilleAeroport.class)
	public ResponseEntity<List<VilleAeroport>> list() {
		return new ResponseEntity<>(daoVilleAeroport.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/villeaeroport", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody VilleAeroport villeAeroport, UriComponentsBuilder uCB) {

		daoVilleAeroport.create(villeAeroport);
		URI uri = uCB.path("/villeaeroport/{id}").buildAndExpand(villeAeroport.getAeroport().getId() + ":" + villeAeroport.getVille().getId())
				.toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);

	}



	@RequestMapping(value = "/villeaeroport/{id}", method = RequestMethod.GET)
	@JsonView(Views.VilleAeroport.class)
	public ResponseEntity<VilleAeroport> find(@PathVariable("id") String id) {
		Long idVille = Long.valueOf(id.split(":")[0]);
		Long idAeroport = Long.valueOf(id.split(":")[1]);

		VilleAeroportId idVilleAeroport = new VilleAeroportId(idVille, idAeroport);

		VilleAeroport tmp = (VilleAeroport) daoVilleAeroport.find(idVilleAeroport);

		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<VilleAeroport>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/villeaeroport/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		Long idVille = Long.valueOf(id.split(":")[0]);
		Long idAeroport = Long.valueOf(id.split(":")[1]);

		VilleAeroportId idVilleAeroport = new VilleAeroportId(idVille, idAeroport);

		VilleAeroport tmp = (VilleAeroport) daoVilleAeroport.find(idVilleAeroport);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoVilleAeroport.delete(tmp);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/villeaeroport/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody VilleAeroport villeAeroport) {
		Long idVille = Long.valueOf(id.split(":")[0]);
		Long idAeroport = Long.valueOf(id.split(":")[1]);

		VilleAeroportId idVilleAeroport = new VilleAeroportId(idVille, idAeroport);

		VilleAeroport tmp = (VilleAeroport) daoVilleAeroport.find(idVilleAeroport);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoVilleAeroport.update(villeAeroport);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
