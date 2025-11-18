package parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import parkinglot.common.CarDto;
import parkinglot.entities.Car;
import parkinglot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtos = new ArrayList<>();
        for (Car car : cars) {
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername()
            );
            carDtos.add(carDto);
        }
        return carDtos;
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar");

        // Creează un obiect Car nou
        Car car = new Car();

        // Setează plăcuța de înmatriculare
        car.setLicensePlate(licensePlate);

        // Setează locul de parcare
        car.setParkingSpot(parkingSpot);

        // Găsește utilizatorul (proprietarul) după ID
        User user = entityManager.find(User.class, userId);

        // Adaugă mașina la lista de mașini a utilizatorului
        user.getCars().add(car);

        // Setează proprietarul în obiectul car
        car.setOwner(user);

        // Persistă mașina în baza de date
        entityManager.persist(car);
    }

    public CarDto findById(Long carId) {
        LOG.info("findById");

        // Găsește mașina în baza de date după ID
        Car car = entityManager.find(Car.class, carId);

        // Convertește entitatea Car la CarDto și returnează
        return new CarDto(
                car.getId(),
                car.getLicensePlate(),
                car.getParkingSpot(),
                car.getOwner().getUsername()
        );
    }

    // METODĂ NOUĂ - updateCar
    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");

        // Găsește mașina existentă în baza de date
        Car car = entityManager.find(Car.class, carId);

        // Actualizează plăcuța de înmatriculare
        car.setLicensePlate(licensePlate);

        // Actualizează locul de parcare
        car.setParkingSpot(parkingSpot);

        // Găsește noul proprietar
        User newOwner = entityManager.find(User.class, userId);

        // Obține vechiul proprietar
        User oldOwner = car.getOwner();

        // Dacă există un proprietar vechi, șterge mașina din lista lui
        if (oldOwner != null) {
            oldOwner.getCars().remove(car);
        }

        // Adaugă mașina la lista de mașini a noului proprietar
        newOwner.getCars().add(car);

        // Setează noul proprietar în obiectul car
        car.setOwner(newOwner);

        // Nu trebuie să faci persist() pentru că entitatea este deja managed (gestionată de EntityManager)
    }
}