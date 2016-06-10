package br.edu.ifrn.postolegal.persistence;

import br.edu.ifrn.postolegal.domain.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.Instant;
import java.util.Date;

@Named
public class DomainFactory
{
	@Inject
	private UserRepository userRepository;

	@Inject
	private VehicleRepository vehicleRepository;

	@Inject
	private StationProductHistoryRepository stationProductHistoryRepository;

	@Inject
	private ProductRepository productRepository;

	@Inject
	private StationRepository stationRepository;

	@Inject
	private ConsumptionRepository consumptionRepository;

	public User user()
	{
		User u = User.builder()
			.email("user@email.com")
			.name("João da Silva")
			.password("Password")
			.salt("salt")
			.build();
		this.userRepository.save(u);
		return u;
	}

	public Vehicle vehicle()
	{
		Vehicle v = Vehicle.builder()
			.user(this.user())
			.licensePlate("ABC1234")
			.model("Uno")
			.engine("1.0")
			.year(2012)
			.build();
		this.vehicleRepository.save(v);
		return v;
	}

	public StationProductHistory stationProductHistory()
	{
		StationProductHistory h = StationProductHistory.builder().product(this.product())
			.station(this.station()).price(2.7f).date(new Date()).build();
		this.stationProductHistoryRepository.save(h);
		return h;
	}

	public Station station()
	{
		Station s = Station.builder().name("Posto 01").build();
		this.stationRepository.save(s);
		return s;
	}

	public Product product()
	{
		Product p = Product.builder().title("Gasolina").build();
		this.productRepository.save(p);
		return p;
	}

	public Consumption consumption(Vehicle vehicle)
	{
		Consumption consumption = Consumption.builder()
			.vehicle(vehicle)
			.history(this.stationProductHistory())
			.totalPaid(12f)
			.odometer(1.2f)
			.date(Date.from(Instant.now()))
			.build();
		this.consumptionRepository.save(consumption);
		return consumption;
	}
}
