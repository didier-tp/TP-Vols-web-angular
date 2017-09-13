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

import vol.metier.dao.ReservationDao;
import vol.metier.model.Reservation;
import vol.metier.model.Views;

/**
 * @author ajc
 *
 */
@RestController
public class ReservationRestController {

	@Autowired
	private ReservationDao daoReservation;
	@JsonView(Views.Reservation.class)
	@RequestMapping(value = "/reservation", method = RequestMethod.GET)
	public ResponseEntity<List<Reservation>> list() {
		return new ResponseEntity<>(daoReservation.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/reservation", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Reservation reservation, UriComponentsBuilder uCB) {
		daoReservation.create(reservation);
		URI uri = uCB.path("/reservation/{id}").buildAndExpand(reservation.getId()).toUri();
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uri);
		return new ResponseEntity<>(header, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/reservation/{id}", method = RequestMethod.GET)
	@JsonView(Views.Reservation.class)
	public ResponseEntity<Reservation> find(@PathVariable("id") Long id) {
		Reservation tmp = daoReservation.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(tmp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/reservation/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Reservation tmp = daoReservation.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoReservation.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/reservation/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
		Reservation tmp = daoReservation.find(id);
		if (tmp == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			reservation.setId(id);
			reservation.setVersion(tmp.getVersion());
			daoReservation.update(reservation);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

}
