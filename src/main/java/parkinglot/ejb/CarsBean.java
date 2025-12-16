package parkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import parkinglot.common.CarDto;
import parkinglot.common.CarPhotoDto;
import parkinglot.entities.Car;
import parkinglot.entities.CarPhoto;
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
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);
        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
        entityManager.persist(car);
    }

    public CarDto findById(Long carId) {
        LOG.info("findById");
        Car car = entityManager.find(Car.class, carId);
        return new CarDto(
                car.getId(),
                car.getLicensePlate(),
                car.getParkingSpot(),
                car.getOwner().getUsername()
        );
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        LOG.info("updateCar");
        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);
        User newOwner = entityManager.find(User.class, userId);
        User oldOwner = car.getOwner();
        if (oldOwner != null) {
            oldOwner.getCars().remove(car);
        }
        newOwner.getCars().add(car);
        car.setOwner(newOwner);
    }

    public void deleteCarsByIds(List<Long> carIds) {
        LOG.info("deleteCarsByIds");

        for (Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);

            // Șterge mașina din lista proprietarului
            User owner = car.getOwner();
            if (owner != null) {
                owner.getCars().remove(car);
            }

            // Șterge mașina din baza de date
            entityManager.remove(car);
        }
    }

    // METODĂ PENTRU ADĂUGARE POZĂ
    public void addPhotoToCar(Long carId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar");
        CarPhoto photo = new CarPhoto();
        photo.setFilename(filename);
        photo.setFileType(fileType);
        photo.setFileContent(fileContent);

        Car car = entityManager.find(Car.class, carId);

        // Dacă mașina are deja o poză, o ștergem mai întâi
        if (car.getPhoto() != null) {
            CarPhoto oldPhoto = car.getPhoto();
            car.setPhoto(null);
            entityManager.remove(oldPhoto);
        }

        car.setPhoto(photo);
        photo.setCar(car);
        entityManager.persist(photo);
    }

    // METODĂ PENTRU ȘTERGERE POZĂ
    public void deleteCarPhoto(Long carId) {
        LOG.info("deleteCarPhoto");
        Car car = entityManager.find(Car.class, carId);

        if (car.getPhoto() != null) {
            CarPhoto photo = car.getPhoto();
            car.setPhoto(null);
            entityManager.remove(photo);
        }
    }

    // METODĂ PENTRU GĂSIRE POZĂ
    public CarPhotoDto findPhotoByCarId(Long carId) {
        LOG.info("findPhotoByCarId");
        List<CarPhoto> photos = entityManager
                .createQuery("SELECT p FROM CarPhoto p WHERE p.car.id = :id", CarPhoto.class)
                .setParameter("id", carId)
                .getResultList();

        if (photos.isEmpty()) {
            return null;
        }

        CarPhoto photo = photos.get(0);
        return new CarPhotoDto(photo.getId(), photo.getFilename(), photo.getFileType(), photo.getFileContent());
    }

}